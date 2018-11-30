$(function(){
    mini.parse();
    var update_bools = mini.get("update_bools");
    var p_menu_text = mini.get("p_menu_text");
    var menu_idText = mini.get("menu_id");
    var p_menu_idText = mini.get("p_menu_id");
    var menu_nameText = mini.get("menu_name");
    var p_menu_nameText = mini.get("p_menu_name");
    var menu_urlText = mini.get("menu_url");
    var menu_pic_cssText = mini.get("menu_pic_css");
    var order_noText = mini.get("order_no");
    var menu_id = getQueryString("menu_id");
    var p_menu_id = getQueryString("p_menu_id");
    $.ajax({
        type: "POST",
        url: "/organ/selectMenu",
        data: {
            p_menu_id : p_menu_id,
            menu_id : menu_id
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                if(menu_id != null && menu_id != ''){
                    p_menu_text.setValue(result.data.p_menu_id+".");
                    menu_idText.setValue(result.data.menu_id.replace(result.data.p_menu_id+".",""));
                    menu_idText.setEnabled(false);
                    update_bools.setValue("true");
                    p_menu_idText.setValue(result.data.p_menu_id);
                    menu_nameText.setValue(result.data.menu_name);
                    menu_pic_cssText.setValue(result.data.menu_pic_css);
                    menu_pic_cssText.setText(result.data.menu_pic_css);
                    p_menu_nameText.setValue(result.data.p_menu_name);
                    menu_urlText.setText(result.data.menu_url);
                    order_noText.setValue(result.data.order_no);
                }else if(result.data.p_menu_id == ""){
                    p_menu_text.setValue("");
                    p_menu_idText.setValue("-1");
                }else{
                    update_bools.setValue("false");
                    p_menu_text.setValue(result.data.menu_id+".");
                    p_menu_idText.setValue(result.data.menu_id);
                    p_menu_nameText.setValue(result.data.p_menu_name);
                }
            }
        }
    });
});

/**
 * 选择图标列表
 * @param e
 */
function onIconEdit(e) {
    var btnEdit = this;
    var icon_class = btnEdit.getValue();
    mini.open({
        url: "/organ/icon?icon_class="+icon_class,
        title: "选择图标",
        width: 800,
        height: 800,
        ondestroy: function (action) {
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = $(iframe.contentWindow.document.getElementById("icon_class")).attr("class").replace(" write", "");
                btnEdit.setValue(data);
                btnEdit.setText(data);
            }
        }
    });
}

/**
 * 添加菜单
 */
function save(){
    var form = new mini.Form("#form1");
    form.validate();
    if (form.isValid() == false) return;
    //提交数据
    var data = form.getData();
    var json = mini.encode(data);
    $.ajax({
        type: "POST",
        url: "/organ/addMenuData",
        data: {
            json : json
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                CloseWindow("ok");
            }else{
                alert(result.msg);
            }
        }
    });
}

function CloseWindow(action) {
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
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