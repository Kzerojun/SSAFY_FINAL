<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page import="com.enjoytrip.user.model.dto.UserDto"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<%
UserDto userDto = (UserDto)session.getAttribute("userInfo");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Enjoy Trip</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
img {
	height: 300px;
}
</style>
</head>
<body>

	<!-- 헤더 영역 -->
	<header class="bg-light py-3">
		<nav
			class="container d-flex justify-content-between align-items-center">
			<a href="${root}/index.jsp" class="navbar-brand fw-bold">EnjoyTrip</a>
			<ul class="nav">

				<c:choose>
					<c:when test="${empty userInfo}">
						<li class="nav-item"><a href="${root}/user?act=mvSignUp"
							class="nav-link" id="signup-link">Sign UP</a></li>
						<li class="nav-item"><a href="#" class="nav-link"
							id="login-link" data-bs-toggle="modal" data-bs-target="#loginModal">LogIn</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="nav-item" id="logout-link"><a
							href="${root}/user?act=logout" class="nav-link">LogOut</a></li>
						<li class="nav-item" id="mypage-link"><a
							href="${root}/user?act=mvMypage" class="nav-link">Mypage</a></li>
					</c:otherwise>
				</c:choose>

				<li class="nav-item"><a href="${root}/trip?act=view"
					class="nav-link">Search</a></li>
			</ul>
		</nav>
	</header>
	<main class="container my-5">
		<div class="search-section mt-5 text-center">
			<h3>관광지 정보</h3>
			<form id="search-form"
				class="d-flex flex-wrap gap-2 my-3 justify-content-center">
				<select id="search-area" class="form-select w-auto" name="sido">
					<option value="0" selected>시/도 지역선택</option>
				</select> <select id="search-sigungu" class="form-select w-auto" name="gugun">
					<option value="0" selected>구/군 지역선택</option>
				</select> <select id="search-content-id" class="form-select w-auto"
					name="content">
					<option value="0" selected>관광지 유형</option>
					<option value="12">관광지</option>
					<option value="14">문화시설</option>
					<option value="15">축제공연행사</option>
					<option value="25">여행코스</option>
					<option value="28">레포츠</option>
					<option value="32">숙박</option>
					<option value="38">쇼핑</option>
					<option value="39">음식점</option>
				</select> <input id="search-keyword" class="form-control w-auto"
					type="search" name="keyword" placeholder="검색어" aria-label="검색어" />
				<button id="btn-search" class="btn btn-outline-success"
					type="button">검색</button>
			</form>
		</div>

		<div id="map" style="width: 100%; height: 500px"></div>
	</main>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1cbc901d3786d37bae1d7e89a64a9427"></script>
	<script>
        const serviceKey = "Mub0bm4epPcrhuQFwy%2F1s31j04EOcTy1JGaOAQM1hmRrFOpjrjsm4vdNu5wCojATTsagW%2FlLpZsE3zxyu9YJ4Q%3D%3D";
        const root = "${root}";

        // index page 로딩 후 전국의 시도 설정.
        let areaUrl =
            "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
            serviceKey +
            "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

        fetch(areaUrl, { method: "GET" })
            .then((response) => response.json())
            .then((data) => makeOption(data));

        function makeOption(data) {
            let areas = data.response.body.items.item;
            // 가져온 데이터로 지역 옵션 추가
            let sel = document.getElementById("search-area");
            areas.forEach((area) => {
            let opt = document.createElement("option");
            opt.setAttribute("value", area.code);
            opt.appendChild(document.createTextNode(area.name));

            sel.appendChild(opt);
            });
        }

        // 전국의 시도가 설정되면 이어서 시군구 설정
        document.querySelector("#search-area").addEventListener("change", function() {

            let sigunguUrl = 
            "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
            serviceKey +
            "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&areaCode=";

            let regcode = this.value;

            sigunguUrl += regcode;

            fetch(sigunguUrl, { method: "GET" })
            .then((response) => response.json())
            .then((data) => makeOption(data));

            function makeOption(data) {
                let sigungus = data.response.body.items.item;
                // 가져온 데이터로 시군구 옵션 추가
                let sel = document.getElementById("search-sigungu");
                // 일단 비우고	
                sel.replaceChildren();
                // 이후 추가
                sigungus.forEach((sigungu) => {
                let opt = document.createElement("option");
                opt.setAttribute("value", sigungu.code);
                opt.appendChild(document.createTextNode(sigungu.name));

                sel.appendChild(opt);
            });
        }});

        document.getElementById("btn-search").addEventListener("click", function() {
            const sido = document.querySelector("[name='sido']").value;
            const gugun = document.querySelector("[name='gugun']").value;
            const content = document.querySelector("[name='content']").value;

            fetch(`${root}/trip?act=search`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ sido, gugun, content })
            })
            .then(response => response.json())
            .then(data => makeList(data))
            .catch(error => console.error('Error:', error));
        });

        let positions = [];

        function makeList(data) {
            positions = data.map(area => ({
                title: area.title,
                latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
                image: area.firstImage,
                addr: area.addr1 + " " + area.addr2
            }));
            displayMarker();
        }

        const mapContainer = document.getElementById("map");
        const mapOption = { center: new kakao.maps.LatLng(37.500613, 127.036431), level: 7 };
        const map = new kakao.maps.Map(mapContainer, mapOption);

        function displayMarker() {
            positions.forEach(position => {
                const marker = new kakao.maps.Marker({
                    map,
                    position: position.latlng,
                    title: position.title,
                });

                const infowindow = new kakao.maps.InfoWindow({
                    content: `<div style="text-align: center; padding: 10px;"><strong>${position.title}</strong><div>${position.addr}</div></div>`
                });

                kakao.maps.event.addListener(marker, 'click', () => {
                    infowindow.open(map, marker);
                });
            });

            if (positions.length > 0) {
                map.setCenter(positions[0].latlng);
            }
        }
    </script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<!-- 로그인 모달 bootstrap 카드 부분-->
	<div class="modal fade" id="loginModal" tabindex="-1"
		aria-labelledby="loginModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title fw-bold" id="loginModalLabel">LogIn</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="card">
						<div class="card-body">
							<!-- 로그인 폼 -->
							<form id="login-form" action="${root}/user?act=login"
								method="post">
								<!-- 아이디 입력창 -->
								<div class="mb-3">
									<label for="login" class="form-label">LogIn</label> <input
										type="text" class="form-control" id="login" name="email"
										required>
								</div>
								<!-- 비밀번호 입력창 -->
								<div class="mb-3">
									<label for="password" class="form-label">Password</label> <input
										type="password" class="form-control" id="password"
										name="password" required>
								</div>
								<div class="passwordhint text-center mt-5">
									<a href="${root}/assets/passwordHint.jsp"
										class="text-primary text-decoration-none">비밀번호 찾기</a>
								</div>
								<div>
									<button type="submit" class="btn btn-danger w-100">LogIn</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>