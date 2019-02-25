function checkFriend() {
    console.log("friend.js loaded!");
}

$('#friends').click(function () {
    loaderOn();
    $('.homeContent').hide();
    $( "div.simplebar-content > div" ).remove();

    $.when(getFriends()).done(function(){
        setTimeout(function () {
            loaderOff();
            $('.homeContent').fadeIn('slow');
        },700);
    });
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
                row.attr('id', 'friend_'+element.id);
                row.attr('class', 'friendRow');
                row.append("<span class='friendFullName'>"+ (index+1) +") "+ element.firstName + " " + element.lastName+"</span");
                row.append("<span class='friendBD'>"+element.birthDate+"</span");
                row.append("<div class='clear'></div>");

                let $friendWishList = element.wishList;
                let $frWishBlock = createWishBlock($friendWishList);

                row.append($frWishBlock);

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

function createWishBlock(wishList) {
    let $wishBlock = $('<div/>');
    $wishBlock.attr('class', 'friendWishBlock');

    if (wishList.length !== 0){
        $.each(wishList, function (index, wish) {
            let $wishItem = $('<div/>');
            $wishItem.attr('class', 'wishItem');

            $wishItem.append("<div class='wishItemNum'>"+(index+1)+"</div>");


            $wishItem.append("<div class='wishTitle'>Title:</div>");
            $wishItem.append("<div class='wishTitleText'>"+wish.title+"</div>");
            $wishItem.append("<div class='clear'></div>");
            $wishItem.append("<div class='wishLink'>Link:</div>");
            $wishItem.append("<div class='wishLinkText'>"+wish.title+"</div>");
            $wishItem.append("<div class='clear'></div>");
            $wishItem.append("<div class='wishDescription'>Description:</div>");
            $wishItem.append("<div class='wishDescriptionText'>"+wish.description+"</div>");
            $wishItem.append("<div class='clear'></div>");

            $wishBlock.append($wishItem);
        });
    }
    else {
        $wishBlock.append("<div class='emptyWishList'>wishList is empty</div>");
    }
    return $wishBlock;

}

$(document).on("click", "div.friendRow", function () {

    $(this).children('.friendWishBlock').slideToggle();
    $(this).toggleClass( "friendRow_withoutShadow" );
});

