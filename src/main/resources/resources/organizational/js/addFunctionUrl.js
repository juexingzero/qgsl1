$(function(){
    mini.parse();
    var update_bools = mini.get("update_bools");
    var url_id_text = mini.get("url_id_text");
    var url_idText = mini.get("url_id");
    var func_idText = mini.get("func_id");
    var order_noText = mini.get("order_no");
    var func_urlText = mini.get("func_url");
    var func_nameText = mini.get("func_name");
    var func_id = getQueryString("func_id");
    var url_id = getQueryString("url_id");
    $.ajax({
        type: "POST",
        url: "/organ/selectFuncUrl",
        data: {
            url_id : url_id,
            func_id : func_id
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                if(url_id != null && url_id != ''){
                    url_id_text.setValue(result.data.func_id+".");
                    url_idText.setValue(result.data.url_id.replace(result.data.func_id+".",""));
                    url_idText.setEnabled(false);
                    func_idText.setValue(result.data.func_id);
                    func_nameText.setValue(result.data.func_name);
                    update_bools.setValue("true");
                    order_noText.setValue(result.data.order_no);
                    func_urlText.setValue(result.data.func_url);
                }else{
                    update_bools.setValue("false");
                    func_idText.setEnabled(true);
                    url_id_text.setValue(result.data.func_id+".");
                    func_idText.setValue(result.data.func_id);
                    func_nameText.setValue(result.data.func_name);
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
        url: "/organ/addFuncUrl",
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