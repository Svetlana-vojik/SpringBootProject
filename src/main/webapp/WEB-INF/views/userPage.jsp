<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Личный кабинет</title>
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
            <a href=${contextPath}/cart/open>
                <button class="btn btn-outline-success m-1" type="button">Корзина</button>
            </a>
        </form>
    </div>
</nav>
<section>
    <c:set var="user" value="${user}"/>
    <div class="col">
        <div class="card mb-4">
            <div class="card-body">
                <div class="row">
                    <div class="col-sm-3">
                        <h4 style="text-align: center"><em>Личные данные</em></h4>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Имя</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <h5 style="color:saddlebrown">${user.getName()}</h5>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Фамилия</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <h5 style="color:saddlebrown">${user.getSurname()}</h5>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Дата рождения</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <h5 style="color:saddlebrown">${user.getBirthday()}</h5>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Email</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <h5 style="color:saddlebrown"> ${user.getEmail()}</h5>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Адрес</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <h5 style="color:saddlebrown">${user.getAddress()}</h5>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section>
    <h5 class="mb-0" style="padding: 20px">История заказов</h5>
    <c:forEach items="${orders}" var="order">
        <span style="padding: 20px">Номер заказа-${order.getId()} / Дата заказа-${order.getOrderDate()}</span>
        <div class="col d-flex justify-content-start">
            <c:forEach items="${order.getProducts()}" var="product">
                <div class="card mb-3" style="max-width: 540px;margin: 20px">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="${contextPath}/images/${product.getImagePath()}"
                                 class="img-fluid rounded-start" alt="Card image">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">${product.getName()}</h5>
                                <p class="card-text">${product.getDescription()}</p>
                                <p class="card-text">Цена: ${product.getPrice()}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
</section>
</body>
</html>