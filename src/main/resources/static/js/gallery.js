;
// function bookmark() {
//     $('#menu-page').removeAttr('hidden');
//     $('#menu-page').addClass('bb-item');
//     $('#bb-bookblock').bookblock('update');
//     $('#bb-bookblock').bookblock('first');
// }

function addRotate($image, rotate) {
    if(rotate == 0 || rotate == 90
        || rotate == 180 || rotate == 270) {
        $image.removeClass('rotate-0');
        $image.removeClass('rotate-90');
        $image.removeClass('rotate-180');
        $image.removeClass('rotate-270');
        $image.addClass('rotate-' + rotate);
    }
}

function loadPhoto(i, filename, title, ratioWH, rotate) {
    $page = $('<div class="bb-item"><div class="left-page"></div><div class="right-page"></div></div>');
    $photo = $('<div class="photo-base"> <a data-fancybox="image"><img></a> <p id="title"></p> </div>');
    var page = parseInt(i / 6),
        pos = i % 6;

    if (page > 0 && pos == 0) {
        $(".bb-bookblock").append($page);
    }

    $photo.addClass("photo-"+(pos+1));
    $photo.find("img").attr("src", "/files/"+filename);
    $photo.children("a").attr("href", "/files/"+filename);
    $photo.children("#title").text(title);

    // total area: 30 * 30
    var area = 28 * 28;
    if (ratioWH > 0) {
        var height = Math.sqrt(area / ratioWH);
        var width = height * ratioWH;
        $photo.css('height', height + '%');
        $photo.css('width', width + '%');
        addRotate($photo.find('img'), rotate);
    }

    if (rotate == 90 || rotate == 270) {
        $photo.find('img').css('width', (100 / ratioWH) + '%');
    }

    if (rotate == 270){
        $photo.find('img').css('right', (100 / ratioWH) + '%');
    }

    if (pos < 3) {
        $($(".bb-item")[page]).children(".left-page").append($photo);
    }else {
        $($(".bb-item")[page]).children(".right-page").append($photo);
    }
}


function handleError(status) {
    if (status.status == 401) {
        window.location.href = "/";
        document.cookie = "error=Please login first; path=/";
    }
}

function setCookie(cname, cval) {
    document.cookie = cname + "=" + cval;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++)
    {
        var c = ca[i].trim();
        if (c.indexOf(name)==0) return c.substring(name.length,c.length);
    }
    return "";
}

function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != "") {
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
}

function uploadComment() {
    var comment = $('#comment').val();
    var file = $('.fancybox-image').attr('src').split('/')[2];
    if(comment == null || comment == ''){
        return;
    }
    $.ajax({
        type: 'POST',
        url: "/gallery/comment/" + file,
        data: {
            comment: comment,
            group: galleryId
        },
        success: function (data) {
            if(data){
                var newComments = '';
                data.forEach(function (c) {
                   newComments += '<p><strong>'+c.username+'  </strong>'
                       + c.comment+'</p>';
                });
                $('.fancybox-comments').html(newComments);
                $('.fancybox-up').children('#comment').val('');
            }
        },
        error: function (status) {
            handleError(status);

        }
    })
}

function deletePhoto() {
    var confirm = prompt("确定要删除该照片吗? 输入'confirm'确定:", "");
    if(confirm == 'confirm') {
        var file = $('.fancybox-image').attr('src').split('/')[2];
        $.ajax({
            type: 'DELETE',
            url: "/gallery/photo/" + file + "?group=" + galleryId,
            data: {
                group: galleryId
            },
            success: function (data) {
                window.location.reload();
            },
            error: function (status) {
                handleError(status);
            }
        })
    }
}

function hideUpload() {
    $('.upload-form').hide();
    $('.upload-cover').hide();
}

function showUpload() {
    $('.upload-cover').show();
    $('.upload-form').show();
}

// $(window).resize(function () {
//     var ratio = $(document).height() / $(document).width();
//     if(0.5 < ratio && ratio < 2) {
//         $('.bb-custom-wrapper').css('height', '100%');
//         $('.bb-custom-wrapper').css('width', '100%');
//     }else {
//         $('.bb-custom-wrapper').css('height', '600px');
//         $('.bb-custom-wrapper').css('width', '800px');
//     }
// });