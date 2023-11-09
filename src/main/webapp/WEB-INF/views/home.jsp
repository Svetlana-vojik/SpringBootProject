<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Домашняя страница</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<nav class="navbar navbar-light">
    <div class="container-fluid">
        <form class="form-inline">
            <a href="${contextPath}/home">
                <button class="btn btn-outline-success" type="button">Главная</button>
            </a>
        </form>
        <form class="form-inline my-2 my-lg-0">
            <a href="${contextPath}/search">
                <button class="btn btn-outline-success m-1" type="button">Поиск</button>
            </a>
            <a href="${contextPath}/userPage">
                <button class="btn btn-outline-success m-1" type="button">Кабинет</button>
            </a>
            <a href="${contextPath}/cart/open">
                <button class="btn btn-outline-success m-1" type="button">Корзина</button>
            </a>
        </form>
    </div>
</nav>
<h1 style="text-align: center">Каталог</h1>
<div class="container">
    <c:if test="${not empty categories}">
        <div class="row d-flex justify-content-center">
            <c:forEach items="${categories}" var="category">
                <div class="card w-25 m-2 text-center" type="category">
                    <a href="${contextPath}/category/${category.getId()}"/>
                    <div class="card-body">
                        <a href="${contextPath}/category/${category.getId()}">
                            <img class="card-img" style="width:160px;height:160px"
                                 src="${category.getImagePath()}"
                                 alt=${category.getImagePath()}></a>
                        <div>
                            <a href="${contextPath}/category/${category.getId()}"
                               class="btn">${category.getName()}</a>
                        </div>
                    </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>
<br>
<form method="POST" action="/home/csv/import" enctype="multipart/form-data"
      class="file-import">
    <label for="file-upload" class="custom-file-upload"
           style="padding: 15px;margin: 0px 0px 15px 15px;border: 1px solid #ccc">
        <input id="file-upload" name="file" type="file" class="title" accept=".csv">
        <button type="submit" class="btn-outline-success">Импортировать категории</button>
    </label>
</form>
<form method="POST" action="/home/csv/export">
    <button type="submit" class="btn-outline-success" style="margin: 15px">Экспортировать категории</button>
</form>
</body>
</html>