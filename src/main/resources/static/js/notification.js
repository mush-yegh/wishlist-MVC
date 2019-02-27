function checkNotification(){
    console.log("notification.js loaded!")
}
//=================== WEB SOCKET===================

let socket;
let stompClient;
$.when($.getScript("js/libs/sockjs.min.js")).done(function () {
    socket = new SockJS('/ws');
    console.log("sockjs loaded");
    //load stomp.js
    $.getScript("js/libs/stomp.js", function (e) {
        console.log("stomp loaded");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/client/notification', function (data) {
                //console.log(message);
                notify(data.body);
            });
        });
    });
});

//=================== /WEB SOCKET ===================


function notify(data) {
    let RespBody = JSON.parse(data).body;
    let notifType = RespBody.notifType;

    let recipientId = RespBody.recipientId;
    let Sender = RespBody.sender;
    if (recipientId === loggedInUserId) {
        if (notifType === 'friendRequest') {

            setTimeout(function () {
                $("#beep")[0].play();
                $('.notifIconBlock').addClass("red");
                $('.notifIconBlock > i').addClass("animated");
                $('#senderFullName')
                    .attr("data-sId", Sender.id)
                    .text(Sender.firstName + ' ' + Sender.lastName);
                $('#notifMsgBlock').fadeIn('slow');
            }, 10000);

        } else if (notifType === 'bd') {
            $("#beep")[0].play();

            let $bdBlock = $('<div/>');
            $bdBlock.attr('id', 'bdBlock');
            $bdBlock.attr('class', 'bdBlock');

            let $bdNotifyBlock = $('<div/>');
            $bdNotifyBlock.attr('class', 'bdNotifyBlock');
            $bdNotifyBlock.append("<p>Today <span>" + Sender.firstName + ' ' + Sender.lastName + "</span> 's birthday!</p>");

            $bdNotifyBlock.text()

            $bdBlock.append($bdNotifyBlock);
            $('body').append($bdBlock);

            $('#bdBlock').fadeIn('slow');
            setTimeout(function () {
                $('#bdBlock').fadeOut('slow');
            }, 4200);
        }

    } else {
        console.log("not for you!");
    }
}


//=================== REJECT REQUEST ===================

$('#reject').click(function () {
    let senderId = $('#senderFullName').attr("data-sId");

    let header = $("meta[name='_csrf_header']").attr("content");
    let token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "POST",
        contentType: "text/plain",
        url: "/requests/reject",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data: senderId,
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            $('#notifMsgBlock').fadeOut('slow');
            showInfo("Successfully rejected");
            resetBell();
        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log("rejectError = " + jsonErr);
            showInfo("ooops... something went wrong!");
            resetBell();
        }
    });
});
//=================== REJECT REQUEST ===================

//=================== ACCEPT REQUEST ===================

$('#accept').click(function () {
    let senderId = $('#senderFullName').attr("data-sId");
    let header = $("meta[name='_csrf_header']").attr("content");
    let token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "POST",
        contentType: "text/plain",
        url: "/requests/accept",
        data: senderId,
        xhrFields: {
            withCredentials: true
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log("successfully accepted");
            $('#notifMsgBlock').fadeOut('slow');
            showInfo("Congrats! Now You're friends.");
            resetBell();
        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log("acceptError = " + jsonErr);
            showInfo("ooops... something went wrong!");
            resetBell();
        }
    });
});
//=================== /ACCEPT REQUEST ===================

//=================== Close notification block ===================

$('#notifCloseIcon').click(function () {
    $('#notifMsgBlock').fadeOut('slow');
    resetBell();
});
//=================== /Close notification block ===================


function resetBell() {
    //have to clear all texts
    $('#notifMsgBlock').fadeOut('slow');
    $('.notifIconBlock').removeClass("red");
    $('.notifIconBlock > i').removeClass("animated");
}


