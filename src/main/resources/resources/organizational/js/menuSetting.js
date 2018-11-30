mini.parse();
var tree1 = mini.get("tree1");
var menu_name = mini.get("menu_name");
var menu_url = mini.get("menu_url");
function loadDatagrid(){
    mini.parse();
    var tree1 = mini.get("tree1");
    var menu_name = mini.get("menu_name");
    var menu_url = mini.get("menu_url");
    var node = tree1.getSelectedNode();
    if(node){
        menu_name.load({menu_id:node.menu_id}, function(){
            if(menu_name.getData().length == 0){
                menu_url.clearRows();
            }
        });
    }
}

function onSelectionChanged(e) {
    var grid = e.sender;
    var record = grid.getSelected();
    if (record) {
        menu_url.load({func_id: record.func_id });
    }
}

//格式化创建日期
function onConvertCreateTime(e) {
    var value = new Date(e.value);
    if (value){
    	return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
    }
}

//beigin of 菜单------------------------------------------------------
//新增菜单
$("#btnNewDept").click(function(){
    var node = tree1.getSelectedNode();
    var p_menu_id = node ? node.menu_id : "";
    editOrganize(null, p_menu_id);
});

//双击菜单
function onTreeNodeDblClick(e){
    editOrganize(e.node.menu_id);
}

//编辑菜单
function editOrganize(menu_id, p_menu_id){
    if(menu_id == null){
        menu_id = "";
    }
    if(p_menu_id == null){
        p_menu_id = "";
    }
    mini.open({
        url: "/organ/addMenu?menu_id="+ menu_id +"&p_menu_id="+ p_menu_id,
        title: "菜单配置",
        width: 600,
        height: 330,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
            tree1.load();
        }
    });
}


//删除菜单
$("#btnDeleteDept").click(function(){
    var node = tree1.getSelectedNode();
    if(node && confirm("确实要删除该菜单吗？")){
        $.ajax({
            type: "POST",
            url: "/organ/deleteMenu",
            data: {
                menu_id:node.menu_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success : function(result) {
                if(result.code == 200){
                    tree1.reload();
                }else{
                    alert("提交有误，请重新尝试");
                }
            }
        });
    }
});
//end of 菜单------------------------------------------------------

//beigin of 功能------------------------------------------------------
//新增功能
$("#btnNewPosition").click(function(){
    var node = tree1.getSelectedNode();
    if(!node){
        alert("请选择菜单！");
        return;
    }
    editFunction(null, node.menu_id);
});

function editFunction(func_id, menu_id){
    if(func_id == null){
        func_id = "";
    }
    if(menu_id == null){
        menu_id = "";
    }
    mini.open({
        url: "/organ/addFunctionSett?func_id="+ func_id +"&menu_id="+ menu_id,
        title: "功能配置",
        width: 600,
        height: 360,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
            menu_name.reload();
        }
    });
}

//编辑功能
function onRowDblClickForFunc(e){
    editFunction(e.record.func_id);
}

//删除功能
$("#btnDeletePosition").click(function(){
    var row = menu_name.getSelected();
    if(!row){
        alert("请选择需要删除的功能！");
        return;
    }
    if(confirm("确实要删除该功能吗？")){
        $.ajax({
            type: "POST",
            url: "/organ/deleteFunc",
            data: {
                func_id:row.func_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function (result) {
                if(result.code == 200){
                    menu_name.reload();
                    menu_url.reload();
                }else{
                    alert("提交有误，请重新尝试");
                }
            }
        });
    }
});
//end of 功能------------------------------------------------------

//beigin of 功能URL------------------------------------------------------
//新增功能
$("#btnAddUser").click(function(){
    var node = menu_name.getSelected();
    if(!node){
        alert("请选择功能！");
        return;
    }
    editFunctionUrl(null, node.func_id);
});

function editFunctionUrl(url_id, func_id){
    if(url_id == null){
        url_id = "";
    }
    if(func_id == null){
        func_id = "";
    }
    mini.open({
        url: "/organ/addFunctionUrl?url_id="+ url_id +"&func_id="+ func_id,
        title: "功能URL",
        width: 600,
        height: 360,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
            menu_name.reload();
        }
    });
}

//编辑功能
function onRowDblClickForFuncUrl(e){
    editFunctionUrl(e.record.url_id);
}

//删除功能
$("#btnRemoveUser").click(function(){
    var row = menu_url.getSelected();
    if(!row){
        alert("请选择需要删除的功能URL！");
        return;
    }
    if(confirm("确实要删除该功能URL吗？")){
        $.ajax({
            type: "POST",
            url: "/organ/deleteFunc",
            data: {
                url_id:row.url_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function (result) {
                if(result.code == 200){
                    menu_name.reload();
                    menu_url.reload();
                }else{
                    alert("提交有误，请重新尝试");
                }
            }
        });
    }
});

//end of 功能URL------------------------------------------------------
$(function(){
    loadDatagrid();
});