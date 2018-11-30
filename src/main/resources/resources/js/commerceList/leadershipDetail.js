mini.parse();
var datagrid = mini.get("datagrid");

$(function(){
    loadGridData();
});

function loadGridData(){
    var shbmid = mini.getbyName("shbmid").getValue();
    var jie = mini.getbyName("jie").getValue();
    datagrid.load({"SHBMID":shbmid,"JIE":jie});
}


//搜索
function serch(){
    var form = new mini.Form("#serchTab");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var shbmid = mini.getbyName("shbmid").getValue();
    var jie = mini.getbyName("jie").getValue();
    var shzwlx = mini.getbyName("SHZWLX").getValue();
    var xm = mini.getbyName("XM").getValue();
    datagrid.load({"SHBMID":shbmid,"JIE":jie,"SHZWLX":shzwlx,"XM":xm});
}


//重置搜索
function clean(){
    window.location.reload();
    //loadGridData();
}


/**
 * 将评价类型转成文字
 * @param e
 * @returns
 */
function onLxModel(e) {
    if(e.record.SHZWLX == "JBQZSHZW-01"){
        return "主席";
    }else if(e.record.SHZWLX == "JBQZSHZW-02"){
        return "党组书记";
    }else if(e.record.SHZWLX == "JBQZSHZW-03"){
        return "副主席";
    }else if(e.record.SHZWLX == "JBQZSHZW-04"){
        return "秘书长";
    }else if(e.record.SHZWLX == "JBQZSHZW-05"){
        return "副会长";
    }else if(e.record.SHZWLX == "JBQZSHZW-06"){
        return "常委";
    }else if(e.record.SHZWLX == "JBQZSHZW-07"){
        return "执委";
    }
}

//新增页面
function btnNew(){
    var shbmid = mini.getbyName("shbmid").getValue();
    var jie = mini.getbyName("jie").getValue();
    var px = mini.getbyName("px").getValue();
    mini.open({
        url: "/web/v1/leadership/toLeadershipAddPage?SHBMID="+shbmid+"&&JIE="+jie+"&&PX="+px,
        title: "添加领导班子",
        width: 750,
        height: 650,
        ondestroy: function (action) {
            window.location.reload();
        }
    });
}


/**
 * 拼接操作 删除 编辑
 * @param e
 * @returns
 */
function onActionRenderer(e) {
    var grid = e.sender;
    var record = e.record;
    var uid = record._uid;
    if (record.index != "*") {
        var row = grid.getRowByUID(uid);
        var id = row.id;
        return '<a href="javascript:delRow(\''+ grid.id + '\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
    }
    return "";
}


//删除功能
function delRow(gridId,row_uid){
    var grid = mini.get(gridId);
    var row = grid.getRowByUID(row_uid);
    var id = row.LDID;
    if (confirm("是否确定删除！")) {
        Ajax({
            type : "post",
            url : "/web/v1/leadership/deleteLeadership?ID="+id,
            success : function(msg) {
                if (errorCheck(msg)) {
                    //保存成功后调取
                    //alert("删除成功");
                    loadGridData();
                }
            }
        });
    }
}