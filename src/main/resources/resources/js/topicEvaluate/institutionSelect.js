mini.parse();
var datagrid = mini.get("datagrid_institutionList");
var topic_type, standard_type, p_datagrid, passive_active, evaluate_start_time, defVal;

//接收父页面参数
function SetData(data) {
	topic_type = data.topic_type;
	standard_type = data.standard_type;
	p_datagrid = data.p_datagrid;
	passive_active = data.type;
	evaluate_start_time = data.evaluate_start_time;
	var it1="区级部门",it2="街镇",it3="企业";
	var zf = [{institution_id:"1",institution_type:it1},{institution_id:"2",institution_type:it2}];
	var qy = [{institution_id:"3",institution_type:it3}];
	var selText,defText;
	if(standard_type == "1"){
		passive_active == "1" ? selText = zf : selText = qy;
		passive_active == "1" ? defVal = "1" : defVal = "3";
		passive_active == "1" ? defText = it1 : defText = it3;
	}else{
		passive_active == "1" ? selText = qy : selText = zf;
		passive_active == "1" ? defVal = "3" : defVal = "1";
		passive_active == "1" ? defText = it3 : defText = it1;
	}
	mini.get("id_institution_type").setData(selText);
	mini.get("id_institution_type").setValue(defVal);
	mini.get("id_institution_type").setText(defText);
	onKeyEnter();
}

//把选中行的数据给传到父页面
function GetData() {
    var row = datagrid.getSelected();
    return row;
}

//查询
function search() {
    var institution_name = mini.get("id_institution_name").getValue();
    var institution_type = mini.get("id_institution_type").getValue();
    datagrid.load({ institution_name: institution_name, institution_type: institution_type });
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

//选择单个机构
function selectRow(selectedRow){
	var row;
	if(selectedRow){
		row = selectedRow;
	}else{
		row = datagrid.getSelected();
	}
	var flag = false;
	var selected_p_data=p_datagrid.data;
	if(selected_p_data){
		for(var i=0; i<selected_p_data.length; i++){
			if(row.institution_id == selected_p_data[i].institution_id){
				flag = true;
				break;
			}
		}
	}
	if(flag){
		alert(row.institution_name+"已被选，请选择其他机构");
		return;
	}else{
		var flag_valid = false;
		//添加被评价方时需要验证
		if(passive_active == "1"){
			if(checkPassive(row.institution_id)){
				var content = "";
				if(topic_type == "1"){
					content = "已经被发起过半年评价，是否继续？";
				}else{
					content = "已经被发起过年度评价，是否继续？";
				}
				if (confirm(row.institution_name+content)) {
					flag_valid = true;
		     	}
			}else{
				flag_valid = true;
			}
		}else{
			flag_valid = true;
		}
		if(flag_valid){
			datagrid.removeRow(row);
			p_datagrid.addRow(row);
		}
	}
}

//根据开始时间判断在一年内，同一个“被评价方”只允许被发起一次半年评价和年度评价
function checkPassive(institution_id){
	var result_flag = false;
	$.ajax({
		type : "POST",
		url : "/web/v1/topicEvaluate/getPassiveListForCheck",
		data : {
			institution_id	: institution_id,
			topic_type		: topic_type
		},
		cache: false,
		async: false,
		success : function(result) {
			if (errorCheck(result)) {
				var data = result.data;
				for(var i=0; i<data.length; i++){
					var start_time = data[i].evaluate_start_time;
					if(start_time != null && start_time != "" && start_time != "undefined"){
						var nextYear = parseInt(start_time.substring(0,4))+1;
						var yearDate = start_time.substring(4,start_time.length);
						if(evaluate_start_time < (nextYear+yearDate)){
							result_flag = true;
							break;
						}
					}
				}
			}
		}
	});
	return result_flag;
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
});