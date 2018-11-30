mini.parse();
var datagrid = mini.get("datagrid_topicEvaluateList");
var user_type = getCookie("jbqgsl_user_type");
var mobile_no = getCookie("jbqgsl_mobile_no");

$(function(){
	//双向评价街镇用户
	if(user_type == "2"){
		$(".addBtn").hide();
	}
	
    $(".searchBtn").click(function(){
        loadGridData();
    }).click();

    $(".cleanBtn").click(function(){
        mini.getByName("topic_name").setValue("");
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
    var data = form.getData(true);
	//双向评价街镇用户
	if(user_type == "2"){
	    data.user_type=user_type;
	    data.mobile_no=mobile_no;
	}
    var json = mini.encode(data);
    datagrid.load({json:json});
}

//面板切换
function changeTabs(e){
	var txtUserId = mini.getByName("evaluate_state");
    txtUserId.setValue(e.tab._id-1);
    loadGridData();
}

//转换主题类型
function onConvertTopicType(e) {
	if(e.record.topic_type == "1"){
        return "半年评价";
    }
    return "年度评价";
}

//转换评价状态
function onConvertEvaluateState(e){
	var evaluate_state = e.record.evaluate_state;
	var convertText = "";
	if(evaluate_state == "0"){
		convertText = "未发布";
    }else if(evaluate_state == "1"){
    	convertText = "未开始";
    }else if(evaluate_state == "2"){
    	convertText = "评价中";
    }else if(evaluate_state == "3"){
    	convertText = "已结束";
    }else if(evaluate_state == "4"){
    	convertText = "已撤销";
    }
    return convertText;
}

//转换操作列内容
function onConvertOperation(e) {
	var record = e.record;
	var topic_id = record.topic_id;
	var evaluate_state = record.evaluate_state;
	var evaluate_start_time = record.evaluate_start_time;
	if(record.index != "*"){
		var buttonHtml;
		var detailBut = '<a href="javascript:detailRow(\''+ topic_id + '\')" style="text-decoration:none;" class="cus_btn_grid">详情</a>';
		var initiateBut = '<a href="javascript:initiateRow(\''+ topic_id +'\',\'' + evaluate_start_time + '\')" style="text-decoration:none;" class="cus_btn_grid">发布</a>';
		var editBut = '<a href="javascript:editRow(\''+ topic_id + '\')" style="text-decoration:none;" class="cus_btn_grid">编辑</a>';
		var deleteBut = '<a href="javascript:deleteRow(\''+ topic_id + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
		var revokeBut = '<a href="javascript:revokeRow(\''+ topic_id + '\')" class="cus_btn_grid_del">撤销</a>';
		//双向评价街镇用户
		if(user_type == "2"){
			buttonHtml = detailBut;
		}else{
			if(evaluate_state == "0"){
				buttonHtml = detailBut + initiateBut+editBut + deleteBut;
			}else if(evaluate_state == "1"){
				buttonHtml = detailBut + revokeBut;
			}else if(evaluate_state == "2"){
				buttonHtml = detailBut + revokeBut;
			}else if(evaluate_state == "3"){
				buttonHtml = detailBut;
			}else if(evaluate_state == "4"){
				buttonHtml = detailBut;
			}
		}
		return buttonHtml;
	}
	return "";
}

//新增
function toAdd(){
	mini.open({
		url: "/web/v1/topicEvaluate/toInitiateEvaluatePage",
		title: "评价新增", 
		width: 800,
		height: 700,
		ondestroy: function (action) {
            loadGridData();
        }
	});
}

//详情
function detailRow(topic_id){
	mini.open({
		url: "/web/v1/topicEvaluate/toTopicEvaluateDetailPage?topic_id="+topic_id+"&operation_flag=detail",
		title: "评价详情", 
		width: 800,
		height: 650,
		ondestroy: function (action) {}
	});
}

//发布
function initiateRow(topic_id, evaluate_start_time){
	var targetDate = new Date(evaluate_start_time+":00".replace(/-/g, '/'));
	var timeDiff = targetDate.getTime() - new Date().getTime(); //时间戳差值
	if (timeDiff <= 0) {
		alert("评价开始时间已晚于当前时间，请重新编辑评价时间后发布");
		return;
	}
	if (confirm("确定发布评价？")) {
		updateTopicEvaluate(topic_id, "1", null);
	}
}

//编辑
function editRow(topic_id){
	mini.open({
		url: "/web/v1/topicEvaluate/toTopicEvaluateEditPage?topic_id="+topic_id+"&operation_flag=edit",
		title: "评价编辑", 
		width: 800,
		height: 700,
		ondestroy: function (action) {
            loadGridData();
        }
	});
}

//删除
function deleteRow(topic_id){
	if (confirm("确定删除评价？")) {
		updateTopicEvaluate(topic_id, null, "1");
	}
}

//撤销
function revokeRow(topic_id){
	if (confirm("确定撤销评价？")) {
		updateTopicEvaluate(topic_id, "4", null);
	}
}

//更新评价信息
function updateTopicEvaluate(topic_id, evaluate_state, del_flag){
	$.ajax({
        type: "post",
        url: "/web/v1/topicEvaluate/updateTopicEvaluate",
        data: {
        	topic_id		: topic_id,
        	evaluate_state	: evaluate_state,
        	del_flag		: del_flag
        },
        success: function (msg) {
            if (errorCheck(msg)) {
                loadGridData();
            }
        }
    });
}
