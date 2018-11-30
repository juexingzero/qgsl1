mini.parse();
var editor = KindEditor.create('#editor',{filterMode: true,allowImageUpload:false,resizeType : 1,width:"100%",height:"400px"});
var editProForm = new mini.Form("#addForm");//项目基本信息表单
$(function(){
init();
});

function init() {
    var lyzj = mini.getbyName("sid").getValue();
    console.log(lyzj);
    Ajax({
        type : "post",
        url : "/web/v1/commerce/queryCommerceDetail",
        data : {"ID":lyzj},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                console.log(result);
                if(result != null) {
                    mini.getbyName("shmc").setValue(result.data.shmc);
                    mini.getbyName("px").setValue(result.data.px);
                    mini.getbyName("lxr").setValue(result.data.lxr);
                    mini.getbyName("sj").setValue(result.data.sj);
                    mini.getbyName("dh").setValue(result.data.dh);
                    mini.getbyName("bgdd").setValue(result.data.bgdd);
                    mini.getbyName("yx").setValue(result.data.yx);
                    mini.getbyName("dz").setValue(result.data.dz);
                    mini.getbyName("sfjldzzs").setValue(result.data.sfjldzz);
                    mini.getbyName("sfjldzz").setValue(result.data.sfjldzz);
                    mini.getbyName("sfzmzbmdjs").setValue(result.data.sfzmzbmdj);
                    mini.getbyName("sfzmzbmdj").setValue(result.data.sfzmzbmdj);
                    if(mini.getbyName("sfjldzzs").getChecked()){
                        $("#time").show();
                        mini.getbyName("zzjlsj1").setValue(result.data.zzjlsj);
                        mini.getbyName("zzjlsj").setValue(result.data.zzjlsj);
                    }
                    editor.html(result.data.js);
                }
            }
        }
    });
}


function timeChange() {
    var time = mini.getbyName("zzjlsj1").getFormValue();
    console.log(time);
    mini.getbyName("zzjlsj").setValue(time);
}


function onValueChanged(e){
	console.log(e);
	 var checked = this.getChecked();
	 if(checked){
		 mini.get("sfjldzz").setValue("1");
         $("#time").show();
	 }else{
		 mini.get("sfjldzz").setValue("0");
         $("#time").hide();
         mini.getbyName("zzjlsj1").setValue("");
         mini.getbyName("zzjlsj").setValue("");
	 }
}

function onValueChanged1(e){
    var checked = this.getChecked();
    if(checked){
        mini.get("sfzmzbmdj").setValue("1");
    }else{
        mini.get("sfzmzbmdj").setValue("0");
    }
    console.log(mini.get("sfzmzbmdj").getValue());
}

/**
 * 保存
 * @param closeWin
 * @returns
 */
function save(){
	var form = new mini.Form("#addForm");
	form.validate();
	if (form.isValid() == false) return;
	var data = form.getData();
    data.js=editor.html();
    data.lyzj = mini.getbyName("lyid").getValue();
    console.log(data);
    //判断手机有效性
    var sj=mini.get("sj").getValue();
    var reg = /^1[34578]\d{9}$/;
    if(reg.test(sj) === false)
    {
        mini.alert("手机号码不合法，请重新输入");
        return  false;
    }
  	Ajax({
        type : "post",
        url : "/web/v1/commerce/saveCommerce",
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

/**
 * 编辑
 * @param closeWin
 * @returns
 */
function edit(){
    var form = new mini.Form("#addForm");
    form.validate();
    if (form.isValid() == false) return;
    var data = form.getData();
    data.js=editor.html();
    data.id=mini.getbyName("sid").getValue();
    console.log(data);
    Ajax({
        type : "post",
        url : "/web/v1/commerce/editCommerce",
        data : {
            json : mini.encode(data)
        },
        success : function(msg) {
            if (errorCheck(msg)) {
                //保存成功后调取
                returns('ok');
            }
        }
    });


}

//返回
function returns(action){
	//window.location.href = "/web/v1/institution/toInstitutionListPage";
	 if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	    else window.close();
}
