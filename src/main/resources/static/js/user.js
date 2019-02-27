function checkUser() {
    console.log("user.js loaded!");
}

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

function getUsers() {

    return $.ajax({
        xhrFields: {
            withCredentials: true
        },
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/users",
        dataType: "json",
        cache: false,
        timeout: 600000,
        success: function (data) {
            $( "div.simplebar-content > div" ).remove();

            $.each(data, function (index, element) {
                let row = $('<div/>');
                row.attr('id', element.id);
                row.attr('class', 'userRow');
                row.text(element.firstName + " " + element.lastName);

                let icon = $('<span/>');
                icon.attr('class', 'sendRequestIcon');
                icon.attr('title','Send friend request');
                icon.append('<i class="fa fa-plus" aria-hidden="true"></i>');

                row.append(icon);

                $( "div.simplebar-content" ).append(row);
            });
        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log(jsonErr);
            alert(e.status);
        }
    });
}

//user_row '+' icon click
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
