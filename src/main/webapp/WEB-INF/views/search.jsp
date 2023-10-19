<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Поиск</title>
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
            <a href="${contextPath}/userPage">
                <button class="btn btn-outline-success m-1" type="button">Кабинет</button>
            </a>
            <a href="${contextPath}/cart/open">
                <button class="btn btn-outline-success m-1" type="button">Корзина</button>
            </a>
        </form>
    </div>
</nav>
<div class="container">
    <form class="input-group mb-3" method="post" action="${contextPath}/search" style="text-align: center">
        <label for="searchString"></label>
        <input type="search" name="searchString" id="searchString" class="form-control"
               placeholder=" Поиск ">
        <button type="submit" class="btn btn-success">Найти</button>
    </form>
</div>
<p></p>
<c:if test="${not empty info}">
    <p style="text-align: center" class="text-danger">${info}</p>
    <p></p>
</c:if>
</div>
<br>
<div class="container">
    <div class="row"><p></p>
        <div class="col"><b>Фильтр</b><br><br>
            <form method="post" action="${contextPath}/search">
                <div class="dropdown">
                    <label for="categories"></label>
                    <select class="col-sm-7" id="categories" name="categories">
                        <option selected>Категория</option>
                        <c:forEach items="${categories}" var="category">
                            <option style="text-align: center"
                                    value="${category.getName()}">${category.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
                <p></p>
                <form>
                    <div class="row">
                        <div class="col">
                            <input type="text" id="priceFrom" placeholder="Цена от" name="priceFrom">
                        </div>
                        <div class="col">
                            <input type="text" id="priceTo" placeholder="Цена до" name="priceTo">
                        </div>
                    </div>
                </form>
                <p></p>
                <div>
                    <button class="btn btn-success" style="text-align:center">Применить</button>
                </div>
            </form>
        </div>
        <div class="col" style="text-align: center"><b>Результаты поиска:</b><br><br>
            <c:if test="${not empty message}">
                <p style="text-align: center" class="text">${message}</p>
                <p></p>
            </c:if>
            <c:forEach items="${products}" var="product">
                <div class="card w-80 m-1" type="product">
                    <div class="card-body">
                        <div class="row">
                            <div class="col m-1"><a
                                    href="${contextPath}/products/${product.getId()}"><img
                                    class="card-img"
                                    style="width:100px;height:100px"
                                    src="${product.getImagePath()}"
                                    alt=${product.getImagePath()}></a></div>
                            <div class="col m-1" style="text-align: left">
                                <a href="${contextPath}/products/${product.getId()}">
                                    <p>${product.getName()}</p>
                                </a>
                                <p>${product.getDescription()}</p>
                                <p>Цена: ${product.getPrice()}</p></div>
                            <div class="col m-1"><br>
                                <a href="${contextPath}/products/${product.getId()}">
                                    <button class="btn btn-outline-success m-2" style="text-align: center"
                                            type="button">
                                        Смотреть
                                    </button>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach><br>
        </div>
    </div>
</div>
<ul class="pagination justify-content-end" style="margin:20px 0">
    <div class="dropdown">
        <button type="button" class="btn btn-outline-success m-1" data-toggle="dropdown">
            Количество
        </button>
        <div class="dropdown-menu" style="color: black">
            <a class="dropdown-item" href="#" style="color: black">5</a>
            <a class="dropdown-item" href="#" style="color: black">10</a>
            <a class="dropdown-item" href="#" style="color: black">15</a>
        </div>
    </div>
    <li class="page-item"><a class="btn btn-outline-success m-1" href="#"><</a></li>
    <li class="page-item"><a class="btn btn-outline-success m-1" href="#">></a></li>
</ul>
</body>
</html>