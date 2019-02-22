<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="_csrf_parameter" content="${_csrf.parameterName}" />
    <meta name="_csrf_header" content="X-CSRF-TOKEN" />
    <meta name="_csrf" content="${_csrf.token}" />

    <title>WishList web-app</title>
    <link rel="icon" type="/image/png" href="/img/favicon.ico"/>

    <link rel="stylesheet" type="text/css" href="/css/main.css"/>

    <script src="/js/jquery-3.0.0.min.js"></script>
    <script src="/js/main.js"></script>

</head>
<body>


<ul class="topNav">
    <li class="item">
        <p class="logo" style="">
            <i class="fa fa-bars" aria-hidden="true"></i>
            <span>ACA JAVA</span>
        </p>
    </li>
    <li class="item" style="margin-left: auto;">
        <div class="userBlock" id="currentUser" data-id="${user.id}">
            <div id="welcomeText">Welcome</div>
            <div class="currUsername">${user.firstName}</div>
            <div class="notifIconBlock">
                <i class="fa fa-bell faa-ring faa-slow"></i>
            </div>
            <div class="clear"></div>
            <div class="notifMsgBlock" id="notifMsgBlock">
                <div class="notifText">
                    <span id="senderFullName"></span>
                    <font>wants to be friend with you</font>
                </div>
                <div class="acceptReject">
                    <div id="reject">Reject</div>
                    <div id="accept">Accept</div>
                    <div class="clear"></div>
                </div>
                <span class="close" id="notifCloseIcon"></span>
            </div>
            <div class="notifMsgBlock" id="notifEmpty">
                <div class="notifText">Empty</div>
            </div>
        </div>
    </li>
</ul>

<ul class="leftNav">
    <li class="item"><p id="wishList" class="homeP">Home</p></li>
    <li class="item"><p id="users">Users</p></li>
    <li class="item"><p id="friends">Friends</p></li>
    <li class="item"><p id="sentRequests">Sent Requests</p></li>
    <li class="item"><p id="receivedRequests">Received Request</p></li>
    <li class="item">
        <div class="logoutDiv">
            <a href="#" onclick="document.getElementById('logout').submit();">Logout</a>
        </div>
        <form action="/logout" method="post" id="logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </form>
    </li>
</ul>


<div class="homeContent data-simplebar" style="display: none;" data-simplebar></div>

<div class="loaderBlock" id="loader">
    <div class="loaderGif"></div>
</div>
<div id="info" class="infoMsg">
    <p id="infoText">something useful</p>
</div>
<audio id="beep" preload="auto">
    <source src="/audio/beep.mp3"/>
</audio>
</body>

</html>