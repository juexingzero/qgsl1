mini.parse();
var tree1 = mini.get("tree1");
var datagrid_position = mini.get("datagrid_position");
var datagrid_user = mini.get("datagrid_user");
function loadDatagrid(){
    mini.parse();
    var tree1 = mini.get("tree1");
    var datagrid_position = mini.get("datagrid_position");
    var datagrid_user = mini.get("datagrid_user");
    var node = tree1.getSelectedNode();
    if(node){
        datagrid_position.load({dept_id:node.dept_id}, function(){
            if(datagrid_position.getData().length == 0){
                datagrid_user.clearRows();
            }
        });
    }
}

function onSelectionChanged(e) {
    var grid = e.sender;
    var record = grid.getSelected();
    if (record) {
        datagrid_user.load({position_id: record.position_id });
    }
}

//beigin of 组织架构------------------------------------------------------
//新增组织架构
$("#btnNewDept").click(function(){
    var node = tree1.getSelectedNode();
     if (node && tree1.getLevel (node)==2) {
		alert("部门最多支持三级");
		return;
     } 
    var p_dept_id = node ? node.dept_id : "";
    editOrganize(null, p_dept_id);
});

//双击组织架构树
function onTreeNodeDblClick(e){
    editOrganize(e.node.dept_id);
}

//编辑组织架构
function editOrganize(dept_id, p_dept_id){
    if(dept_id == null){
        dept_id = "";
    }
    if(p_dept_id == null){
        p_dept_id = "";
    }
    mini.open({
        url: "/organ/addDepartmentPage?dept_id="+ dept_id +"&p_dept_id="+ p_dept_id,
        title: "组织架构",
        width: 400,
        height: 550,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
            tree1.load();
        }
    });
}


//删除组织架构
$("#btnDeleteDept").click(function(){
    var node = tree1.getSelectedNode();
    if(node && confirm("确实要删除该组织吗？")){
        Ajax({
            type : "post",
            url : "/organ/deleteDepartment",
            data : {dept_id:node.dept_id},
            success : function(msg) {
                if (errorCheck(msg)) {
                    tree1.load();
                }
            }
        });
    }
});
//end of 组织架构------------------------------------------------------

//beigin of 岗位------------------------------------------------------
//新增岗位
$("#btnNewPosition").click(function(){
    var node = tree1.getSelectedNode();
    if(!node){
        alert("请选择组织架构！");
        return;
    }
    editPosition(null, node.dept_id);
});

function editPosition(position_id, dept_id){
    if(position_id == null){
        position_id = "";
    }
    if(dept_id == null){
        dept_id = "";
    }
    mini.open({
        url: "/organ/addPost?position_id="+ position_id +"&dept_id="+ dept_id,
        title: "岗位信息",
        width: 350,
        height: 350,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
            datagrid_position.reload();
        }
    });
}

//编辑岗位
function onRowDblClickForPosition(e){
    editPosition(e.record.position_id);
}

//删除岗位
$("#btnDeletePosition").click(function(){
    var row = datagrid_position.getSelected();
    if(!row){
        alert("请选择需要删除的岗位！");
        return;
    }
    if(confirm("确实要删除该岗位吗？")){
        $.ajax({
            type: "POST",
            url: "/organ/deletePosition",
            data: {
                position_id:row.position_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function (result) {
                if(result.code == 200){
                    datagrid_position.reload();
                    datagrid_user.reload();
                }else{
                    alert("提交有误，请重新尝试");
                }
            }
        });
    }
});
//end of 岗位------------------------------------------------------

//beigin of 用户------------------------------------------------------
//添加成员
$("#btnAddUser").click(function(){
    var position = datagrid_position.getSelected();
    if(!position){
        alert("请选需要添加成员的岗位！");
        return;
    }
    mini.open({
        url: "/organ/userSelect",
        title: "选择列表",
        width: 650,
        height: 500,
        ondestroy: function (action) {
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var rtData = iframe.contentWindow.GetData();
                rtData = mini.clone(rtData);    //必须
                if (rtData) {
                    var data = [{name: "position_id", value: position.position_id}];
                    data.push({name: "user_id", value: rtData.user_id});
                    $.ajax({
                        type: "POST",
                        url: "/organ/addPositionToUser",
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        dataType: "json",
                        success: function (result) {
                            if(result.code == 200){
                                datagrid_user.reload();
                            }else if(result.code == 201){
                                alert("该岗位已经存在这个人员了");
                            }else{
                                alert("提交有误，请重新尝试");
                            }
                        }
                    });
                }
            }
        }
    });
});

//移除成员
$("#btnRemoveUser").click(function(){
    var position = datagrid_position.getSelected();
    var rows = datagrid_user.getSelecteds();
    if(rows.length == 0){
        alert("请选择需要移除的成员！");
        return;
    }
    var data = [{name: "position_id", value: position.position_id}];
    for(var i = 0; i < rows.length; i++){
        var row = rows[i];
        if(row.is_primary == "1"){
            alert("默认岗位用户不允许移除！");
            return;
        }
        data.push({name: "user_id", value: row.user_id});
    }
    $.ajax({
        type: "POST",
        url: "/organ/deletePositionToUser",
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                datagrid_user.reload();
            }else{
                alert("提交有误，请重新尝试");
            }
        }
    });
});

function onMarriedRenderer(e) {
    if (e.value == 1) return "是";
    else return "否";
}

//end of 用户------------------------------------------------------
$(function(){
    loadDatagrid();
});