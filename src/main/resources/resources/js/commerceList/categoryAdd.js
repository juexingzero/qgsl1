mini.parse();

$(function(){
    init();
});

/**
 * 加载数据
 */
function init() {
 var id = mini.getbyName("id").getValue();
 console.log(id);
    Ajax({
        type : "post",
        url : "/web/v1/commerce/queryCommerceDetail",
        data : {"ID":id},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                console.log(result);
                if(result != null) {
                    mini.getbyName("shlx").setValue(result.data.shlx);
                    mini.getbyName("shmc").setValue(result.data.shmc);
                    mini.getbyName("px").setValue(result.data.px);
                }
            }
        }
    });
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
	
  	Ajax({
        type : "post",
        url : "/web/v1/commerce/saveCommerceCategory",
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

    Ajax({
        type : "post",
        url : "/web/v1/commerce/editCommerceCategory",
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
