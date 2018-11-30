mini.parse();
var datagrid = mini.get("datagrid_memberList");
var menu_type = mini.getByName("menu_type").getValue();

$(function(){
	loadCommerceList();
	
	//根据菜单类型来决定列表列的内容及查询条件
	if(menu_type == "1"){
		$("#forApprove_text").hide();
		$("#forApprove_value").hide();
		datagrid.hideColumn(8);
		datagrid.hideColumn(9);
	}
	else if(menu_type == "2"){
		$("#forApprove_text").show();
		$("#forApprove_value").show();
		datagrid.showColumn(8);
		datagrid.showColumn(9);
	}
});

//绑定事件
$(".searchBtn").click(function(){
    loadGridData();
}).click();
$(".cleanBtn").click(function(){
    mini.getByName("commerce_name").setValue("0");
    mini.getByName("member_type").setValue("0");
    mini.getByName("approve_status").setValue("0");
  	mini.getByName("member_name").setValue("");
  	mini.getByName("linkman_name").setValue("");
  	mini.getByName("linkman_phone").setValue("");
    loadGridData();
});

//加载商会名称下拉列表
function loadCommerceList(){
	$.ajax({
		type : "POST",
        dataType: "json",
		url : "/web/v1/memberManager/getCommerceList",
		data : {},
		success : function(result) {
            var commerceList = result;
        	mini.getByName("commerce_name").setData(commerceList);
        	loadGridData();
		}
	});
}

//加载列表数据
function loadGridData(){
  var form = new mini.Form("#searchForm");
  form.validate();
  if (form.isValid() == false){
      return;
  }
  var data = form.getData(true);
  data.menu_type = menu_type;
  var json = mini.encode(data);
  datagrid.load({json:json});
}

//转换会员类型
function covertMemberType(e) {
	if (e.record.member_type == "HYLX-01") {
		return "个人会员";
	} else if (e.record.member_type == "HYLX-02") {
		return "团体会员";
	}  else if (e.record.member_type == "HYLX-03") {
		return "企业会员";
	} 
}

//转换审核状态
function covertApproveStatus(e) {
    if (e.record.approve_status == "HYSP-01") {
        return "未审核";
    } else if (e.record.approve_status == "HYSP-02") {
        return "审核通过";
    } else if (e.record.approve_status == "HYSP-03") {
        return "审核拒绝";
    }
}

//操作列
function onActionRenderer(e) {
	var grid = e.sender;
	var record = e.record;
	var uid = record._uid;
	if (record.index != "*") {
		var buttonHtml = "";
		var showBut = '<a href="javascript:onOperatorRow(\'' + grid.id + '\',\''+ uid + '\',\''+ "show" + '\')" style="text-decoration:none;" class="cus_btn_grid">查看</a>';
		var approveBut = '<a href="javascript:onOperatorRow(\'' + grid.id + '\',\''+ uid + '\',\''+ "approve" + '\')" style="text-decoration:none;" class="cus_btn_grid">审核</a>';
        var deleteBut = '<a href="javascript:onDeleteRow(\'' + grid.id + '\',\''+ uid + '\')" style="text-decoration:none;" class="cus_btn_grid_del">作废</a>';
        if(menu_type == "1"){
            buttonHtml = showBut + deleteBut;
        }else if(menu_type == "2"){
            buttonHtml = approveBut;
        }
		return buttonHtml;
	}
	return "";
}

//查看、审批
function onOperatorRow(gridId, row_uid, operator_type){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
	var title,titleText;
	if(operator_type == "show"){
		titleText = "查看";
	}else if(operator_type == "approve"){
		titleText = "审批";
	}
	if(row.member_type == "HYLX-01"){
		title = "个人会员"+titleText;
	}else if(row.member_type == "HYLX-02"){
		title = "团体会员"+titleText;
	}else if(row.member_type == "HYLX-03"){
		title = "企业会员"+titleText;
	}
	
	mini.open({
		url: "/web/v1/memberManager/toMemberDetailPage?joinId="+row.joinId + "&app_user_id="+ row.app_user_id + "&app_user_name="+ row.app_user_name + "&member_id="+ row.member_id + "&member_type="+row.member_type + 
			 "&approve_status="+row.approve_status + "&approve_fail_info="+row.approve_fail_info + "&approve_fail_reason="+row.approve_fail_reason + "&operator_type="+operator_type,
		title: title,
		width: 850,
		height: 750,
		ondestroy: function (action) {
			loadGridData();
		}
	});
}

//作废
function onDeleteRow(gridId, row_uid){
    var grid = mini.get(gridId);
    var row = grid.getRowByUID(row_uid);
	if (confirm("确定删除会员信息？")) {
	    Ajax({
	        type : "post",
	        dataType: "json",
	        url : "/web/v1/memberManager/updateMember",
	        data : {
	            "joinId": row.joinId,
	            "state"	: "HYSP-04"
	        },
	        success : function(msg) {
	            if (errorCheck(msg)) {
	                if(msg.data != null){
	                    alert(msg.data);
	                }else{
	        			loadGridData();
	                }
	            }
	        }
	    });
	}
}
