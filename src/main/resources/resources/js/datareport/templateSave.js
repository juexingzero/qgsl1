mini.parse();
var editProForm = new mini.Form("#tabs");//项目基本信息表单

function init(){
	var data = mini.getByName("template_id").getValue();
	console.log(data);
	Ajax({
        type : "post",
        url : "/web/v1/datareport/dataTemplateDetail",
        data : {"template_id":data},
        success : function(msg) {
            console.log(msg);
            if (errorCheck(msg)) {
                var result = mini.decode(msg.data);
                if(result != null) {
                    console.log(result);
                	setFormData("tabs", result);
                }
            }
        }
    });
}

//控制结束时间不能早于开始时间
function onDrawDate(e) {
    var date = e.date;
    var d = new Date();
    d.setDate(d.getDate()-1);
    if (date.getTime() < d.getTime()){
        e.allowSelect = false;
    }
}


//保存
function save(){
    editProForm.validate();
    if (editProForm.isValid() == false) return;

    var data = editProForm.getData();
    data.start_time = mini.getbyName("start_time").getFormValue();
    data.end_time = mini.getbyName("end_time").getFormValue();
    var start_time = onTimestampToDateStr(Date.parse(mini.getByName("start_time").getValue()));
    var end_time = onTimestampToDateStr(Date.parse(mini.getByName("end_time").getValue()));
    if(start_time == end_time){
        alert("模板开始跟结束时间不能相同");
        return;
    }else if(start_time > end_time){
        alert("模板开始时间不能大于结束时间");
        return;
    }
    data.introduce = mini.getbyName("introduce").getValue();
    console.log(data);
    Ajax({
        type : "post",
        url : "/web/v1/datareport/dataTemplateSave",
        data : {
            json : mini.encode(data)
        },
        success : function(msg) {
            if (errorCheck(msg)) {
                //保存成功后调取
                if(msg.data != null){
                    alert(msg.data);
                }else{
                    returns('ok');
                }
            }
        }
    });
}


//编辑
function edit(){
    editProForm.validate();
    if (editProForm.isValid() == false) return;

    var data = editProForm.getData();
    data.start_time = mini.getbyName("start_time").getFormValue();
    data.end_time = mini.getbyName("end_time").getFormValue();
    data.introduce = mini.getbyName("introduce").getValue();
    console.log(data);
    Ajax({
        type : "post",
        url : "/web/v1/datareport/dataTemplateEdit",
        data : {
            json : mini.encode(data)
        },
        success : function(msg) {
            if (errorCheck(msg)) {
                //保存成功后调取
                if(msg.data != null){
                    alert(msg.data);
                }else{
                    returns('ok');
                }
            }
        }
    });
}

//返回
function returns(action){
	//window.location.href = "/web/v1/institution/toInstitutionListPage";
	 if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	    else window.close();
}


//加载数据
$(function(){
	init();
});
