<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WishList web-app</title>
    <link rel="icon" type="/image/png" href="/img/favicon.ico"/>

    <link rel="stylesheet" href="/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/css/font-awesome-animation.css"/>
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>

    <script src="/js/jquery-3.0.0.min.js"></script>
    <script src="/js/main.js"></script>

    <script src="/js/sockjs.min.js"></script>
    <script src="/js/stomp.js"></script>
</head>
<body>


<ul class="topNav">
    <li class="item">
        <p class="logo" style="">
            <i class="fa fa-bars" aria-hidden="true"></i>
            ACA JAVA
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
            <div class="notifMsgBlock">
                <div class="notifText"><span id="senderFullName"></span> wants to be friend with you</div>
                <div class="acceptReject">
                    <div id="reject">Reject</div>
                    <div id="accept">Accept</div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="notifMsgBlock" id="notifEmpty">
                <div class="notifText">Empty</div>
            </div>
        </div>
    </li>
</ul>

<ul class="leftNav">
    <li class="item"><p class="homeP">Home</p></li>
    <li class="item"><p id="users">Users</p></li>
    <li class="item"><p id="friends">Friends</p></li>
    <li class="item"><p>Sent Requests</p></li>
    <li class="item"><p>Received Request</p></li>
    <li class="item"
    ">
    <div class="logoutDiv">
        <a href="/logout">Logout</a>
    </div>
    </li>
</ul>


<div class="homeContent" style="display: none;"></div>

</body>

</html>