mini.parse();
var datagrid = mini.get("datagrid_companyFeedbackList");

//狀態查詢
function queryState(e){
    var is_answer = e.tab.name;
    if(is_answer == -1){
        is_answer = null;
    }
    loadGridData(is_answer);
}
//加载列表数据
function loadGridData(is_answer){
    var form = new mini.Form("#searchForm");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    if(is_answer == null || is_answer =='null'){
        is_answer = '';
	}
    var data = form.getData(true);
    data.is_answer = is_answer;
    var json = mini.encode(data);
    datagrid.load({json:json});
}

//面板切换
function changeTabs(e){
	cleanQueryValue();
    loadGridData();
}

//清除查询条件值
function cleanQueryValue(){
    mini.getByName("company_name").setValue("");
    mini.getByName("feedback_type").setValue("0");
    mini.getByName("feedback_start_time").setValue("");
    mini.getByName("feedback_end_time").setValue("");
}

//转换问题类型
function onConvertFeedbackType(e){
    var record = e.record;
    var feedback_type = record.feedback_type;
    var str = '';
    if(feedback_type == '1'){
        str = '经济服务';
	}else if(feedback_type == '2'){
        str = '政策咨询';
	}
    else if(feedback_type == '3'){
        str = '法律维权';
    }
    else if(feedback_type == '4'){
        str = '商会建设';
    }
    else if(feedback_type == '5'){
        str = '党建工作';
    }
    else if(feedback_type == '6'){
        str = '人事人才';
    }
    else if(feedback_type == '7'){
        str = '其他';
    }
    return str;
}

//转换操作列内容
function onConvertOperation(e) {
	var record = e.record;
	var feedback_id = record.feedback_id;
	var is_answer = record.is_answer;
	if(record.index != "*"){
		var buttonHtml;
		var answerBut = '<a href="javascript:answerRow(\''+ feedback_id + '\')" style="text-decoration:none;" class="cus_btn_grid">回复</a>';
		var releaseBut = '<a href="javascript:releaseRow(\''+ feedback_id + '\')" style="text-decoration:none;" class="cus_btn_grid">发布</a>';
		var detailBut = '<a href="javascript:detailRow(\''+ feedback_id + '\')" style="text-decoration:none;" class="cus_btn_grid">查看</a>';
		//var deleteBut = '<a href="javascript:deleteRow(\''+ feedback_id + '\')" style="text-decoration:none;">删除</a>';
		if(is_answer == "0"){
			buttonHtml = answerBut + detailBut;
		}else if(is_answer == "1"){
			buttonHtml = answerBut + releaseBut;
		}else if(is_answer == "2"){
			buttonHtml = detailBut;
		}
		return buttonHtml;
	}
	return "";
}
//状态转换
function onConvertState(e) {
    var record = e.record;
    var is_answer = record.is_answer;
    var str = '';
    if(record.index != "*"){
        if(is_answer == "0"){
            str = '未读';
        }else if(is_answer == "1"){
            str = '已读';
        }else if(is_answer == "2"){
            str = '已回复';
        }
    }
    return str;
}

//回复
function answerRow(feedback_id){
	mini.open({
		url: "/web/v1/companyFeedback/toFeedbackDetailPage?feedback_id="+feedback_id+"&flag="+true,
		title: "反馈信息回复", 
		width: 800,
		height: 600,
        ondestroy: function (action) {
            loadGridData();
        }
	});
}

//发布
function releaseRow(feedback_id){
	if (confirm("确定发布企业反馈回复信息？")) {
		$.ajax({
			type : "POST",
			url : "/web/v1/companyFeedback/releaseCompanyFeedback",
			data : {
				feedback_id	: feedback_id,
				is_answer	: "2"
			},
			success : function(msg) {
				if (msg.state == "1") {
					alert("发布成功！");
			    	loadGridData();
				}else{
	            	alert(msg.message);
	            }
			}
		});
	}
}

//查看
function detailRow(feedback_id){
	mini.open({
		url: "/web/v1/companyFeedback/toFeedbackDetailPage?feedback_id="+feedback_id+"&flag="+false,
		title: "反馈信息详情", 
		width: 800,
		height: 600,
		ondestroy: function (action) {
	      	loadGridData();
	    }
	});
}

//删除
function deleteRow(feedback_id){
	if (confirm("确定删除企业反馈信息？")) {
		$.ajax({
	        type: "POST",
	        url: "/web/v1/companyFeedback/deleteCompanyFeedback",
	        data: {
	        	feedback_id		: feedback_id
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


$(function(){

    $(".searchBtn").click(function(){
      	var feedback_start_time = mini.getByName("feedback_start_time").getValue();
      	var feedback_end_time = mini.getByName("feedback_end_time").getValue();
      	if(feedback_start_time != "" && feedback_end_time != ""){
          	if(feedback_start_time == feedback_end_time){
          		alert("反馈开始跟结束时间不能相同");
          	  	return;
          	}else if(feedback_start_time > feedback_end_time){
          		alert("反馈开始时间不能大于结束时间");
          	  	return;
          	}
      	}
        loadGridData();
    }).click();

    $(".cleanBtn").click(function(){
    	cleanQueryValue();
        loadGridData();
    }).click();
});  