$(document).ready(function () {
    //=================== UI ANIMATIONS ===================
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
        },1100);
    }, 3000);


    //=================== /UI ANIMATIONS ===================

    //=================== LOAD OTHER SCRIPTS ===================
    //have to remove this field soon :)
    const loggedInUserId = $('#currentUser').data("id");

    //load user.js
    $.getScript("js/user.js", function (e) {
        check();
    });

    //load friend.js
    $.getScript("js/friend.js", function (e) {
        checkFriend();
    });

    //load wish.js
    $.getScript("js/wish.js", function (e) {
        console.log("wish loaded");
        checkWish();
    });

    //load simplebar.js
    $.getScript("js/simplebar.js", function (e) {
        console.log("simplebar loaded");
    });
    //=================== /LOAD OTHER SCRIPTS ===================

    //=================== USERS ===================

    $('#users').click(function () {
        loaderOn();
        $('.homeContent').hide();
        $( "div.simplebar-content > div" ).remove();

        //getUsers();//call to user.js ajax
        $.when(getUsers()).done(function(){
                console.log( "getUsers done" );
            setTimeout(function () {
                loaderOff();
                $('.homeContent').fadeIn('slow');
            },700);

        });
    });
    //=================== /USER ===================


    //=================== WEB SOCKET===================
    let socket;
    let stompClient;
    $.when($.getScript("js/sockjs.min.js")).done(function(){
        socket = new SockJS('/ws');
            console.log("sockjs loaded");
        //load stomp.js
        $.getScript("js/stomp.js", function (e) {
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



    //user row + icon click
    $(document).on("click", "span.sendRequestIcon", function () {
        //prevent double click
        $(this).unbind();

        let recipientId = $(this).parent().attr("id");

        stompClient.send("/app/srvSocket", {}, recipientId);

        hideUserRow(recipientId);
        showInfo('Request successfully sent!');

    });
    function hideUserRow(userId) {
        let $userRow = $('#'+userId);
        $userRow.slideUp();
        setTimeout(function () {
            $userRow.remove();
        }, 1000);
    }

    function notify(data) {
        let RespBody = JSON.parse(data).body;
            console.log("resp " + RespBody);
        let notifType = RespBody.notifType;
            console.log("notifType " + notifType);

        let recipientId = RespBody.recipientId;
            console.log("recipient id " + recipientId);
        let Sender = RespBody.sender;
            //console.log("sender id = " + Sender.id);
        if (recipientId === loggedInUserId) {
            if (notifType === 'friendRequest'){

                setTimeout(function () {
                    $("#beep")[0].play();
                    $('.notifIconBlock').addClass("red");
                    $('.notifIconBlock > i').addClass("animated");
                    $('#senderFullName').attr("data-sId", Sender.id)
                        .text(Sender.firstName + ' ' + Sender.lastName);
                    //$('#senderFullName').text(Sender.firstName + ' ' + Sender.lastName)
                    console.log("color changed!");
                    $('#notifMsgBlock').fadeIn('slow');
                },10000);

            }else if (notifType === 'bd'){
                //alert("upcoming bd");
                $("#beep")[0].play();

                let $bdBlock = $('<div/>');
                $bdBlock.attr('id', 'bdBlock');
                $bdBlock.attr('class', 'bdBlock');

                let $bdNotifyBlock = $('<div/>');
                $bdNotifyBlock.attr('class', 'bdNotifyBlock');
                $bdNotifyBlock.append("<p>Today <span>"+Sender.firstName + ' ' + Sender.lastName+"</span> 's birthday!</p>");

                $bdNotifyBlock.text()

                $bdBlock.append($bdNotifyBlock);
                $('body').append($bdBlock);
                
                $('#bdBlock').fadeIn('slow');
                setTimeout(function () {
                    $('#bdBlock').fadeOut('slow');
                    //$('#bdBlock').remove();
                }, 4200);
            }

        }else {
            console.log("not for you!");
        }
    }
    //=================== /WEB SOCKET ===================

    //=================== REJECT REQUEST ===================

    $('#reject').click(function () {
        //let data = {};
        //data["senderId"] = $('#senderFullName').attr("data-sId");
        let senderId = $('#senderFullName').attr("data-sId");
        //data["recipientId"] = loggedInUserId;

        //let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        let token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            //contentType: "application/json",
            contentType: "text/plain",
            url: "/requests/reject",

            //headers: {"X-CSRF-TOKEN": token},
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            //data: JSON.stringify(data),
            //dataType: "json",
            data: senderId,
            //cache: false,
            //timeout: 600000,
            xhrFields: {
                withCredentials: true
            },
            success: function (data) {
                console.log("successfully rejected");
                $('#notifMsgBlock').fadeOut('slow');
                showInfo("Successfully rejected");
                resetBell();
            },
            error: function (e) {
                let jsonErr = e.responseText;
                console.log("rejectError = "+jsonErr);
                showInfo("ooops... something went wrong!");
                resetBell();
            }
        });

    });
    function resetBell(){
        //have to clear all texts
        $('#notifMsgBlock').fadeOut('slow');
        $('.notifIconBlock').removeClass("red");
        $('.notifIconBlock > i').removeClass("animated");
    }
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
            beforeSend: function(xhr){
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
                console.log("acceptError = "+jsonErr);
                showInfo("ooops... something went wrong!");
                resetBell();
            }
        });
    });

    //=================== /ACCEPT REQUEST ===================


    //=================== SENT REQUESTS ===================
    //show sent requests
    $('#sentRequests').click(function () {
        loaderOn();
        $('.homeContent').hide();
        $( "div.simplebar-content > div" ).remove();

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/requests/sentRequests",
            success: function (data) {
                console.log("successfully done getSentRequests call");

                $( "div.simplebar-content > div" ).remove();

                if (data) {
                    let titleRow = $('<div/>');
                    titleRow.attr('class', 'sentRequestRow');
                    titleRow.append("<p class='sentRequestRowTitle'>Recipient</p>");
                    titleRow.append("<p class='sentRequestRowTitle'>Sent date</p>");
                    titleRow.append("<p class='sentRequestRowTitle'>Status</p>");
                    $("div.simplebar-content").append(titleRow);

                    $.each(data, function (index, element) {
                        let row = $('<div/>');
                        row.attr('id', element.requestId);
                        row.attr('class', 'sentRequestRow');
                        row.append("<p class='sentRequestInfoItem'>" + element.recipient.firstName + " " + element.recipient.lastName + "</p>")
                        row.append("<p class='sentRequestInfoItem'>" + element.requestDate + "</p>");
                        row.append("<p class='sentRequestInfoItem'>" + element.status + "</p>");
                        $("div.simplebar-content").append(row);
                    });
                }else{
                    let emptyRequests = $('<div/>');
                    emptyRequests.attr('class', 'emptyRequests');
                    emptyRequests.text("No sent request !");
                    $("div.simplebar-content").append(emptyRequests);
                }

                setTimeout(function () {
                    loaderOff();
                    $('.homeContent').fadeIn('slow');
                },700);

            },
            error: function (e) {
                alert(e);
                let jsonErr = e.responseText;
                console.log("sentReqErr = "+jsonErr);
            }
        });
    });
    //=================== /SENT REQUESTS ===================

    //=================== RECEIVED REQUESTS ===================
    //show received requests
    $('#receivedRequests').click(function () {
        loaderOn();
        $('.homeContent').hide();
        $( "div.simplebar-content > div" ).remove();

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/requests/receivedRequests",

            success: function (data) {
                console.log("successfully done getReceivedRequests call");

                $( "div.simplebar-content > div" ).remove();

                if (data) {
                    let titleRow = $('<div/>');
                    titleRow.attr('class', 'sentRequestRow');
                    titleRow.append("<p class='sentRequestRowTitle'>Sender</p>");
                    titleRow.append("<p class='sentRequestRowTitle'>Sent date</p>");
                    titleRow.append("<p class='sentRequestRowTitle'>Status</p>");
                    $("div.simplebar-content").append(titleRow);

                    $.each(data, function (index, element) {
                        let row = $('<div/>');
                        row.attr('id', element.requestId);
                        row.attr('class', 'sentRequestRow');
                        row.append("<p class='sentRequestInfoItem'>" + element.sender.firstName + " " + element.sender.lastName + "</p>")
                        row.append("<p class='sentRequestInfoItem'>" + element.requestDate + "</p>");
                        row.append("<p class='sentRequestInfoItem'>" + element.status + "</p>");
                        $("div.simplebar-content").append(row);
                    });
                }else{
                    let emptyRequests = $('<div/>');
                    emptyRequests.attr('class', 'emptyRequests');
                    emptyRequests.text("No received request !");
                    $("div.simplebar-content").append(emptyRequests);
                }

                setTimeout(function () {
                    loaderOff();
                    $('.homeContent').fadeIn('slow');
                },700);

            },
            error: function (e) {
                alert(e);
                let jsonErr = e.responseText;
                console.log("sentReqErr = "+jsonErr);
            }
        });
    });
    //=================== /RECEIVED REQUESTS ===================



    //=================== WishList ===================

    //=================== ADD WISH ===================




    $('#notifCloseIcon').click(function () {
        $('#notifMsgBlock').fadeOut('slow');
        //showInfo("Received requests");
        resetBell();

    });
    //=================== DRAFT ===================

    /////////////////////////////////////////
   /* $(function(){
        //function1().done(function(){
            // function1 is done, we can now call function2
          //  console.log('function1 is done!');

            function2().done(function(){
                //function2 is done
                console.log('function2 is done!');
            });
        //});
    });

    function function2(){
        let dfd = $.Deferred();
        setTimeout(function(){
            // doing async stuff
            console.log('task 1 in function2 is done!');
            dfd.resolve();
        }, 2000);
        return dfd.promise();
    }*/

    /*function function1(){
        var dfrd1 = $.Deferred();
        var dfrd2= $.Deferred();

        setTimeout(function(){
            // doing async stuff
            console.log('task 1 in function1 is done!');
            dfrd1.resolve();
        }, 1000);

        setTimeout(function(){
            // doing more async stuff
            console.log('task 2 in function1 is done!');
            dfrd2.resolve();
        }, 750);

        return $.when(dfrd1, dfrd2).done(function(){
            console.log('both tasks in function1 are done');
            // Both asyncs tasks are done
        }).promise();
    }*/








    $('html').click(function () {
        //$('#notifEmpty').toggle($('#notifEmpty').is(':visible'));
    });


});// /document ready


//==============================================

    //LOADING gif block show - hide
    function loaderOn(){
        //$loader.fadeIn('fast');
        $('#loader').fadeIn('fast');
    }
    function loaderOff(){
        //$loader.hide();
        $('#loader').hide();
    }

//==============================================
    function showInfo(msg) {
        $('#infoText').text(msg);

        const $info = $('#info');
        $info.fadeIn('slow');
        setTimeout(function () {
            $info.fadeOut('slow');
        }, 3000);
    }