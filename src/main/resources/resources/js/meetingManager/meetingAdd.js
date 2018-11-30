	    mini.parse();
	    var datagrid_passive = mini.get("datagrid_activeList");
	    var editor = KindEditor.create('#editor',{filterMode: true,allowImageUpload:false,resizeType : 1,width:"100%",height:"350px"});
	    var cpi_pathText = mini.getByName("company_image");//首页图片
	    
	    var fileVideo = mini.get("file");//视频路径
        var imgObj = "";
	    var company_describe = "";

	    /****************************logo-start****************************/
	    
	    //文件操作
	    function onUploadSuccess(e) {
	        mini.alert("上传成功","系统提示");
	        var obj = JSON.parse(e.serverData);
	        this.setText(obj.path[0]);
	        videoUrl = obj.path[0];
	    }
	    function onUploadError(e) {
	        mini.alert("上传失败","系统提示");
	    }
	    //上传文件
	    function startUpload() {
	        fileVideo.startUpload();
	    }

	    //删除上传文件
	    function delUpload(){
	        if (confirm("是否确定删除视频！")) {
	            //fileVideo.setText("");
	            fileVideo.clear();
	            videoUrl = "";
	        }
	    }
	    /****************************logo-end****************************/
	    
	    //提交表单数据
	    function save(){
	        var form = new mini.Form("#form_elegant_add");
	        form.validate();//验证
	        if (form.isValid() == false) return;
	
	        var evaluate_start_time = onTimestampToDateStr(Date.parse(mini.getByName("meeting_starttime").getValue()));
	      	var evaluate_end_time = onTimestampToDateStr(Date.parse(mini.getByName("meeting_endtime").getValue()));
	      	if(evaluate_start_time == evaluate_end_time){
	      		alert("会议开始跟结束时间不能相同");
	      	  	return;
	      	}else if(evaluate_start_time > evaluate_end_time){
	      		alert("会议开始时间不能大于结束时间");
	      	  	return;
	      	}
	        
	        var data = form.getData();
	        
	        //参会人员信息
	        var passive_data = datagrid_passive.data;
	       
	        if(passive_data==''||passive_data==null){
	        	 mini.alert("请选择参会人员！");
	        	 return;
	        }
	        
	        data.meeting_theme = mini.getByName("meeting_theme").getValue();
	        data.meeting_starttime = mini.getByName("meeting_starttime").getValue();
	        data.meeting_endtime = mini.getByName("meeting_endtime").getValue();
	        data.meeting_address = mini.getByName("meeting_address").getValue();
	        data.meeting_navigation = mini.getByName("meeting_navigation").getValue();
	        data.meeting_ll = mini.getByName("meeting_ll").getValue();
	        data.meeting_data = fileVideo.getText();
	        data.is_vote = mini.getByName("is_vote").getValue();
	        data.allow_select = mini.getByName("allow_select").getValue();
	        data.meetings = mini.getByName("meetings").getValue();
	        data.meeting_content = editor.html();
	        var json = mini.encode(data);   //序列化成JSON
	       // $("#submit").off("click");
	        var str=[];
	        $(".selectTxt").each(function(i,o){
	        	str.push($(o).find("input").val())
	        })
	        $.ajax({
	            url: "/web/v1/meetingManager/saveMeetingManager",
	            type: "POST",
	            data: { json: json,passive_json:mini.encode(passive_data),vote_options:mini.encode(str) },
	            dataType: "json",
	            success: function (result) {
	                if(result.state == "1"){
	                	 window.location.href="/web/v1/meetingManager/toMeetingManagerPage";
	                }else{
	                    alert("添加失败！");
	                }
	            }
	        });
	        $("#submit").on("click", save);
	    }
		
    	//取消
    	function onCancel() {
    	  	window.history.go(-1);
    	}
    	
    	//显示会议内容/参会人员
    	function onMeetingShow(e) {
    		var type = e.tab._id;
    		if(type == "1"){
    			$("#meetingcontent_tr").show();
    			$("#meetingperson_tr").hide();
    		}else{
    			$("#meetingcontent_tr").hide();
    			$("#meetingperson_tr").show();
    		}
    	}
    	
    	
    	//进入人员选择页面
    	function onInstitutionSelect(div_id) {
    		var total_span_id = "active_total";
    		var p_datagrid = getDataGridByDivId(div_id);
    		mini.open({
    			url: "/web/v1/meetingManager/toPersonSelectPage",
    			title: "选择参会人员",
    			width: 600,
    			height: 485,
    		    onload: function () {
    		        var iframe = this.getIFrameEl();
    		        var data = {
    	        		//topic_type			: mini.getByName("topic_type").getValue(),
    	        		//standard_type		: standard_type, 
    	        		//type				: type, 
    	        		p_datagrid 			: p_datagrid
    	        	//	evaluate_start_time	: evaluate_start_time
    		        };
    		        iframe.contentWindow.SetData(data);
    		    },
    			ondestroy: function (action) {
    				if (action == "ok") {
    					var iframe = this.getIFrameEl();
    					var data = iframe.contentWindow.GetData();
    					data = mini.clone(data);
    					var total = p_datagrid.data.length;
    					$("#"+total_span_id).text(total);
    				}
    			}
    		});
    	}
    	
    	//根据div_id获取要操作的datagrid块
    	function getDataGridByDivId(div_id){
    		var datagrid;
    		if(div_id == "meetingperson_tr"){
    			datagrid = datagrid_passive;
    		}
    		return datagrid;
    	}
    	
    	//参会人员列表-操作-增加“删除”按钮
    	function onPassiveOperation(e) {
    		var record = e.record;
    		if(record.index != "*"){
    			return '<a href="javascript:onDeleteRow(\'meetingperson_tr\',\'active_total\')" style="text-decoration:none;">删除</ a>';
    		}
    		return "";
    	}
    	
    	//参会人员列表-操作-删除单条
    	function onDeleteRow(div_id, total_id){
    		var datagrid = getDataGridByDivId(div_id);
    		var row = datagrid.getSelected();
    		if (row) {
    			if (confirm("确定删除该条记录？")) {
    				datagrid.removeRow(row);
    				$("#"+total_id).text(parseInt(datagrid.getData().length));
    	     	}
    		}
    	}
    	
    	//定位
    	function onPositioning(){
    		var address=mini.getByName("meeting_navigation").getValue();
    		
    		$.ajax({
	            url: "/web/v1/meetingManager/positioning",
	            type: "POST",
	            data: { address: address },
	            dataType: "json",
	            success: function (result) {
	                if(result.state == "1"){
	                	 mini.alert("定位成功！");
	                	 mini.getByName("meeting_ll").setValue(result.data);
	                }else{
	                	 mini.alert("请准确填写地址！");
	                }
	            },
	            error: function(){
	            	mini.alert("请准确填写地址！");
	            }
	        });
    	}
    	
    	$(".searchBtn").click(function(){
    		var list=$(this).parents("tr").prevAll('.selectTxt').eq(0).clone();
    		list.find('label').remove();
    		list.find('input').val('');
    		$(this).parents("tr").before(list)
    		
    	})
    	
    	//是否选择会议投票
    	$("#is_vote").click(function(){
    		var is_vote=mini.getByName("is_vote").getValue();
    		if(is_vote==0){
    			$("#xxxz").hide();
    			$("#yt").hide();
    			$(".selectTxt").hide();
    			$("#tjxx").hide();
    		}else{
    			$("#xxxz").show();
    			$("#yt").show();
    			$(".selectTxt").show();
    			$("#tjxx").show();
    		}
    		
    	})
    	
    	