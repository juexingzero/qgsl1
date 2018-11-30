mini.parse();
var datagrid = mini.get("datagrid");
var datagrid1 = mini.get("datagrid1");

//初始加载
$(function(){
	var institution_id=mini.getByName("institution_id").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/institution/institutionDetail",
        data : {"institution_id":institution_id},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result != null) {
                	var text = $("#institution_name_text");
                	text.html(result.institution_name);
                	datagrid.show();
                	datagrid.load({
                		"institution_id":institution_id
                		});
                }
            }
        }
    });
});


//搜索
function serch(){
    var form = new mini.Form("#serchTab");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var institution_id=mini.getByName("institution_id").getValue();
    var industry_id = mini.getByName("industry_id").getValue();
    var institution_name = mini.getByName("institution_name").getValue();
    var tabs = mini.get("tabs1");
	var tab = tabs.getActiveTab();
    if(tab._id == 1){
    	datagrid1.hide();
    	datagrid.show();
    	datagrid.load({
    		"institution_id":institution_id,
    		"industry_id":industry_id,
    		"institution_name":institution_name
    		});
    }else{
    	datagrid1.show();
    	datagrid.hide();
    	datagrid1.load({
    		"institution_id":"",
    		"industry_id":industry_id,
    		"institution_name":institution_name
    		});
    }
    
}


//重置搜索
function clean(){
	window.location.reload();
	//loadGridData();
}


//面板列表
function changeTabs(e){
	var institution_id = mini.getByName("institution_id").getValue();
    var industry_id = mini.getByName("industry_id").getValue();
    var institution_name = mini.getByName("institution_name").getValue();
	var index = e.index;
	if(index == 0){
		//加载选中企业列表
		datagrid.load({"institution_id" : institution_id,"industry_id" : industry_id,"institution_name" : institution_name});
		datagrid.show();
		datagrid1.hide();
	}else if(index == 1){
		//加载未选中企业列表
		datagrid1.load({"institution_id" : "","industry_id" : industry_id,"institution_name" : institution_name});
		datagrid.hide();
		datagrid1.show();
	}
}

//已选择企业操作
function onConvertOperation(e) {
	var grid = e.sender;
    var record = e.record;
    var uid = record._uid;
    if(record.index != "*"){
		return '<a href="javascript:cancel(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;">取消选择</a>';
	}
    return "";
}


//未选择企业操作
function onConvertOperation1(e) {
	var grid = e.sender;
    var record = e.record;
    var uid = record._uid;
    if(record.index != "*"){
		return '<a href="javascript:select(\''+ grid.id +'\',\'' + uid + '\')" style="text-decoration:none;">选择</a>';
	}
    return "";
}


//全部取消
function allCancel(){
	var institution_id = mini.get("institution_id").getValue();
	if (confirm("确认要全部取消吗？")) {
        Ajax({
            type: "post",
            url: "/web/v1/institution/cancelAllSelect?institution_id="+institution_id,
            success: function (msg) {
                if (errorCheck(msg)) {
                	if(msg.data != null){
                		alert(msg.data);
                	}else{
                		//alert("已全部取消")
                        datagrid.reload();
                	}
                }
            }
        });
    }
}


//取消选择
function cancel(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	if (row && confirm("确定要取消选择吗？")) {
        Ajax({
            type: "post",
            url: "/web/v1/institution/cancelSelect?institution_id="+row.institution_id,
            success: function (msg) {
                if (errorCheck(msg)) {
                	if(msg.data != null){
                		alert(msg.data);
                	}else{
                		//alert("已取消")
                        datagrid.reload();
                	}
                }
            }
        });
    }
}


//选择
function select(gridId,row_uid){
	var institution_id = mini.get("institution_id").getValue();
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	if (row && confirm("确定要选择企业吗？")) {
        Ajax({
            type: "post",
            url: "/web/v1/institution/select?institution_id="+row.institution_id+"&main_institution="+institution_id,
            success: function (msg) {
                if (errorCheck(msg)) {
                	if(msg.data != null){
                		alert(msg.data);
                	}else{
                		//alert("已选择企业")
                        datagrid1.reload();
                	}
                }
            }
        });
    }
}