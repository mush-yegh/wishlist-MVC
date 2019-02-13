function check() {
    //alert(123);
    console.log("user.js loaded!");
}

function getUsers() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/users",
        //data: JSON.stringify(search),
        //dataType: "json",
        cache: false,
        timeout: 600000,
        success: function (data) {
            //let jsonResp = JSON.stringify(data, null, 4);
            //console.log(jsonResp);

            $.each(data, function (index, element) {
                let row = $('<div/>');
                row.attr('id', element.id);
                row.attr('class', 'userRow');
                    //console.log("elem = " + element.firstName);
                row.text(element.firstName + " " + element.lastName);

                let icon = $('<span/>');
                icon.attr('class', 'sendRequestIcon');
                icon.attr('title','Send friend request');
                icon.append('<i class="fa fa-plus" aria-hidden="true"></i>');
                //icon.click(handle());

                row.append(icon);


                $('.homeContent').append(row);

            })
            $('.homeContent').show();

        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log(jsonErr);
        }
    })
}