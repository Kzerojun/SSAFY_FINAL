<%@page import="com.enjoytrip.user.model.dto.UserDto"%>
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
<script src="./assets/js/key.js"></script>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
img {
	height: 300px;
}

.passwordhint a:hover {
	color: red !important;
	text-decoration: underline !important;
}
</style>
</head>
<body>

	<!-- 헤더 영역 -->
	<header class="bg-light py-3">
		<nav
			class="container d-flex justify-content-between align-items-center">
			<a href="${root}/" class="navbar-brand fw-bold">EnjoyTrip</a>
			<ul class="nav">
								
			<c:if test="${userInfo == null}">
				<li class="nav-item"><a href="${root}/user?act=mvSignUp"
					class="nav-link" id="signup-link">Sign UP</a></li>
				<li class="nav-item"><a href="#" class="nav-link"
					id="login-link" data-bs-toggle="modal" data-bs-target="#loginModal">LogIn</a>
				</li>
			</c:if>
			<c:if test="${userInfo ne null}">
				<li class="nav-item" id="logout-link"><a href="${root}/user?act=logout"
					class="nav-link">LogOut</a></li>
				<li class="nav-item" id="mypage-link"><a
					href="${root}/user?act=mvMypage" class="nav-link">Mypage</a></li>
			</c:if>

				<li class="nav-item"><a href="${root}/trip/view"
					class="nav-link">Search</a></li>
					
				<li class="nav-item"><a href="${root}/trip/mvrecomm"
					class="nav-link">Find</a></li>
			</ul>
		</nav>
	</header>

	<!-- 메인 콘텐츠 영역 -->
	<main class="container my-5">
		<!-- 지역별 관광 조회 결과 페이지를 포함하는 영역 -->
		<!--여행 리스트 영역-->
		<div id="tourist-results-container">
			<!-- 카드 리스트 -->
			<div class="row g-4 text-center fw-bold">
				<!-- 카드 1 -->
				<div class="col-12 col-sm-6 col-md-4 col-lg-3">
					<a href="assets/tourist_detail.html" class="text-decoration-none">
						<div class="card">
							<img src="assets/img/tour_img_01.jpg" class="card-img-top"
								alt="여행 사진 1">
							<div class="card-body">
								<h5 class="card-title">여행지 제목 1</h5>
							</div>
						</div>
					</a>
				</div>

				<!-- 카드 2 -->
				<div class="col-12 col-sm-6 col-md-4 col-lg-3">
					<a href="assets/tourist_detail.html" class="text-decoration-none">
						<div class="card">
							<img src="assets/img/tour_img_02.jpg" class="card-img-top"
								alt="여행 사진 2">
							<div class="card-body">
								<h5 class="card-title">여행지 제목 2</h5>
							</div>
						</div>
					</a>
				</div>

				<!-- 카드 3 -->
				<div class="col-12 col-sm-6 col-md-4 col-lg-3">
					<a href="assets/tourist_detail.html" class="text-decoration-none">
						<div class="card">
							<img src="assets/img/tour_img_03.jpg" class="card-img-top"
								alt="여행 사진 3">
							<div class="card-body">
								<h5 class="card-title">여행지 제목 3</h5>
							</div>
						</div>
					</a>
				</div>

				<!-- 카드 4 -->
				<div class="col-12 col-sm-6 col-md-4 col-lg-3">
					<a href="assets/tourist_detail.html" class="text-decoration-none">
						<div class="card">
							<img src="assets/img/tour_img_04.jpg" class="card-img-top"
								alt="여행 사진 4">
							<div class="card-body">
								<h5 class="card-title">여행지 제목 4</h5>
							</div>
						</div>
					</a>
				</div>

				<!-- 추가 카드들을 여기에 추가하면 됩니다. -->
				<!-- 결과 리스트는 나중에 JavaScript로 동적으로 추가 -->
			</div>
		</div>
	</main>

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
							<form id="login-form" action="${root}/user/login"
								method="post">
								<!-- 아이디 입력창 -->
								<div class="mb-3">
									<label for="login" class="form-label">LogIn</label> <input
										type="text" class="form-control" id="login" name="userEmail"
										required>
								</div>
								<!-- 비밀번호 입력창 -->
								<div class="mb-3">
									<label for="password" class="form-label">Password</label> <input
										type="password" class="form-control" id="userPwd"
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

	<!-- Bootstrap JS (팝오버, 툴팁 등 부트스트랩 기능 사용 시 필요) -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>