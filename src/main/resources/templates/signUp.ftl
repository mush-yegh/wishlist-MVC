<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WishList web-app</title>
    <link rel="icon" type="/image/png" href="/img/favicon.ico"/>

    <link rel="stylesheet" type="text/css" href="/css/owl.css">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <script src="js/libs/jquery-3.0.0.min.js"></script>
    <script src="js/libs/bootstrap-datepicker.min.js"></script>
</head>
<body>

<#if error??>
    <div class="alert alert-danger">mail is non unique</div>
</#if>

<ul class="topNav">
    <li class="item">
        <p class="logo">
            <i class="fa fa-bars" aria-hidden="true"></i>
            ACA JAVA
        </p>
    </li>
</ul>
<div class="homeContent">
    <div class="owl">
        <div class="hand"></div>
        <div class="hand hand-r"></div>
        <div class="arms">
            <div class="arm"></div>
            <div class="arm arm-r"></div>
        </div>
    </div>

    <form method="post" action="/signUp">
        <div class="form">
            <div class="control">
                <label for="firstName" class="fa fa-user"></label>
                <input id="firstName" placeholder="First name" type="text" name="firstName" required
                       pattern=".{3,}" title="3 charackters minimum"/>
            </div>
            <div class="control">
                <label for="lastName" class="fa fa-user"></label>
                <input id="lastName" placeholder="Last name" type="text" name="lastName" required/>
            </div>
            <div class="control" id="datePick">
                <label for="birthDate" class="fa fa-calendar"></label>
                <input id="birthDate" placeholder="Birth day" type="text" name="birthDate" required
                       autocomplete="off" onkeydown="return false;"
                       data-provide="datepicker"/>
            </div>
            <div class="control">
                <label for="email" class="fa fa-envelope"></label>
                <input id="email" placeholder="Email" type="email" name="mail" required/>
            </div>
            <div class="control">
                <label for="password" class="fa fa-asterisk"></label>
                <input id="password" placeholder="Password" type="password" name="password" required/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="loginButton">
                <input type="submit" value="SignUp"/>
                <div class="clear"></div>
            </div>
        </div>
    </form>
</div>

<script>
    $(document).ready(function () {
        $('input[type="password"]').on('focus', function () {
            $('*').addClass('password');
        }).on('focusout', function () {
            $('*').removeClass('password');
        });
        $('#birthDate').datepicker({
            format: 'yyyy-mm-dd',
            endDate: '0d',
            showWeekDays: false,
            showYears: true,
            orientation: "bottom left",
            autoclose: true,
            keyboardNavigation: true,
            startView: 2
        });
    });
</script>

</body>
</html>