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
        //$('#welcomeText').remove();
        $('#welcomeText').fadeOut('slow');
    }, 3000);

    //
    const loggedInUserId = $('#currentUser').data("id");




    //load user.js
    $.getScript("js/user.js", function (e) {
        check();
    });





    $('#users').click(function () {

        loaderOn();

        //setTimeout(function () {
            //getUsers();//call to user.js
        $.when(getUsers()).done(function(){
            console.log( "getUsers done" );

            //load simplebar.js
            $.getScript("js/simplebar.js", function (e) {
                console.log("simplebar loaded");
                setTimeout(function () {
                    loaderOff();
                },500);
            });


            $('.homeContent').fadeIn('slow');
        });



        //},900);

    });


    /*
        //load request.js
        $.getScript( "js/request.js",function (e) {
            check();

        });*/


    let socket;
    let stompClient;
    //load sockjs.js
    $.getScript("js/sockjs.min.js", function (e) {
        socket = new SockJS('/ws');
            console.log("sockjs loaded");
    });

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

    //SOCKET
    //const socket = new SockJS('/ws');
    //const stompClient = Stomp.over(socket);

    // stompClient.connect({}, function (frame) {
    //     //setConnected(true);
    //     console.log('Connected: ' + frame);
    //     stompClient.subscribe('/client/notification', function (data) {
    //         //let message = data.body;
    //         //alert(message);
    //         //console.log(message);
    //         notify(data.body);
    //     });
    // });




    $(document).on("click", "span.sendRequestIcon", function () {
        //let loggedInUserId = $('#currentUser').data("id");
        let recipientId = $(this).parent().attr("id");
        //sendRequest(currId, userToId);
        let data = {};
        data['senderId'] = loggedInUserId;
        data['recipientId'] = recipientId;
            //alert(123);
        stompClient.send("/app/srvSocket", {}, JSON.stringify(data));

        $('#'+recipientId).slideToggle();
        showInfo('Request successfully sent!');


    });

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
                    $('#senderFullName').attr("data-sId", Sender.id);
                    $('#senderFullName').text(Sender.firstName + ' ' + Sender.lastName)
                    console.log("color changed!");
                    $('#notifMsgBlock').fadeIn('slow');
                },10000);

            }

        } else {
            console.log("not for you!");
        }
    }


    $('#reject').click(function () {
        let data = {};
        data["senderId"] = $('#senderFullName').attr("data-sId");
        data["recipientId"] = loggedInUserId;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/requests/reject",
            data: JSON.stringify(data),
            dataType: "json",
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("successfully rejected");
                $('#notifMsgBlock').fadeOut('slow');
                showInfo("Successfully rejected");
            },
            error: function (e) {
                let jsonErr = e.responseText;
                console.log(jsonErr);
            }
        });
    });

    $('#accept').click(function () {
        let data = {};
        data["senderId"] = $('#senderFullName').attr("data-sId");
        data["recipientId"] = loggedInUserId;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/requests/accept",
            data: JSON.stringify(data),
            dataType: "json",
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("successfully accepted");
                $('#notifMsgBlock').fadeOut('slow');
                showInfo("Congrats! Now You're friends.");
            },
            error: function (e) {
                alert(e);
                let jsonErr = e.responseText;
                console.log("qwe = "+jsonErr);
            }
        });
    });
    
    function showInfo(msg) {
        $('#infoText').text(msg);

        $('#info').fadeIn('slow');
        setTimeout(function () {
            $('#info').fadeOut('slow');
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







    //LAOD block show - hide
    function loaderOn(){
        $('#loader').fadeIn('fast');
    }
    function loaderOff(){
        $('#loader').hide();
    }






    $('html').click(function () {
        //$('#notifEmpty').toggle($('#notifEmpty').is(':visible'));
    });



















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


});