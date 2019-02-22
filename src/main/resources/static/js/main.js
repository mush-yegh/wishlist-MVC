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


    const $loader = $('#loader');
    //LOADING gif block show - hide
    function loaderOn(){
        $loader.fadeIn('fast');
    }
    function loaderOff(){
        $loader.hide();
    }

    //=================== /UI ANIMATIONS ===================

    //=================== LOAD OTHER SCRIPTS ===================
    //have to remove this field soon :)
    const loggedInUserId = $('#currentUser').data("id");

    //load user.js
    $.getScript("js/user.js", function (e) {
        check();
    });

    //load simplebar.js
    $.getScript("js/simplebar.js", function (e) {
        console.log("simplebar loaded");
    });
    //=================== /LOAD OTHER SCRIPTS ===================

    //=================== USERS ===================

    $('#users').click(function () {
        loaderOn();
        //getUsers();//call to user.js ajax
        $.when(getUsers()).done(function(){
                console.log( "getUsers done" );
            setTimeout(function () {
                loaderOff();
            },500);

            $('.homeContent').fadeIn('slow');
        });
    });
    //=================== /USER ===================

    /*
    //load request.js
    $.getScript( "js/request.js",function (e) {
        check();

    });
    */

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

    /*function sendFriendRequest(recipientId) {
        $.ajax('/app/srvSocket', {
            type: 'POST',
            data: recipientId,
            contentType: 'text/plain',
            xhrFields: {
                withCredentials: true
            },
            success: function (response) {
                alert("successfully sent!!!");
            }
        });
    }*/

    //user row + icon click
    $(document).on("click", "span.sendRequestIcon", function () {

        $(this).unbind();

        let recipientId = $(this).parent().attr("id");
        //sendRequest(currId, userToId);
        //let data = {};
        //data['recipientId'] = recipientId;
        //stompClient.send("/app/srvSocket", {}, JSON.stringify(data));

        stompClient.send("/app/srvSocket", {}, recipientId);

        hideUserRow(recipientId);
        showInfo('Request successfully sent!');

        /*$.when( stompClient.send("/app/srvSocket", {}, recipientId) ).done(function(){
            hideUserRow(recipientId);
            showInfo('Request successfully sent!');
        });*/
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
            console.log("resp" + RespBody);
        let notifType = RespBody.notifType;
            console.log("notifType " + notifType);

        let recipientId = RespBody.recipientId;
            console.log("recipient id " + recipientId);
        let Sender = RespBody.sender;
            console.log("sender id = " + Sender.id);
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

            }

        } else {
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
        //have to free all texts
        $('#notifMsgBlock').fadeOut('slow');
        $('.notifIconBlock').removeClass("red");
        $('.notifIconBlock > i').removeClass("animated");
    }
    //=================== REJECT REQUEST ===================

    //=================== ACCEPT REQUEST ===================
    $('#accept').click(function () {
        /*let data = {};
        data["senderId"] = $('#senderFullName').attr("data-sId");
        data["recipientId"] = loggedInUserId;*/
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

    function showInfo(msg) {
        $('#infoText').text(msg);

        const $info = $('#info');
        $info.fadeIn('slow');
        setTimeout(function () {
            $info.fadeOut('slow');
        }, 3000);
    }


    /*
    $('.notifIconBlock').click(function (event) {
        event.stopPropagation();
        $('#notifEmpty').fadeIn( "slow" );

        setTimeout(function () {
            $('#notifEmpty').fadeOut( "slow" );
        }, 3000 );
    });*/









    //=================== SENT REQUESTS ===================
    //show sent requests
    $('#sentRequests').click(function () {
        loaderOn();

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
                    emptyRequests.text("You have no sent request!");
                    $("div.simplebar-content").append(emptyRequests);
                }

                setTimeout(function () {
                    loaderOff();
                },500);

                $('.homeContent').fadeIn('slow');
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
                    emptyRequests.text("You have no received request!");
                    $("div.simplebar-content").append(emptyRequests);
                }

                setTimeout(function () {
                    loaderOff();
                },500);

                $('.homeContent').fadeIn('slow');
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
    $('#wishList').click(function () {
        loaderOn();

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/wishes",

            success: function (data) {
                console.log("successfully done getWishList call");

                $( "div.simplebar-content > div" ).remove();
                if (data) {
                    $.each(data, function (index, element) {
                        let wishRow = $('<div/>');
                        wishRow.attr('class', 'wishListRow');
                        wishRow.append("<span class='wishTitle'>" + element.title + "</span>");
                        wishRow.append("<span class='wishRowIcon' title='Delete'><i class='fa fa-trash'></i></span>");
                        wishRow.append("<span class='wishRowIcon' title='Edit'><i class='fa fa-edit'></i></span>");
                        wishRow.append("<div class='clear'></div>");
                        $("div.simplebar-content").append(wishRow);
                    });

                    /*$.each(data, function (index, element) {
                        let row = $('<div/>');
                        row.attr('id', element.requestId);
                        row.attr('class', 'sentRequestRow');
                        row.append("<p class='sentRequestInfoItem'>" + element.sender.firstName + " " + element.sender.lastName + "</p>")
                        row.append("<p class='sentRequestInfoItem'>" + element.requestDate + "</p>");
                        row.append("<p class='sentRequestInfoItem'>" + element.status + "</p>");
                        $("div.simplebar-content").append(row);
                    });*/
                }else{
                    let emptyWishList = $('<div/>');
                    emptyWishList.attr('class', 'emptyRequests');
                    emptyWishList.text("You have no wish !");
                    $("div.simplebar-content").append(emptyWishList);
                }

                setTimeout(function () {
                    loaderOff();
                },500);

                $('.homeContent').fadeIn('slow');
            },
            error: function (e) {
                alert(e);
                let jsonErr = e.responseText;
                console.log("getWishListErr = "+jsonErr);
            }
        });
    });
    //=================== /WishList ===================




    //=================== DRAFT ===================

    /////////////////////////////////////////
    $(function(){
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
    }

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


});