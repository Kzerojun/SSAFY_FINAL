<%@page import="com.enjoytrip.user.model.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

String root = request.getContextPath();

UserDto user =(UserDto)session.getAttribute("userInfo");
System.out.println(user);
%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enjoy Trip</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .profile-info {
            margin-bottom: 20px;
        }

        .profile-info label {
            font-weight: bold;
            color: #495057;
        }

        .profile-info .form-control {
            background-color: #F9F9F9;
            border: 1px solid #CED4DA;
        }

        .profile-actions {
            display: flex;
            gap: 10px;
            justify-content: center;
        }

        .profile-card {
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 100px;
        }
    </style>
</head>

<body>
     <!-- 헤더 영역 -->
     <header class="bg-light py-3">
        <nav class="container d-flex justify-content-between align-items-center">
            <a href="<%=root%>/index.jsp" class="navbar-brand fw-bold">EnjoyTrip</a>
            <ul class="nav">
                <li class="nav-item" id="mypage-link">
                    <a href="mypage.html" class="nav-link">Mypage</a>
                </li>
                <li class="nav-item" id="logout-link">
                    <a href="<%=root%>/user?act=logout" class="nav-link">LogOut</a>
                </li>
            </ul>
        </nav>
    </header>
    <!--마이페이지 frame-->
    <div class="container mt-5">
        <div class="container vh-100 d-flex justify-content-center align-items-center">
            <div class="col-lg-6 col-md-8 col-12">
                <div class="card profile-card">
                    <h2 class="text-center mb-4 fw-bold">My Page</h2>
                    <!-- 사용자 정보 표시 -->
                    <form id="editProfileForm">
                        <div class="mb-3">
                            <label for="editName" class="form-label">Name</label>
                            <input type="text" class="form-control" id="editName" value="<%=user.getUserName()%>">
                        </div>
                        <div class="mb-3">
                            <label for="editEmail" class="form-label">Email</label>
                            <input type="email" class="form-control" id="editEmail" value="<%=user.getUserEmail()%>">
                        </div>
                        <div class="mb-3">
                            <label for="editPhone" class="form-label">Password</label>
                            <input type="tel" class="form-control" id="editPhone" value="<%=user.getUserPwd()%>">
                        </div>
                        <div class="d-flex justify-content-center mb-3">
                            <div class="me-4"><button type="submit" class="btn btn-primary me-2">저장</button></div>
                            <div class="me-4"><button type="button" class="btn btn-danger"><a href="<%=root%>/user?act=delete">삭제</a></button></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>