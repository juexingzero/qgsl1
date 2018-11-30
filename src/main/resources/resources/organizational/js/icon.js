var classStr = "";
$(function(){
    var icons = getQueryString("icon_class")
    if(icons != null){
        classStr = icons;
        if(icons.indexOf(" ", 3) == -1){
            classStr += " write";
        }

    }
    // 颜色点击
    $(".check_box").click(function(){
        $(".check_box input").attr("checked", false);
        $(this).find("input").attr("checked", true);
        var color = $(this).parent().attr("class");
        var classStr = $("table td[class='click']").find("i").attr("class");
        $(".icon_preview i").attr("class", classStr+" "+color);
    });
    data();

});

/**
 * 读取图标
 * */
function data(){
    $.ajax({
        type: "POST",
        url: "/organ/selectIcon",
        data: {
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            $(".icon table tbody").html("");
            if(result.code == 200){
                var indexs = 1;
                var html = "";
                $(result.data).each(function(index, item){
                    if(indexs == 1){
                        html += "<tr>";
                    }
                    html += '<td><i class="'+ item.ic_class +'"></i></td>';
                    if(indexs == 10){
                        html += "</tr>";
                        indexs = 1;
                    }else{
                        indexs++;
                    }
                });
                $(".icon table tbody").html(html);
                $(".icon table td").click(function(){
                    $(".icon table td").attr("class","");
                    $(this).attr("class","click");
                    var color = $(".check_box input[checked=checked]").parent().parent().attr("class");
                    $(".icon_preview i").attr("class", $(this).find("i").attr("class")+" "+color);
                });
                if(classStr == ""){
                    $(".icon table td").eq(0).attr("class","click");
                    classStr = $(".icon table td").eq(0).find("i").attr("class");
                    $(".icon_preview i").attr("class", classStr+" write");
                }else{
                    $(".icon_preview i").attr("class", classStr);
                    var icon_class = classStr.substring(0, classStr.indexOf(" ", 3));
                    var color = classStr.substring(classStr.indexOf(" ", 3)+1, classStr.length);
                    $(".icon table td").each(function(i){
                        if($(this).find("i").attr("class") == icon_class){
                            $(this).attr("class","click");
                        }
                    });
                    $(".center_title .color li").each(function(){
                        if($(this).attr("class") == color){
                            $(".check_box input").attr("checked", false);
                            $(this).find("input").attr("checked",true);
                        }
                    });
                }
            }
        }
    });
}
//////////////////////////////////
function CloseWindow(action) {
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
}

function onOk() {
    CloseWindow("ok");
}
function onCancel() {
    CloseWindow("cancel");
}
/**
 * 获取url参数
 *
 * @param name 参数名称
 * @returns {*}
 * @constructor
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}