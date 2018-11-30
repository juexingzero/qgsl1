mini.parse();
var datagrid = mini.get("datagrid");

function onActionRenderer(e) {
    var grid = e.sender;
    var record = e.record;
    var uid = record._uid;
    if(record.index != "*"){
        var state = record.state;
        console.log(record);
        if(state == "0"){
            return '<a href="javascript:detailRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">详情</a>'+
                '<a href="javascript:editRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">编辑</a>'+
                '<a href="javascript:delRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
        }else{
            return '<a href="javascript:detailRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">详情</a>'+
                '<a href="javascript:exportfile(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">导出</a>';
        }
    }
    return "";
}

$(function(){
    loadGridData();
});

//加载列表
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
    var title=mini.getbyName("title").getValue();
    console.log(title);
    datagrid.load({"title":title});
}


//重置搜索
function clean(){
    window.location.reload();
}


/**
 * 将模板类别转成文字
 * @param e
 * @returns
 */
function onLxModel(e) {
    if(e.record.template_type == "1"){
        return "个人基本情况调研表";
    }else if(e.record.template_type == "2"){
        return "团体基本情况调研表";
    }else if(e.record.template_type == "3"){
        return "企业基本情况调研表";
    }
}


/**
 * 将状态转成文字
 * @param e
 * @returns
 */
function onStateModel(e) {
    if(e.record.state == "0"){
        return "未开始";
    }else if(e.record.state == "1"){
        return "进行中";
    }else if(e.record.state == "2"){
        return "已结束";
    }
}

//删除
function delRow(gridId,row_uid){
    var grid = mini.get(gridId);
    var row = grid.getRowByUID(row_uid);
    if (row && confirm("确定删除吗？")) {
        Ajax({
            type: "post",
            url: "/web/v1/datareport/deleteDataTemplate?template_id="+row.template_id,
            success: function (msg) {
                if (errorCheck(msg)) {
                    datagrid.reload();
                }
            }
        });
    }
}


//详情
function detailRow(gridId,row_uid){
    var grid = mini.get(gridId);
    var row = grid.getRowByUID(row_uid);
    if(row.template_type == "1"){
        //个人
    }else if(row.template_type == "2"){
        //团体
    }else{
        mini.open({
            url: "/web/v1/datareport/toEnterpriseDataReportListPage?template_id="+row.template_id+"&&template_title="+row.template_title,
            title: "详情",
            width: 750,
            height: 550,
            ondestroy: function (action) {
                datagrid.reload();
            }
        });
    }
}


//发起
function btnNew(){
    mini.open({
        url: "/web/v1/datareport/toAddDataTemplatePage",
        title: "发起数据上报模板",
        width: 750,
        height: 430,
        ondestroy: function (action) {
            datagrid.reload();
        }
    });
}


//编辑页面
function editRow(gridId,row_uid){
    var grid = mini.get(gridId);
    var row = grid.getRowByUID(row_uid);
    mini.open({
        url: "/web/v1/datareport/toEditDataTemplatePage?template_id=" + row.template_id,
        title: "修改数据上报模板",
        width: 750,
        height: 450,
        ondestroy: function (action) {
            datagrid.reload();
        }
    });
}


//导出文件
function exportfile(gridId,row_uid){
    var grid = mini.get(gridId);
    var row = grid.getRowByUID(row_uid);
    Ajax({
        type: "post",
        url: "/web/v1/datareport/exportDataReportResult?template_id="+row.template_id+"&&template_type="+row.template_type,
        cache: false,
        async: false,
        success: function (msg) {
            if (errorCheck(msg)) {
                alert("导出成功！");
            }
        }
    });
}