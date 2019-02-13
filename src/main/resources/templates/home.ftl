<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WishList web-application</title>
    <link rel="icon" type="/image/png" href="/img/favicon.ico"/>

    <link rel="stylesheet" href="/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>

    <script src="/js/jquery-3.0.0.min.js"></script>
    <script src="/js/main.js"></script>
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
        <div class="usernameBlock" id="currentUser" data-id="${user.id}">
            <span id="welcomeText">Welcome</span> ${user.firstName}
            <#--<input type="hidden" id="currentUserId" value=${user.id}/>-->
        </div>
    </li>
</ul>

<ul class="leftNav visible">
    <li class="item"><p class="homeP">Home</p></li>
    <li class="item"><p id="users">Users</p></li>
    <li class="item"><p id="friends">Friends</p></li>
    <li class="item"><p>Sent Requests</p></li>
    <li class="item"><p>Received Request</p></li>
    <li class="item" style="margin-left: auto;">
        <a href="/logout">Logout</a>
    </li>
</ul>


<div class="homeContent" style="display: none;">


</body>

</script>
</html>