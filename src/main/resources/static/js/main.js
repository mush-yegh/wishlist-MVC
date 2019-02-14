$(document).ready(function () {
    //left menu show-hide
    $('p.logo').click(function () {
        var leftMenu = $('.leftNav');
        if (leftMenu.hasClass('visible')) {
            leftMenu.animate({'left': '-310px'}, 600).removeClass('visible');
        } else {
            leftMenu.animate({'left': '0'}, 600).addClass('visible');
        }
    });
    $('.leftNav').css('left', '-310px');

    //clear 'Welcome' text
    setTimeout(function () {
        $('#welcomeText').remove();
    }, 3000);

    //
    const loggedInUserId = $('#currentUser').data("id");

    //load user.js
    $.getScript("js/user.js", function (e) {
        check();
    });

    $('#users').click(function () {
        getUsers();//call to user.js
    });


    /*
        //load request.js
        $.getScript( "js/request.js",function (e) {
            check();

        });*/


    //load sockjs.js
    $.getScript("js/sockjs.min.js", function (e) {
        console.log("sockjs loaded");

    });

    //load stomp.js
    $.getScript("js/stomp.js", function (e) {
        console.log("stomp loaded");
    });

    //SOCKET
    const socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/client/notification', function (data) {
            //let message = data.body;
            //alert(message);
            //console.log(message);
            notify(data.body);
        });
    });

    function notify(data) {
        //alert("notify fired");

        let RespBody = JSON.parse(data).body;
        console.log("resp" + RespBody);
        let recipientId = RespBody.recipientId;
        console.log("recipientId " + recipientId);
        let Sender = RespBody.sender;
        console.log("sender id = " + Sender.id);
        if (recipientId === loggedInUserId) {
            $('.notifIconBlock').addClass("red");
            //$('.notifIconBlock').css("color", "red");
            $('.notifIconBlock > i').addClass("animated");
            $('#senderFullName').text(Sender.firstName + ' ' + Sender.lastName)
            console.log("color changed!");
        } else {
            console.log("id = " + Sender.id);
            console.log("loggedInUserId = " + loggedInUserId);
        }

    }


    $(document).on("click", "span.sendRequestIcon", function () {
        //let loggedInUserId = $('#currentUser').data("id");
        let recipientId = $(this).parent().attr("id");
        //sendRequest(currId, userToId);
        let data = {};
        data['senderId'] = loggedInUserId;
        data['recipientId'] = recipientId;
        //alert(123);
        stompClient.send("/app/srvSocket", {}, JSON.stringify(data));

        //===============
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
        //===============

    });

    $('.notifIconBlock').click(function (event) {
        event.stopPropagation();
        $('#notifEmpty').fadeIn( "slow" );
        setTimeout(function () {
            $('#notifEmpty').fadeOut( "slow" );
        }, 3000 );
    });

    $('html').click(function () {
        //$('#notifEmpty').toggle($('#notifEmpty').is(':visible'));
    });

});