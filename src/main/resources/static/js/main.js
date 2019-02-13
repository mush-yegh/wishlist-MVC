$(document).ready(function () {

    $('p.logo').click(function () {
        var leftMenu = $('.leftNav');
        if (leftMenu.hasClass('visible')) {
            leftMenu.animate({'left': '-310px'}, 600).removeClass('visible');
        } else {
            leftMenu.animate({'left': '0'}, 600).addClass('visible');
        }
    });
    $('.leftNav').css('left', '-310px');


    setTimeout(function(){ $('#welcomeText').remove(); }, 3000);



    $('#users').click(function() {

       $.ajax({
            url: "localhost:8080/users"
        }).then(function(data) {
           console.log("data = " + data);
        });

    });//click


});