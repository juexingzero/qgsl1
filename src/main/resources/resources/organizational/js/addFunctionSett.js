$(function(){
    mini.parse();
    var update_bools = mini.get("update_bools");
    var func_id_text = mini.get("func_id_text");
    var func_idText = mini.get("func_id");
    var menu_idText = mini.get("menu_id");
    var menu_nameText = mini.get("menu_name");
    var func_nameText = mini.get("func_name");
    var func_descText = mini.get("func_desc");
    var order_noText = mini.get("order_no");
    var auth_flagText = mini.get("auth_flag");
    var func_levelText = mini.get("func_level");
    var menu_id = getQueryString("menu_id");
    var func_id = getQueryString("func_id");
    $.ajax({
        type: "POST",
        url: "/organ/selectFunc",
        data: {
            func_id : func_id,
            menu_id : menu_id
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                if(func_id != null && func_id != ''){
                    func_id_text.setValue(result.data.menu_id+".");
                    func_idText.setValue(result.data.func_id.replace(result.data.menu_id+".",""));
                    func_idText.setEnabled(false);
                    menu_idText.setValue(result.data.menu_id);
                    menu_nameText.setValue(result.data.menu_name);
                    update_bools.setValue("true");
                    func_nameText.setValue(result.data.func_name);
                    func_descText.setValue(result.data.func_desc);
                    order_noText.setValue(result.data.order_no);
                    auth_flagText.setValue(result.data.auth_flag);
                    func_levelText.setValue(result.data.func_level);
                }else{
                    update_bools.setValue("false");
                    func_idText.setEnabled(true);
                    func_id_text.setValue(result.data.menu_id+".");
                    menu_idText.setValue(result.data.menu_id);
                    menu_nameText.setValue(result.data.menu_name);
                }
            }
        }
    });
});

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
        url: "/organ/insertFunc",
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