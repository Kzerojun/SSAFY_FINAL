<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String root = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign Up</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	background-color: #F3F4F9;
	font-family: Verdana, Tahoma, sans-serif;
}

.form-container {
	min-height: 100vh;
}

.form-inner-container {
	background-color: white;
	border-radius: 0.5rem;
	box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}
</style>
</head>
<body>
	<!-- 헤더 영역 -->
	<header class="bg-light py-3">
		<nav
			class="container d-flex justify-content-between align-items-center">
			<a href="<%=root%>/index.jsp" class="navbar-brand fw-bold">EnjoyTrip</a>
			<ul class="nav">
				<li class="nav-item"><a href="<%=root%>/user?act=mvSignUp"
					class="nav-link" id="signup-link">Sign Up</a></li>
				<li class="nav-item"><a href="#" class="nav-link"
					id="login-link" data-bs-toggle="modal" data-bs-target="#loginModal">LogIn</a>
				</li>
				<li class="nav-item" id="mypage-link"><a
					href="<%=root%>/user/mypage" class="nav-link">Mypage</a></li>
				<li class="nav-item" id="logout-link"><a href="#"
					class="nav-link">LogOut</a></li>
			</ul>
		</nav>
	</header>

	<!--회원가입 form -->
	<div
		class="container d-flex justify-content-center align-items-center form-container">
		<div class="form-inner-container p-5 col-lg-6 col-md-8 col-12">
			<h3 class="text-center mb-4 text-danger fw-bold">Sign Up</h3>
			<form action="<%=root%>/user" method="post" id="form-join">
				<input type="hidden" name="act" value="signup">
				<div class="mb-3">
					<label for="userName" class="form-label text-secondary">User
						Name</label> <input type="text" class="form-control" id="userName"
						name="userName" placeholder="User Name" required>
				</div>

				<div class="mb-3">
					<label for="userEmail" class="form-label text-secondary">Email</label>
					<input type="email" class="form-control" id="userEmail"
						name="userEmail" placeholder="user@example.com" required>
				</div>

				<div class="mb-3">
					<label for="userPwd" class="form-label text-secondary">Password</label>
					<input type="password" class="form-control" id="userPwd"
						name="userPwd" placeholder="소문자, 특수기호를 사용해 입력해주세요." required>
				</div>

				<div class="mb-3">
					<label for="confirmPassword" class="form-label text-secondary">비밀번호
						확인</label> <input type="password" class="form-control"
						id="confirmPassword" name="confirmPassword"
						placeholder="다시한번 입력해주세요." required>
				</div>

				<div id="result-view" class="mb-3"></div>

				<div class="text-center mt-5">
					<button type="button" id="btn-join" class="btn btn-danger">SignUp</button>
				</div>
			</form>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<script>
      let isUseId = false;
      let input = document.querySelector("#userEmail");
      let resultDiv = document.querySelector("#result-view");
      
      if (input) {
        input.addEventListener("keyup", function () {
          let checkid = input.value;
          let len = checkid.length;
          if (len < 4 || len > 16) {
            isUseId = false;
            resultDiv.setAttribute("class", "mb-3 fw-bold text-dark");
            resultDiv.innerHTML = "이메일은 4자이상 16자이하입니다.";
          } else {
            let url = "<%=root%>/user?act=idcheck&checkid=" + checkid;
            fetch(url)
              .then((response) => response.text())
              .then(data => resultView(data))
          }
        });
      }

      function resultView(data) {
        let split = data.split(',');
        let checkid = split[0];
        let cnt = split[1];
        if (cnt == 0) {
          isUseId = true;
          resultDiv.setAttribute("class", "mb-3 text-primary");
          resultDiv.innerHTML = "<span class='fw-bold'>" + checkid + "</span>은 사용할 수 있습니다.";
        } else {
          isUseId = false;
          resultDiv.setAttribute("class", "mb-3 text-warning");
          resultDiv.innerHTML = "<span class='fw-bold'>" + checkid + "</span>은 사용할 수 없습니다.";
        }
      }

      document.querySelector("#btn-join").addEventListener("click", function () {
    	    if (!document.querySelector("#userName").value) {
    	        alert("이름 입력!!");
    	        return;
    	    } else if (!document.querySelector("#userEmail").value) {
    	        alert("이메일 입력!!");
    	        return;
    	    } else if (!isUseId) {
    	        alert("이메일 중복 확인!!");
    	        return;
    	    } else if (!document.querySelector("#userPwd").value) {
    	        alert("비밀번호 입력!!");
    	        return;
    	    } else if (
    	        document.querySelector("#userPwd").value != document.querySelector("#confirmPassword").value
    	    ) {
    	        alert("비밀번호 확인!!");
    	        return;
    	    } else {
    	        let form = document.querySelector("#form-join");
    	        form.submit();
    	    }
    	});
    </script>
</body>
</html>