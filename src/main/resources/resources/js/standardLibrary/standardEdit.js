mini.parse();
var datagrid = mini.get("datagrid_standardAdd");

/**
 * 动态获取到miniui的值--联动 选择企业评价政府 政府评价企业 进行后台数据查询
 * @returns
 */
function valuechanged(){
	var standard_type = mini.get("standard_type").getValue();
	var standard_id = mini.get("standard_id").getValue();
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
function addRows(){
		var tree = mini.get("datagrid_standardAdd");
		var record  = tree.getSelected();
		//var newNode = {};
		/*if(!node ){
			alert("请选择标准分类");
			return;
		}*/
		/*if(node && node.children && node.children.length==5){
			alert("细则只能添加5条")
			return;
		}
		if(node && node._level == 1 ){
			alert("细则最多只能添加2级")
			return;
		}*/
		//tree.addNode(newNode, "add", node);
		if(record){
			if(record.p_detail_id != -1 && record.p_detail_id != -2){
				alert("项目最多支持二级！");
				return;
			}
		}
		var url = "";
		var standard_id = mini.getByName("standard_id").getValue();
		var detail_id = "";
		if (typeof(record) == "undefined") { 
			//新增1级
			url = "/web/v1/standradLibarary/toStandardsDetailPage?p_detail_id=-1&standard_id="+standard_id+"&detail_level=1&detail_id="+detail_id; 
		} else{
			//新增2级
			var p_detail_id = record.detail_id;
			url = "/web/v1/standradLibarary/toStandardsDetailPage?p_detail_id="+p_detail_id+"&standard_id="+standard_id+"&detail_level=2&detail_id="+detail_id; 
		}
		mini.open({
            url: url,
            title: "新增项目", width: 650, height: 450,
            allowResize:false,
            onload: function () {
            },
            ondestroy: function (action) {
            	datagrid.reload();
            }
        });
}


datagrid.on("rowdblclick", function (sender, record) {
	var record = datagrid.getSelected();
	if(record){
		if(record.p_prj_id == -2){
			alert("请选择具体项目！");
			datagrid.reload();
			return;
		}
	}
	var p_detail_id = record.p_detail_id;
	var standard_id = mini.getByName("standard_id").getValue();
	var detail_level = "";
	if(p_detail_id != "-1"){
		detail_level = "2";
	}else{
		detail_level = "1";
	}
	var detail_id = record.detail_id;
	mini.open({
        url: "/web/v1/standradLibarary/toStandardsDetailPage?p_detail_id="+p_detail_id+"&standard_id="+standard_id+"&detail_level="+detail_level+"&detail_id="+detail_id,
        title: "修改项目", 
		width: 650, 
		height: 450,
		allowResize:false,
        onload: function () {
        },
        ondestroy: function (action) {
        	datagrid.reload();
        }
    });
});



/**
 * 删除行---评价细则
 * @returns
 */
function deleteRow(){
	 var tree = mini.get("datagrid_standardAdd");
	    var node = tree.getSelected();
	    if (node) {
	        if (confirm("确定删除选中节点?")) {
	            var standard_id = mini.getByName("standard_id").getValue();
	            var detail_id = node.detail_id;
	        	//删除细则信息
	            Ajax({
	                type: "post",
	                url: "/web/v1/standradLibarary/deleteDetail?standard_id="+standard_id+"&detail_id="+detail_id,
	                success: function (msg) {
	                    if (errorCheck(msg)) {
	                    	if(msg.data != null){
	                    		alert(msg.data);
	                    	}else{
	                    		 datagrid.reload();
	                    	}
	                    }
	                }
	            });
	        }
	    }
}

/**
 * 保存
 * @param closeWin
 * @returns
 */
function save(){
	var form = new mini.Form("#addForm");
	if (form.isValid() == false) return;
	var treeData = datagrid.getData();
	var standard_plan_score = mini.getByName("standard_plan_score");
	var countScore = 0;
	//遍历获取到tree节点下面分数的值
	for (var i = 0; i < treeData.length; i++) {
		var score = 0;
		if(treeData[i].children && treeData[i].children.length>0){
			for (var j = 0; j < treeData[i].children.length; j++) {
				score += parseInt(treeData[i].children[j].detail_plan_score);
			}
		}
		//分数校验
		if(parseInt(treeData[i].detail_plan_score) != score){
			alert("细则分数与下级细则总分不一致");
			return ;
		}
		countScore += parseInt(treeData[i].detail_plan_score);
	}
	standard_plan_score.setValue(countScore);
	
	var data = form.getData();
	
  	Ajax({
        type : "post",
        url : "/web/v1/standradLibarary/standardUpdate",
        data : {
        	json : mini.encode(data),
        	//treeJson : mini.encode(treeData)
        },
        success : function(msg) {
            if (errorCheck(msg)) {
            	if(msg.data != null){
            		alert(msg.data);
            	}else{
            		//保存成功后调取
                    returns('ok');
            	}
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


//加载列表
$(function(){
	init();
});


function init(){
	var data = mini.getByName("standard_id").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/standradLibarary/evaluateStandardsDetail",
        data : {"standard_id":data},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result != null) {
                	setFormData("addForm", result);
                	if(result.standard_belonged == 2){
                		var belongeds = mini.getByName("standard_belongeds");
                		belongeds.setChecked(true);
                	}
                	datagridInit(result.standard_id);
                	valuechanged();
                }
            }
        }
    });
}


function datagridInit(standard_id){
	datagrid.load({standard_id:standard_id});
}