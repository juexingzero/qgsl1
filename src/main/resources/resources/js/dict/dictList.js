mini.parse();
var dict = mini.get("dict");
$(function(){
    dict.load();
});

//beigin of 字典------------------------------------------------------
//新增字典
$("#btnNewDict").click(function(){
    editOrganize();
});

//双击字典修改
function onDictNodeDblClick(e){
    editOrganize(e.record.dict_id);
}

//编辑字典
function editOrganize(dict_id){
    if(dict_id == null){
        dict_id = "";
    }
    mini.open({
        url: "/dict/addDict?dict_id="+ dict_id +"",
        title: "字典",
        width: 600,
        height:280,
        onload: function () {
            var iframe = this.getIFrameEl();
        },
        ondestroy: function (action) {
            dict.load();
        }
    });
}


//删除字典
$("#btnDeleteDict").click(function(){
    var node = dict.getSelected();
    if(node.isSystem == 1){
        alert("系统字典不能删除");
        return;
    }
    if(node && confirm("确实要删除该字典吗？")){
        $.ajax({
            type : "post",
            url : "/dict/deleteDictionary",
            data : {
                dict_id: node.dict_id
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success : function(result) {
                if (result.code == 200) {
                    dict.load();
                }
            }
        });
    }
});
//end of 字典------------------------------------------------------

function onMarriedRenderer(e) {
    if (e.value == 1) return "是";
    else return "否";
}