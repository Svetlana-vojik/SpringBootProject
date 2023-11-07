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
    <c:if test="${not empty orders}">
        <c:forEach items="${orders}" var="order">
            <p><b>Дата заказа:</b> ${order.getOrderDate()}</p>
            <p><b>Номер заказа: </b> ${order.getId()}</p>
            <c:forEach items="${order.getProductList()}" var="product">
                <div class="card w-50 m-1" type="product">
                    <div class="card-body">
                        <div class="row">
                            <div class="col m-1">
                                <img class="card-img"
                                     style="width:70px;height:70px"
                                     src="${product.getImagePath()}"
                                     alt=${product.getImagePath()}></div>
                            <div class="col m-1" style="text-align: center">
                                <p>${product.getName()}</p></div>
                            <div class="col m-1" style="text-align: center">
                                <p>${product.getDescription()}</p></div>
                            <div class="col m-1" style="text-align: center">
                                <p>Цена: ${product.getPrice()}</p></div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:forEach>
    </c:if>
</section>
<nav>
    <ul class="pagination justify-content-center" style="margin: 15px">
        <li class="page-item" style="margin-right:10px"><a class="btn btn-outline-success"
                                                           href="/profile/pagination/${paginationParams.getPageNumber()-1}">Назад</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/profile/pagination/0">1</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/profile/pagination/1">2</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/profile/pagination/2">3</a>
        </li>
        <li class="page-item" style="margin-left:10px"><a class="btn btn-outline-success"
                                                          href="/profile/pagination/${paginationParams.getPageNumber()+1}">Вперед</a>
        </li>
        <div class="dropdown">
            <button class="btn btn-success" type="button" id="dropdownMenu" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false" style="margin-left:30px ">
                Размер страницы
            </button>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu">
                <a class="dropdown-item" href="/profile/changeSize/1">1</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/profile/changeSize/2">2</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/profile/changeSize/3">3</a>
            </div>
        </div>
    </ul>
</nav>
<form method="POST" action="/userPage/csv/import" enctype="multipart/form-data"
      class="file-import">
    <label for="file-upload" class="custom-file-upload"
           style="padding: 15px;margin: 0px 0px 15px 15px;border: 1px solid #ccc">
        <input id="file-upload" name="file" type="file" class="title" accept=".csv">
        <button type="submit" class="btn-outline-success">Импортировать заказы пользователя</button>
    </label>
</form>
<form method="POST" action="/userPage/csv/export/${user.getId()}">
    <button type="submit" class="btn-outline-success" style="margin: 15px">Экспортировать заказы пользователя</button>
</form>
</body>
</html>