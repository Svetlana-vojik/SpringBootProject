<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<body style="background-color:lightyellow">
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
            <sec:authorize access="isAuthenticated()">
            <a href="/logout">
                <button class="btn btn-outline-success m-1" type="button">Выйти</button></a>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                <a href="/login">
                    <button class="btn btn-outline-success m-1" type="button">Войти</button></a>
                    </sec:authorize>
        </form>
    </div>
</nav>
<div class="container">
    <form class="input-group mb-3" method="post" action="/search/applyFilter" style="text-align: center">
        <label for="searchKey"></label>
        <input type="text" name="searchKey" id="searchKey" class="form-control"
               placeholder=" Поиск ">
        <button type="submit" style="margin-left:10px" class="btn btn-success">Найти</button>
    </form>
    <p></p>
</div>
<br>
<br>
<div class="container">
    <div class="row"><p></p>
        <div class="col-lg-6 col-md-6">
            <label><h3>Фильтр</h3></label>
            <form method="post" action="/search/applyFilter">
                <select id="categoryName" name="categoryName">
                    <c:forEach items="${categories}" var="category">
                        <option name="category"
                                <c:if test="${searchParams.getCategoryName().equals(category.getName())}">selected</c:if>
                                value="${category.getName()}">
                                ${category.getName()}
                        </option>
                    </c:forEach>
                </select>
                <p></p>
                <div>Цена</div>
                <p></p>
                <input id="priceFrom" name="priceFrom" type="text" placeholder="Цена от">
                <input id="priceTo" name="priceTo" type="text" placeholder="Цена до">
                <button type="submit" style="margin-left:10px" class="btn btn-success"> Применить</button>
            </form>
        </div>
        <p></p>
        <p></p>
        <div class="col" style="text-align: center"><b>Результаты поиска:</b><br><br>
            <c:forEach items="${products}" var="product">
                <div class="card w-80 m-1" type="product">
                    <div class="card-body">
                        <div class="row">
                            <div class="col m-1"><a
                                    href="${contextPath}/products/${product.getId()}"><img
                                    class="card-img"
                                    style="width:100px;height:100px"
                                    src="<c:url value="/${product.getImagePath()}"/>"
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
    <p></p>
    <p></p>
    <p></p>
    <p></p>
    <nav>
    <ul class="pagination justify-content-center" style="margin: 15px">
        <li class="page-item" style="margin-right:10px"><a class="btn btn-outline-success"
                                                           href="/search/pagination/${paginationParams.getPageNumber()-1}">Назад</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/search/pagination/0">1</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/search/pagination/1">2</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/search/pagination/2">3</a>
        </li>
        <li class="page-item" style="margin-left:10px"><a class="btn btn-outline-success"
                                                          href="/search/pagination/${paginationParams.getPageNumber()+1}">Вперед</a>
        </li>

        <div class="dropdown">
            <button class="btn btn-success" type="button" id="dropdownMenu" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false" style="margin-left:30px ">
                Размер страницы
            </button>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu">
                <a class="dropdown-item" href="/search/setPageSize/1">1</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/search/setPageSize/2">2</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/search/setPageSize/3">3</a>
            </div>
        </div>
    </ul>
</nav>
</div>
</body>
</html>