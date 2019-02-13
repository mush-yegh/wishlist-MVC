function check() {
    //alert(123);
    console.log("request.js loaded!");
}


function sendRequest(currId, userToId) {
    console.log("currId " +currId);
    console.log("userToId " +userToId);
    console.log("send_request fired");

    let data = {};
    data["userFromId"] = currId;
    data["userToId"] = userToId;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/requests/sendRequest",
            data: JSON.stringify(data),
            dataType: "json",
            cache: false,
            timeout: 600000,
            success: function (resp) {

                /*$.each(data, function (index, element) {
                    let row = $('<div/>');
                    row.attr('id', element.id);
                    row.attr('class', 'userRow');
                    //console.log("elem = " + element.firstName);
                    row.text(element.firstName + " " + element.lastName);

                    $('.homeContent').append(row);

                })
                $('.homeContent').show();*/
                console.log("success!");
                console.log(resp);

            },
            error: function (e) {
                let jsonErr = e.responseText;
                console.log(jsonErr);
            }
        })

}