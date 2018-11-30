mini.parse();
var editor = KindEditor.create('#editor',{filterMode:true, allowImageUpload:false, readonlyMode:true, resizeType:1, width:"100%", height:"400px"});
var fieldContent = mini.getByName("fieldContent").getValue();

$(function(){
	editor.html(fieldContent);
});

//取消
function onClose(){
	CloseWindow("cancel");
}
