mini.parse();
var datagrid = mini.get("datagrid_userSuggestList");

//加载列表数据
function loadGridData(){
    var form = new mini.Form("#searchForm");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var data = form.getData(true);
    var json = mini.encode(data);
    datagrid.load({json:json});
}

//面板切换
function changeTabs(e){
	mini.getByName("is_answer").setValue(e.tab._id-1);
	//为回复 0 已回复 1
//	if(mini.getByName("is_answer").value == "1"){
//		
//	}
	var is_answer = mini.getByName("is_answer").value;
	if(is_answer == "0"){
		datagrid.hideColumn(4);
		datagrid.hideColumn(5);
	}else if(is_answer == "1"){
		datagrid.showColumn(4);
		datagrid.showColumn(5);
	}
    loadGridData();
}

//转换操作列内容
function onConvertOperation(e) {
	var record = e.record;
	var id = record.id;
	if(record.index != "*"){
		var buttonHtml;
		var detailBut = '<a href="javascript:detailRow(\''+ id + '\')" style="text-decoration:none;" class="cus_btn_grid">查看</a>';
		buttonHtml = detailBut;
		return buttonHtml;
	}
	return "";
}


//查看
function detailRow(id){
	mini.open({
		url: "/web/v1/userSuggest/toUserSuggestDetailPage?id="+id,
		title: "反馈信息详情", 
		width: 800,
		height: 600,
		ondestroy: function (action) {
      	loadGridData();
      }
	});
}

$(function(){
    loadGridData();
}); 