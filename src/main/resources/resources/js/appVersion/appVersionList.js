mini.parse();
var datagrid = mini.get("datagrid_List");
function onActionRenderer(e) {
	var grid = e.sender;
    var record = e.record;
    var uid = record._uid;
    if(record.index != "*"){
		return '<a href="javascript:editRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">编辑</a>'+
			   '<a href="javascript:delRow(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
	}
    return "";
}

//加载列表
function loadGridData(){
	var channal = mini.get("channal").getValue();
    datagrid.load({"channal":channal});
}

//新增页面
function add(){
	 mini.open({
        url: "/web/v1/version/toAddPage",
        title: "版本新增",
        width: 500,
        height: 400,
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
      url: "/web/v1/version/toEditPage?id=" + row.id,
      title: "版本编辑",
      width: 460,
      height: 420,
      ondestroy: function (action) {
      	datagrid.reload();
      }
	 });
}

//搜索
function serch(){
    var form = new mini.Form("#serchTab");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var channal=mini.get("channal").getValue();
    var version = mini.get("version").getValue();
    datagrid.load({"channal":channal,"version":version});
}

//删除
function delRow(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	if (row && confirm("确定删除吗？")) {
        Ajax({
            type: "post",
            url: "/web/v1/version/deleteVersion",
            data: {id: row.id},
            success: function (msg) {
                if (errorCheck(msg)) {
                    datagrid.reload();
                }
            }
        });
    }
}
$(function(){
	loadGridData();
});
//重置搜索
function clean(){
	window.location.reload();
}

