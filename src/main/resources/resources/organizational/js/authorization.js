$(function(){ 
	data();
}); 
//毫秒转换为日期格式
var format = function(time, format){
    var t = new Date(time);
    var tf = function(i){return (i < 10 ? '0' : '') + i};
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        switch(a){
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    });
}

//查询所有一级账号
function data(){
	$(".one").children().html("");
	$.ajax({
	    type: "POST",
	    url: "/organ/deptImage",
	    data: {},
	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    dataType: "json",
	    async:false,
	    success: function(result) {
	    	if(result.code != 200){
	    		return;
			}
	    	//遍历所有公司
	    	$.each(result.data, function(idx, company) {
	    		var gs = "gs";
	    		if(company.dept_name == '' || company.dept_name == null){
	    			var name = company.dept_name
	    		}else{
	    			var name = company.dept_name
	    		}
	    		if(idx == 0){
	    			if(result.size > 1){
                        $(".td1").append("<br><table cellspacing='1' cellpadding='4'><tr><td class='tdbottom' nowrap><a id='organizationgs"+company.dept_id+"'  href='javascript:shower(\""+company.dept_id+"\",\"gs\");'>"+name+"</a></td></tr></table><i></i><table border='0' cellspacing='0' cellpadding='0' width='100%'><tr class='next'></tr></table>");
                    }else if(result.size == 1){
                        //只有一个下级时添加包裹
                        $(".td1").append("<br><table cellspacing='1' cellpadding='4'><tr><td class='tdbottom' nowrap><a id='organizationgs"+company.dept_id+"'  href='javascript:shower(\""+company.dept_id+"\",\"gs\");'>"+name+"</a></td></tr></table>");
                    }
                }else{
	    			if(result.size == 1){
		    			//只有一个下级时添加
	    				$("#organizationgs"+company.p_dept_id).parent("td").parent("tr").parent("tbody").parent("table").parent("td").children("table").children("tbody").children(".next").append("<td align=center valign='top'><table border='0'  cellspacing='1' cellpadding='4' width='100%' height=1><tr><td width='50%'></td><td width='50%'></td></tr></table><i></i><table border='0' cellspacing='0' cellpadding='0' width='100%'><tr class='next'></tr></table></td>");
		    		}else{
		    			//多个下级时添加包裹
		    			if($("#organizationgs"+company.p_dept_id).parent("td").parent("tr").parent("tbody").parent("table").parent("td").children("table").children("tbody").children(".next").html() == ""){
		    				//第一个下级
		    				$("#organizationgs"+company.p_dept_id).parent("td").parent("tr").parent("tbody").parent("table").parent("td").children("table").children("tbody").children(".next").append("<td align=center class='next' valign='top'><table border='0'  cellspacing='0' cellpadding='0' width='100%' height=1><tr><td width='50%'></td><td width='50%' bgcolor='#000000'></td></tr></table><i></i><table style='margin-left:10px;' cellspacing='1' cellpadding='4'><tr><td class='tdbottom' nowrap><a id='organizationgs"+company.dept_id+"' href='javascript:shower(\""+company.dept_id+"\",\"gs\");' >"+name+"</a></td></tr></table><i></i><table border='0' cellspacing='0' cellpadding='0' width='100%'><tr class='next'></tr></table></td>");
		    			}else if((idx+1) == result.size){
		    				//最后一个下级
		    				$("#organizationgs"+company.p_dept_id).parent("td").parent("tr").parent("tbody").parent("table").parent("td").children("table").children("tbody").children(".next").append("<td align=center class='next' valign='top'><table border='0'  cellspacing='0' cellpadding='0' width='100%' height=1><tr><td width='50%' bgcolor='#000000'></td><td width='50%'></td></tr></table><i></i><table style='margin-left:10px;' cellspacing='1' cellpadding='4'><tr><td class='tdbottom' nowrap><a id='organizationgs"+company.dept_id+"' href='javascript:shower(\""+company.dept_id+"\",\"gs\");' >"+name+"</a></td></tr></table></td>");
		    			}else{
		    				//中间的下级
		    				$("#organizationgs"+company.p_dept_id).parent("td").parent("tr").parent("tbody").parent("table").parent("td").children("table").children("tbody").children(".next").append("<td align=center class='next' valign='top'><table border='0'  cellspacing='0' cellpadding='0' width='100%' height=1><tr><td width='50%' bgcolor='#000000'></td><td width='50%' bgcolor='#000000'></td></tr></table><i></i><table style='margin-left:10px;' cellspacing='1' cellpadding='4'><tr><td class='tdbottom' nowrap><a id='organizationgs"+company.dept_id+"' href='javascript:shower(\""+company.dept_id+"\",\"gs\");' >"+name+"</a></td></tr></table><i></i><table border='0' cellspacing='0' cellpadding='0' width='100%'><tr class='next'></tr></table></td>");
		    			}
		    		}
	    		}
	    		
	    	 });
	    	
	    	//去除右边表格横线
	    	$(".next").each(function(idx) {
	    		if($(this).attr("valign") != 'top'){
	    			$(this).children("td").each(function(idxe) {
	    				
	    				var len = $(this).parent("tr").children("td").length;
	    				if(len == 1){
	    					$(this).parent("tr").children("td").children("i").remove();
	    				}
	    				if(idxe == 0){
	    					$(this).children("table").each(function(idxs) {
	    						if(idxs == 0){
	    							$(this).children("tbody").children("tr").children("td").each(function(idxss) {
	    								if(idxss == 0){
	    									$(this).css("background","#fff");
	    								}
	    							});	
	    						}
	    					});	
	    				}
	    				if((idxe+1) == len){
	    					$(this).children("table").each(function(idxs) {
	    						if(idxs == 0){
	    							$(this).children("tbody").children("tr").children("td").each(function(idxss) {
	    								if(idxss == 1){
	    									$(this).css("background","#fff");
	    								}
	    							});	
	    						}
	    					});	
	    				}
	    			});
	    		}
	    	});

            $(".next").each(function(idx) {
                if($(this).html() == ""){
                	$(this).parent().parent().prev().css("background","#FFF");
				}
            });

	    	//去除顶部表格横线
	    	$("#organizationgs1").children("td").each(function(idx) {
	    		var len = $(this).parent("tr").children("td").length;	
	    		if((idx+1) == len){
	    			$(this).children("table").each(function(idxs) {
						if(idxs == 0){
							$(this).children("tbody").children("tr").children("td").each(function(idxss) {
								if(idxss == 1){
									$(this).css("background","#fff");
								}
							});	
						}
					});	
	    		}
	    	});	
	    }
	    });
}

function shower(id,name){
	/*var data = {};
    data.id = "1_1_1";
    data.text = "组织管理";
    var urlText = "/organ/organAdd";
    if(id != null && name != null){
        urlText += "?id="+id+"&name="+name;
    }
    data.url = urlText;
    data.iconCls = "fa-cog";
	window.parent.activeTab(data);*/
}
