mini.parse();
var treegrid = mini.get("treegrid");
$(function(){
    //treegrid.load();
});

//转换栏目模型
/*function onConvertMenuModel(e) {
    if(e.record.menuModel == "articleType"){
        return "文章类型";
    }else if(e.record.menuModel == "listType"){
        return "列表类型";
    }
}*/

function onActionRenderer(e){
    var record = e.record;
    var row = e.row;
    var menuId = record.id;
    if(row.lyzj==null){//顶级父节点
        var funs = record.functions;
        var html = '';
        if(menuId != '1'){
            html = createChild(html,menuId);
            html = rootEdit(html,menuId);
            html = rootDel(html,menuId);
        }
    }else {
        var funs = record.functions;
        var html = '';
        if(menuId != '2'){
            html = leadsAdd(html,menuId);
            html = createEdit(html,menuId);
            html = delChild(html,menuId);
        }else{
            html = allLeadsAdd(html,menuId);
        }
    }
    return html;
}


function rootEdit(html,menuId){
    html = html+ '&nbsp;&nbsp;<a href="javascript:void(0)"  class="cus_btn_grid" onclick="editRoot(\''+menuId+'\')" style="text-decoration:none;">编辑</a>'
    return html;
}
function createChild(html,menuId){
    html = html+ '&nbsp;&nbsp;<a href="javascript:void(0)" onclick="addChild(\''+menuId+'\')" class="cus_btn_grid" style="text-decoration:none;">添加商会</a>'
    return html;
}

function rootDel(html,menuId){
    html = html+ '&nbsp;&nbsp;<a href="javascript:void(0)" class="cus_btn_grid_del" onclick="delRoot(\''+menuId+'\')" style="text-decoration:none;">删除</a>'
    return html;
}

function leadsAdd(html,menuId){
    html = html+ '&nbsp;&nbsp;<a href="javascript:void(0)" class="cus_btn_grid" onclick="lead(\''+menuId+'\')" style="text-decoration:none;">领导班子</a>'
    return html;
}

function allLeadsAdd(html,menuId){
    html = html+ '&nbsp;&nbsp;<a href="javascript:void(0)" class="cus_btn_grid" onclick="allLeads(\''+menuId+'\')" style="text-decoration:none;">领导班子</a>'
    return html;
}

function createEdit(html,menuId){
    html = html+ '&nbsp;&nbsp;<a href="javascript:void(0)"  class="cus_btn_grid" onclick="edit(\''+menuId+'\')" style="text-decoration:none;">编辑</a>'
    return html;
}

function delChild(html,menuId){
    html = html+ '&nbsp;&nbsp;<a href="javascript:void(0)" class="cus_btn_grid_del" onclick="del(\''+menuId+'\')" style="text-decoration:none;">删除</a>'
    return html;
}


//新增商会类别
function addRoot(){
    mini.open({
        url: "/web/v1/commerce/toCategoryAddPage",
        title: "新增商会类别",
        width: 450,
        height: 300,
        ondestroy: function (action) {
            treegrid.load();
        }
    });
}


//编辑商会类别
function editRoot(menuId){
    console.log(menuId);
     mini.open({
        url: "/web/v1/commerce/toCategoryEditPage?ID="+menuId,
        title: "编辑商会类别",
        width: 450,
        height: 300,
        ondestroy: function (action) {
            treegrid.load();
        }
    });
}

//添加商会
function addChild(menuId) {
    mini.open({
        url: "/web/v1/commerce/toCommerceAddPage?LYID=" + menuId,
        title: "添加商会",
        width: 750,
        height: 650,
        ondestroy: function (action) {
            treegrid.load();
        }
    });
}

//修改商会
function edit(menuId){
    mini.open({
        url: "/web/v1/commerce/toCommerceEditPage?ID=" + menuId,
        title: "编辑商会",
        width: 750,
        height: 600,
        ondestroy: function (action) {
            treegrid.load();
        }
    });
}

//删除商会类别
function delRoot(menuId) {
    if (confirm("是否确定删除！")) {
        Ajax({
            type : "post",
            url : "/web/v1/commerce/deleteCommerceCategory",
            data : {
                "ID":menuId
            },
            success : function(msg) {
                if (errorCheck(msg)) {
                    //保存成功后调取
                    if(msg.data != null){
                        alert(msg.data);
                    }else{
                        //alert("删除成功！");
                    }
                    treegrid.load();
                }
            }
        });
    }
}


//删除商会
function del(menuId) {
    if (confirm("是否确定删除！")) {
        Ajax({
            type : "post",
            url : "/web/v1/commerce/deleteCommerce",
            data : {
                "ID":menuId
            },
            success : function(msg) {
                if (errorCheck(msg)) {
                    //保存成功后调取
                    if(msg.data != null){
                        alert(msg.data);
                    }else{
                        //alert("删除成功!");
                    }
                    treegrid.load();
                }
            }
        });
    }
}


//领导班子
function lead(menuId){
    //console.log("11111");
    mini.open({
        url: "/web/v1/leadership/toLeadershipPage?SHBMID="+ menuId,
        title: "领导班子",
        width: 500,
        height: 300,
        ondestroy: function (action) {
            treegrid.load();
        }
    });
}


//总商会领导班子
function allLeads(menuId){
    //console.log("22222");
    window.location.href = "/web/v1/leadership/toAllLeadershipPage?SHBMID="+ menuId;
}

