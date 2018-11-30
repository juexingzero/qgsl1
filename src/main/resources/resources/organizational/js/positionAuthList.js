mini.parse();
var tree1 = mini.get("tree1");
var __positionSelected = false;
var treegrid1 = mini.get("treegrid1");

function onDrawNode(e){
    var node = e.node;
    if(e.node.position_type == 'P'){
        e.showCheckBox = true;
    }else{
        e.iconCls = "mini-tree-folder";
    }
}

function onBeforeNodeCheck(e){
    if(e.checked == false){
        tree1.uncheckAllNodes();
    }
}

function onNodeCheck(e){
    var selectedNodes = tree1.getCheckedNodes(false);
    if(selectedNodes.length == 1){
        __positionSelected = true;
        //加载岗位权限
        var position_id = selectedNodes[0].position_id;
        $.ajax({
            type : "POST",
            url : "/organ/selectPositionFnc",
            data : {
                position_id: position_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success : function(result) {
                if(result.code == 200){
                    var funcList = mini.decode(result.data);
                    if(funcList){
                        var menuList = treegrid1.getList();
                        for(var i = 0; i < menuList.length; i++){
                            var funs = menuList[i].functions;
                            if(funs){
                                for(var j = 0; j < funs.length; j++) {
                                    funs[j].checked = false;
                                    for(var k = 0; k < funcList.length; k++){
                                        if(funs[j].func_id == funcList[k].func_id){
                                            funs[j].checked = true;
                                        }
                                    }
                                }
                                treegrid1.updateRow(menuList[i]);
                            }
                        }
                    }
                }else{
                    treegrid1.reload();
                }
            }
        });
    }
}

function ondrawcell(e) {
    var tree = e.sender,
        record = e.record,
        column = e.column,
        field = e.field,
        id = record[tree.getIdField()],
        funs = record.functions;

    function createCheckboxs(funs) {
        if (!funs) return "";
        var html = "";

        // 全选、取消按钮
        html += '<input onclick="checkAllFunc(\'' + id + '\', true)" type="button" value="全选" style="font-size: 13px; border: 1px solid #74b2e2;background: #e4f1fb;color: #0070a3;"/>';
        html += '&nbsp<input onclick="checkAllFunc(\'' + id + '\', false)" type="button" value="取消" style="font-size: 13px; border: 1px solid #74b2e2;background: #e4f1fb;color: #0070a3;"/>';

        for (var i = 0, l = funs.length; i < l; i++) {
            var fn = funs[i];
            var clickFn = 'checkFunc(\'' + id + '\',\'' + fn.func_id + '\', this.checked)';
            var checked = fn.checked ? 'checked' : '';
            var disabled = __positionSelected ? "":"disabled";
            html += '<label class="function-item" style="display:inline-block"><label for="'+ fn.func_id +'" class="checkText"><input onclick="' + clickFn + '" ' + checked + ' type="checkbox" id="'+ fn.func_id +'" name="' + fn.func_id +'" hideFocus '+ disabled +'/>' + fn.func_name + '</label></label>';
        }
        return html;
    }

    if (field == 'functions') {
        e.cellHtml = createCheckboxs(funs);
    }
}

function checkFunc(id, func_id, checked) {
    var record = treegrid1.getRecord(id);
    if(!record) return;
    var funs = record.functions;
    if (!funs) return;
    function getFunc(func_id) {
        for (var i = 0, l = funs.length; i < l; i++) {
            var o = funs[i];
            if (o.func_id == func_id) return o;
        }
    }
    var obj = getFunc(func_id);
    if (!obj) return;
    obj.checked = checked;

    //设置事件关联
    for(var i = 0, l = funs.length; i < l; i++){
        if(checked){
            if(funs[i].func_level < obj.func_level){
                funs[i].checked = checked;
            }
        }else{
            if(funs[i].func_level > obj.func_level){
                funs[i].checked = checked;
            }
        }
    }
    treegrid1.updateRow(record);
}

function checkAllFunc(id, checked) {
    var record = treegrid1.getRecord(id);
    if (!record) return;
    var funs = record.functions;
    if (!funs) return;
    for (var i = 0, l = funs.length; i < l; i++) {
        var o = funs[i];
        o.checked = checked;
    }
    treegrid1.updateRow(record);
}
/**
 * 引入角色权限
 */
$("#importRole").click(function(){
    var selectedPositions = tree1.getCheckedNodes(false);
    if(selectedPositions.length == 0){
        alert("请选择需要授权的岗位！");
        return;
    }
    if (!window.confirm("如果引入角色将覆盖原有权限。您确定吗？")) {
        return ;
    }
    var para = [];
    for(var i = 0; i < selectedPositions.length; i++){
        para.push({name: "position_id", value: selectedPositions[i].position_id});
    }
    mini.open({
        url: "roleAuth/common_SelectRolePage",
        title: "选择角色",
        width: 460,
        height: 400,
        ondestroy: function (action) {
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = iframe.contentWindow.GetData();
                data = mini.clone(data);    //必须
                if (data) {
                    if(data.role_id!=null){
                        para.push({
                            name: "role_id",
                            value: data.role_id
                        });
                        Ajax({
                            type : "post",
                            url : "roleAuth/importRole",
                            data : para,
                            success : function(msg) {
                                if (errorCheck(msg)) {
                                    onNodeCheck();
                                }
                            }
                        });
                    }
                }
            }
        }
    });

});
/**
 * 引入角色权限 增量引入
 */
$("#importRole1").click(function(){
    var selectedPositions = tree1.getCheckedNodes(false);
    if(selectedPositions.length == 0){
        alert("请选择需要授权的岗位！");
        return;
    }
    if (!window.confirm("您确定添加新角色到岗位吗？")) {
        return ;
    }
    var para = [];
    for(var i = 0; i < selectedPositions.length; i++){
        para.push({name: "position_id", value: selectedPositions[i].position_id});
    }
    para.push({
        name: "is_cover",
        value: "1"
    });
    mini.open({
        url: "roleAuth/common_SelectRolePage",
        title: "选择角色",
        width: 460,
        height: 400,
        ondestroy: function (action) {
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = iframe.contentWindow.GetData();
                data = mini.clone(data);    //必须
                if (data) {
                    if(data.role_id!=null){
                        para.push({
                            name: "role_id",
                            value: data.role_id
                        });
                        Ajax({
                            type : "post",
                            url : "roleAuth/importRole",
                            data : para,
                            success : function(msg) {
                                if (errorCheck(msg)) {
                                    onNodeCheck();
                                }
                            }
                        });
                    }
                }
            }
        }
    });

});
$("#btnSave").click(function(){
    var selectedPositions = tree1.getCheckedNodes(false);
    if(selectedPositions.length == 0){
        alert("请选择需要授权的岗位！");
        return;
    }
    var data = [];
    var position_id = selectedPositions[0].position_id;

    var menuList = treegrid1.getList();
    for(var i = 0; i < menuList.length; i++){
        var funs = menuList[i].functions;
        if(funs){
            for(var j = 0; j < funs.length; j++){
                if(funs[j].checked){
                    data.push({"position_id": position_id, "func_id": funs[j].func_id});
                }
            }
        }
    }
    if(data.length == 0){
        data.push({"position_id": position_id, "func_id": null});
    }

    $.ajax({
        type : "POST",
        url : "/organ/insertPositionFnc",
        data : {
            json : JSON.stringify(data)
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success : function(result) {
            if (result.code == 200) {
            	alert("保存成功！");
            }
        }
    });
});