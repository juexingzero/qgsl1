mini.parse();
var datagrid = mini.get("editTable");

$(function (){
	init();
})


function init(){
	var detail_id = mini.getByName("detail_id").getValue();
	var standard_id = mini.getByName("standard_id").getValue();
	Ajax({
        type : "post",
        url : "/web/v1/standradLibarary/loadStandardsDetail",
        data : {"standard_id":standard_id,
        		"detail_id":detail_id
        		},
        success : function(msg) {
            if (errorCheck(msg)) {
                var result = mini.decode(msg);
                if(result != null) {
                	setFormData("editTable", result);
                }
            }
        }
    });
}

//关闭窗口
function CloseWindow(action) {
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
}

function save(closeWin){
	var form = new mini.Form("#editTable");
	form.validate();
	if (form.isValid() == false) return;
	var data = form.getData();
	Ajax({
		type : "post",
		url : "/web/v1/standradLibarary/update",
		data : {
        	json : mini.encode(data),
        },
		success : function(msg) {
			if (errorCheck(msg)) {
				var result = mini.decode(msg);
				if(result.data != null && result.data !=""){
					alert(result.data);
					return;
				}
				
				if(closeWin)CloseWindow("ok");
			}
		}
	});
}
