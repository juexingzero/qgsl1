mini.parse();
var datagrid = mini.get("datagrid_standardAdd");

//关闭
function returns(action){
	 if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	    else window.close();
}

function init(){
	var standard_id = mini.get("id_standard_id").getValue();
	$.ajax({
        type : "post",
        url : "/web/v1/standradLibarary/evaluateStandardsDetail",
        data : {
        	standard_id	: standard_id
        },
        success : function(msg) {
            if (errorCheck(msg)) {
                var standard = mini.decode(msg);
                if(standard != null) {
                	$("#id_standard_name").text(standard.standard_name);
                	var standard_type = standard.standard_type;
                	if(standard_type == "1"){
                		$("#id_standard_type").text("企业评价政府");
                		$("#belongeds_td").show();
                	}else{
                		$("#id_standard_type").text("政府评价企业");
                		$("#belongeds_td").hide();
                	}
                	$("#id_standard_plan_score").text(standard.standard_plan_score);
                	var standard_belongeds = standard.standard_belongeds;
                	if(standard_belongeds == "1"){
                		$("#id_standard_belongeds").text("否");
                	}else{
                		$("#id_standard_belongeds").text("是");
                	}
                	datagridInit(standard.standard_id);
                }
            }
        }
    });
}

function datagridInit(standard_id){
	datagrid.load({standard_id:standard_id});
}

//加载列表
$(function(){
	init();
});
