mini.parse();
var datagrid_info = mini.get("datagrid_messageInfoList");
var datagrid_flowing = mini.get("datagrid_messageFlowingList");

//加载消息信息列表数据
function loadInfoGridData(){
    var form = new mini.Form("#searchForm_messageInfo");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var data = form.getData(true);
    
    var message_type = data.message_type;
    if(data.message_type == "0"){
    	data.message_type = "";
    }
    if(data.message_mode == "0"){
    	data.message_mode = "";
    }
    
    var json = mini.encode(data);
    datagrid_info.load({json:json});
}

//转换消息类型
function onConvertMessageType(e){
	var message_type = e.record.message_type;
	var convertText = "";
	if(message_type == "1"){
		convertText = "广播通知";
	}else if(message_type == "2"){
		convertText = "工作任务";
	}else if(message_type == "3"){
		convertText = "意见回复";
	}
	return convertText;
}

//转换消息形式
function onConvertMessageMode(e){
	var message_mode = e.record.message_mode;
	var convertText = "";
	if(message_mode == "1"){
		convertText = "文本";
	}else if(message_mode == "2"){
		convertText = "图片";
	}else if(message_mode == "3"){
		convertText = "语音";
	}else if(message_mode == "4"){
		convertText = "视频";
	}else if(message_mode == "5"){
		convertText = "混合";
	}
	return convertText;
}

//新增
$("#btnNew").click(function(){
    edit();
});

//编辑-双击行
function onRowDblClick(e){
	var message_type=e.record.message_type;
	if(message_type == "3"){
		alert("意见回复类型不可编辑！");
	}else{
	    edit(e.record.message_id);
	}
}

//编辑-具体实现
function edit(message_id){
    if(message_id == null || message_id == ""){
    	title = "消息新增";
    }else{
    	title = "消息编辑";
    }
    mini.open({
        url: "/web/v1/message/toMessageInfoAddPage?message_id="+ empty(message_id),
        title: title,
        width: 500,
        height: 350,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
        	datagrid_info.reload();
        }
    });
}

//删除
$("#btnDelete").click(function(){
    var rows = datagrid_info.getSelecteds();
    var message_ids = "";
    if(!rows[0]){
    	alert("请选择需要删除的消息!");
    	return;
    }else{
    	for(var i=0; i<rows.length; i++){
    		message_ids += rows[i].message_id+",";
    	}
    	message_ids.substring(0, message_ids.length-1);
    }
    if(confirm("确定要删除消息信息吗？")) {
        Ajax({
            type : "POST",
            url : "/web/v1/message/deleteMessageInfo",
            data : {
            	message_ids : message_ids
            },
            success : function(msg) {
                if (msg.state == "1") {
                	if(msg.data == "2"){
                		alert("该消息信息下存在发送记录，无法删除！");
                	}else{
                        datagrid_info.reload();
                	}
                }else{
                	alert("操作失败");
                }
            }
        });
    }
});

//发送消息
$("#sendMessage").click(function(){
    var rows = datagrid_info.getSelecteds();
    var message_id = "";
    if(!rows[0]){
    	alert("请选择需要发送的消息!");
    	return;
    }else if(rows.length > 1){
    	alert("只能选择一条消息进行发送!");
    	return;
    }else{
    	message_id = rows[0].message_id;
    }
    mini.open({
        url: "/web/v1/message/toAppUserSelectPage?message_id="+ empty(message_id),
        title: "发送消息",
        width: 800,
        height: 700,
        onload: function () {
	        var iframe = this.getIFrameEl();
	    },
        ondestroy: function (action) {
        	loadFlowingGridData();
        }
    });
});

//单击加载流水列表
function onrowclick(e){
	var message_id = e.record.message_id;
	mini.getByName("message_id").setValue(message_id);
	loadFlowingGridData();
}
/*****************************************************************************/
function loadFlowingGridData(){
    var form = new mini.Form("#searchForm_messageFlowing");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var data = form.getData(true);
    var json = mini.encode(data);
    datagrid_flowing.load({json:json});
}

function onConvertIsRead(e){
	var is_read = e.record.is_read;
	var convertText = "";
	if(is_read == "0"){
		convertText = "否";
	}else if(is_read == "1"){
		convertText = "是";
	}
	return convertText;
}

$(function(){
    //信息
    $(".searchBtn_info").click(function(){
        loadInfoGridData();
    }).click();
    $(".cleanBtn_info").click(function(){
        mini.getByName("message_type").setValue("0");
        mini.getByName("message_mode").setValue("0");
        mini.getByName("message_content").setValue("");
        loadInfoGridData();
    }).click();
    //流水
    $(".searchBtn_flowing").click(function(){
        loadInfoGridData();
    }).click();
    $(".cleanBtn_flowing").click(function(){
        mini.getByName("send_name").setValue("");
        mini.getByName("receive_name").setValue("");
        loadInfoGridData();
    }).click();
});