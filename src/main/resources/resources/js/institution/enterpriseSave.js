mini.parse();
var datagrid = mini.get("datagrid");
var user_mobile = getCookie("jbqgsl_mobile_no");

$(function(){
    loadGridData();
});

function loadGridData(){
    datagrid.load();
}

//搜索
function serch(){
    var form = new mini.Form("#serchTab");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var xm = mini.getbyName("XM").getValue();
    var yddh = mini.getbyName("YDDH").getValue();
    datagrid.load({"XM":xm,"YDDH":yddh});
}


//重置搜索
function clean(){
    window.location.reload();
}


function save(){
    var rows = datagrid.getSelecteds();
    var len = rows.length;
    if (rows.length > 0) {
        var datas = [];
        for (var i = 0, l = rows.length; i < l; i++) {
            var r = rows[i];
            console.log(r);
            datas.push(r);
        }
        console.log(datas);
        Ajax({
            type : "post",
            url : "/web/v1/institution/enterpriseDetailSave",
            data : {
                json : mini.encode(datas),
                "mobile":user_mobile
            },
            success : function(msg) {
                if (errorCheck(msg)) {
                    //保存成功后调取
                    if(msg.data != null){
                        alert(msg.data);
                    }
                    returns('ok');
                }
            }
        });
    }else{
        alert("请选中一条记录");
    }
}



//关闭
function returns(action){
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
}