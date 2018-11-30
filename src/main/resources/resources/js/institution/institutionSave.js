mini.parse();
var editor = KindEditor.create('#editor',{filterMode: true,allowImageUpload:false,resizeType : 1,width:"100%",height:"400px"});
var editProForm = new mini.Form("#tabs");//项目基本信息表单

//保存
function save(){
	//changeindustry();
	editProForm.validate();
if (editProForm.isValid() == false) return;
	
	var data = editProForm.getData();
	data.institution_describe=editor.html();
	data.institution_linkman_name=$.trim(mini.get("institution_linkman_name").getValue());
	//判断手机有效性
	 var institution_linkman_phone=mini.get("institution_linkman_phone").getValue();
	   var reg = /^1[34578]\d{9}$/;
	   if(reg.test(institution_linkman_phone) === false)
	   {
	       mini.alert("手机号码不合法，请重新输入");
	       return  false;  
	   }
/*	  var institution_type = mini.get("institution_type").getValue();
	  var industry_id = mini.get("industry_id").getValue();
	  if(institution_type == 2){
		  //街镇不允许选择行业
		  if(industry_id != null && industry_id != ""){
			  mini.alert("街镇不允许选择行业，请重新新增");
		      return  false;  
		  }
	  }else if(institution_type == 3){
		  //企业必须选择行业
		  if(industry_id == null || industry_id == ""){
			  mini.alert("企业必须选择行业，请重新编辑");
		      return  false; 
		  }
	  }*/
	Ajax({
        type : "post",
        url : "/web/v1/institution/institutionDetailSave",
        data : {
        	json : mini.encode(data)
        },
        success : function(msg) {
            if (errorCheck(msg)) {
            	//保存成功后调取
            	if(msg.data != null){
            		alert(msg.data);
            	}else{
            		returns('ok');
            	}
            }
        }
    });
}

/*function init(){
	var data = mini.getByName("institution_id").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/institution/institutionDetail",
        data : {"institution_id":data},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result != null) {
                	setFormData("tabs", result);
                	editor.html(result.institution_describe);
                }
            }
        }
    });
}*/

//返回
function returns(action){
	//window.location.href = "/web/v1/institution/toInstitutionListPage";
	 if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	    else window.close();
}

//更改关联行业唯一性
function valuechanged(){
	var institution_type = mini.getByName("institution_type").getValue();
	var industry_id = mini.getByName("industry_id");
	var industryText = $("#industryText");
	//var main = $("main");
	
	if(institution_type == 3){
		industryText.show();
		industry_id.show();
		industry_id.required = "required";
	}else if(institution_type == 2){
		//不展示所属行业
		industryText.hide();
		industry_id.hide();
		/*industry_id.required = "";
		industry_id.setValue("");
		industry_id.disable();*/
	}else{
		//不展示所属行业
		industryText.hide();
		industry_id.hide();
		/*industry_id.required = "";
		industry_id.enable();*/
	}
}

//加载数据
/*$(function(){
	init();
});
*/