;
function bookmark() {
    $('#menu-page').removeAttr('hidden');
    $('#menu-page').addClass('bb-item');
    $('#bb-bookblock').bookblock('update');
    $('#bb-bookblock').bookblock('first');
}

function loadPhoto(i, filename, title) {
    $page = $('<div class="bb-item"><div class="left-page"></div><div class="right-page"></div></div>');
    $photo = $('<div class="photo-base"> <a data-fancybox="image"><img></a> <h3></h3> </div>');

    $photo.addClass("photo-"+(i+1));
    $photo.find("img").attr("src", "/files/"+filename);
    $photo.children("a").attr("href", "/files/"+filename);
    $photo.children("h3").text(title);

    if ((i % 6) < 3) {
        $($(".bb-item")[0]).children(".left-page").append($photo);
    }else {
        $($(".bb-item")[0]).children(".right-page").append($photo);
    }
}