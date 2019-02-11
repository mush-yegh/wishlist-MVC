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
        <!-- <li class="item"><a href="#">Home</a></li>
        <li class="item"><a href="#">About</a></li>
        <li class="item"><a href="#">Blog</a></li>
        <li class="item"><a href="#">Other</a></li>
        <li class="item"><a href="#">Video Tutorials</a></li>
        <li class="item"><a href="#">Bacon</a></li>
        <li class="item"><a href="#">Some Item</a></li> -->
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


    <div class="login-wrap">
    	<div class="login-html">
    		<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Sign In</label>
    		<input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label>
    		<div class="login-form">
    			<div class="sign-in-htm">
    				<div class="group">
    					<label for="user" class="label">Username</label>
    					<input id="user" type="text" class="input">
    				</div>
    				<div class="group">
    					<label for="pass" class="label">Password</label>
    					<input id="pass" type="password" class="input" data-type="password">
    				</div>
    				<div class="group">
    					<input id="check" type="checkbox" class="check" checked>
    					<label for="check"><span class="icon"></span> Keep me Signed in</label>
    				</div>
    				<div class="group">
    					<input type="submit" class="button" value="Sign In">
    				</div>
    				<div class="hr"></div>
    				<div class="foot-lnk">
    					<a href="#forgot">Forgot Password?</a>
    				</div>
    			</div>
    			<div class="sign-up-htm">
    				<div class="group">
    					<label for="user" class="label">Username</label>
    					<input id="user" type="text" class="input">
    				</div>
    				<div class="group">
    					<label for="pass" class="label">Password</label>
    					<input id="pass" type="password" class="input" data-type="password">
    				</div>
    				<div class="group">
    					<label for="pass" class="label">Repeat Password</label>
    					<input id="pass" type="password" class="input" data-type="password">
    				</div>
    				<div class="group">
    					<label for="pass" class="label">Email Address</label>
    					<input id="pass" type="text" class="input">
    				</div>
    				<div class="group">
    					<input type="submit" class="button" value="Sign Up">
    				</div>
    				<div class="hr"></div>
    				<div class="foot-lnk">
    					<label for="tab-1">Already Member?</a>
    				</div>
    			</div>
    		</div>
    	</div>
    </div>

</body>
</html>