<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>WishList web-application</title>
    <link rel="icon" type="/image/png" href="/img/favicon.ico"/>

    <link rel="stylesheet" href="/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="/css/owl.css">
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>

    <script src="/js/jquery-3.0.0.min.js" type="text/javascript"></script>

</head>
<body>
<!-- <#if error??>
    <div class="alert alert-danger">Wrong username/password</div>
</#if> -->


<ul class="topNav">
    <li class="item">
        <p class="logo" id="logo">
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

    <form method="post" action="/login">
        <div class="form">
            <div class="control">
                <label for="email" class="fa fa-envelope"></label>
                <input id="email" placeholder="Email" type="email" name="mail"></input>
            </div>
            <div class="control">
                <label for="password" class="fa fa-asterisk"></label>
                <input id="password" placeholder="Password" type="password" name="password"></input>
            </div>
            <div class="loginButton">
                <input type="submit" value="LogIn" class=""></input>
                <div class="clear"></div>
            </div>

        </div>
    </form>

    <script>
        $(document).ready(function () {
            $('input[type="password"]').on('focus', function () {
                $('*').addClass('password');
            }).on('focusout', function () {
                $('*').removeClass('password');
            });
        });
    </script>


</body>
</html>