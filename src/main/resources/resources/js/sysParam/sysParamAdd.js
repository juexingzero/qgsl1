mini.parse();
var editProForm = new mini.Form("#form1");//项目基本信息表单

$(function(){
    init();
});

//获取数据
function init() {
    var param_id = mini.getbyName("param_id").getValue();
    if (param_id) {
        $.ajax({
            type: "POST",
            url: "/common/v1/sysParam/paramDataDetail",
            data: {
                "param_id": param_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function (result) {
                if (result) {
                    if (result != null) {
                        setFormData("form1", result);
                    }
                }
            }
        });
    }
}

//返回
function CloseWindow(action){
    //window.location.href = "/web/v1/institution/toInstitutionListPage";
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
}


//保存数据
function save() {
    editProForm.validate();
    if (editProForm.isValid() == false) return;
    var data = editProForm.getData();
    var p_param_id = mini.getbyName("p_param_id").getValue();
    $.ajax({
        type : "post",
        url : "/common/v1/sysParam/paramSave",
        data : {
            json : mini.encode(data),
            "p_param_id" : p_param_id
        },
        success : function(msg) {
            if (errorCheck(msg)) {
                if(msg.data != null){
                    alert(msg.data);
                }else{
                    //保存成功后调取
                    CloseWindow('ok');
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
    $.ajax({
        type : "post",
        url : "/common/v1/sysParam/paramEdit",
        data : {
            json : mini.encode(data)
        },
        success : function(msg) {
            if (errorCheck(msg)) {
                if(msg.data != null){
                    alert(msg.data);
                }else{
                    //保存成功后调取
                    CloseWindow('ok');
                }
            }
        }
    });
}