mini.parse();
var pageType = mini.getByName("pageType").getValue();

$(function(){
	if(pageType == "person"){
		$("#div_grryxq").find("input").attr("disabled",true);
		$("#div_grryxq").css("display","block");
		$("#div_qyryxq").css("display","none");
	}else if(pageType == "company"){
		$("#div_qyryxq").find("input").attr("disabled",true);
		$("#div_grryxq").css("display","none");
		$("#div_qyryxq").css("display","block");
	}
});

//取消
function onClose(){
	CloseWindow("cancel");
}
