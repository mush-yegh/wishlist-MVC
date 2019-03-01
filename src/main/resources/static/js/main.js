$(document).ready(function () {
    //left menu show-hide
    $('p.logo').click(function () {
        const leftMenu = $('.leftNav');
        if (leftMenu.hasClass('visible')) {
            leftMenu.animate({'left': '-310px'}, 600).removeClass('visible');
        } else {
            leftMenu.animate({'left': '0'}, 600).addClass('visible');
        }
    });

    //clear 'Welcome' text
    setTimeout(function () {
        const $welcomeText = $('#welcomeText');
        $welcomeText.fadeOut('slow');
        setTimeout(function () {
            $welcomeText.remove();
        }, 1100);
    }, 3000);



});// /document ready


//loader gif block show - hide
function loaderOn() {
    $('#loader').fadeIn('fast');
}

function loaderOff() {
    $('#loader').hide();
}

// show message for 3 sec
function showInfo(msg) {
    $('#infoText').text(msg);
    const $info = $('#info');
    $info.fadeIn('slow');
    setTimeout(function () {
        $info.fadeOut('slow');
    }, 3000);
}

//=================== LOAD OTHER SCRIPTS ===================

//load notification.js
$.getScript("js/notification.js", function (e) {
    checkNotification();
});

//load wish.js
$.getScript("js/wish.js", function (e) {
    checkWish();
});

//load user.js
$.getScript("js/user.js", function (e) {
    checkUser();
});
//load friend.js
$.getScript("js/friend.js", function (e) {
    checkFriend();
});

//load request.js
$.getScript("js/request.js", function (e) {
    checkRequest();
});

//load simplebar.js
$.getScript("js/simplebar.js", function (e) {
    console.log("simplebar loaded");
});
//=================== /LOAD OTHER SCRIPTS ===================


