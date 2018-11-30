	    mini.parse();
	    var datagrid_passive = mini.get("datagrid_activeList");
	    var datagrid = mini.get("datagrid_institutionList");
	    var editor = KindEditor.create('#editor',{filterMode: true,allowImageUpload:false,resizeType : 1,width:"100%",height:"350px"});
	    var cpi_pathText = mini.getByName("company_image");//首页图片
	    var fileVideo = mini.get("file");//视频路径
        var imgObj = "";
	    var company_describe = "";
	    var videoUrl = "";

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
	    
	  //控制结束时间不能早于开始时间
	    function onDrawDate(e) {
	        var date = e.date;
	        var d = new Date();
	        d.setDate(d.getDate()-1);
	        if (date.getTime() < d.getTime()){
	            e.allowSelect = false;
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
	    
    	
    	//显示会议内容/参会人员
    	function onMeetingShow(e) {
    		var type = e.tab._id;
    		if(type == "1"){
    			$("#meetingcontent_tr").show();
    			$("#meetingperson_tr").hide();
    		}else{
//    			var position= mini.getByName("position_type").getValue();
//        		datagrid.load({position:position});
    			$("#meetingcontent_tr").hide();
    			$("#meetingperson_tr").show();
//    			var allRow = datagrid.getData();
    		}
    	}
    	
    	
    	//进入人员选择页面
    	function onInstitutionSelect(div_id) {
    		var total_span_id = "active_total";
    		mini.open({
    			url: "/web/v1/meetingManager/toPersonSelectPage",
    			title: "选择参会人员",
    			width: 800,
    			height: 600,
    		    onload: function () {
    		        var iframe = this.getIFrameEl();
    		        var data = {
    	        		//topic_type			: mini.getByName("topic_type").getValue(),
    	        		//standard_type		: standard_type, 
    	        		//type				: type, 
    	        		p_datagrid 			: datagrid
    	        	//	evaluate_start_time	: evaluate_start_time
    		        };
    		        iframe.contentWindow.SetData(data);
    		    },
    			ondestroy: function (action) {
    				if (action == "ok") {
    					var total = datagrid.data.length;
    					
//    					var iframe = this.getIFrameEl();
//    					var data = iframe.contentWindow.GetData();
//    					data = mini.clone(data);
//    					var total = p_datagrid.data.length;
//    					$("#"+total_span_id).text("2");
    					$("#"+total_span_id).text(total);
    				}
    			}
    		});
    	}
    	
    	//根据div_id获取要操作的datagrid块
    	function getDataGridByDivId(div_id){
    		var datagrid;
    		if(div_id == "meetingperson_tr"){
    			datagrid = datagrid;
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
    	
    	//参会人员列表-将商会职务转成文字
    	function covertshzw(e) {
    		if (e.record.position == "JBQZSHZW-01") {
    			return "主席";
    		} else if (e.record.position == "JBQZSHZW-02") {
    			return "党组书记";
    		}  else if (e.record.position == "JBQZSHZW-03") {
    			return "副主席";
    		}  else if (e.record.position == "JBQZSHZW-04") {
    			return "副会长";
    		}  else if (e.record.position == "JBQZSHZW-05") {
    			return "常委";
    		}  else if (e.record.position == "JBQZSHZW-06") {
    			return "执委";
    		}  else if (e.record.position == "JBQZSHZW-07") {
    			return "会员";
    		}  else{
    			return "未知职务";
    		}   
    	}
    	
    	//参会人员列表-操作-删除单条
    	function onDeleteRow(div_id, total_id){
    		
    		var allRow = datagrid.getData();
    		 var rows = datagrid.getSelecteds();
             if (rows.length > 0) {
            	 datagrid.removeRows(rows, true);      
            	 $("#active_total").text(allRow.length-1);
             }
    		
//    		var datagrid = getDataGridByDivId(div_id);
//    		var row = datagrid.getSelected();
//    		if (row) {
//    			if (confirm("确定删除该条记录？")) {
//    				datagrid.removeRow(row);
//    				$("#"+total_id).text(parseInt(datagrid.getData().length));
//    	     	}
//    		}
    	}
    	
    	
    	//清空商会
    	function onInstitutionClear(div_id, span_total_id){
    		var allRow = datagrid.getData();
    			if(allRow != null && allRow.length > 0){
    				for(var i=0; i<allRow.length; i++){
    					datagrid.removeRow(allRow[i]);
    				}
    				$("#active_total").text("0");
    			}
    	}
    	
	    //提交表单数据
	    function save(){
	    	
	        var form = new mini.Form("#form_meeting_detail");
	        var state = mini.getByName("state").getValue();
	        form.validate();//验证
	        if (form.isValid() == false) return;
	        
	    	var evaluate_start_time = onTimestampToDateStr(Date.parse(mini.getByName("meeting_starttime").getValue()));
	      	var evaluate_end_time = onTimestampToDateStr(Date.parse(mini.getByName("meeting_endtime").getValue()));
	      	var receipt_time = onTimestampToDateStr(Date.parse(mini.getByName("receipt_time").getValue()));
	      	if(receipt_time != 'NaN-NaN-NaN NaN:NaN'){
	      	if(receipt_time > evaluate_start_time){
	      		alert("回执截止时间不能大于会议开始时间！");
	      	  	return;
	      	}else if(receipt_time == evaluate_start_time){
	      		alert("回执截止时间不能等于会议开始时间！");
	      	  	return;
	      	}
	      	}
	      	if(evaluate_start_time == evaluate_end_time){
	      		alert("会议开始跟结束时间不能相同！");
	      	  	return;
	      	}else if(evaluate_start_time > evaluate_end_time){
	      		alert("会议开始时间不能大于结束时间！");
	      	  	return;
	      	}
	      	
	
	        var data = form.getData();
	        
	        //参会人员信息
	        var passive_data = datagrid.data;
	        
	        if(passive_data==''){
	        	alert("请选择参会人员！");
	      	  	return;
	        }
	        if(editor.html()==''||editor.html()==null){
	        	alert("请填写会议内容！");
	      	  	return;
	        }
	        var str=[];
	        $(".selectTxt").each(function(i,o){
	        	if($(o).find("input").val()!=''){
	        		str.push($(o).find("input").val())	
	        	}
	        	
	        })
	        if(mini.getByName("is_vote").getValue()=="0"){
	        	if(str.length<=mini.getByName("allow_select").getValue()){
		        	alert("会议投票允许选择数量必须小于选项数量！");
		      	  	return;
		        }	
	        	if(mini.getByName("meetings").getValue()==''){
	        		alert("请填写议题内容！");
		      	  	return;
	        	}
	        }
	        
	        if(mini.getByName("meeting_ll").getValue()==''){
	        	alert("请点击定位按钮！");
	        	return;
	        }
	        data.vote_id = mini.getByName("vote_id").getValue();
	        data.meeting_id = mini.getByName("meeting_id").getValue();
	        data.meeting_theme = mini.getByName("meeting_theme").getValue();
	        data.meeting_starttime = mini.getByName("meeting_starttime").getValue();
	        data.meeting_endtime = mini.getByName("meeting_endtime").getValue();
	        data.meeting_address = mini.getByName("meeting_address").getValue();
	        data.meeting_navigation = mini.getByName("meeting_navigation").getValue();
	        data.meeting_ll = mini.getByName("meeting_ll").getValue();
	        data.meeting_data = mini.getByName("wjmc").getValue();
	        data.is_vote = mini.getByName("is_vote").getValue();
	        data.allow_select = mini.getByName("allow_select").getValue();
	        data.meetings = mini.getByName("meetings").getValue();
	        data.receipt_time=mini.getByName("receipt_time").getValue();
	        data.meeting_content = editor.html();
	        var json = mini.encode(data);   //序列化成JSON
	       
	        
	        if(state!=2){
	        	$.ajax({
		            url: "/web/v1/meetingManager/updateMeetingManager",
		            type: "POST",
		            data: { json: json,passive_json:mini.encode(passive_data),vote_options:mini.encode(str) },
		            dataType: "json",
		            success: function (result) {
		                if(result == "1"){
		                	CloseWindow("cancel");
		                	 window.location.href="/web/v1/meetingManager/toMeetingManagerPage";
		                }else{
		                    alert("已存在该会议名称！");
		                }
		            }
		        });
	        }else{
	        	 $.ajax({
	 	            url: "/web/v1/meetingManager/saveMeetingManager",
	 	            type: "POST",
	 	            data: { json: json,passive_json:mini.encode(passive_data),vote_options:mini.encode(str) },
	 	            dataType: "json",
	 	            success: function (result) {
	 	                if(result == "1"){
	 	                	CloseWindow("cancel");
	 	                	 window.location.href="/web/v1/meetingManager/toMeetingManagerPage";
	 	                }else{
	 	                	 alert("已存在该会议名称！");
	 	                }
	 	            }
	 	        });
	        }
	        
	        $("#submit").on("click", save);
	    }
		
    	
    	
    	//加载会议数据
    	function init(){
	var data = mini.getByName("meeting_id").getValue();
	var state = mini.getByName("state").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/meetingManager/MeetingManagerDetail",
        data : {"meeting_id":data},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(state==0){
                	for(var i=0;i<result.wjlength-1;i++){
                		
                		var list=$("#form1").find(".list").eq(i).clone();
                		list.find('input[name=text]').val(result.dataList[i+1]);
                		//list.find('.form1_del').css('display','inline-block');
                		$("#form1").append(list);
                	}
                	//详情
                	$("#xxxz1").hide();
                	//$("#wjsctd").hide();
            	if(result.is_vote==1){
            		$("#xxxz").hide();
            		$("#yt").hide();
            		$("#hytp").hide();
            		$(".selectTxt").hide();
            		$("#tjxx").hide();
            	}}else{
            		for(var i=0;i<result.wjlength-1;i++){
                		
                		var list=$("#form1").find(".list").eq(i).clone();
                		list.find('input[name=text]').val(result.dataList[i+1]);
                		list.find('.form1_del').css('display','inline-block');
                		$("#form1").append(list);
                	}
            		//编辑
            		if(result.is_vote==0){
                		$("#xxxz").hide();
//                		$("#xxxz1").hide();
//                		$("#xxxz1").show();
//                		$("#yt").hide();
//                		$(".selectTxt").hide();
//                		$("#tjxx").hide();
                	}else{
                		$("#xxxz").hide();
                		$("#xxxz1").hide();
                		$("#yt").hide();
                		$(".selectTxt").hide();
                		$("#tjxx").hide();
                	}
            	}
                if(result != null) {
                	for(var i=0;i<result.optionsList.length-1;i++){
                		var list=$('.selectTxt').eq(i).clone();
                		list.find('label').remove();
                		list.find('input').val(result.optionsList[i+1]);
                		if(state!=0){
                		list.find('td').eq(i+1).append("<span class='cus_btn_grid_del'>删除</span>");
                		}
                		$('.selectTxt').eq(i).after(list);
                	}
                	setFormData("form_meeting_detail", result);
                	 setTimeout(function() {
                		 $('.selectTxt').eq(0).find('input[name=company_name]').val(result.optionsList[0]);
                		 $(".list").eq(0).find('input[name=text]').val(result.dataList[0]);
//                		 $("#zl").eq(0).find("input").val(result.dataList[0]);
                	 }, 0)
                	editor.html(result.meeting_content);
                	 mini.getByName("allow_select1").setValue(result.allow_select);
                	
                }
            }
        }
    });
	if(state==0){
		$(".saveBut").hide();
		$(".cancelBut").hide();
		$(".closeBut").show();
		$(".searchBtn").hide();
		$(".sc").hide();
		$(".shangchuan").hide();
		$(".shanchu").hide();
		$(".xuanze").hide();
		$("#cssc").hide();
		$(".addfile").hide();
		mini.getByName("meeting_theme").setEnabled(false);
		mini.getByName("meeting_starttime").setEnabled(false);
		mini.getByName("meeting_endtime").setEnabled(false);
		mini.getByName("meeting_address").setEnabled(false);
		document.getElementById("file").disabled=true;
		//mini.getByName("file").setEnabled(false);
		//mini.getByName("meeting_data").setEnabled(false);
		mini.getByName("meeting_navigation").setEnabled(false);
		mini.getByName("meetings").setEnabled(false);
		mini.getByName("company_name").setEnabled(false);
		mini.getByName("allow_select").setEnabled(false);
		mini.getByName("receipt_time").setEnabled(false);
	}else{
		$("#zl").hide();
		$(".saveBut").show();
		$(".cancelBut").show();
		$(".closeBut").hide();
	}
}
    	//添加选项
    	$(".searchBtn").click(function(){
    		var tjxxnu =mini.getByName("xxlength").getValue();
    		if(tjxxnu==''){
    			tjxxnu=1;
    			mini.getByName("xxlength").setValue(tjxxnu);
    		}
    		if(tjxxnu=='0'){
    			tjxxnu=1;
    			mini.getByName("xxlength").setValue(tjxxnu);
    		}
    		var tjxxnum=parseInt(tjxxnu);
    		if(tjxxnum>=15){
    			alert("已经达到最大限度");
    		}else{
    		var list=$(this).parents("tr").prevAll('.selectTxt').eq(0).clone();
    		list.find('label').remove();
    		list.find('input').val('');
    		list.find('.cus_btn_grid_del').remove();
    		list.find('td').eq(-1).append("<span class='cus_btn_grid_del'>删除</span>")
    		$(this).parents("tr").before(list);
    		tjxxnum=tjxxnum+1;
    		mini.getByName("xxlength").setValue(tjxxnum);
    		}
    	})
    	$(".table_edit").on('click','.cus_btn_grid_del',function(){
    		$(this).parents(".selectTxt").remove();
    	})
    	//加载人员详情数据
    	function onLoadMeetingPersonDetailData(){
    		
    		var meeting_id = mini.getByName("meeting_id").getValue();
    		var state = mini.getByName("state").getValue();
    		if(state==0){
    			$(".sel_div").hide();
    			$("#zshzw").hide();
    		}
    		
    		$.ajax({
    			type : "POST",
    			url : "/web/v1/meetingManager/getMeetingPersonDetailData",
    			data : {
    				meeting_id	: meeting_id
    			},
    			cache: false,
    			async: false,
    			success : function(result) {
    	          if(result.state){
    	          	var resultMap = result.data;
    	          	
    	          	var passive_data = resultMap.arList;
    	          	datagrid.setData(mini.decode(passive_data));
    	          	
    				$("#active_total").text(passive_data.length);
    	          }else{
    	          	alert("请求错误");
    	          }
    			}
    		});
    	}
    	
    	//测试上传
    	$("#cssc").click(function(){
    		var formData = new FormData($('#form1')[0]);
            $.ajax({
                type: 'post',
                url: "/upload/uploadFile",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
            }).success(function (data) {
            	var obj=JSON.parse(data);
            	alert(obj.path);
            	if(obj.path!=null){
            		mini.getByName("wjmc").setValue(obj.path);
                    alert("上传成功！");
            	}else{
            		 alert("请选择文件！");
            	}
            	
            }).error(function () {
                alert("上传失败！");
            });
    	})
    	
    	//测试添加上传
    	$(".addfile").click(function(){
    		var wjlengt=mini.getByName("wjlength").getValue();
    		if(wjlengt==''){
    			wjlengt=1;
    			mini.getByName("wjlength").setValue(wjlengt);
    		}
    		if(wjlengt<3){
    			var list=$("#form1").find(".list").eq(0).clone();
        		list.find('input').val('');
        		list.find('.form1_del').css('display','inline-block');
        		$("#form1").append(list);
        		mini.getByName("wjlength").setValue(parseInt(wjlengt)+1);
    		}else{
    			alert("已达到上限！");
    		}
    		
    	})
    	$("#form1").on('click','.form1_del',function(){
    		$(this).parent().remove();
    		var num=mini.getByName("wjlength").getValue();
    		var a=num-1;
    		mini.getByName("wjlength").setValue(a);
    	})
    	
    	$("#form1").on('change','.htmlfile2',function(){
    		$(this).parent().prev('.htmlfile1').val($(this).val())
    	})
    	//是否选择会议投票
    	$("#is_vote").click(function(){
    		var is_vote=mini.getByName("is_vote").getValue();
    		var state = mini.getByName("state").getValue();
    		if(state==2){
    			if(is_vote==0){
    				$("#xxxz1").hide();
        			$("#xxxz").hide();
        			$("#yt").hide();
        			$(".selectTxt").hide();
        			$("#tjxx").hide();
        		}else{
        			$("#xxxz1").show();
        			$("#yt").show();
        			$(".selectTxt").show();
        			$("#tjxx").show();
        		}
    		}else{
    		if(is_vote==0){
    			$("#xxxz1").hide();
    			$("#yt").hide();
    			$(".selectTxt").hide();
    			$("#tjxx").hide();
    		}else{
    			$("#xxxz1").show();
    			$("#yt").show();
    			$(".selectTxt").show();
    			$("#tjxx").show();
    		}
    		}
    	})
    	
    	
    	//选择总商会职位类型
//    	$("#position_type").valuechanged(function(){
//    		var position= mini.getByName("position_type").getValue()
//    		datagrid.load({position:position});
//    	})
    	
    	//参会人员-根据职位选择总商会人员信息
    	var rbl = mini.get("position_type");
    rbl.on("valuechanged", function (e) {
    	var position= mini.getByName("position_type").getValue();
    	$.ajax({
			type : "POST",
			url : "/web/v1/meetingManager/getAttendeesResultList",
			data : {
				position	: position
			},
			cache: false,
			async: false,
			success : function(result) {
	          	var resultMap = result.data;
	          	
	          	var passive_data = resultMap.arList;
	          	datagrid.setData(mini.decode(passive_data));
				$("#active_total").text(resultMap.len);
			}
		});
    });
    	
    	//取消
    	function onCancel() {
    		 CloseWindow("cancel");
    	}
    	
    	//加载数据
		$(function(){
			var state = mini.getByName("state").getValue();
			if(state!=2){
    			onLoadMeetingPersonDetailData();	
				init();	
			}else{
				$("#zl").hide();
				$("#xxxz").hide();
			}
			
		});