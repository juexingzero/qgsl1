mini.parse();

$(function(){
    init();
});

//初始化页面
function  init() {
    var approve_fail_info = mini.getByName("approve_fail_info").getValue();
    if(approve_fail_info.indexOf(",") != -1){
    	var infoArr = approve_fail_info.split(",");
    	var checkHtml = "";
    	for(var i=0; i<infoArr.length; i++){
    		var infoText = infoArr[i];
        	var infoTextArr = infoText.split("~");
    		if(i==0){
    			checkHtml += '<span>';
    		}else{
    			checkHtml += '<span style="margin-left:10px;">';
    		}
    		checkHtml += '<input type="checkbox" name="approve_fail_info" value="'+infoTextArr[0]+'"/>'+infoTextArr[1]+'</span>';
    	}
    	$("#div_info_text").append(checkHtml);
    }
}

//确定
function onSave(){
    var joinId = mini.getByName("joinId").getValue();
	var app_user_id = mini.getByName("app_user_id").getValue();
	var app_user_name = mini.getByName("app_user_name").getValue();
    var member_id = mini.getByName("member_id").getValue();
    var member_type = mini.getByName("member_type").getValue();
    var approve_fail_reason = mini.getByName("approve_fail_reason").getValue();
    var approve_fail_info="";
    $.each($('input:checkbox:checked'),function(){
    	approve_fail_info += $(this).val()+",";
    });
    if(approve_fail_info.length < 1){
    	alert("至少需要勾选一个资料类别！");
    	return;
    }else{
    	approve_fail_info = approve_fail_info.substring(0, approve_fail_info.length-1);
    }

	var form = new mini.Form("#form_approve");
  	form.validate();
  	if (form.isValid() == false){
  		return;
  	}

    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/web/v1/memberManager/approveMember",
        data: {
        	joinId				: joinId,
        	app_user_id			: app_user_id,
        	app_user_name		: app_user_name,
        	member_id			: member_id,
        	member_type			: member_type,
        	approve_status		: "HYSP-03",
        	approve_fail_info	: approve_fail_info,
        	approve_fail_reason	: approve_fail_reason
        },
        success: function (result) {
            if(result){
            	alert("操作成功！");
            	onClose();
            }
        }
    });
}

//取消
function onClose(){
	CloseWindow("cancel");
}
