function checkFriend() {
    console.log("friend.js loaded!");
}

$('#friends').click(function () {
    getFriends();
});

function getFriends() {

    return $.ajax({
        xhrFields: {
            withCredentials: true
        },
        type: "GET",
        contentType: "application/json",
        url: "/friends",
        dataType: "json",
        cache: false,
        timeout: 600000,
        success: function (data) {
            $( "div.simplebar-content > div" ).remove();

            $.each(data, function (index, element) {
                let row = $('<div/>');
                row.attr('id', element.id);
                row.attr('class', 'userRow');
                row.text(element.friend.firstName + " " + element.friend.lastName);

                /*let icon = $('<span/>');
                icon.attr('class', 'sendRequestIcon');
                icon.attr('title','Send friend request');
                icon.append('<i class="fa fa-plus" aria-hidden="true"></i>');

                row.append(icon);*/

                $( "div.simplebar-content" ).append(row);
            });
        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log(jsonErr);
            //alert(e.status);
        }
    });

}