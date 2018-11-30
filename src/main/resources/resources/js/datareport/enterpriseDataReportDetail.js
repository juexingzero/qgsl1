mini.parse();
var editProForm = new mini.Form("#tabs");//项目基本信息表单
$(function(){
init();
});

function init() {
    var data_report_id = mini.getbyName("data_report_id").getValue();
    Ajax({
        type : "post",
        url : "/web/v1/datareport/queryEnterpriseDataReportDetail",
        data : {
            "data_report_id":data_report_id,
        },
        success : function(msg) {
            if (errorCheck(msg)) {
                console.log(msg);
                var result = mini.decode(msg);
                console.log(result);
                if(result != null) {
                    setFormData("tabs", result);
                }
            }
        }
    });
}

//返回
function returns(action){
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
}
