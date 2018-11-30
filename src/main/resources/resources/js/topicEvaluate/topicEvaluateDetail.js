mini.parse();
var datagrid_passive = mini.get("datagrid_passiveList");
var datagrid_active = mini.get("datagrid_activeList");
var topic_id,evaluate_state;
var timer;

//初始化数据
function initData() {
	//根据状态设置进度及定时器是否显示
	if(evaluate_state == "4"){
		$("#state3").hide();
		$("#state4").show();
		$("#timer").hide();
	}else{
		if(evaluate_state == "1"){
			$("#timer").show();
			$("#startOrEnd").text("开始");
		}else if(evaluate_state == "2"){
			$("#timer").show();
			$("#startOrEnd").text("结束");
		}else{
			$("#timer").hide();
		}
		$("#state3").show();
		$("#state4").hide();
	}
	$("#state"+evaluate_state).addClass('active').prevAll('span').addClass('active');
	$('.step_cycle').eq(evaluate_state - 1).addClass('active').prevAll().addClass('active');
	
	//加载主题评价详情数据
	onLoadTopicEvaluateDetailData();
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
            	$("#topic_name").text(topic_data.topic_name);
            	var evaluate_start_time = topic_data.evaluate_start_time;
            	var evaluate_end_time = topic_data.evaluate_end_time;
            	$("#evaluate_start_time").text(evaluate_start_time);
            	$("#evaluate_end_time").text(evaluate_end_time);
            	//标准
            	var standard_data = resultMap.standard_data;
            	//onLoadStandardData(standard_data);
            	//被评价方
            	var passive_data = resultMap.passive_data;
                datagrid_passive.setData(mini.decode(passive_data));
                //评价方
            	var active_data = resultMap.active_data;
                datagrid_active.setData(mini.decode(active_data));
            }else{
            	alert("请求错误");
            }
		}
	});
}

//加载是否完成评价列内容
function onConvertIsEvaluate(e) {
	var text = "";
	var record = e.record;
	var is_evaluate = record.is_evaluate;
	if(record.index != "*"){
		if(evaluate_state == "1"){
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
	if(record.index != "*"){
		if(evaluate_state == "1"){
			text = "--";
		}
	}
	return text;
}

//更新评价状态
function updateEvaluateState(){
	var flag = false;
	if(evaluate_state == "1"){
		evaluate_state = "2";
		flag = true;
	}else if(evaluate_state == "2"){
		evaluate_state = "3";
		flag = true;
	}
	if(flag){
		$.ajax({
	        type: "post",
	        url: "/web/v1/topicEvaluate/updateTopicEvaluate",
	        data: {
	        	topic_id		: topic_id,
	        	evaluate_state	: evaluate_state
	        },
	        success: function (msg) {
	            if (errorCheck(msg)) {
	            	initData();
	            }
	        }
	    });
	}
}

$(function(){
	topic_id = mini.get("topic_id").getValue();
	evaluate_state = mini.get("evaluate_state").getValue();
	
	initData();
});
