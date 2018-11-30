mini.parse();
var datagrid = mini.get("datagrid_meetingManagerList");

$(function(){
	$(".cleanBtn").click(function(){
        mini.getByName("meeting_theme").setValue("");
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
	 window.location.href="/web/v1/meetingManager/toMeetingManagerPage?starttime="+starttime+"&endtime="+endtime;
	
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
	var meeting_theme=mini.getbyName("meeting_theme").getValue();
	var is_answer = mini.get("is_answer_tabs").getActiveTab();
	var selIndex = is_answer._id-1;
	datagrid.load({meeting_state:selIndex,meeting_theme:meeting_theme});
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
	var meeting_id = record.meeting_id;
	var is_answer = record.meeting_state;
	if(record.index != "*"){
		var buttonHtml;
		var detailBut = '<a href="javascript:seeDetailRow(\''+ meeting_id + '\')" style="text-decoration:none;" class="cus_btn_grid">详情</a>';
		var releaseBut = '<a href="javascript:releaseRow(\''+ meeting_id + '\')" style="text-decoration:none;" class="cus_btn_grid">发布</a>';
		var undoBut = '<a href="javascript:undoRow(\''+ meeting_id + '\')" style="text-decoration:none;" class="cus_btn_grid_del">撤销</a>';
		var editorBut = '<a href="javascript:updateRow(\''+ meeting_id + '\')" style="text-decoration:none;" class="cus_btn_grid">编辑</a>';
		var deleteBut = '<a href="javascript:deleteRow(\''+ meeting_id + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
		if(is_answer == "未发布"){
			buttonHtml = detailBut+"&nbsp;&nbsp;&nbsp;"+releaseBut+"&nbsp;&nbsp;&nbsp;"+editorBut+"&nbsp;&nbsp;&nbsp;"+deleteBut;
		}else if(is_answer == "待开会"){
			buttonHtml = detailBut+"&nbsp;&nbsp;&nbsp;"+undoBut;
		}else if(is_answer == "会议中"){
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
	var meeting_id = record.meeting_id;
	var meeting_theme=record.meeting_theme;
	var is_answer = record.meeting_state;
	if(record.index != "*"){
		var buttonHtml;
		var wfbcode = '<a href="javascript:wfbcode(\''+ meeting_id + '\')" style="text-decoration:none;">签到二维码</a>';
		var orcode = '<a href="javascript:orcodeRow(\''+ meeting_id + '\')" style="text-decoration:none;">查看签到</a>';
		var seereceipt = '<a href="javascript:seeReceiptRow(\''+ meeting_id + '\',\''+ meeting_theme + '\')" style="text-decoration:none;">查看回执</a>';
		var seevote = '<a href="javascript:seeVoteRow(\''+ meeting_id + '\')" style="text-decoration:none;">查看投票</a>';
		var meetingminutes = '<a href="javascript:meetingMinutesRow(\''+ meeting_id + '\')" style="text-decoration:none;">会议纪要</a>';
		if(is_answer == "未发布"){
			buttonHtml = wfbcode;
		}else if(is_answer == "待开会"){
			buttonHtml = orcode+"&nbsp;&nbsp;&nbsp;"+seereceipt;
		}else if(is_answer == "会议中"){
			buttonHtml = orcode+"&nbsp;&nbsp;&nbsp;"+seereceipt+"&nbsp;&nbsp;&nbsp;"+seevote;
		}else if(is_answer == "已结束"){
			buttonHtml = orcode+"&nbsp;&nbsp;&nbsp;"+seereceipt+"&nbsp;&nbsp;&nbsp;"+seevote+"&nbsp;&nbsp;&nbsp;"+meetingminutes;
		}else if(is_answer == "已撤销"){
		}
		return buttonHtml;
	}
	return "";
}


//发起会议
function addMeeting(){
    mini.open({
		url: "/web/v1/meetingManager/toseeMeetingManagerPage?state=2&meeting_id=",
		title: "发起会议", 
		width: 1000,
		height: 800,
		ondestroy: function (action) {
			datagrid.reload();
	    }
	});
}

//查看会议详情
function seeDetailRow(meeting_id){
	    mini.open({
			url: "/web/v1/meetingManager/toseeMeetingManagerPage?state=0&meeting_id="+meeting_id,
			title: "会议详情", 
			width: 1000,
			height: 800,
			ondestroy: function (action) {
				datagrid.reload();
		    }
		});
}


//编辑会议
function updateRow(meeting_id){
	    mini.open({
			url: "/web/v1/meetingManager/toseeMeetingManagerPage?state=1&meeting_id="+meeting_id,
			title: "会议修改", 
			width: 1000,
			height: 800,
			ondestroy: function (action) {
				datagrid.reload();
		    }
		});
}


//发布
function releaseRow(meeting_id){
	if (confirm("确定发布该会议？")) {
		$.ajax({
			type : "POST",
			url : "/web/v1/meetingManager/releaseMeetingManager",
			data : {
				meeting_id	: meeting_id,
				meeting_state	: "1"
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
function undoRow(meeting_id){
	if (confirm("确定撤销该会议？")) {
		$.ajax({
			type : "POST",
			url : "/web/v1/meetingManager/releaseMeetingManager",
			data : {
				meeting_id	: meeting_id,
				meeting_state	: "0"
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

//查看回执
function seeReceiptRow(meeting_id,meeting_theme){
	mini.open({
		url: "/web/v1/meetingManager/toseeReceiptPage?meeting_id="+meeting_id+"&meeting_theme="+meeting_theme,
		title: "参会回执", 
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
function wfbcode(meeting_id){
	mini.open({
		url: "/web/v1/meetingManager/toseeOrCodePage?meeting_id="+meeting_id,
		title: "签到二维码", 
		width: 400,
		height: 400,
		ondestroy: function (action) {
	      	loadGridData();
	    }
	});
}


//查看签到
function orcodeRow(meeting_id){
	mini.open({
		url: "/web/v1/meetingManager/toseeSignPage?meeting_id="+meeting_id,
		title: "会议签到", 
		width: 800,
		height: 600,
		ondestroy: function (action) {
	      	loadGridData();
	    }
	});
}

//删除
function deleteRow(meeting_id){
	if (confirm("确定删除会议？")) {
		$.ajax({
	        type: "POST",
	        url: "/web/v1/meetingManager/deleteMeetingManager",
	        data: {
	        	meeting_id		: meeting_id
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

//是否显示回复信息
function showAnswer(){
	var is_answer = mini.get("is_answer_tabs").getActiveTab();
	var selIndex = is_answer._id-1;
	 var meeting_theme=mini.getbyName("meeting_theme").getValue();
	datagrid.load({meeting_state:selIndex,meeting_theme:meeting_theme});
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
