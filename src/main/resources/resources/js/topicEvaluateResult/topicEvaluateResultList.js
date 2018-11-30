mini.parse();
var datagrid = mini.get("datagrid_topicEvaluateResultList");
var user_type = getCookie("jbqgsl_user_type");
var mobile_no = getCookie("jbqgsl_mobile_no");

$(function(){
	//查询
    $(".searchBtn").click(function(){
        loadGridData();
    }).click();
});

//加载列表数据
function loadGridData(){
    var form = new mini.Form("#searchForm");
    form.validate();
    if (form.isValid() == false){
        return;
    }

    var start_time = mini.getByName("evaluate_start_time").getValue();
    var end_time = mini.getByName("evaluate_end_time").getValue();
  	if(start_time != "" && end_time != ""){
  	  	var evaluate_start_time = onTimestampToDateStr(Date.parse(start_time));
  	  	var evaluate_end_time = onTimestampToDateStr(Date.parse(end_time));
  	  	if(evaluate_start_time == evaluate_end_time){
  	  		alert("评价开始跟结束时间不能相同");
  	  	  	return;
  	  	}else if(evaluate_start_time > evaluate_end_time){
  	  		alert("评价开始时间不能大于结束时间");
  	  	  	return;
  	  	}
  	}
  	
    var data = form.getData(true);
	//双向评价街镇用户
	if(user_type == "2"){
	    data.user_type=user_type;
	    data.mobile_no=mobile_no;
	}
    var json = mini.encode(data);
    datagrid.load({json:json});
}

//转换主题类型
function onConvertTopicType(e) {
	if(e.record.topic_type == "1"){
        return "半年评价";
    }
    return "年度评价";
}

//加载操作列内容
function onActionRenderer(e) {
	var record = e.record;
	var topic_id = record.topic_id;
	var evaluate_state = record.evaluate_state;
	if(record.index != "*"){
		return '<a href="javascript:detailRow(\''+ topic_id + '\')" style="text-decoration:none;" class="cus_btn_grid">查看</a>'+
			   '<a href="javascript:exportRow(\''+ topic_id + '\')" style="text-decoration:none;" class="cus_btn_grid">导出</a>';
	}
	return "";
}

//查看
function detailRow(topic_id){
	mini.open({
		url: "/web/v1/topicEvaluateResult/getTopicEvaluateOrgDetails?topic_id="+topic_id,
		title: "被评价方得分列表",
		width: 800,
		height: 670,
		ondestroy: function (action) {}
	});
}

//导出
function exportRow(topic_id){
    $.ajax({
        type : "POST",
        url : "/web/v1/topicEvaluateResult/exportTopicEvaluateResult",
        data : {
        	topic_ids : topic_id,
        	user_type : user_type,
        	mobile_no : mobile_no
        },
		cache: false,
		async: false,
        success : function(msg) {
            if (errorCheck(msg)) {
            	var exportFileName = msg.data;
				window.location.href = "/upload/download?fileType=topicEvaluate&fileName="+exportFileName;
            }
        }
    });
}

//批量导出
function exportRows(){
    var rows = datagrid.getSelecteds();
    var topic_ids = "";
    if(!rows[0]){
    	alert("请选择需要导出的主题评价!");
    	return;
    }else{
    	for(var i=0; i<rows.length; i++){
    		topic_ids += rows[i].topic_id+",";
    	}
    	topic_ids.substring(0, topic_ids.length-1);
    }
    exportRow(topic_ids);
}

//重置
$(".cleanBtn").click(function(){
	mini.getByName("topic_name").setValue("");
	mini.getByName("evaluate_start_time").setValue("");
	mini.getByName("evaluate_end_time").setValue("");
    loadGridData();
}).click();
