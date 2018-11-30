$(function(){
    mini.parse();
    var dept_idText = mini.get("dept_id");
    var p_dept_idText = mini.get("p_dept_id");
    var dept_codeText = mini.get("dept_code");
    var dept_nameText = mini.get("dept_name");
    var leader_idText = mini.get("leader_id");
    var p_dept_id = getQueryString("p_dept_id");
    var dept_id = getQueryString("dept_id");
    var order_noText = mini.get("order_no");
    var inner_outer_dept = mini.getByName("inner_outer_dept");
    $.ajax({
        type: "POST",
        url: "/organ/topDepartment",
        data: {
            p_dept_id : p_dept_id,
            dept_id : dept_id
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                if(dept_id != null && dept_id != ''){
                    dept_idText.setValue(result.data.dept_id);
                    dept_codeText.setValue(result.data.dept_code);
                    dept_nameText.setValue(result.data.dept_name);
                    leader_idText.setValue(result.data.leader_id);
                    leader_idText.setText(result.data.leader_name)
                    p_dept_idText.setValue(result.data.p_dept_id);
                    p_dept_idText.setText(result.data.p_dept_name);
                    order_noText.setValue(result.data.order_no);
                    inner_outer_dept.setValue(result.data.inner_outer_dept);
                }else{
                    p_dept_idText.setValue(result.data.dept_id);
                    p_dept_idText.setText(result.data.dept_name);
                }
            }
        }
    });
});
function onButtonEdit(e) {
    var btnEdit = this;
    mini.open({
        url: "/organ/userSelect",
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
                    btnEdit.setValue(data.user_id);
                    btnEdit.setText(data.user_name);
                }
            }
        }
    });
}
/**
 * 双击--上级部门
 * @param e
 * @returns
 */
function onButtonDeptEdit(e) {
	var btnEdit = this;
	mini.open({
		url: "/organ/organizeMenuList",
		title: "选择部门",
		width: 350,
		height: 450,
		ondestroy: function (action) { 
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = iframe.contentWindow.GetData();
                 data = mini.clone(data);
                 if (data) {
                     btnEdit.setValue(data.dept_id);
                     btnEdit.setText(data.dept_name);
                 }
             }
         }
	    
	});
}

/**
 * 保存组织架构
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
        url: "/organ/addDepartment",
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