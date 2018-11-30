	    mini.parse();
	    var datagrid_passive = mini.get("datagrid_activeList");
	    var datagrid = mini.get("datagrid_institutionList");
	    var editor = KindEditor.create('#editor',{filterMode: true,allowImageUpload:false,resizeType : 1,width:"100%",height:"350px"});
	    var cpi_pathText = mini.getByName("newsImg");//首页图片
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
    		var address=mini.getByName("activity_navigation").getValue();
    		
    		$.ajax({
	            url: "/web/v1/meetingManager/positioning",
	            type: "POST",
	            data: { address: address },
	            dataType: "json",
	            success: function (result) {
	                if(result.state == "1"){
	                	 mini.alert("定位成功！");
	                	 mini.getByName("longitude_latitude").setValue(result.data);
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
    		if (e.record.position == "SHZWLX-01") {
    			return "主席";
    		} else if (e.record.position == "SHZWLX-02") {
    			return "党组书记";
    		}  else if (e.record.position == "SHZWLX-03") {
    			return "副主席";
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
	        var form = new mini.Form("#form_activity_detail");
	        var state = mini.getByName("state").getValue();
	        form.validate();//验证
	        if (form.isValid() == false) return;
	        
	    	var evaluate_start_time = onTimestampToDateStr(Date.parse(mini.getByName("activity_start_time").getValue()));
	      	var evaluate_end_time = onTimestampToDateStr(Date.parse(mini.getByName("activity_end_time").getValue()));
	      	var receipt_time = onTimestampToDateStr(Date.parse(mini.getByName("activity_entry_end_time").getValue()));
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
	        var qbhy=mini.getByName("qbhy").getValue()
	        if(passive_data==''&&qbhy==''){
	        	alert("请选择参会人员！");
	      	  	return;
	        }
	        if(editor.html()==''||editor.html()==null){
	        	alert("请填写活动内容！");
	      	  	return;
	        }
//	        var str=[];
//	        $(".selectTxt").each(function(i,o){
//	        	str.push($(o).find("input").val())
//	        })
//	        if(mini.getByName("is_vote").getValue()=="0"){
//	        	if(str.length<=mini.getByName("allow_select").getValue()){
//		        	alert("允许选择数量必须小于选项数量！");
//		      	  	return;
//		        }	
//	        }
	        
//	        data.vote_id = mini.getByName("vote_id").getValue();
	        data.activity_id = mini.getByName("activity_id").getValue();
	        data.activity_name = mini.getByName("activity_name").getValue();
	        data.activity_start_time = mini.getByName("activity_start_time").getValue();
	        data.activity_end_time = mini.getByName("activity_end_time").getValue();
	        data.activity_address = mini.getByName("activity_address").getValue();
	        data.activity_navigation = mini.getByName("activity_navigation").getValue();
	        data.longitude_latitude = mini.getByName("longitude_latitude").getValue();
	        data.activity_image=mini.getByName("newsImg").getValue();
	        //data.meeting_data = mini.getByName("wjmc").getValue();
	        data.is_sign = mini.getByName("is_sign").getValue();
	        //data.allow_select = mini.getByName("allow_select").getValue();
	       // data.meetings = mini.getByName("meetings").getValue();
	        data.activity_file=videoUrl;
	        data.activity_entry_end_time=mini.getByName("activity_entry_end_time").getValue();
	        data.activity_content = editor.html();
	        var json = mini.encode(data);   //序列化成JSON
	        if(state!=2){
	        	$.ajax({
		            url: "/web/v1/activity/updateActivityInfo",
		            type: "POST",
		            data: { json: json,passive_json:mini.encode(passive_data),qbhy:qbhy},
		            dataType: "json",
		            success: function (result) {
		                if(result == "1"){
		                	CloseWindow("cancel");
		                	 window.location.href="/web/v1/activity/toActivityListPage";
		                }else{
		                    alert("已存在该活动名称！");
		                }
		            }
		        });
	        }else{
	        	 $.ajax({
	 	            url: "/web/v1/activity/saveActivityInfo",
	 	            type: "POST",
	 	            data: { json: json,passive_json:mini.encode(passive_data),qbhy:qbhy},
	 	            dataType: "json",
	 	            success: function (result) {
	 	                if(result == "1"){
	 	                	CloseWindow("cancel");
	 	                	 window.location.href="/web/v1/activity/toActivityListPage";
	 	                }else{
	 	                	 alert("已存在该活动名称！");
	 	                }
	 	            }
	 	        });
	        }
	        
	        $("#submit").on("click", save);
	    }
		
    	
    	
    	//加载会议数据
    	function init(){
	var data = mini.getByName("activity_id").getValue();
	var state = mini.getByName("state").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/activity/ActivityInfoDetail",
        data : {"activity_id":data},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result != null) {
                	// 图片
                    var cpi_pathText = mini.get("newsImg");
                   // var test=[{\"path\":\"\/upload\/FILE_OK_15429608329656034.jpg\",\"name\":\"\"}];
                    $("#uploadImage").uploadFile({
                        size : 1,
                        imagePath:result.activity_image,
                        success: function (data) {
                            var images = "";
                            for (var i = 0; i < data.length; i++) {
                                images += data[i]+",";
                            }
                            if(images != ""){
                                images = images.substring(0, images.length-1);
                            }
                            cpi_pathText.setValue(images);
                        }
                    });
                    if(state==0){
                    	//详情
                    	 $(".image_success").css("display","none");
             			$(".image_down").hide();
             			$(".image_delete").hide();
             			$(".uploadBtn").hide();
                	}else{
                		//编辑
                	}
                   
        			
                	setFormData("form_activity_detail", result);
                	mini.getByName("file").setText(result.activity_file);
                	editor.html(result.activity_content);
                	
                }
            }
        }
    });
	if(state==0){
		$(".saveBut").hide();
		$(".cancelBut").hide();
		$(".closeBut").show();
		$(".searchBtn").hide();
//		$(".sc").hide();
//		$(".shangchuan").hide();
//		$(".shanchu").hide();
//		$(".xuanze").hide();
		$("#fileupload").hide();
		$("#filedel").hide();
		mini.getByName("activity_name").setEnabled(false);
		mini.getByName("activity_start_time").setEnabled(false);
		mini.getByName("activity_end_time").setEnabled(false);
		mini.getByName("activity_entry_end_time").setEnabled(false);
		document.getElementById("file").disabled=true;
		mini.getByName("file").setEnabled(false);
		mini.getByName("activity_link_man").setEnabled(false);
		mini.getByName("activity_link_phone").setEnabled(false);
		mini.getByName("activity_address").setEnabled(false);
		mini.getByName("activity_navigation").setEnabled(false);
		mini.getByName("is_sign").setEnabled(false);
//		mini.getByName("receipt_time").setEnabled(false);
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
    		
    		var activity_id = mini.getByName("activity_id").getValue();
    		var state = mini.getByName("state").getValue();
    		if(state==0){
    			$(".sel_div").hide();
    		}
    		
    		$.ajax({
    			type : "POST",
    			url : "/web/v1/activity/getActivityPersonDetailData",
    			data : {
    				activity_id	: activity_id
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
            	mini.getByName("wjmc").setValue(obj.path);
                alert("上传成功！");
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
        		mini.getByName("wjlength").setValue(wjlengt+1);
    		}else{
    			alert("已达到上限！");
    		}
    		
    	})
    	$("#form1").on('click','.form1_del',function(){
    		$(this).parent().remove();
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
//    	var rbl = mini.get("position_type");
//    rbl.on("valuechanged", function (e) {
//    	var position= mini.getByName("position_type").getValue();
//    	$.ajax({
//			type : "POST",
//			url : "/web/v1/meetingManager/getAttendeesResultList",
//			data : {
//				position	: position
//			},
//			cache: false,
//			async: false,
//			success : function(result) {
//	          	var resultMap = result.data;
//	          	
//	          	var passive_data = resultMap.arList;
//	          	datagrid.setData(mini.decode(passive_data));
//				$("#active_total").text(resultMap.len);
//			}
//		});
//    });
    	
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
				// 图片
		        $("#uploadImage").uploadFile({
		            size : 1,
		            type: "gif|jpg|jpeg|png|svg|GIF|JPG|PNG|SVG",       // 文件类型 |隔开 比如图片类型：
		            fileSize:'2MB',
		            success: function (data) {
		                var fileObj;
		                var fileName = '';
		                var filePath = '';
		                for (var i = 0; i < data.length; i++) {
		                    fileObj = data[i];
		                    fileName += fileObj.fileName+",";
		                    filePath += fileObj.path+",";
		                    if(fileObj.videoUrl != null && fileObj.videoUrl != ''){
		                        videoUrl += fileObj.videoUrl+","
		                    }

		                }
		                if(fileName != ""){
		                    fileName = fileName.substring(0, fileName.length-1);
		                    filePath = filePath.substring(0, filePath.length-1);
		                }
		                cpi_pathText.setValue(filePath);
		                cpi_pathText.setText(fileName);
		            }

		        });
			}
		});