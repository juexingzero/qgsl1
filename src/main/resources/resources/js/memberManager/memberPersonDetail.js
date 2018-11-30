mini.parse();
var operator_type = mini.getByName("operator_type").getValue();
var member_type = mini.getByName("member_type").getValue();
var approve_status_old = mini.getByName("approve_status").getValue();

$(function(){
	//根据操作类型来决定是否显示审批按钮
	if(operator_type == "show"){
		$("#div_jbxx").css("height", "630px");
		$("#div_jbxx").find("input").attr("disabled",true);
	}
	else if(operator_type == "approve"){
		//待审批
		if(approve_status_old == "null" || approve_status_old == "HYSP-01"){
			$("#div_jbxx").css("height", "590px");
			$("#div_approve_btn").css("display","block");
		}
		//审批通过
		else if(approve_status_old == "HYSP-02"){
			$("#div_jbxx").css("height", "630px");
		}
		//审批拒绝
		else if(approve_status_old == "HYSP-03"){
			$("#div_jbxx").css("height", "540px");
			$("#div_approve_content").css("display","block");
			var approve_fail_info = mini.getByName("approve_fail_info").getValue();
			var approve_fail_reason = mini.getByName("approve_fail_reason").getValue();
			loadApproveFailInfo(approve_fail_info, approve_fail_reason);
		}
		$("#div_jbxx").find("input").attr("disabled",true);
	}
	
	//加载商会职务、证件材料、主要荣誉
	var app_user_id = mini.getByName("app_user_id").getValue();
	var app_user_name = mini.getByName("app_user_name").getValue();
	var requestUrl = mini.getByName("requestUrl").getValue();
	loadOther(app_user_id, app_user_name, member_type, requestUrl);
});

//加载审批失败资料及原因
function loadApproveFailInfo(info, reason){
	var info_text = "资料：";
    if(info.indexOf(",") != -1){
    	var infoArr = info.split(",");
    	for(var i=0; i<infoArr.length; i++){
    		info_text += covertFailInfolb(infoArr[i]) + "、";
    	}
    	info_text = info_text.substring(0, info_text.length-1);
	}else{
		info_text = covertFailInfolb(infolb);
	}
	$("#span_approve_fail_info").html(info_text);
	$("#span_approve_fail_reason").text("原因："+reason);
}

//加载商会职务、证件材料、主要荣誉
function loadOther(app_user_id, app_user_name, member_type, requestUrl){
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/web/v1/memberManager/getCommerceForMemberList",
        data: {
        	app_user_id		: app_user_id,
        	app_user_name	: app_user_name,
        	member_type		: member_type
        },
        success: function (result) {
        	//商会职务
            if(result.shzw){
            	var shzwList = result.shzw.data;
            	if(shzwList.length > 0){
            		var shzwTd = "";
            		for(var i=0; i<shzwList.length; i++){
            			var shzw = shzwList[i];
            			var shzwlb_text = covertShzwlb(shzw.position);
            			shzwTd += "<tr>";
            			shzwTd += "<td>"+(i+1)+"</td>";
            			shzwTd += "<td>"+shzw.joinObjName+"</td>";
            			shzwTd += "<td>"+shzwlb_text+"</td>";
            			shzwTd += "</tr>";
            		}
            		$("#div_shzw").find("tbody").append(shzwTd);
            	}
            }
        	//证件材料
            if(result.zjzl){
            	var zjzlList = result.zjzl.data;
            	if(zjzlList.length > 0){
            		for(var i=0; i<zjzlList.length; i++){
            			var zj = zjzlList[i];
            			var fileUrl = zj.fileUrl;
            			if(zj.ryzjlx == "ZJZPLX-01"){
            				$("#ZJZPLX-01").attr("src", requestUrl+fileUrl.substring(1,fileUrl.length));
            			}else if(zj.ryzjlx == "ZJZPLX-02"){
            				if(zj.pan == "1"){
            					$("#ZJZPLX-0201").attr("src", requestUrl+fileUrl.substring(1,fileUrl.length));
            				}else{
            					$("#ZJZPLX-0202").attr("src", requestUrl+fileUrl.substring(1,fileUrl.length));
            				}
            			}else if(zj.ryzjlx == "ZJZPLX-03"){
            				$("#ZJZPLX-03").attr("src", requestUrl+fileUrl.substring(1,fileUrl.length));
            			}
            		}
            	}
            }
    		//主要荣誉
            if(result.zyry){
            	var zyryList = result.zyry.data;
            	if(zyryList.length > 0){
            		var zyryTd = "";
            		for(var i=0; i<zyryList.length; i++){
            			var zyry = zyryList[i];
            			var zyrylb_text = covertZyrylb(zyry.zyrylb);
            			zyryTd += "<tr>";
            			zyryTd += "<td>"+(i+1)+"</td>";
            			zyryTd += "<td>"+zyrylb_text+"</td>";
            			zyryTd += "<td>"+'<a href="javascript:onShowZyryDetail(\'' + zyry.id + '\',\''+ zyry.zyrylb + '\',\''+ zyry.ryjb + '\',\''+ zyry.je + '\',\''+ zyry.hqsj + '\',\''+ zyry.ryzsbh + '\',\''+ zyry.bfdw + '\',\''+ zyry.hdyy + '\')" style="text-decoration:none;" class="cus_btn_grid">查看</a>'+"</td>";
            			zyryTd += "</tr>";
            		}
            		$("#div_zyry").find("tbody").append(zyryTd);
            	}
            }
        }
    });
}

//展示主要荣誉详情
function onShowZyryDetail(id, zyrylb, ryjb, je, hqsj, ryzsbh, bfdw, hdyy){
	mini.open({
		url: "/web/v1/memberManager/toMemberHonorDetailPage?id="+id + "&zyrylb="+zyrylb + "&ryjb="+ryjb + "&je="+je + "&hqsj="+hqsj + "&ryzsbh="+ryzsbh + "&bfdw="+bfdw + "&hdyy="+hdyy + "&pageType=person",
		title: "个人荣誉详情", 
		width: 600,
		height: 350,
		ondestroy: function (action) {}
	});
}


//面板切换
function onChangeTabs(e){
	var order = e.tab._id;
	if(order == "1"){
		$("#div_jbxx").css("display","block");
		$("#div_shzw").css("display","none");
		$("#div_zyry").css("display","none");
	}else if(order == "2"){
		$("#div_jbxx").css("display","none");
		$("#div_shzw").css("display","block");
		$("#div_zyry").css("display","none");
	}else if(order == "3"){
		$("#div_jbxx").css("display","none");
		$("#div_shzw").css("display","none");
		$("#div_zyry").css("display","block");
	}
}

//关闭
function onClose(){
	CloseWindow("cancel");
}

//审批
function onApprove(approve_status_new){
	var joinId = mini.getByName("joinId").getValue();
	var app_user_id = mini.getByName("app_user_id").getValue();
	var app_user_name = mini.getByName("app_user_name").getValue();
	var member_id = mini.getByName("member_id").getValue();
	if(approve_status_new == "HYSP-02"){
	    if(confirm("确定审批通过该入会信息吗？")){
	        $.ajax({
	            type: "POST",
	            dataType: "json",
	            url: "/web/v1/memberManager/approveMember",
	            data: {
	            	joinId			: joinId,
	            	app_user_id		: app_user_id,
	            	app_user_name	: app_user_name,
	            	member_id		: member_id,
	            	member_type		: member_type,
	            	approve_status	: approve_status_new
	            },
	            success: function (result) {
	                if(result){
	                	alert("操作成功！");
	                }
	            }
	        });
	    }
	}else{
		mini.open({
			url: "/web/v1/memberManager/toMemberApprovePage?joinId="+joinId + "&app_user_id="+app_user_id + "&app_user_name="+app_user_name + "&member_id="+member_id + "&member_type="+member_type + "&approve_fail_info=HYSPZL-01~基本信息,HYSPZL-02~商会职务,HYSPZL-03~主要荣誉,HYSPZL-08~证件资料",
			title: "审批拒绝", 
			width: 500,
			height: 350,
			ondestroy: function (action) {}
		});
	}
}
