mini.parse();
var datagrid = mini.get("treegrid");

function valuechanged(){
	var standard_type = mini.get("standard_type").getValue();
	//var standard_id = mini.get("standard_id").getValue();
	if(!standard_type){
		return;
	}else{
		if(standard_type == 1){
			mini.get("standard_belongeds").show();
		}else{
			mini.get("standard_belongeds").hide();
		}
	}
}


function onValueChanged(e){
	 var checked = this.getChecked();
	 if(checked){
		 mini.get("standard_belonged").setValue("2");
	 }else{
		 mini.get("standard_belonged").setValue("1");
	 }
}


/**
 * 增加行---评价细则
 * @returns
 */
/*function addRows(){
	 
		var record = datagrid.getSelected();
		
		if(node && node.children && node.children.length==5){
			alert("细则只能添加5条")
			return;
		}
		
		if(node.p_detail_id != -1 && node.p_detail_id != -2){
			alert("项目最多支持二级！");
			return;
		}

		//新增
		var url = "";
		if (typeof(record) == "undefined") { 
			//添加1级细则
			url = "?p_detail_id=-1&detail_level=1"; 
		} else{
			if(record.p_detail_id == -1){
				//添加2级细则
				url = "?p_detail_id="+record.p_detail_id+"&detail_level=2"; 
			}
		}
		mini.open({
            url: url,
            title: "新增项目", width: 900, height: 560,
            onload: function () {
            },
            ondestroy: function (action) {

            	treegrid.reload();
            }
        });
}*/


/**
 * 删除行---评价细则
 * @returns
 */
/*function deleteRow(){
    var record  = datagrid.getSelectedNode();
    if(record){
		if(record.prj_id == -1){
			alert("请选择具体类别！");
			return;
		}
	}
    if(record && confirm("确实要删除所选细则吗？") ){
		Ajax({
			type : "post",
			url : "",
			data : {detail_id:record.detail_id},
			success : function(msg) {
				if (errorCheck(msg)) {
					treegrid.reload();
				}
			}
		});
	}


}*/

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
        url : "/web/v1/standradLibarary/save",
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
