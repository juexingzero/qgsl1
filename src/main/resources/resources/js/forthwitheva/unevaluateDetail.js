mini.parse();

//切换标准ID加载标准列表
function onLoadStandardList(e){
	var passive_id = mini.get("passive_id").getValue();
	var active_id = mini.get("active_id").getValue();
	var forthwith_id = mini.get("forthwith_id").getValue();
	
	var datagrid = mini.get("datagrid_standardAdd");
	datagrid.load({forthwith_id : forthwith_id,passive_id : passive_id,active_id : active_id});
	/*Ajax({
		type : "POST",
		url : "/web/v1/forthwitheva/showFlowingScore",
		data : {
			forthwith_id: forthwith_id,
			active_id: active_id,
			passive_id: passive_id
		},
		cache: false,
		async: false,
		success : function(result) {
            var standard = result;
            //topicEvaluateStandardData = standard;
            if(standard != null){
            	//二级
        		var paramList = standard;
        		if(paramList != null && paramList.length > 0){
        			$("#standardDetailDiv").html("");
        			var paramHtml = "";
                	for(var i=0; i<paramList.length; i++){
                		var param = paramList[i]
                		var standard_id = param.forthwith_standard_detail_id;
                		var param_name = param.forthwith_standard_detail_name;
                		var param_score = param.plan_score;
                		var param_real_score = param.real_score;
            			paramHtml += "<div class='item_div' standard_id='"+standard_id+"'>"+
        								"<h4><span>"+(i+1)+"、"+param_name+"</span><span class='right blue_text'>"+param_score+"分</span>" +
        								"&nbsp;&nbsp;&nbsp;<span class='right violet_text'>"+param_real_score+"分</span></h4>"+
        								"<ul>";
                		//三级
                		var childrenList = param.evaluateFlowingScoreList;
                		if(childrenList != null && childrenList.length > 0){
                        	for(var j=0; j<childrenList.length; j++){
                        		var children = childrenList[j];
                        		var child_param_id = children.forthwith_standard_detail_id;
                        		var child_param_name = children.forthwith_standard_detail_name;
                        		var child_param_score = children.plan_score;
                        		var child_param_real_score = children.real_score;
                        		paramHtml += "<li class='item_row' child_param_id='"+child_param_id+"'>"+
                    						   	   "<span style='margin-left:15px;'>"+(j+1)+"."+child_param_name+"</span>"+
                    						   	   "<span class='right'>"+child_param_score+"分</span>"+
                    						   	   "&nbsp;&nbsp;&nbsp;<span class='right'>"+child_param_real_score+"分</span>"+
                    						   "</li>";
                        	}
                		}
                    	paramHtml += "</ul></div>";
                	}
                	$("#standardDetailDiv").html(paramHtml);
        		}
            }
		}
	});*/
}

function save(){
	var form = new mini.Form("#addForm");
	form.validate();
	if (form.isValid() == false) return;
	var suggest_id = mini.getByName("suggest_id").getValue();
	var suggest_answer = mini.getByName("suggest_answer").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/forthwitheva/updateAnswer",
        data : {"suggest_id":suggest_id,
        		"suggest_answer":suggest_answer
        		},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result.data != null) {
                	alert(result.data);
                }else{
                	returns('ok');
                }
            }
        }
    });
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
        data : {
	        	"forthwith_id":forthwith_id,
	    		"passive_id":passive_id,
	    		"active_id":active_id
	    		},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result != null) {
                	setFormData("addForm", result);
                	if(result.standard_belonged == 2){
                		var belongeds = mini.getByName("standard_belongeds");
                		belongeds.setChecked(true);
                	}
                	//datagridInit(result.standard_id);
                	//valuechanged();
                }
            }
        }
    });
	onLoadStandardList();
}


//返回
function returns(action){
	//window.location.href = "/web/v1/institution/toInstitutionListPage";
	 if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	    else window.close();
}

