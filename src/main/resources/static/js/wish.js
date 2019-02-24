function checkWish() {console.log("wish.js loaded!");}

//=================== WishList ===================

$('#wishList').click(function () {
    loaderOn();
    $('.homeContent').hide();
    $( "div.simplebar-content > div" ).remove();

    //getWishList();
    $.when(getWishList()).done(function(){

        setTimeout(function () {
            loaderOff();
            $('.homeContent').fadeIn('slow');
        },700);

    });

});
function getWishList(){
    let $simplebarContent = $("div.simplebar-content");
    return $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/wishes",
        success: function (data) {
            console.log("successfully done getWishList call");
            //addWish button
            let wishRow = $('<div/>');
            wishRow.attr('class', 'wishListAddButton');
            wishRow.append("<span class='wishRowIcon' title='Add' id='wishRowIconAdd'><i class='fa fa-plus'></i></span>");
            wishRow.append("<div class='clear'></div>");
            $simplebarContent.append(wishRow);

            if (data.length !== 0) {
                $.each(data, function (index, element) {
                    let wishRow = $('<div/>');
                    wishRow.attr('class', 'wishListRow');
                    wishRow.attr('id', element.id);
                    wishRow.append("<span class='wishTitle'>" + element.title + "</span>");
                    wishRow.append("<span class='wishRowIcon' id='wishRowIconDelete' title='Delete'><i class='fa fa-trash'></i></span>");
                    wishRow.append("<span class='wishRowIcon' id='wishRowIconEdit' title='Edit'><i class='fa fa-edit'></i></span>");
                    wishRow.append("<div class='clear'></div>");
                    $simplebarContent.append(wishRow);
                });
            }else{
                let emptyWishList = $('<div/>');
                emptyWishList.attr('class', 'emptyRequests');
                emptyWishList.text("You have no wish !");
                $simplebarContent.append(emptyWishList);
            }
        },
        error: function (e) {
            //alert(e);
            let jsonErr = e.responseText;
            console.log("getWishListErr = "+jsonErr);
            let emptyWishList = $('<div/>');
            emptyWishList.attr('class', 'emptyRequests');
            emptyWishList.text("ooops :"+jsonErr);
            $simplebarContent.append(emptyWishList);
        }
    });
}
//=================== /WishList ===================


//=================== AddNewWish ===================

    //=================== AddNewWish Block ===================

$(document).on("click", "#wishRowIconAdd", function () {
    loaderOn();
    $('.homeContent').hide();
    $( "div.simplebar-content > div" ).remove();


    let wishBlock = $('<div/>');
    wishBlock.attr('class', 'newWishBlock');

    wishBlock.append("<div class='newWishBlockTitle'>ADD NEW WISH</div>");

    wishBlock.append(createWishRowInput('Title'));
    wishBlock.append(createWishRowInput('Link'));
    wishBlock.append(createWishRowTxtArea('Description'));
    wishBlock.append(createButtons('saveNewWish','cancelNewWish'));
    //wishBlock.append(createWishRow('Price'));

    wishBlock.append("<div class='clear'></div>");
    $("div.simplebar-content").append(wishBlock);


    setTimeout(function () {
        loaderOff();
        $('.homeContent').fadeIn('slow');
    },700);

});
function createWishRowInput(name, value){
    let $val = value? value : '';

    let wishRow = $('<div/>');
    wishRow.attr('class', 'wishRow');
    wishRow.append(
        "<div class='wishPropertyTitle'>" +
        "<label for='wish"+ name+"'>"+name+"</label>" +
        "</div>");
    wishRow.append(
        "<div class='wishPropertyInput'>" +
        "<input type='text' id='wish"+name+"' name='wish"+name+"' value='"+ $val +"'/>" +
        "</div>");
    wishRow.append("<div class='clear'></div>");
    return wishRow;
}
function createWishRowTxtArea(name, value){
    let $val = value? value : '';

    let wishRow = $('<div/>');
    wishRow.attr('class', 'wishRow');
    wishRow.append(
        "<div class='wishPropertyTitle'>" +
        "<label for='wish"+ name+"'>"+name+"</label>" +
        "</div>");
    wishRow.append(
        "<div class='wishPropertyInput'>" +
        "<textarea type='text' id='wish"+name+"' name='wish"+name+"'>" + $val + "</textarea>" +
        "</div>");
    wishRow.append("<div class='clear'></div>");
    return wishRow;
}
function createButtons(saveButtonId, cancelButtonId){
    let wishRow = $('<div/>');
    wishRow.attr('class', 'wishRow');
    wishRow.append("<div class='wishSaveButton' id='"+saveButtonId+"'>Save</div>");
    wishRow.append("<div class='wishCancelButton' id='"+cancelButtonId+"'>Cencel</div>");
    wishRow.append("<div class='clear'></div>");
    return wishRow;
}

    //=================== /AddNewWish Block ===================

    //=================== AddNewWish cancel button click ===================

$(document).on("click", "#cancelNewWish", function () {
    $("#wishList").trigger( "click" );
});

    //=================== /AddNewWish cancel button click ===================

    //=================== AddNewWish Save button click ===================

$(document).on("click", "#saveNewWish", function () {
    let title = $('#wishTitle').val();
    if (!title.trim()){
        showInfo("Title can't be empty!");
        return false;
    }
    let data = {};
    let link = $('#wishLink').val();
    let description = $('#wishDescription').val();

    data['title'] = title;
    data['link'] = link;
    data['description'] = description;

    let header = $("meta[name='_csrf_header']").attr("content");
    let token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/wishes",
        data: JSON.stringify(data),
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            console.log("new wish  successfully saved");
            //showInfo("Successfully added");
            $("#wishList").trigger( "click" );
        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log("new wish save error = "+jsonErr);
            showInfo("ooops... something went wrong!");
        }
    });


});

    //=================== /AddNewWish Save button click ===================

//=================== AddNewWish ===================


//=================== EditWish ===================

    //=================== EditWish block ===================

$(document).on("click", "#wishRowIconEdit", function () {
    let $rowId = $(this).parent('.wishListRow').attr('id');

    loaderOn();
    $('.homeContent').hide();
    $( "div.simplebar-content > div" ).remove();

    let $wish;
    $.ajax({
        type: "GET",
        contentType: "text/plain",
        url: "/wishes/"+$rowId,
        async: false,//I know this is so bad
        success: function (data) {
            $wish = data;
        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log("get wishByIdError = " + jsonErr);
            showInfo("ooops... something went wrong!");
            $("#wishList").trigger( "click" );
        }
    });

    let wishBlock = $('<div/>');
    wishBlock.attr('class', 'newWishBlock');

    wishBlock.append("<div class='newWishBlockTitle' id='" + $wish.id + "'>EDIT WISH</div>");

    wishBlock.append(createWishRowInput('Title', $wish.title));
    wishBlock.append(createWishRowInput('Link', $wish.link));
    wishBlock.append(createWishRowTxtArea('Description', $wish.description));

    wishBlock.append(createButtons("saveEditedWish","cancelNewWish"));

    //wishBlock.append(createWishRow('Price'));

    wishBlock.append("<div class='clear'></div>");
    $("div.simplebar-content").append(wishBlock);


    setTimeout(function () {
        loaderOff();
        $('.homeContent').fadeIn('slow');
    },700);

});
    //=================== /EditWish block ===================

    //=================== EditWish Save button click ===================

$(document).on("click", "#saveEditedWish", function () {
    let title = $('#wishTitle').val();
    if (!title.trim()){
        showInfo("Title can't be empty!");
        return false;
    }
    let data = {};
    let wishId = $('.newWishBlockTitle').attr('id');
    let link = $('#wishLink').val();
    let description = $('#wishDescription').val();

    data['id'] = wishId;
    data['title'] = title;
    data['link'] = link;
    data['description'] = description;

    let header = $("meta[name='_csrf_header']").attr("content");
    let token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/wishes",
        data: JSON.stringify(data),
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            console.log("edited wish  successfully saved");
            $("#wishList").trigger( "click" );
        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log("edit save error = "+jsonErr);
            showInfo("ooops... something went wrong!");
        }
    });


});

    //=================== /EditWish Save button click ===================

//=================== /EditWish ===================

//=================== DeleteWish ===================
$(document).on("click", "#wishRowIconDelete", function () {
    loaderOn();

    let $wishId = $(this).parent('.wishListRow').attr('id');

    let header = $("meta[name='_csrf_header']").attr("content");
    let token = $("meta[name='_csrf']").attr("content");

    $.ajax({
        type: "DELETE",
        contentType: "text/plain",
        url: "/wishes/"+$wishId,
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            console.log("wish deleted successfully");
            //$("#wishList").trigger( "click" );
            $("#"+$wishId).slideUp();
            setTimeout(function () {
                loaderOff();
            },500);
        },
        error: function (e) {
            let jsonErr = e.responseText;
            console.log("wish delete error = "+jsonErr);
            showInfo("ooops... something went wrong!");
        }
    });

});
//=================== / DeleteWish ===================
