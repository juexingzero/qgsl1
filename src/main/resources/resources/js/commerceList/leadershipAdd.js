mini.parse();
var datagrid = mini.get("datagrid");

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
    var shbmid = mini.getbyName("shbmid").getValue();
    var jie = mini.getbyName("jie").getValue();
    var px = mini.getbyName("px").getValue();
    var shzwlx = mini.getbyName("SHZWLX").getValue();
    console.log(shzwlx);
    var rows = datagrid.getSelecteds();
    var len = rows.length;
    if (rows.length > 0) {
        if(rows.length > 1 && (shzwlx == 'JBQZSHZW-01' || shzwlx == 'JBQZSHZW-02' || shzwlx == 'JBQZSHZW-04')){
            //主席、党组书记、秘书长三个职务的担任人员只能由一人担任
            alert("该职位最多由一人担任，请重新选择！");
            return false;
        }
        var ids = [];
        for (var i = 0, l = rows.length; i < l; i++) {
            var r = rows[i];
            ids.push(r.id);
        }
        var id = ids.join(',');
        Ajax({
            type : "post",
            url : "/web/v1/leadership/saveLeaderMember",
            data : {
                "id":id,
                "shzwlx":shzwlx,
                "jie":jie,
                "px":px,
                "shbmid":shbmid
            },
            success : function(msg) {
                if (errorCheck(msg)) {
                    //保存成功后调取
                    if(msg.data != ""){
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