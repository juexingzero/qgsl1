mini.parse();
var datagrid = mini.get("datagrid_eva");
function onActionRenderer(e) {
	var grid = e.sender;
    var record = e.record;
    var uid = record._uid;
    if(record.index != "*"){
		return '<a href="javascript:detailRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">查看</a>';
	}
    return "";
}

//加载列表
function loadGridData(){
    datagrid.load();
}

//编辑页面
function detailRow(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	 mini.open({
        url: "/web/v1/forthwitheva/toEvaluatePage?forthwith_id=" + row.forthwith_id,
        title: "即时评价-已回复",
        width: 800,
        height: 700,
        allowResize:false,
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
    var evaluate_active_name=mini.getbyName("evaluate_active_name").getValue();
    datagrid.load({"evaluate_active_name":evaluate_active_name});
}



//重置搜索
function clean(){
	window.location.reload();
}