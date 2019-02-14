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
    $.getScript("js/user.js", function (e) {
        check();

    });

    $('#users').click(function () {
        getUsers();
    });


    /*
        //load request.js
        $.getScript( "js/request.js",function (e) {
            check();

        });*/


    //


    //load sockjs.js
    $.getScript("js/sockjs.min.js", function (e) {
        console.log("sockjs loaded");

    });

    //load stomp.js
    $.getScript("js/stomp.js", function (e) {
        console.log("stomp loaded");
    });

    //SOCKET
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        //setConnected(true);
        console.log('Connected: ' + frame);


        stompClient.subscribe('/topic/greetings', function (data) {


            let message = data.body;

            alert(message);

        });
    });


    $(document).on("click", "span.sendRequestIcon", function () {
        let currId = $('#currentUser').data("id");
        let userToId = $(this).parent().attr("id");
        //sendRequest(currId, userToId);
        let data = {};
        data['userFromId'] = currId;
        data['userToId'] = userToId;
        alert(123);
        stompClient.send("/app/hello", {}, JSON.stringify(data));

        ////////////////////////////////////////////
        /*$.ajax({
            type: "post",
            contentType: "application/json",
            url: "http://localhost:8080/app/requests/sendRequest",
            data: JSON.stringify(data),
            //dataType: "json",
            cache: false,
            timeout: 600000,
            success: function (data) {
                let jsonResp = JSON.stringify(data, null, 4);
                console.log("jsonResp = " + jsonResp);

            },
            error: function (e) {
                let jsonErr = e.responseText;
                console.log("err" + jsonErr);
                console.log("e = " + JSON.stringify(e));
            }
        })*/
        ////////////////////////////////////////////

    });

    /*function sendRequest(currId, userToId) {
        alert(999);
        /!*console.log("currId " +currId);
        console.log("userToId " +userToId);
        console.log("send_request fired");

        stompClient.send("/app/hello", {}, JSON.stringify(data));
*!/
    };*/

    /*$('.homeContent').on("click","span", function () {
        alert(123);
        let currId = $('#currentUser').data("id");
        let userToId = $(this).parent().attr("id");
        sendRequest(currId, userToId);
    });*/

    /*
        $(".homeContent").delegate('span','click', function () {
            alert(123);
        });
    */


})
;