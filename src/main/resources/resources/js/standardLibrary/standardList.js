mini.parse();
var datagrid = mini.get("datagrid1_standardLibararyList");
/**
 * 将评价类型转成文字
 * @param e
 * @returns
 */
function covert(e) {
	if (e.record.standard_type == "1") {
		return "评政府";
	}
	return "评企业";
}
/**
 * 拼接操作 删除 编辑
 * @param e
 * @returns
 */
function onActionRenderer(e) {
	var grid = e.sender;
	var record = e.record;
	var uid = record._uid;
	if (record.index != "*") {
		var row = grid.getRowByUID(uid);
		var is_effect = row.is_effect;
		if(is_effect == '1'){
			return '<a href="javascript:novaild(\''+ grid.id + '\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">停用</a>'+
				   '<a href="javascript:editRow(\'' + grid.id + '\',\'' + uid +'\')" style="text-decoration:none;" class="cus_btn_grid">编辑</a>'+
				   '<a href="javascript:delRow(\''+ grid.id + '\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
		}else{
			return '<a href="javascript:vaild(\''+ grid.id + '\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">启用</a>'+
				   '<a href="javascript:editRow(\'' + grid.id + '\',\'' + uid +'\')" style="text-decoration:none;" class="cus_btn_grid">编辑</a>'+
				   '<a href="javascript:delRow(\''+ grid.id + '\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid_del">删除</a>';
		}
	}
	return "";
}


/**
 * 添加
 * @returns
 */
function add() {
	
            mini.open({
                //targetWindow: window,
                url: "/web/v1/standradLibarary/toStandardLibraryAdd",
                title: "新增评价", 
                width: 600,
                height: 250,
                allowResize:false,
                ondestroy: function (action) {
                	datagrid.reload();
                }
            });
            
        }

function loadGridData(){
    var form = new mini.Form("#datagrid1_standardLibararyList");
    form.validate();
    if (form.isValid() == false){
        return;
    }
    var data = form.getData(true);
    var json = mini.encode(data);
    datagrid.load({json:json});
}
/**
 * 编辑
 * @param gridId
 * @param row_uid
 * @returns
 */
function editRow(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	var standard_id = row.standard_id;
	mini.open({
        //targetWindow: window,
        url: "/web/v1/standradLibarary/toStandardLibraryEdit?standard_id="+standard_id,
        title: "编辑评价", 
        width: 800,
        height: 700,
        allowResize:false,
        ondestroy: function (action) {
        	datagrid.reload();
        }
    });
}
/**
 * 删除
 * @param gridId
 * @param row_uid
 * @returns
 */
function delRow(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	var standard_belonged = row.standard_belonged;
	if(standard_belonged == 2){
		if(row && confirm("确定将删除评价标准吗？")){
			if(standard_belonged == 2 && confirm("该评价同时被设置为即时评价标准，是否同时删除？")){
				Ajax({
		            type: "post",
		            url: "/web/v1/standradLibarary/deleteEvaluateStandards?standard_id="+row.standard_id,
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
	if (standard_belonged == 1||standard_belonged == "") {
		if(row && confirm("确定将删除评价标准吗？")){
			Ajax({
	            type: "post",
	            url: "/web/v1/standradLibarary/deleteEvaluateStandards?standard_id="+row.standard_id,
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



$(function(){
	loadGridData();
});



//标准生效
function vaild(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	if (row && confirm("确定将这条标准启用吗？")) {
		Ajax({
	        type: "post",
	        url: "/web/v1/standradLibarary/checkEvaluateStandard?standard_id="+row.standard_id,
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



//标准失效
function novaild(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	if (row && confirm("确定将这条标准停用吗？")) {
		Ajax({
	        type: "post",
	        url: "/web/v1/standradLibarary/invalidEvaluateStandard?standard_id="+row.standard_id,
	        success: function (msg) {
	            if (errorCheck(msg)) {
	                datagrid.reload();
	            }
	        }
	    });
	}
}

