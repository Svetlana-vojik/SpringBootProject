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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
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
</html>