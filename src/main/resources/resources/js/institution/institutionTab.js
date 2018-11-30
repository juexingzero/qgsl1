mini.parse();
var user_type = getCookie("jbqgsl_user_type");

$(function(){
    console.log(user_type);
    if(user_type == "2"){
        $("#btnModInpot").hide();
        $("#btnModDown").hide();
        $("#tabs1").find('.mini-tabs-headers .mini-tab').eq(0).hide();
        $("#tabs1").find('.mini-tabs-headers .mini-tab').eq(1).hide();
        var tabs = mini.get("tabs1");
        tabs.activeTab (tabs.tabs[2])
    }
});

//新增页面
function btnNew(){
    var tabs = mini.get("tabs1");
    console.log(tabs.activeIndex);
    if(tabs.activeIndex == 2){
        //进入选择企业新增
        mini.open({
            url: "/web/v1/institution/toInstitutionEnterpriseSavePage",
            title: "新增企业机构数据",
            width: 750,
            height: 650,
            ondestroy: function (action) {
                window.location.reload();
            }
        });
    }else{
        mini.open({
            url: "/web/v1/institution/toInstitutionSavePage",
            title: "新增机构数据",
            width: 750,
            height: 650,
            ondestroy: function (action) {
                window.location.reload();
            }
        });
    }
}

//下载
function btnModDown(){
	var url = "/web/v1/institution/templateDown";  //mybatis+spring  
    url = encodeURI(url);
    location.href = url;  
}

//打开文件
function btnModInpot(){
	document.getElementById("btn_file").click(); 
}


//自动上传
function uploadFile(){
	// $("form").submit(); 
	$("form").ajaxSubmit(function(data){    
		if(data.message=='ok'){  
            alert(data.data);     
        }else{
        	alert(data.data);
        }  
		window.location.reload();
    }); 
}

//切换时刷新
function load(e){
	var tabs = mini.get("tabs1");
	var tab = tabs.getActiveTab();
	var url = tab.url;
	tabs.loadTab(url, tab);
}
