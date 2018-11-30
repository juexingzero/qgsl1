	mini.parse();
	var datagrid = mini.get("datagrid1_standardLibararyList");
	
	$(function(){
		loadGridData();
	});
	
	//导出
	function exportRow(){
		
	    $.ajax({
	        type : "POST",
	        url : "/web/v1/meetingManager/exportTopicEvaluateResult",
	        data : {
	        	meeting_id : meeting_id
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
	    datagrid.load({meeting_id:meeting_id});
	}
	
	//将参与类型转成文字
	function coverttpi(e) {
		if (e.record.participate_state == "0") {
			return "不参加";
		} else if (e.record.participate_state == "1") {
			return "参加";
		} else{
			return "-";
		}
	}
	
	//面板切换
	function changeTabs(e){
		var state = e.tab._id;
		datagrid.load({state:state,meeting_id:meeting_id});
	}
