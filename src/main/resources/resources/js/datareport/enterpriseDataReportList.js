mini.parse();
var datagrid = mini.get("datagrid");

$(function(){
    loadGridData();
});

function loadGridData(){
    var template_id = mini.getbyName("template_id").getValue();
    console.log(template_id);
    datagrid.load({"template_id":template_id});
}


//搜索
function serch(){
    var form = new mini.Form("#serchTab");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var QYMC = mini.getbyName("QYMC").getValue();
    var template_id = mini.getbyName("template_id").getValue();
    datagrid.load({"QYMC":QYMC,"template_id":template_id});
}


//重置搜索
function clean(){
    window.location.reload();
}

//返回
function returns(action){
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
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
        return '<a href="javascript:seeRow(\''+ grid.id + '\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid_del">查看</a>';
    }
    return "";
}


//查看
function seeRow(gridId,row_uid){
    var grid = mini.get(gridId);
    var row = grid.getRowByUID(row_uid);
    mini.open({
        url: "/web/v1/datareport/toEnterpriseDataReportPage?data_report_id="+row.data_report_id,
        title: "详情",
        width: 750,
        height: 800,
        ondestroy: function (action) {
            loadGridData();
        }
    });
}
