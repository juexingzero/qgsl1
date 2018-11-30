mini.parse();
var datagrid = mini.get("datagrid_institutionList");
var p_datagrid,passive_active,evaluate_start_time;
var message_id = "";

//查询
function search() {
    var user_name = mini.getByName("user_name").getValue();
    var json = mini.encode({"user_name": user_name});
    datagrid.load({json:json});
}

//回车键
function onKeyEnter(e) {
    search();
}

//重置
function clean(e){
	mini.getByName("user_name").setValue("");
	datagrid.load();
}

//确定
function onOk() {
    CloseWindow("ok");
}

//取消
function onCancel() {
    CloseWindow("cancel");
}

//获取url参数
function getQueryString(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
  var r = window.location.search.substr(1).match(reg);
  if (r != null) return unescape(r[2]);
  return null;
}

//发送
function onOk(){
    var institution_data = datagrid.getSelecteds();
    if(!institution_data[0]){
    	alert("至少选择一个用户!");
    	return;
    }
    if(confirm("确定为已选用户发送消息吗？")) {
      	$.ajax({
    		type : "POST",
    		url : "/web/v1/message/saveMessageFlowing",
    		data : {
    			message_id 		: message_id,
    			institution_json: mini.encode(institution_data)
    		},
    		success : function(msg) {
    			if (errorCheck(msg)) {
    				alert("发送成功！");
    				CloseWindow("ok");
    			}
    		}
    	});
    }
}

$(function(){
    message_id = getQueryString("message_id");
	datagrid.load();
});