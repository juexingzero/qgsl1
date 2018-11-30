mini.parse();
var datagrid = mini.get("datagrid_meetingManagerList");

$(function(){
	$(".cleanBtn").click(function(){
        mini.getByName("activity_theme").setValue("");
        loadGridData();
    }).click();
});

//根据条件查询列表信息
$(".searchBtn").click(function(){
    loadGridData();
})

//根据条件查询上方图表信息
$(".searchBtnt").click(function(){
	var starttime=mini.getbyName("starttime").getValue();
	var endtime = mini.getbyName("endtime").getValue();
	 window.location.href="/web/v1/activity/toActivityListPage?starttime="+starttime+"&endtime="+endtime;
	
})

//控制结束时间不能早于开始时间
function onDrawDate(e) {
	   var date = e.date;
	   var d = new Date();
	   d.setDate(d.getDate()-1);
	   if (date.getTime() < d.getTime()){
	       e.allowSelect = false;
	   }
}

//加载列表数据
function loadGridData(){
	var activity_theme=mini.getbyName("activity_theme").getValue();
	var is_answer = mini.get("is_answer_tabs").getActiveTab();
	var selIndex = is_answer._id-1;
	datagrid.load({activity_state:selIndex,activity_theme:activity_theme});
}

//面板切换
function changeTabs(e){
	mini.getByName("is_answer").setValue(e.tab._id-1);
	showAnswer();
//	cleanQueryValue();
   // loadGridData();
}

//转换操作列内容
function onConvertOperation(e) {
	var record = e.record;
	var activity_id = record.activity_id;
	var is_answer = record.activity_state;
	if(record.index != "*"){
		var buttonHtml;
		var detailBut = '<a href="javascript:seeDetailRow(\''+ activity_id + '\')" style="text-decoration:none;" class="cus_btn_grid">详情</a>';
		var releaseBut = '<a href="javascript:releaseRow(\''+ activity_id + '\')" style="text-decoration:none;" class="cus_btn_grid">发布</a>';
		var undoBut = '<a href="javascript:undoRow(\''+ activity_id + '\')" style="text-decoration:none;" class="cus_btn_grid_del">撤销</a>';
		var editorBut = '<a href="javascript:updateRow(\''+ activity_id + '\')" style="text-decoration:none;" class="cus_btn_grid">编辑</a>';
		var deleteBut = '<a href="javascript:deleteRow(\''+ activity_id + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
		if(is_answer == "未发布"){
			buttonHtml = detailBut+"&nbsp;&nbsp;&nbsp;"+releaseBut+"&nbsp;&nbsp;&nbsp;"+editorBut+"&nbsp;&nbsp;&nbsp;"+deleteBut;
		}else if(is_answer == "未开始"){
			buttonHtml = detailBut+"&nbsp;&nbsp;&nbsp;"+undoBut;
		}else if(is_answer == "进行中"){
			buttonHtml = detailBut;
		}else if(is_answer == "已结束"){
			buttonHtml = detailBut;
		}else if(is_answer == "已撤销"){
			buttonHtml = detailBut;
		}
		return buttonHtml;
	}
	return "";
}

//转换功能列内容
function onConvertFunction(e) {
	var record = e.record;
	var activity_id = record.activity_id;
	var activity_name=record.activity_name;
	var is_answer = record.activity_state;
	if(record.index != "*"){
		var buttonHtml;
		var wfbcode = '<a href="javascript:wfbcode(\''+ activity_id + '\')" style="text-decoration:none;">签到二维码</a>';
		var orcode = '<a href="javascript:orcodeRow(\''+ activity_id + '\')" style="text-decoration:none;">查看签到</a>';
		var seereceipt = '<a href="javascript:seeReceiptRow(\''+ activity_id + '\',\''+ activity_name + '\')" style="text-decoration:none;">查看报名</a>';
		if(is_answer == "未发布"){
			buttonHtml = wfbcode;
		}else if(is_answer == "未开始"){
			buttonHtml = orcode+"&nbsp;&nbsp;&nbsp;"+seereceipt;
		}else if(is_answer == "进行中"){
			buttonHtml = orcode+"&nbsp;&nbsp;&nbsp;"+seereceipt;
		}else if(is_answer == "已结束"){
			buttonHtml = orcode+"&nbsp;&nbsp;&nbsp;"+seereceipt;
		}else if(is_answer == "已撤销"){
			buttonHtml="-";
		}
		return buttonHtml;
	}
	return "";
}


//发起活动
function addMeeting(){
    mini.open({
		url: "/web/v1/activity/toseeActivityPage?state=2&activity_id=",
		title: "发起活动", 
		width: 1000,
		height: 800,
		ondestroy: function (action) {
			datagrid.reload();
	    }
	});
}

//查看活动详情
function seeDetailRow(activity_id){
	    mini.open({
			url: "/web/v1/activity/toseeActivityPage?state=0&activity_id="+activity_id,
			title: "活动详情", 
			width: 1000,
			height: 800,
			ondestroy: function (action) {
				datagrid.reload();
		    }
		});
}


//编辑活动
function updateRow(activity_id){
	    mini.open({
			url: "/web/v1/activity/toseeActivityPage?state=1&activity_id="+activity_id,
			title: "活动修改", 
			width: 1000,
			height: 800,
			ondestroy: function (action) {
				datagrid.reload();
		    }
		});
}


//发布
function releaseRow(activity_id){
	if (confirm("确定发布该活动？")) {
		$.ajax({
			type : "POST",
			url : "/web/v1/activity/releaseActivity",
			data : {
				activity_id	: activity_id,
				activity_state	: "1"
			},
			success : function(msg) {
				if (msg.state == "1") {
					location.reload();
				}else{
	            	alert("发布失败！");
	            }
			}
		});
	}
}


//撤销
function undoRow(activity_id){
	if (confirm("确定撤销该会议？")) {
		$.ajax({
			type : "POST",
			url : "/web/v1/activity/releaseActivity",
			data : {
				activity_id	: activity_id,
				activity_state	: "0"
			},
			success : function(msg) {
				if (msg.state == "1") {
					alert("撤销成功！");
					location.reload();
				}else{
	            	alert("发布失败！");
	            }
			}
		});
	}
}

//查看报名
function seeReceiptRow(activity_id,activity_name){
	mini.open({
		url: "/web/v1/activity/toseeEntryPage?activity_id="+activity_id+"&activity_name="+activity_name,
		title: "活动报名", 
		width: 1000,
		height: 800,
		ondestroy: function (action) {
			datagrid.reload();
	    }
	});
}

//查看投票
function seeVoteRow(meeting_id){
	mini.open({
		url: "/web/v1/meetingManager/toseeVotePage?meeting_id="+meeting_id,
		title: "查看投票", 
		width: 800,
		height: 600,
		ondestroy: function (action) {
			datagrid.reload();
	    }
	});
}

//查看签到二维码
function wfbcode(activity_id){
	mini.open({
		url: "/web/v1/activity/toseeOrCodePage?activity_id="+activity_id,
		title: "签到二维码", 
		width: 400,
		height: 400,
		ondestroy: function (action) {
	      	loadGridData();
	    }
	});
}


//查看签到
function orcodeRow(activity_id){
	mini.open({
		url: "/web/v1/activity/toseeSignPage?activity_id="+activity_id,
		title: "活动签到", 
		width: 800,
		height: 600,
		ondestroy: function (action) {
	      	loadGridData();
	    }
	});
}

//删除
function deleteRow(activity_id){
	if (confirm("确定删除活动？")) {
		$.ajax({
	        type: "POST",
	        url: "/web/v1/activity/deleteActivity",
	        data: {
	        	activity_id		: activity_id
	        },
	        success: function (msg) {
				if (msg.state == "1") {
					location.reload();
	            }else{
	            	alert("操作失败");
	            }
	        }
	    });
	}
}

//重新加载页面
function showAnswer(){
	var is_answer = mini.get("is_answer_tabs").getActiveTab();
	var selIndex = is_answer._id-1;
	 var activity_theme=mini.getbyName("activity_theme").getValue();
	datagrid.load({activity_state:selIndex,activity_theme:activity_theme});
}

//会议纪要
function meetingMinutesRow(meeting_id){
	mini.open({
		url: "/web/v1/meetingManager/toseeMeetingMinutesPage?meeting_id="+meeting_id,
		title: "会议纪要", 
		width: 600,
		height: 400,
		ondestroy: function (action) {
	      	loadGridData();
	    }
	});
}
