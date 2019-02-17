function check() {
    //alert(123);
    console.log("user.js loaded!");
}

function getUsers() {
    console.log("ajax");
    //let dfd = $.Deferred();

    return $.ajax({
        xhrFields: {
            withCredentials: true
        },
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/users",

        //async: false,
        //data: JSON.stringify(search),
        //dataType: "json",
        cache: false,
        timeout: 600000,
        success: function (data) {
            //let jsonResp = JSON.stringify(data, null, 4);
            //console.log(jsonResp);
           /* let sum = 0;
            for (let i = 0; i < 1000000000; i++) {
                sum+=i;
            }
            console.log("sum = " +sum);*/
            let hasChildByClassName = !!$("div.homeContent").has("div.simplebar-content").length;
            if(hasChildByClassName){
                $( "div.simplebar-content > div" ).remove();
            }
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

                //$('.homeContent').append(row);
                if(hasChildByClassName){
                    $( "div.simplebar-content" ).append(row);
                }else{
                    $('.homeContent').append(row);
                }

            });
           // $('.homeContent').show();


        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log(jsonErr);
            alert(e.status);
        }
    });

    /*.then(function () {
        dfd.resolve("Finished fading out!");
        return dfd.promise();
    });*/



}