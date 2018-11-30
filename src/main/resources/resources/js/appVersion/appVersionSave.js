mini.parse();
var fileVideo = mini.get("file");//文件路径
var url="";
function onValueChanged(e) {
	var checked = this.getChecked();
	if (checked) {
		mini.get("is_force1").setValue("1");//强制更新
	} else {
		mini.get("is_force1").setValue("0");//不强制更新
	}
}
/**
 * 保存 
 * @param closeWin
 * @returns
 */
function save() {
	var form = new mini.Form("#addForm");
    form.validate();//验证
    if (form.isValid() == false) return;
	var data = form.getData();
	data.url = url;
	data.is_force=mini.get("is_force1").getValue();
	//验证版本号名称格式
	if(data.version!=null && data.version !=""){
		if(!(/^(\d+\.(\d\.)+\d)$/.test(data.version))){
			alert("请输入正确格式的版本号！");
			return;
		}
	}
	//安卓必须要上传文件才能保存
	if(data.channal=="2" && data.url=="" ){
		mini.alert("安卓平台必须先上传*.apk文件！");
		return;
	}
	//保存
	Ajax({
		type : "post",
		url : "/web/v1/version/versionSave",
		data : {
			json : mini.encode(data),
		},
		success : function(data) {
			if(data.state==0){
				mini.alert(data.message);
			}else{
				//保存成功后调取
				returns('ok');
			}
		}
	});
}

//文件 *.apk
function onFileSelect(e) {
	var standardSize = 150 * 1024 * 1024;
	var fileSize = e.file.size;
	if (fileSize > standardSize) {
		mini.alert("文件最大不能超过150MB", "系统提示");
		fileVideo.clear();
	}
}

function onUploadSuccess(e) {
	mini.alert("上传成功", "系统提示");
	var obj = JSON.parse(e.serverData);
	this.setText(obj.path[0]);
	url = obj.path[0];
}

function onUploadError(e) {
	mini.alert("上传失败", "系统提示");
}

//上传文件
function startUpload() {
    fileVideo.startUpload();
}

//删除上传文件
function delUpload(){
    if (confirm("是否确定删除文件！")) {
        fileVideo.setText("");
        url = "";
    }
}

//返回
function returns(action) {
	if (window.CloseOwnerWindow)
		return window.CloseOwnerWindow(action);
	else
		window.close();
}

function init() {
	var data = mini.getByName("id").getValue();
	if (data != null && data != "") {
		Ajax({
			type : "post",
			url : "/web/v1/version/appVersion",
			data : {
				"id" : data,
			},
			success : function(msg) {
				var result = mini.decode(msg);
				if (result != null) {
					setFormData("addForm", result);
					if (result.is_force == 1) {
						var is_force = mini.getByName("is_force");
						is_force.setChecked(true);
					}
					
					if (result.file !=null) {
						var file = mini.getByName("file");
						file.setText(result.file);
						//版本号 以及文件都不能更改
						mini.get("version").setEnabled(false);
						mini.get("channal").setEnabled(false);
						mini.get("file").setEnabled(false);
		            	mini.get("fileupload").setEnabled(false);
		            	mini.get("filedel").setEnabled(false);
					}
					url=result.url;
				}
			}
		});
	}
}

//加载列表
$(function() {
	init();
});
