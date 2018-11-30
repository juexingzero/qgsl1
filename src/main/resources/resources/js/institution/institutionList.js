mini.parse();
var datagrid = mini.get("datagrid_institutionList");
function onActionRenderer(e) {
	var grid = e.sender;
    var record = e.record;
    var uid = record._uid;
    if(record.index != "*"){
		return '<a href="javascript:mainRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">主管企业</a>'+
			   '<a href="javascript:editRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">编辑</a>'+
			   '<a href="javascript:delRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
	}
    return "";
}

//加载列表
function loadGridData(){
    datagrid.load();
}

//编辑页面
function editRow(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	 mini.open({
        url: "/web/v1/institution/toInstitutionEditPage?institution_id=" + row.institution_id,
        title: "修改机构数据",
        width: 750,
        height: 600,
        ondestroy: function (action) {
        	datagrid.reload();
        }
	 });
}


//设置主管企业页面
function mainRow(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	 mini.open({
        url: "/web/v1/institution/toInstitutionMainPage?institution_id=" + row.institution_id,
        title: "主管企业设定",
        width: 750,
        height: 650,
        ondestroy: function (action) {
        	datagrid.reload();
        }
	 });
}

$(function(){
	loadGridData();
});

//搜索
function serch(){
    var form = new mini.Form("#serchTab");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var institution_name=mini.getbyName("institution_name").getValue();
    datagrid.load({"institution_name":institution_name});
}

//删除
function delRow(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	if (row && confirm("确定删除吗？")) {
        Ajax({
            type: "post",
            url: "/web/v1/institution/deleteInstitution?institution_id="+row.institution_id,
            data: {biz_obj_id: row.biz_obj_id},
            success: function (msg) {
                if (errorCheck(msg)) {
                    datagrid.reload();
                }
            }
        });
    }
}


//重置搜索
function clean(){
	window.location.reload();
	//loadGridData();
}
