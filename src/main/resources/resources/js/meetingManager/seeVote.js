	    
	    var datagrid_passive = mini.get("searchForm");
    	
    	//加载投票数据
  function init(){
   var meeting_id=mini.getbyName("meeting_id").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/meetingManager/VoteDetail",
        data : {"meeting_id":meeting_id},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result != null) {
                	$("#meeting_theme").text(result.meeting_theme);
                	$("#meetings").text(result.meetings);
                	$("#votePerson").text(result.votePerson);
                	
                	for(var i=0;i<result.optionsList.length-1;i++){
                		var list=$('.selectTxt').eq(i).clone();
                		list.find('label').text("选项"+(i+2)+"：");
                		list.find('span').text(result.optionsList[i+1]);
                		$('.selectTxt').eq(i).after(list);
                	}
                	for(var i=0;i<result.voteNum.length-1;i++){
                		var list=$('.selectxx').eq(0).clone();
                		list.find('label').text("选项"+(i+2)+"：");
                		list.find('span').text(result.voteNum[i+1]+"票("+result.optionsProportion[i+1]+")");
                		$('.selectxx').eq(i).after(list);
                		list.find('td').append('<a href="javascript:void(0)" onclick="votedetail('+result.voteNum[i+1]+',\''+result.optionsList[i+1]+'\')" style="text-decoration:none;" class="more">详情</a>')
                		
                	}
                	 setFormData("searchForm", result);
                	 setTimeout(function() {
                		 $('.selectTxt').eq(0).find('span[id=company_name]').text(result.optionsList[0]);
                		 $('.selectxx').eq(0).find('span').text(result.voteNum[0]+"票("+result.optionsProportion[0]+")");
                		 $('.selectxx').eq(0).find('td').append('<a href="javascript:void(0)" onclick="votedetail('+result.voteNum[0]+',\''+result.optionsList[0]+'\')" style="text-decoration:none;" class="more">详情</a>')
                	 }, 0)
                }
            }
        }
    });
	mini.getByName("vote_starttime").setEnabled(false);
	mini.getByName("vote_endtime").setEnabled(false);
}
    	
//查看投票人数详情
	function votepersondetail(){
		var meeting_id=mini.getbyName("meeting_id").getValue();
		var people=document.getElementById("votePerson").innerText; 
		mini.open({
			url: "/web/v1/meetingManager/toVotePersonNumPage?meeting_id="+meeting_id+"&vote_options="+"&people="+people,
			title: "详情", 
			width: 800,
			height: 600,
			ondestroy: function (action) {
		      	loadGridData();
		    }
		});
	}
	
	//查看每个选项详情
	function votedetail(people,vote){
		var meeting_id=mini.getbyName("meeting_id").getValue();
		mini.open({
			url: "/web/v1/meetingManager/toVotePersonNumPage?meeting_id="+meeting_id+"&vote_options="+vote+"&people="+people,
			title: "详情", 
			width: 800,
			height: 600,
			ondestroy: function (action) {
		      	loadGridData();
		    }
		});
	}
    	
    	//加载数据
		$(function(){
			mini.parse();
			init();
		});