mini.parse();
var datagrid = mini.get("datagrid_institutionList");
var topic_type, standard_type, p_datagrid, passive_active, evaluate_start_time, defVal;

//接收父页面参数
function SetData(data) {
//	topic_type = data.topic_type;
//	standard_type = data.standard_type;
	p_datagrid = data.p_datagrid;
//	passive_active = data.type;
//	evaluate_start_time = data.evaluate_start_time;
//	var it1="区级部门",it2="街镇",it3="企业";
//	var zf = [{institution_id:"1",institution_type:it1},{institution_id:"2",institution_type:it2}];
//	var qy = [{institution_id:"3",institution_type:it3}];
//	var selText,defText;
//	if(standard_type == "1"){
//		passive_active == "1" ? selText = zf : selText = qy;
//		passive_active == "1" ? defVal = "1" : defVal = "3";
//		passive_active == "1" ? defText = it1 : defText = it3;
//	}else{
//		passive_active == "1" ? selText = qy : selText = zf;
//		passive_active == "1" ? defVal = "3" : defVal = "1";
//		passive_active == "1" ? defText = it3 : defText = it1;
//	}
//	mini.get("id_institution_type").setData(selText);
//	mini.get("id_institution_type").setValue(defVal);
//	mini.get("id_institution_type").setText(defText);
//	onKeyEnter();
}

//把选中行的数据给传到父页面
function GetData() {
    var row = datagrid.getSelected();
    return row;
}

//查询
function search() {
    var joinObjId = mini.getByName("joinObjName").getValue();
    var contact = mini.get("contact").getValue();
    var user_phone = mini.get("user_phone").getValue();
    if(joinObjId=="0"){
    	position="";
    }
    datagrid.load({ joinObjId: joinObjId, contact: contact,user_phone:user_phone });
}

//回车键
function onKeyEnter(e) {
    search();
}

//重置
function clean(e){
	mini.get("id_institution_name").setValue("");
	mini.get("id_institution_type").setValue(defVal);
	search();
}

//加载列表操作列内容
function onSelectRow(e) {
	var grid = e.sender;
    var record = e.record;
    if(record.index != "*"){
		return '<a href="javascript:selectRow()" style="text-decoration:none;cursor:pointer;">选择</a>';
	}
    return "";
}

//选择所有机构
function selectAll(){
	var allRow = datagrid.getData();
	if(allRow != null && allRow.length > 0){
		for(var i=0; i<allRow.length; i++){
			selectRow(allRow[i]);
		}
	}
}

//选择单个参会人员
function selectRow(selectedRow){
	var row;
	if(selectedRow){
		row = selectedRow;
	}else{
		row = datagrid.getSelected();
	}
	var flag = false;
	var selected_p_data=p_datagrid.getData();
	if(selected_p_data){
		for(var i=0; i<selected_p_data.length; i++){
			if(row.user_name == selected_p_data[i].user_name){
				flag = true;
				break;
			}
		}
	}
	if(flag){
		alert(row.user_name+"已被选，请选择其他参会人员");
		return;
	}else{
		datagrid.removeRow(row);
		p_datagrid.addRow(row);
	}
}

//参会人员列表-将商会职务转成文字
function covertshzw(e) {
	if (e.record.position == "JBQZSHZW-01") {
		return "主席";
	} else if (e.record.position == "JBQZSHZW-02") {
		return "党组书记";
	}  else if (e.record.position == "JBQZSHZW-03") {
		return "副主席";
	}  else if (e.record.position == "JBQZSHZW-04") {
		return "副会长";
	}  else if (e.record.position == "JBQZSHZW-05") {
		return "常委";
	}  else if (e.record.position == "JBQZSHZW-06") {
		return "执委";
	}  else if (e.record.position == "JBQZSHZW-07") {
		return "会员";
	}  else{
		return "未知职务";
	}  
}


//确定
function onOk() {
    CloseWindow("ok");
}

//取消
function onCancel() {
    CloseWindow("cancel");
}

$(function(){
	datagrid.load();
	//加载商会
	$.ajax({
		type : "POST",
		url : "/web/v1/meetingManager/getshList",
		cache: false,
		async: false,
		success : function(result) {
            var shList = result.data;
            mini.getByName("joinObjName").setData(shList);
		}
	});
});