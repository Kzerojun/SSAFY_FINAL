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
        .attraction-item {
            font-size: 1.2em;
            margin-bottom: 10px;
        }
        .order-number {
            font-weight: bold;
            color: #007bff; /* 강조 색상 */
            margin-right: 5px;
        }
    </style>
</head>
<body>
    <main class="container my-5">
        <div class="search-section mt-5 text-center">
            <h3>당신의 여정의 최적의 경로는...</h3>
            <div id="recommended-route" class="mt-4">
                <c:forEach var="attraction" items="${attractions}" varStatus="status">
                    <div class="attraction-item">
                        <span class="order-number">${status.index + 1}.</span> <!-- 순서 강조 -->
                        ${attraction.title}
                    </div>
                </c:forEach>
            </div>
        </div>
    </main>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
