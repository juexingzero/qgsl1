mini.parse();
var datagrid_passive = mini.get("datagrid_passiveList");
var datagrid_active = mini.get("datagrid_activeList");
var topic_id, evaluate_state, operation_flag;
var user_type = getCookie("jbqgsl_user_type");
var mobile_no = getCookie("jbqgsl_mobile_no");

//控制结束时间不能早于开始时间
function onDrawDate(e) {
    var date = e.date;
    var d = new Date();
    d.setDate(d.getDate()-1);
    if (date.getTime() < d.getTime()){
        e.allowSelect = false;
    }
}

//切换标准分类加载标准名称
function onLoadStandardName(e){
	var standard_type = mini.getByName("standard_type").getValue();
	//加载标准名称
	$.ajax({
		type : "POST",
		url : "/web/v1/standradLibarary/listStandard",
		data : {
			standard_type	: standard_type,
			is_effect		: "1"
		},
		cache: false,
		async: false,
		success : function(result) {
            var standardList = result.data;
            mini.getByName("standard_name").setData(null);
        	mini.getByName("standard_name").setValue("0");
        	mini.getByName("standard_name").setText("--请选择--");
        	$("#standardDetail").hide();
            if(standardList != null && standardList.length>0){
            	mini.getByName("standard_name").setData(standardList);
            	mini.getByName("standard_name").setValue(standardList[0].standard_id);
            	mini.getByName("standard_name").setText(standardList[0].standard_name);
            	$("#standardDetail").show();
            	onLoadStandardList();
            }
		}
	});
	/****************切换“关联行业”列显示在哪块*******************/
	//评政府
	if(standard_type == "1"){
		datagrid_passive.hideColumn(2);
		datagrid_active.showColumn(2);
	}
	//评企业
	else{
		datagrid_passive.showColumn(2);
		datagrid_active.hideColumn(2);
	}
	//新增页面切换时需要清空已选机构信息
	if(topic_id == "" && operation_flag == ""){
		var allRow_passive = datagrid_passive.getData();
		if(allRow_passive != null && allRow_passive.length > 0){
			for(var i=0; i<allRow_passive.length; i++){
				datagrid_passive.removeRow(allRow_passive[i]);
			}
			$("#passive_total").text("0");
		}
		var allRow_active = datagrid_active.getData();
		if(allRow_active != null && allRow_active.length > 0){
			for(var i=0; i<allRow_active.length; i++){
				datagrid_active.removeRow(allRow_active[i]);
			}
			$("#active_total").text("0");
		}
	}
}

//切换标准ID加载标准列表
function onLoadStandardList(e){
	var standard_name = mini.getByName("standard_name").getValue();
	$.ajax({
		type : "POST",
		url : "/web/v1/standradLibarary/showEvaluateStandards",
		data : {
			standard_id	: standard_name
		},
		cache: false,
		async: false,
		success : function(result) {
			topicEvaluateStandardData = result;
		}
	});
}

//展示标准详情
function onToStandardDetail(){
	var standard_name = mini.getByName("standard_name").getValue();
	mini.open({
		url: "/web/v1/topicEvaluate/toStandardDetail?standard_id="+standard_name,
		title: "标准详情", 
		width: 800,
		height: 700,
		ondestroy: function (action) {}
	});
}

//显示已选机构列表
function onInstitutionShow(e) {
	var type = e.tab._id;
	if(type == "1"){
		$("#passive_div").show();
		$("#active_div").hide();
        mini.parse();
	}else{
		$("#passive_div").hide();
		$("#active_div").show();
        mini.parse();
	}
}

//进入机构选择页面
function onInstitutionSelect(div_id) {
	var standard_type = mini.getByName("standard_type").getValue();
	if(standard_type == "0"){
		alert("请先选择标准分类!");
		return;
	}
	var evaluate_start_time = mini.getByName("evaluate_start_time").getValue();//miniUI的时间内容：Mon May 16 2016 18:48:34 GMT+0800 (中国标准时间)
	if(evaluate_start_time == ""){
		alert("请先选择评价时间!");
		return;
	}else{
		evaluate_start_time = onTimestampToDateStr(Date.parse(evaluate_start_time));
	}
	
	var selectedIndex = mini.get("passive_active_tabs").activeIndex+1;
	var type,title,total_span_id;
	if(selectedIndex == "1"){
		type = "1";
		title = "选择被评价方";
		total_span_id = "passive_total";
	}else{
		type = "2";
		title = "选择评价方";
		total_span_id = "active_total";
	}
	var p_datagrid = getDataGridByDivId(div_id);
	mini.open({
		url: "/web/v1/topicEvaluate/toInstitutionSelectPage",
		title: title,
		width: 500,
		height: 485,
	    onload: function () {
	        var iframe = this.getIFrameEl();
	        var data = {
        		topic_type			: mini.getByName("topic_type").getValue(),
        		standard_type		: standard_type, 
        		type				: type, 
        		p_datagrid 			: p_datagrid,
        		evaluate_start_time	: evaluate_start_time
	        };
	        iframe.contentWindow.SetData(data);
	    },
		ondestroy: function (action) {
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				var data = iframe.contentWindow.GetData();
				data = mini.clone(data);
				var total = p_datagrid.data.length;
				$("#"+total_span_id).text(total);
			}
		}
	});
}

//清空机构
function onInstitutionClear(div_id, span_total_id){
	var datagrid = getDataGridByDivId(div_id);
	var allRow = datagrid.getData();
	if (confirm("确定清空所有已选机构？")) {
		if(allRow != null && allRow.length > 0){
			for(var i=0; i<allRow.length; i++){
				datagrid.removeRow(allRow[i]);
			}
			$("#"+span_total_id).text("0");
		}
	}
}

//被评价方列表-操作-增加“删除”按钮
function onPassiveOperation(e) {
	var record = e.record;
	var institution_id = record.institution_id;
	if(record.index != "*"){
		var buttonHtml;
		var scoreDetailBut = '<a href="javascript:onScoreDetailRow(\''+ institution_id +'\',\'' + topic_id + '\')" style="text-decoration:none;" class="cus_btn_grid">得分详情</a>';
		var deleteBut = '<a href="javascript:onDeleteRow(\'passive_div\',\'passive_total\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</ a>';
		if(evaluate_state == "2" || evaluate_state == "3"){
			buttonHtml = scoreDetailBut;
		}else{
			buttonHtml = deleteBut;
		}
		return buttonHtml;
	}
	return "";
}

//评价方列表-操作-增加“删除”按钮
function onActiveOperation(e) {
	var record = e.record;
	if(record.index != "*"){
		return '<a href="javascript:onDeleteRow(\'active_div\',\'active_total\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</ a>';
	}
	return "";
}

//被评价方列表-操作-得分详情
function onScoreDetailRow(institution_id, topic_id){
	mini.open({
		url: "/web/v1/topicEvaluateResult/getTopicEvaluateDetailsList?institution_id="+institution_id+"&topic_id="+topic_id+"&page_type=evaluate_initiate",
		title: "被评价方得分详情列表", 
		width: 800,
		height: 650,
		ondestroy: function (action) {}
	});
}

//被评价方列表-操作-删除单条
function onDeleteRow(div_id, total_id){
	var datagrid = getDataGridByDivId(div_id);
	var row = datagrid.getSelected();
	if (row) {
		if (confirm("确定删除该条记录？")) {
			datagrid.removeRow(row);
			$("#"+total_id).text(parseInt(datagrid.getData().length));
     	}
	}
}

//根据div_id获取要操作的datagrid块
function getDataGridByDivId(div_id){
	var datagrid;
	if(div_id == "passive_div"){
		datagrid = datagrid_passive;
	}else{
		datagrid = datagrid_active;
	}
	return datagrid;
}

//保存发起主题评价内容
function onSave(){
	var form = new mini.Form("#initiateForm");
  	form.validate();
  	if (form.isValid() == false){
  		return;
  	}
  	var evaluate_start_time = onTimestampToDateStr(Date.parse(mini.getByName("evaluate_start_time").getValue()));
  	var evaluate_end_time = onTimestampToDateStr(Date.parse(mini.getByName("evaluate_end_time").getValue()));
  	if(evaluate_start_time == evaluate_end_time){
  		alert("评价开始跟结束时间不能相同");
  	  	return;
  	}else if(evaluate_start_time > evaluate_end_time){
  		alert("评价开始时间不能大于结束时间");
  	  	return;
  	}
  	var topic_data = {
		topic_id			: mini.getByName("topic_id").getValue(),
		topic_name			: mini.getByName("topic_name").getValue(),
		topic_type			: mini.getByName("topic_type").getValue(),
		evaluate_start_time	: evaluate_start_time,
		evaluate_end_time	: evaluate_end_time,
		topic_describe		: mini.getByName("topic_describe").getValue()
  	};
  	var standard_data = topicEvaluateStandardData;
  	var passive_data = datagrid_passive.data;
  	var active_data = datagrid_active.data;
  	if(passive_data == null || passive_data == ""){
  		alert("请至少选择一家被评价方");
  		return;
  	}
  	if(active_data == null || active_data == ""){
  		alert("请至少选择一家评价方");
  		return;
  	}
  	$.ajax({
		type : "post",
		url : "/web/v1/topicEvaluate/saveTopicEvaluate",
		data : {
			topic_json 		: mini.encode(topic_data),
			standard_json	: mini.encode(standard_data),
			passive_json 	: mini.encode(passive_data),
			active_json 	: mini.encode(active_data)
		},
		success : function(msg) {
			if (errorCheck(msg)) {
			  	$(".saveBut").attr("disabled",true);
				CloseWindow("ok");
			}
		}
	});
}

//取消
function onCancel() {
  CloseWindow("cancel");
}

/******************************************edit****************************************/
function initTopicEvaluateForEdit(){
	onLoadTopicEvaluateDetailData();
  	mini.getByName("standard_type").setEnabled(false);
	datagrid_active.hideColumn(8);
	datagrid_active.hideColumn(9);
}

//加载主题评价详情数据
function onLoadTopicEvaluateDetailData(){
	$.ajax({
		type : "POST",
		url : "/web/v1/topicEvaluate/getTopicEvaluateDetailData",
		data : {
			topic_id	: topic_id
		},
		cache: false,
		async: false,
		success : function(result) {
          if(result.state){
          	var resultMap = result.data;
          	//主题
          	var topic_data = resultMap.topic_data;
          	mini.getByName("topic_name").setValue(topic_data.topic_name);
          	mini.getByName("topic_type").setValue(topic_data.topic_type);
          	mini.getByName("evaluate_start_time").setValue(topic_data.evaluate_start_time);
          	mini.getByName("evaluate_end_time").setValue(topic_data.evaluate_end_time);
          	mini.getByName("topic_describe").setValue(topic_data.topic_describe);
          	evaluate_state = topic_data.evaluate_state;
          	//标准
          	var standard_data = resultMap.standard_data;
          	mini.getByName("standard_type").setValue(standard_data.standard_type);
          	onLoadStandardName();
        	mini.getByName("standard_name").setValue(standard_data.standard_id);
        	mini.getByName("standard_name").setText(standard_data.standard_name);
          	//被评价方
          	var passive_data = resultMap.passive_data;
          	if(user_type == "2"){ //双向评价街镇用户，在被评价方列表只显示其自身
          		for(var i=0; i<passive_data.length; i++){
          			var passive = passive_data[i];
          			if(passive.institution_linkman_phone == mobile_no){
          				passive_data.splice(0, passive_data.length);
                  		passive_data.push(passive);
                  		break;
          			}
          		}
          	}
          	datagrid_passive.setData(mini.decode(passive_data));
			$("#passive_total").text(passive_data.length);
            //评价方
          	var active_data = resultMap.active_data;
          	datagrid_active.setData(mini.decode(active_data));
			$("#active_total").text(active_data.length);
          }else{
          	alert("请求错误");
          }
		}
	});
}

/******************************************detail****************************************/
function initTopicEvaluateForDetail(){
	onLoadTopicEvaluateDetailData();
	//设置各组件为只读
	$(".saveBut").hide();
	$(".cancelBut").hide();
	$(".closeBut").show();
	$("#passive_div .sel_div").hide();
	$("#active_div .sel_div").hide();
	if(evaluate_state == "2" || evaluate_state == "3"){
		datagrid_passive.showColumn(5);
	}else{
		datagrid_passive.hideColumn(5);
	}
	datagrid_active.hideColumn(5);
	datagrid_active.showColumn(8);
	datagrid_active.showColumn(9);
  	mini.getByName("topic_name").setEnabled(false);
  	mini.getByName("topic_type").setEnabled(false);
  	mini.getByName("evaluate_start_time").setEnabled(false);
  	mini.getByName("evaluate_end_time").setEnabled(false);
  	mini.getByName("topic_describe").setEnabled(false);
  	mini.getByName("standard_type").setEnabled(false);
  	mini.getByName("standard_name").setEnabled(false);
}

//加载是否完成评价列内容
function onConvertIsEvaluate(e) {
	var text = "";
	var record = e.record;
	var is_evaluate = record.is_evaluate;
	if(record.index != "*"){
		if(evaluate_state == "0" || evaluate_state == "1"){
			text = "--";
		}else{
			if(is_evaluate == "0"){
				text = "否";
			}else if(is_evaluate == "1"){
				text = "是";
			}
		}
	}
	return text;
}

//加载完成评价时间内容
function onConvertEvaluateTime(e) {
	var text = "";
	var record = e.record;
	var is_evaluate = record.is_evaluate;
	if(record.index != "*"){
		if(evaluate_state == "0" || evaluate_state == "1"){
			text = "--";
		}else{
			if(is_evaluate == "0"){
				text = "--";
			}else{
				var evaluate_time = record.evaluate_time;
				if(evaluate_time != undefined){
					text = onTimestampToDateStr(Date.parse(evaluate_time));
				}
			}
		}
	}
	return text;
}

//加载评价分数列内容
function onConvertEvaluateScore(e) {
	var text = "";
	var record = e.record;
	var evaluate_score = record.evaluate_score;
	var is_evaluate = record.is_evaluate;
	if(record.index != "*"){
		if(evaluate_state == "0" || evaluate_state == "1"){
			text = "--";
		}else{
			if(is_evaluate == "0"){
				text = "--";
			}else{
				text = evaluate_score;
			}
		}
	}
	return text;
}

$(function(){
	topic_id = mini.getByName("topic_id").getValue();
	operation_flag = mini.getByName("operation_flag").getValue();
	if(topic_id != "" && operation_flag == "edit"){
		initTopicEvaluateForEdit();
	}else if(topic_id != "" && operation_flag == "detail"){
		initTopicEvaluateForDetail();
	}
	
	onLoadStandardName();

	$(".saveBut").attr("disabled",false);
});