	mini.parse();
	var datagrid = mini.get("datagrid1_standardLibararyList");
	
	$(function(){
		loadGridData();
	});
	
	//导出
	function exportRow(){
		
	    $.ajax({
	        type : "POST",
	        url : "/web/v1/activity/exportActivityEntryResult",
	        data : {
	        	activity_id : activity_id
	        },
			cache: false,
			async: false,
	        success : function(msg) {
	            if (errorCheck(msg)) {
	            	alert("导出成功！");
	            }
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
	    datagrid.load({activity_id:activity_id});
	}
	
	//参会性别务转成文字
	function covertxb(e) {
		if (e.record.member_linkman_sex == "XB-01") {
			return "男";
		}else{
			return "女";
		}  
	}
