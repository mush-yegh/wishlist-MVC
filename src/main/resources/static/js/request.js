function checkRequest() {
    console.log("request.js loaded!");
}



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
            // alert(e);
            let jsonErr = e.responseText;
            console.log("sentReqErr = "+jsonErr);
        }
    });
});
//=================== /RECEIVED REQUESTS ===================

