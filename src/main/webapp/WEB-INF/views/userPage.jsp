<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Личный кабинет</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>

        .fa {
            padding: 20px;
            font-size: 30px;
            width: 30px;
            text-align: center;
            text-decoration: none;
            margin: 5px 2px;
            border-radius: 50%;
        }

        .fa:hover {
            opacity: 0.7;
        }
    </style>
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
            <a href="${contextPath}/search">
                <button class="btn btn-outline-success m-1" type="button">Поиск</button>
            </a>
            <a href="${contextPath}/userPage">
                <button class="btn btn-outline-success m-1" type="button">Кабинет</button>
            </a>
            <a href=${contextPath}/cart/open>
                <button class="btn btn-outline-success m-1" type="button">Корзина</button>
            </a>
            <sec:authorize access="isAuthenticated()">
            <a href="/logout">
                <button class="btn btn-outline-success m-1" type="button">Выйти</button>
            </a>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                <a href="/login">
                    <button class="btn btn-outline-success m-1" type="button">Войти</button>
                </a>
                    </sec:authorize>
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
            <p><b>Дата заказа:</b> ${order.getOrderDate()}</p>
            <p><b>Номер заказа: </b> ${order.getId()}</p>
            <c:forEach items="${order.getProductList()}" var="product">
                <div class="card w-50 m-1" type="product">
                    <div class="card-body">
                        <div class="row">
                            <div class="col m-1">
                                <img class="card-img"
                                     style="width:70px;height:70px"
                                     src="<c:url value="/${product.getImagePath()}"/>"
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
</section>
<nav>
    <ul class="pagination justify-content-center" style="margin: 15px">
        <li class="page-item" style="margin-right:10px"><a class="btn btn-outline-success"
                                                           href="/userPage/pagination/${paginationParams.getPageNumber()-1}">Назад</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/userPage/pagination/0">1</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/userPage/pagination/1">2</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/userPage/pagination/2">3</a>
        </li>
        <li class="page-item" style="margin-left:10px"><a class="btn btn-outline-success"
                                                          href="/userPage/pagination/${paginationParams.getPageNumber()+1}">Вперед</a>
        </li>

        <div class="dropdown">
            <button class="btn btn-success" type="button" id="dropdownMenu" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false" style="margin-left:30px ">
                Размер страницы
            </button>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu">
                <a class="dropdown-item" href="/userPage/changeSize/1">1</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/userPage/changeSize/2">2</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/userPage/changeSize/3">3</a>
            </div>
        </div>
    </ul>
</nav>
<sec:authorize access="hasAuthority('ADMIN')">
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
</sec:authorize>
</body>
<!-- Footer -->
<footer class="text-center text-lg-start bg-white text-muted">
    <!-- Section: Social media -->
    <section class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
        <div class="me-5 d-none d-lg-block">
            <span>Присоединяйтесь к нам в социальных сетях:</span>
        </div>
        <div>
            <a href="https://www.facebook.com/sveta.yezhaleva/ class=" class="me-4 link-secondary"  >
                <i class="fa fa-facebook" style="align-items: center"></i>
            </a>
            <a href="https://t.me/sviatlana_y" class="me-4 link-secondary">
                <i class="fa fa-telegram"></i>
            </a>
            <a href="https://www.instagram.com/svetik_vojik/" class="me-4 link-secondary">
                <i class="fa fa-instagram"></i>
            </a>
            <a href="https://www.linkedin.com/in/svetlana-yezheleva/" class="me-4 link-secondary">
                <i class="fa fa-linkedin"></i>
            </a>
            <a href="https://github.com/Svetlana-vojik" class="me-4 link-secondary" style="margin-right:30px">
                <i class="fa fa-github"></i>
            </a>
        </div>
    </section>
    <!-- Section: Social media -->
    <!-- Section: Links  -->
    <section class="">
        <div class="container text-center text-md-start mt-5">
            <div class="row mt-3">
                <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
                    <h6 class="text-uppercase fw-bold mb-4">
                        <i class="fas fa-gem me-3 text-secondary"></i>Cake shop
                    </h6>
                    <p>
                        В жизни так мало радостей - балуйте себя сегодня ... не откладывайте на завтра!
                    </p>
                </div>
                <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
                    <h6 class="text-uppercase fw-bold mb-4">
                        Категории продуктов
                    </h6>
                    <p>
                        <a href="${contextPath}/category/1" class="text-reset">Капкейки</a>
                    </p>
                    <p>
                        <a href="${contextPath}/category/2" class="text-reset">Киши</a>
                    </p>
                    <p>
                        <a href="${contextPath}/category/3" class="text-reset">Круассаны</a>
                    </p>
                    <p>
                        <a href="${contextPath}/category/4" class="text-reset">Пирожные</a>
                    </p>
                    <p>
                        <a href="${contextPath}/category/5" class="text-reset">Торты</a>
                    </p>
                </div>
                <div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">
                    <h6 class="text-uppercase fw-bold mb-4">
                        Полезные ссылки
                    </h6>
                    <p>
                        <a href="/userPage" class="text-reset">Личный кабинет</a>
                    </p>
                    <p>
                        <a href="/search" class="text-reset">Поиск</a>
                    </p>
                </div>
                <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
                    <h6 class="text-uppercase fw-bold mb-4">Контакты</h6>
                    <p><i class="fa fa-home me-3 text-secondary" style="margin-right:10px"></i> Минск, ул.Скрыганова д.3 каб.36 </p>
                    <p>
                        <i class="fa fa-envelope me-3 text-secondary" style="margin-right:10px"></i>
                        cake@example.com
                    </p>
                    <p><i class="fa fa-phone me-3 text-secondary" style="margin-right:10px"></i> + 375 29 182 42 28</p>
                </div>
            </div>
        </div>
    </section>
    <div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.025);">
        © 2023 Copyright:
        <a class="text-reset fw-bold" href="https://bootstrap.com/">Bootstrap.com</a>
    </div>
</footer>
<!-- Footer -->
</html>