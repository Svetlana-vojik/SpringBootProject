<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        Регистрация
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
<body><h2 style="text-align:center">Регистрация</h2>
<p></p>
<div class="container">
    <c:if test="${not empty info}">
        <p style="text-align: center" class="text-danger">${info}</p>
    </c:if>
    <div class="row">
        <div class="col-md-12 offset-md-5">
            <form method="post" class="needs-validation" novalidate>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="email" placeholder="Введите email"
                           name="email"
                           required>
                    <span class="error">${emailError}</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="password" placeholder="Введите пароль"
                           name="password"
                           required>
                    <span class="error">${passwordError}</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="name" placeholder="Имя"
                           name="name"
                           required>
                    <span class="error">${nameError}</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="surname" placeholder="Фамилия"
                           name="surname"
                           required>
                    <span class="error">${surnameError}</span>
                </div>
                <div class="form-group">
                    <label for="birthday"><b>Дата рождения</b></label>
                    <input type="text" class="form-control w-25 datepicker" id="birthday" placeholder="YYYY-mm-dd"
                           name="birthday"
                           required>
                    <span class="error">${birthdayError}</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="address" placeholder="Адрес"
                           name="address"
                           required>
                    <span class="error">${addressError}</span>
                </div>
                <button id="registrationBtn" type="submit" class="btn btn-success">Регистрация</button>
            </form>
            <a class="btn btn-outline-dark" href="/login">На страницу входа</a>
        </div>
    </div>
</div>
</body>
</html>