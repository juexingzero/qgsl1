mini.parse();
    
var message_id = "";
$(function(){
    message_id = getQueryString("message_id");
    if(message_id != null && message_id != ''){
    	onLoadData(message_id);
    }
});

//编辑或详情时加载现有数据
function onLoadData(message_id){
	$.ajax({
        type: "POST",
        url: "/web/v1/message/getMessageInfo",
        data: {
        	message_id : message_id
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
        	var mi = result.data;
            if(mi != null){
            	mini.getByName("message_id").setValue(mi.message_id);
            	mini.getByName("message_type").setValue(mi.message_type);
                mini.getByName("message_mode").setValue(mi.message_mode);
                mini.getByName("message_content").setValue(mi.message_content);
            }
        }
    });
}

/**
 * 保存用户
 *
 * @param _this
 */
function save(){
    var form = new mini.Form("#form_messageInfoAdd");
    form.validate();
    if (form.isValid() == false) return;
    var json = mini.encode(form.getData());
    $.ajax({
        type: "POST",
        url: "/web/v1/message/saveMessageInfo",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {
            json : json
        },
        success: function (result) {
            if(result.code == 200){
                CloseWindow("ok");
            }else{
                alert("提交有误，请重新尝试");
            }
        }
    });
}

//获取url参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}