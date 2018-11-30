$(function(){
    mini.parse();
    var dict_idText = mini.get("dict_id");
    var dict_typeText = mini.get("dict_type");
    var dict_nameText = mini.get("dict_name");
    var dict_pidText = mini.get("dict_pid");
    var dict_valueText = mini.get("dict_value");
    var isSystemText = mini.get("isSystem");
    var dict_id = getQueryString("dict_id");
    $.ajax({
        type: "POST",
        url: "/dict/dictionarySelect",
        data: {
            dict_id : dict_id
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                if(dict_id != null && dict_id != ''){
                    dict_idText.setValue(result.data.dict_id);
                    dict_typeText.setValue(result.data.dict_type);
                    dict_nameText.setValue(result.data.dict_name);
                    dict_pidText.setValue(result.data.dict_pid);
                    dict_pidText.setText(result.data.dict_pidName);
                    dict_valueText.setValue(result.data.dict_value);
                    isSystemText.setValue(result.data.isSystem);
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
        url: "/dict/addDictionary",
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
 * 选择上级
 * @param e
 */
function onButtonEdit(e) {
    var btnEdit = this;
    mini.open({
        url: "/dict/dictSelect",
        title: "选择列表",
        width: 650,
        height: 380,
        ondestroy: function (action) {
            //if (action == "close") return false;
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = iframe.contentWindow.GetData();
                data = mini.clone(data);    //必须
                if (data) {
                    btnEdit.setValue(data.dict_id);
                    btnEdit.setText(data.dict_name);
                }
            }
        }
    });
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