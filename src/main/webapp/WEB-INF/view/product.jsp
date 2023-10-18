<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${product.getName()}</title>
    <meta charset="utf-8">
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
            <a href=${contextPath}/cart/open>
                <button class="btn btn-outline-success m-1" type="button">Корзина</button>
            </a>
        </form>
    </div>
</nav>
<h2 style="text-align: center" >${product.getName()}</h2>
<p></p>
<div class="card w-50 m-1" style="text-align: center" type="product">
    <c:set var="product" value="${product}" />
    <div class="card-body">
        <div class="row">
            <div class="col m-1"><img
                    class="card-img"
                    style="width:350px;height:350px"
                    src="${contextPath}/${product.getImagePath()}"
                    alt=${product.getImagePath()}></div>
            <div class="col m-1" style="text-align: left"><p></p>
                <h2>${product.getName()}</h2>
                <h3 style="color:green">${product.getDescription()}</h3>
                <p>Цена: ${product.getPrice()}</p></div>
            <div class="col m-1" style="text-align: center"><p></p></div>
        </div>
        <div class="row" style="text-align: end">
            <div class="col m-1" style="text-align: center"><p></p></div>
            <div class="col m-1" style="text-align: center"><p></p></div>
            <a href="${contextPath}/cart/add?product_id=${product.getId()}">
                <button type="button" class="btn btn-success">Купить</button>
            </a>
        </div>
    </div>
</div>
</body>
</html>