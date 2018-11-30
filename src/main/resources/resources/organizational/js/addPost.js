var position_id = "";
var dept_id = "";

$(function(){
    mini.parse();
    position_id = getQueryString("position_id");
    dept_id = getQueryString("dept_id");
    var position_idText = mini.get("position_id");
    var dept_idText = mini.get("dept_id");
    var position_nameText = mini.get("position_name");
    var order_noText = mini.get("order_no");
    var memoText = mini.get("memo");
    $.ajax({
        type: "POST",
        url: "/organ/selectPost",
        data: {
            dept_id : dept_id
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
		cache: false,
		async: false,
        success: function (result) {
            if(result.code == 200){
                dept_idText.setValue(result.data.dept_id);
                dept_idText.setText(result.data.dept_name);
            }
        }
    });
    if(position_id != null && position_id != ""){
        $.ajax({
            type: "POST",
            url: "/organ/selectPosition",
            data: {
                position_id : position_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
    		cache: false,
    		async: false,
            success: function (result) {
                if(result.code == 200){
                    position_idText.setValue(result.data.position_id);
                    position_nameText.setValue(result.data.position_name);
                    order_noText.setValue(result.data.order_no);
                    memoText.setValue(result.data.memo);
                    dept_idText.setValue(result.data.dept_id);
                    dept_idText.setText(result.data.dept_name);
                }
            }
        });
    }
});

/**
 * 选择列表
 * @param e
 */
function onButtonEdit(e) {
    var btnEdit = this;
    mini.open({
        url: "/organ/departmentSelect",
        title: "选择列表",
        width: 650,
        height: 380,
        ondestroy: function (action) {
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = iframe.contentWindow.GetData();
                data = mini.clone(data);    //必须
                if (data) {
                    btnEdit.setValue(data.dept_id);
                    btnEdit.setText(data.dept_name);
                }
            }
        }
    });
}

/**
 * 保存岗位
 *
 * @param _this
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
        url: "/organ/addPosition",
        data: {
            json : json
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                CloseWindow("ok");
            }else{
                alert("提交有误，请重新尝试");
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