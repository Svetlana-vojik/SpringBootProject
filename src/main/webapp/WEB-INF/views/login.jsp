<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        Вход
    </title>
    <meta charset="utf-8">
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
        span.error {
            color: red;
        }
    </style>
</head>
<body style="background-color:lightyellow">
<h2 style="text-align:center">Вход</h2>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<p style="text-align:center">Заполните поля для входа</p>
<div class="container">
    <div class="row">
        <div class="col-md-3 offset-md-4">
            <form method="post" action="${contextPath}/login">
                <div class="form-group">
                    <label for="email">Имя пользователя:</label>
                    <input type="text" class="form-control" id="email" placeholder="Введите email" name="email">
                    <span class="error">${emailError}</span>
                </div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="text" class="form-control" id="password" placeholder="Enter password" name="password">
                    <span class="error">${passwordError}</span>
                </div>
                <button id="loginBtn" class="btn btn-primary">Войти</button>
                <a href="${contextPath}/registration">
                    <button class="btn btn-danger" type="button">Регистрация</button>
                </a>
            </form>
        </div>
    </div>
</div>
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