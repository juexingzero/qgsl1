mini.parse();
var tree1 = mini.get("tree1");
var menu_name = mini.get("menu_name");
//初始化
function loadDatagrid(){
    mini.parse();
    var tree1 = mini.get("tree1");
    var menu_name = mini.get("menu_name");
    var node = tree1.getSelectedNode();
    if(node && node.p_param_id != "-1" && node.p_param_id != "0"){
        console.log(node.p_param_id);
        menu_name.load({p_param_id:node.param_id});
    }
}

//新增功能
$("#btnNewPosition").click(function(){
    var node = tree1.getSelectedNode();
    if(!node){
        alert("请选择菜单！");
        return;
    }
    if(node.p_param_id == "-1" || node.p_param_id == "0"){
        alert("请选择最后类型菜单！");
        return;
    }
    addFunction(node.param_id);
});


//新增页面
function addFunction(param_id){
    if(param_id == null){
        param_id = "";
    }
    mini.open({
        url: "/common/v1/sysParam/toAddParamPage?p_param_id="+ param_id,
        title: "参数配置",
        width: 600,
        height: 260,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
            menu_name.reload();
        }
    });
}


//编辑功能
function onRowDblClickForFunc(e){
    editFunction(e.record.param_id);
}

//编辑页面
function editFunction(param_id){
    if(param_id == null){
        param_id = "";
    }
    mini.open({
        url: "/common/v1/sysParam/toEditParamPage?param_id="+ param_id,
        title: "参数配置",
        width: 600,
        height: 260,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
            menu_name.reload();
        }
    });
}


//删除功能
$("#btnDeletePosition").click(function(){
    var row = menu_name.getSelected();
    if(!row){
        alert("请选择需要删除的功能！");
        return;
    }
    if(confirm("确实要删除该功能吗？")){
        $.ajax({
            type: "POST",
            url: "/common/v1/sysParam/paramDataDelete",
            data: {
                param_id:row.param_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function (result) {
                if(result){
                    menu_name.reload();
                }else{
                    alert("删除失败，请重新尝试");
                }
            }
        });
    }
});


//自动上传
function uploadFile(){
    // $("form").submit();
    $("#form1").ajaxSubmit(function(data){
        if(data.message=='ok'){
            alert(data.data);
        }else{
            alert(data.data);
        }
        window.location.reload();
    });
}


//打开文件
function btnModInpot(){
    document.getElementById("btn_file").click();
}


//下载
function btnModDown(){
    var url = "/common/v1/sysParam/templateDown";  //mybatis+spring
    url = encodeURI(url);
    location.href = url;
}


//格式化创建日期
function onConvertCreateTime(e) {
    var value = new Date(e.value);
    if (value){
        return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
    }
}