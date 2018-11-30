mini.parse();

//切换标准ID加载标准列表
function onLoadStandardList(e){
	var passive_id = mini.get("passive_id").getValue();
	var active_id = mini.get("active_id").getValue();
	var forthwith_id = mini.get("forthwith_id").getValue();
	
	var datagrid = mini.get("datagrid_standardAdd");
	datagrid.load({forthwith_id : forthwith_id,passive_id : passive_id,active_id : active_id});
}

$(function(){
	init();
	//折叠切换
	$("#cuttle").click(function(){
		$("#standardDetailDiv").toggle();
	});
});

function init(){
	var forthwith_id = mini.getByName("forthwith_id").getValue();
	var passive_id = mini.getByName("passive_id").getValue();
	var active_id = mini.getByName("active_id").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/forthwitheva/evaluateDetail",
        data : {"forthwith_id":forthwith_id,
        		"passive_id":passive_id,
        		"active_id":active_id
        		},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result != null) {
                	setFormData("addForm", result);
                	//mini.get("real_score_sum").setValue(result.real_score_sum);
                	onLoadStandardList();
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

