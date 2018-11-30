mini.parse();
$(document).keypress(function(e) {
	if (e.which == 13) {
		onLogin();
	}
});
//登录
function onLogin() {
	var username = $("#username").val();
	var password = $("#password").val();
	var remFlag = $("#rememberPwd").val();
	if (username == '' || username == null) {
		alert("账号不能为空！");
		return;
	} else {
		if (username.length < 3) {
			alert("账号不能少于3位！");
			return;
		}
	}
	if (password == '' || password == null) {
		alert("密码不能为空！");
		return;
	} else {
		if (password.length < 6) {
			alert("密码不能少于6位！");
			return;
		}
	}
	//判断是否选中复选框，如果选中，添加cookie  
	if ($("[name='checkbox']").attr("checked", "true")) {
		//添加cookie    
		setCookie("jbqgsl_username",$("#username").val());// 设置cookie      
		setCookie("jbqgsl_password",$("#password").val());
	} else {
		removeCookie();
	}
	$.ajax({
		dataType : "json",
		type : "POST",
		url : "/login/loginSubmit",
		data : {
			userName : username,
			passWord : password
		},
		cache : false,
		success : function(msg) {
			if (msg.code == 200) {
				window.location.href = "/manager/toIndex";
			} else {
				alert("账号或密码有误，请重新填写！");
			}
		}
	});
}

//忘记密码
function onButtonEdit(e) {
	var btnEdit = this;
	mini.open({
		url : "/login/forgetPwd?login_name=" + $("#username").val(),
		title : "忘记密码",
		width : 450,
		height : 300,
		ondestroy : function(action) {
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				var data = iframe.contentWindow.GetData();
				data = mini.clone(data); //必须
				if (data) {
					btnEdit.setValue(data.id);
					btnEdit.setText(data.name);
				}
			}

		}
	});

}
  

$(function() {
	//    $(".rememberPwd").click(function(){
	//    }).click();
								
	var loginCode = getCookie("jbqgsl_username");
	var pwd = getCookie("jbqgsl_password");
	if (loginCode) {// 用户名存在的话把用户名填充到用户名文本框    
		$("#username").val(mini.encode(loginCode).substring(1,mini.encode(loginCode).length-1));
	}
	if (pwd) {// 密码存在的话把密码填充到密码文本框    
		$("#password").val(mini.encode(pwd).substring(1,mini.encode(pwd).length-1));//去掉双引号
	} else {
		$("#password").val("");
	}
	
});