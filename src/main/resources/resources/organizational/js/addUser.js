mini.parse();
var user_id = "";
var head_img=""
var url="" //图片地址
$(function(){
    user_id = getQueryString("user_id");
    head_img = mini.get("head_img");
    var user_idText = mini.get("user_id");
    var login_nameText = mini.get("login_name");
    var user_nameText = mini.get("user_name");
    var user_noText = mini.get("user_no");
    var user_typeText = mini.get("user_type");
    var position_idText = mini.get("position_id");
    var dept_nameText = mini.get("dept_name");
    var passwordText = mini.get("password");
    var emailText = mini.get("email");
    var user_sex = mini.get("user_sex");
    var work_phoneText = mini.get("work_phone");
    var mobile_noText = mini.get("mobile_no");
    var home_phoneText = mini.get("home_phone");
    var memoText = mini.get("memo");
    if(user_id != null && user_id != ''){
        $.ajax({
            type: "POST",
            url: "/user/userSelectData",
            data: {
                user_id : user_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function (result) {
                if(result.code == 200){
                    user_idText.setValue(result.data.user_id);
                    login_nameText.setEnabled(false);
                    login_nameText.setValue(result.data.login_name);
                    user_nameText.setValue(result.data.user_name);
                    position_idText.setEnabled(false);
                    user_noText.setValue(result.data.user_no);
                    user_typeText.setValue(result.data.user_type);
                    position_idText.setValue(result.data.position_id);
                    position_idText.setText(result.data.position_name);
                    dept_nameText.setValue(result.data.dept_name);
                    passwordText.setRequired(false);
                    passwordText.setEnabled(false);
                    emailText.setValue(result.data.email);
                    work_phoneText.setValue(result.data.work_phone);
                    mobile_noText.setValue(result.data.mobile_no);
                    home_phoneText.setValue(result.data.home_phone);
                    memoText.setValue(result.data.memo);
                    head_img.setText(result.data.head_img);
                    user_sex.setValue(result.data.user_sex);
                }
            }
        });
    }
});

/**
 * 选择岗位列表
 * @param e
 */
function onButtonEdit(e) {
    var btnEdit = this;
    var dept_name = mini.get("dept_name");
    mini.open({
        url: "/organ/positionListPage",
        title: "选择列表",
        width: 700,
        height: 500,
        ondestroy: function (action) {
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = iframe.contentWindow.GetData();
                data = mini.clone(data);    //必须
                if (data) {
                    btnEdit.setValue(data.position_id);
                    btnEdit.setText(data.position_name);
                    dept_name.setValue(data.dept_name);
                }
            }
        }
    });
}

/**
 * 保存用户
 *
 * @param _this
 */
function save(){
    var form = new mini.Form("#form1");
    form.validate();
    if (form.isValid() == false) return;
	//判断手机有效性
	 var mobile_no=mini.get("mobile_no").getValue();
	   var reg = /^1[34578]\d{9}$/;
	   if(mobile_no !='' &&reg.test(mobile_no) === false){
	       mini.alert("手机号码不合法，请重新输入");
	       return  false;  
	   }
    //提交数据
    var data = form.getData();
    if(url !=''){
    	data.head_img = url;//头像--服务器地址
    }else{
    	data.head_img = mini.get("head_img").getText();
    }
    var json = mini.encode(data);
    $.ajax({
        type: "POST",
        url: "/user/addUser",
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
//文件 
function onFileSelect(e) {
	var standardSize = 10 * 1024 * 1024;
	var fileSize = e.file.size;
	if (fileSize > standardSize) {
		mini.alert("文件最大不能超过10MB", "系统提示");
		head_img.clear();
	}
}

function onUploadSuccess(e) {
	mini.alert("上传成功", "系统提示");
	var obj = JSON.parse(e.serverData);
	this.setText(obj.path[0]);
	console.info(obj.path[0])
	url = obj.path[0];
}

function onUploadError(e) {
	mini.alert("上传失败", "系统提示");
}
//上传文件
function startUpload() {
	head_img.startUpload();
}
//删除上传文件
function delUpload(){
    if (confirm("是否确定删除文件！")) {
    	head_img.setText("");
        url = "";
    }
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