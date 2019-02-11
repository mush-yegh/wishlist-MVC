<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WishList web-application</title>
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,500,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <script src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
    <script>

        $(document).ready(function () {
            $('a.logo').click(function () {
                var leftMenu = $('.leftNav');
                if (leftMenu.hasClass('visible')) {
                    leftMenu.animate({'left': '-310px'}, 600).removeClass('visible');
                } else {
                    leftMenu.animate({'left': '0'}, 600).addClass('visible');
                }
            });

            $('.leftNav').css('left', '-310px');
        });
    </script>
</head>
<body>

    <ul class="topNav">
        <li class="item">
            <a href="#" class="logo">
                <i class="fa fa-bars" aria-hidden="true"></i>
                ACA JAVA
            </a>
        </li>
        <li class="item" style="margin-left: auto;"><div style="color: #ffffff;padding: 12px 20px;">hello ${username}</div></li>
        <li class="item" style="margin-left: auto;"><a href="#">Logout</a></li>
    </ul>

    <ul class="leftNav visible">
        <li class="item"><a href="#">Home</a></li>
        <li class="item"><a href="#">Users</a></li>
        <li class="item"><a href="#">Friends</a></li>
        <li class="item"><a href="#">Sent Requests</a></li>
        <li class="item"><a href="#">Received Request</a></li>
    </ul>


<style>
	/*@import url("https://fonts.googleapis.com/css?family=Roboto");*/
/*@import url("https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css");*/
body {
  /*background-color: #1a8fb4;
  font-family: 'Roboto', sans-serif;
  font-size: 14px;*/
}
.owl {
  margin: auto;
  width: 211px;
  height: 108px;
  background-image: url("https://dash.readme.io/img/owl-login.png");
  position: relative;
}
.owl .hand {
  width: 34px;
  height: 34px;
  border-radius: 40px;
  background-color: #472d20;
  transform: scaleY(0.6);
  position: absolute;
  left: 14px;
  bottom: -8px;
  transition: 0.3s ease-out;
}
.owl .hand.password {
  transform: translateX(42px) translateY(-15px) scale(0.7);
}
.owl .hand.hand-r {
  left: 170px;
}
.owl .hand.hand-r.password {
  transform: translateX(-42px) translateY(-15px) scale(0.7);
}
.owl .arms {
  position: absolute;
  top: 58px;
  height: 41px;
  width: 100%;
  overflow: hidden;
}
.owl .arms .arm {
  width: 40px;
  height: 65px;
  background-image: url("https://dash.readme.io/img/owl-login-arm.png");
  position: absolute;
  left: 20px;
  top: 40px;
  transition: 0.3s ease-out;
}
.owl .arms .arm.password {
  transform: translateX(40px) translateY(-40px);
}
.owl .arms .arm.arm-r {
  left: 158px;
  transform: scaleX(-1);
}
.owl .arms .arm.arm-r.password {
  transform: translateX(-40px) translateY(-40px) scaleX(-1);
}
.form {
  margin: auto;
  margin-top: -9px;
  padding: 30px;
  background-color: #000;
  width: 175px;

  width: 250px;
}
.form .control {
  margin-bottom: 10px;
  position: relative;
}
.form .control label {
  position: absolute;
  font-size: 16px;
  top: 11px;
  left: 9px;
  color: rgba(0,0,0,0.3);
}
.form .control input {
  padding: 9px 6px 9px 30px;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 14px;

  width: 84%;
}

</style>
<div style = "padding: 50px;box-sizing: border-box;max-width: 500px; margin: 45px auto 0 auto;
               background: rgba(25, 24, 24, 0.32); box-shadow: 0px 1px 13px 2px rgba(251, 251, 251, 0.09);



                                  ">
        <div class="owl">
          <div class="hand"></div>
          <div class="hand hand-r"></div>
          <div class="arms">
            <div class="arm"></div>
            <div class="arm arm-r"></div>
          </div>
        </div>
        <div class="form">
          <div class="control">
            <label for="email" class="fa fa-envelope"></label>
            <input id="email" placeholder="Email" type="email"></input>
        </div>
        <div class="control">
          <label for="password" class="fa fa-asterisk"></label>
          <input id="password" placeholder="Password" type="password"></input>
        </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script>
$( document ).ready(function() {
	$('input[type="password"]').on('focus', function() {
	  $('*').addClass('password');
	}).on('focusout', function() {
	  $('*').removeClass('password');
	});
});

</script>


</body>
</html>