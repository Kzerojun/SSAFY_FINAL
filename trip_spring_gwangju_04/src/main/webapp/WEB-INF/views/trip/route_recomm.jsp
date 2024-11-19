<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enjoy Trip</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        img { height: 300px; }
    </style>
</head>
<body>
    <main class="container my-5">
        <div class="search-section mt-5 text-center">
		    <h3>여정에 추가할 관광지를 입력하세요.</h3>
            <p class="alert alert-info">첫 번째로 선택된 관광지가 출발점이 됩니다.</p>
		    <form id="search-form" class="d-flex flex-wrap gap-2 my-3 justify-content-center" action="${root}/trip?act=recomm" method="POST">
		        <!-- 시/도 선택 목록 -->
		        <select id="search-area" class="form-select w-auto" name="sido">
		            <option value="0" selected>시/도 지역선택</option>
		        </select>
		        <!-- 구/군 선택 목록 -->
		        <select id="search-sigungu" class="form-select w-auto" name="gugun">
		            <option value="0" selected>구/군 지역선택</option>
		        </select>
		        <!-- 관광지 유형 선택 목록 -->
		        <select id="search-content-id" class="form-select w-auto" name="content">
		            <option value="0" selected>관광지 유형</option>
		            <option value="12">관광지</option>
		            <option value="14">문화시설</option>
		            <option value="15">축제공연행사</option>
		            <option value="25">여행코스</option>
		            <option value="28">레포츠</option>
		            <option value="32">숙박</option>
		            <option value="38">쇼핑</option>
		            <option value="39">음식점</option>
		        </select>
		        <!-- 관광지 선택 목록 -->
		        <select id="search-attraction" class="form-select w-auto" name="attraction">
		            <option value="0" selected>관광지 선택</option>
		        </select>
		        <button id="btn-add" class="btn btn-outline-secondary" type="button">추가</button>
		        <button id="btn-submit" class="btn btn-outline-success" type="button">입력 완료</button>
		    </form>
		
		    <div id="selected-attractions" class="mt-3">
		        <h5>선택된 관광지</h5>
		        <ul id="attraction-list" class="list-group"></ul>
		    </div>
		</div>


        <div id="map" style="width: 100%; height: 500px"></div>
    </main>
    
    <script>
        const serviceKey = "Mub0bm4epPcrhuQFwy%2F1s31j04EOcTy1JGaOAQM1hmRrFOpjrjsm4vdNu5wCojATTsagW%2FlLpZsE3zxyu9YJ4Q%3D%3D";
        const root = "${root}";

        // 시도 목록 불러오기
        let areaUrl =
            "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
            serviceKey +
            "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

        fetch(areaUrl)
            .then(response => response.json())
            .then(data => makeOption(data));

        function makeOption(data) {
            let areas = data.response.body.items.item;
            let sel = document.getElementById("search-area");
            areas.forEach(area => {
                let opt = document.createElement("option");
                opt.setAttribute("value", area.code);
                opt.appendChild(document.createTextNode(area.name));
                sel.appendChild(opt);
            });
        }

        // 구군 목록 불러오기
        document.querySelector("#search-area").addEventListener("change", function() {
            let sigunguUrl = 
                "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
                serviceKey +
                "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&areaCode=" + this.value;

            fetch(sigunguUrl)
            .then(response => response.json())
            .then(data => makeOption(data));

            function makeOption(data) {
                let sigungus = data.response.body.items.item;
                let sel = document.getElementById("search-sigungu");
                sel.replaceChildren();
                sigungus.forEach(sigungu => {
                    let opt = document.createElement("option");
                    opt.setAttribute("value", sigungu.code);
                    opt.appendChild(document.createTextNode(sigungu.name));
                    sel.appendChild(opt);
                });
            }
        });

        // 관광지 목록 불러오기
        document.querySelector("#search-content-id").addEventListener("change", function() {
            const sido = document.querySelector("[name='sido']").value;
            const gugun = document.querySelector("[name='gugun']").value;
            const content = document.querySelector("[name='content']").value;

            fetch(`${root}/trip?act=search`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ sido, gugun, content })
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                makeOption(data);
            })
            .catch(error => console.error('Error fetching data:', error));

            function makeOption(attractions) {
                let sel = document.getElementById("search-attraction");
                sel.replaceChildren();
                attractions.forEach(attraction => {
                    let opt = document.createElement("option");
                    opt.setAttribute("value", attraction.no);
                    opt.setAttribute("data-latitude", attraction.latitude); // 위도 저장
                    opt.setAttribute("data-longitude", attraction.longitude); // 경도 저장
                    opt.appendChild(document.createTextNode(attraction.title));
                    sel.appendChild(opt);
                });
            }
        });

        // 선택된 관광지 추가
        document.getElementById("btn-add").addEventListener("click", function() {
            const attractionSelect = document.getElementById("search-attraction");
            const selectedOption = attractionSelect.options[attractionSelect.selectedIndex];
            if (selectedOption.value !== "0") {
                const selectedAttraction = {
                    no: selectedOption.value,
                    title: selectedOption.text,
                    latitude: selectedOption.dataset.latitude,  // 위도
                    longitude: selectedOption.dataset.longitude  // 경도
                };
                addAttractionToList(selectedAttraction);
            }
        });
        
     	// 최종적으로 선택된 관광지 목록 서버에 전송
        document.getElementById("btn-submit").addEventListener("click", function() {
            const selectedAttractions = Array.from(document.querySelectorAll("#attraction-list li"))
                .map(li => ({
                    no: li.dataset.attractionNo,
                    title: li.dataset.attractionTitle,
                    latitude: li.dataset.attractionLatitude,
                    longitude: li.dataset.attractionLongitude
                }));

            // 전송할 데이터 콘솔에 출력
            console.log("전송할 데이터:", JSON.stringify(selectedAttractions));

            // JSON으로 변환하여 hidden input에 추가
            const hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'selectedAttractions';
            hiddenInput.value = JSON.stringify(selectedAttractions);
            document.getElementById("search-form").appendChild(hiddenInput);

            // 폼 제출
            document.getElementById("search-form").submit();
        });

        function addAttractionToList(attraction) {
            const attractionList = document.getElementById("attraction-list");
            const listItem = document.createElement("li");
            listItem.className = "list-group-item d-flex justify-content-between align-items-center";
            listItem.textContent = attraction.title;
            listItem.dataset.attractionNo = attraction.no; // 번호를 데이터 속성에 저장
            listItem.dataset.attractionTitle = attraction.title; // 제목을 데이터 속성에 저장
            listItem.dataset.attractionLatitude = attraction.latitude; // 위도를 데이터 속성에 저장
            listItem.dataset.attractionLongitude = attraction.longitude; // 경도를 데이터 속성에 저장

            const removeButton = document.createElement("button");
            removeButton.className = "btn btn-danger btn-sm";
            removeButton.textContent = "삭제";
            removeButton.onclick = function() {
                attractionList.removeChild(listItem);
            };

            listItem.appendChild(removeButton);
            attractionList.appendChild(listItem);
        }

        // 폼 제출 시 데이터 준비
        function prepareData() {
		    const selectedAttractions = Array.from(document.querySelectorAll("#attraction-list li"))
		        .map(li => ({
		            no: li.dataset.attractionNo,
		            title: li.dataset.attractionTitle,
		            latitude: li.dataset.attractionLatitude,
		            longitude: li.dataset.attractionLongitude
		        }));
		
		    // JSON으로 변환하여 hidden input에 추가
		    const hiddenInput = document.createElement('input');
		    hiddenInput.type = 'hidden';
		    hiddenInput.name = 'selectedAttractions';
		    hiddenInput.value = JSON.stringify(selectedAttractions);
		    document.getElementById("search-form").appendChild(hiddenInput);
		
		    return true; // 폼 제출을 계속 진행
		}

    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
