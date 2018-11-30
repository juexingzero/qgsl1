mini.parse();
var datagrid = mini.get("datagrid_companyElegantList");

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

//清除查询条件值
function cleanQueryValue(){
    mini.getByName("company_name").setValue("");
    mini.getByName("company_type").setValue("0");
}

//转换问题类型
function onConvertCompanyType(e) {
	var text = "";
	if(e.record.company_type == "1"){
		text = "普通会员";
    }else if(e.record.company_type == "2"){
    	text = "优秀会员";
    }
    return text;
}

//转换操作列内容
function onConvertOperation(e) {
	var record = e.record;
	var elegant_id = record.elegant_id;
	if(record.index != "*"){
		var buttonHtml;
		var detailBut = '<a href="javascript:detailRow(\''+ elegant_id + '\')" style="text-decoration:none;" class="cus_btn_grid">查看</a>';
		var editBut = '<a href="javascript:editRow(\''+ elegant_id + '\')" style="text-decoration:none;" class="cus_btn_grid">修改</a>';
		var deleteBut = '<a href="javascript:deleteRow(\''+ elegant_id + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
		buttonHtml = detailBut + editBut + deleteBut;
		return buttonHtml;
	}
	return "";
}

//新增
function onAdd(){
	var url = "/web/v1/companyElegant/toElegantAddPage?operation_flag=add";
	var title = "企业新增";
	openWindow(url, title);
}

//修改
function editRow(elegant_id){
	var url = "/web/v1/companyElegant/toElegantAddPage?operation_flag=edit&elegant_id="+elegant_id;
	var title = "企业编辑";
	openWindow(url, title);
}

//查看
function detailRow(elegant_id){
	var url = "/web/v1/companyElegant/toElegantAddPage?operation_flag=detail&elegant_id="+elegant_id;
	var title = "企业查看";
	openWindow(url, title);
}

//删除
function deleteRow(elegant_id){
	if (confirm("确定删除企业信息？")) {
		$.ajax({
	        type: "POST",
	        url: "/web/v1/companyElegant/deleteCompanyElegant",
	        data: {
	        	elegant_id		: elegant_id
	        },
	        success: function (msg) {
				if (msg.state == "1") {
	                loadGridData();
	            }else{
	            	alert("操作失败");
	            }
	        }
	    });
	}
}

//打开弹窗
function openWindow(url, title){
	mini.open({
		url: url,
		title: title,
		width: 800,
		height: 800,
		ondestroy: function (action) {
        	loadGridData();
        }
	});
}

$(function(){
    $(".searchBtn").click(function(){
        loadGridData();
    }).click();

    $(".cleanBtn").click(function(){
    	cleanQueryValue();
        loadGridData();
    }).click();
});  