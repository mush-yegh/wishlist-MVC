$(document).ready(function () {

    $('p.logo').click(function () {
        var leftMenu = $('.leftNav');
        if (leftMenu.hasClass('visible')) {
            leftMenu.animate({'left': '-310px'}, 600).removeClass('visible');
        } else {
            leftMenu.animate({'left': '0'}, 600).addClass('visible');
        }
    });
    $('.leftNav').css('left', '-310px');


    setTimeout(function () {
        $('#welcomeText').remove();
    }, 3000);



    //load user.js
    $.getScript( "js/user.js",function (e) {
        check();

    });

    $('#users').click(function () {
        getUsers();
    });



    //load user.js
    $.getScript( "js/request.js",function (e) {
        check();

    });
    //

    $(document).on("click","span.sendRequestIcon", function () {
        let currId = $('#currentUser').data("id");
        let userToId = $(this).parent().attr("id");
        sendRequest(currId, userToId);
    });



})
;