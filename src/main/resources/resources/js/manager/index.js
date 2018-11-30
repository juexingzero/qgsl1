mini.parse();
var _titleThis = null;
var boolTitle = false;
$(function () {
    // 用户
	$.ajax({
		dataType : "json",
		type : "POST",
		url : "/user/userSelectData",
		data : {
			user_id : $.cookie("user_id")
		},
		cache : false,
		success : function(msg) {
			if (msg.code == 200) {
			    $("#userName").text(msg.data.user_name);
			    setCookie("jbqgsl_user_type", msg.data.user_type);
			    setCookie("jbqgsl_mobile_no", msg.data.mobile_no);
			}
		}
	});
	
    //menu
    var menu = new Menu("#mainMenu", {
        itemclick: function (item) {
            if (!item.children) {
                activeTab(item);
                $('#mainMenu').find('.menu-title').css('color', '');
                $('#mainMenu').find('[data-id="' + item.id + '"]').css('color', 'rgb(8, 134, 219)');
            }
        }
    });
    
    // 门户快捷入口跳转
    $('.kuaijie_table').on('click', '.rukou', function() {
    	var item = {};
    	item.id = $(this).data('id');
    	item.text = $(this).data('text');
    	item.url = $(this).data('url');
    	item.iconCls = $(this).data('iconCls');
    	activeTab(item);
        $('#mainMenu').find('.menu-title').css('color', '');
        $('#mainMenu').find('[data-id="' + item.id + '"]').css('color', 'rgb(8, 134, 219)').closest('.has-children').addClass('open');
    });
    
    // 初始化tabs
    var tabs = mini.get("mainTabs");
    /*
    var tabsJson = $.cookie("tabs");
    var tabse = eval(tabsJson);
    if(tabse != null){
        var tabs_active = $.cookie("tabs_active");
        for (var i = 1; i < tabse.length; i++) {
            var item = tabse[i];
            var tab = { name: item.name, title: item.title, url: item.url, iconCls: item.iconCls, showCloseButton: true,
                onload: function(){
                    $("iframe").contents().find("body").on("click",function(){
                        $('.rightClick_menu', window.parent.document).hide();
                    });
                }
            };
            tabs.addTab(tab);
            if(i == tabs_active){
                tabs.activeTab(tab);
            }
        }
        boolTitle = true;
    }
    */
    
    tabs.on("activechanged", function (e) {
        if(boolTitle){
            setTab();
            var activeTab = mini.get("mainTabs").getActiveTab();
            $('#mainMenu').find('.menu-title').css('color', '');
            $('#mainMenu').find('[data-id="' + activeTab.name + '"]').css('color', 'rgb(8, 134, 219)').closest('.has-children').addClass('open');
        }
    });
    tabs.on("closeclick", function (e) {
        if(boolTitle){
            setTab();
            var activeTab = mini.get("mainTabs").getActiveTab();
            $('#mainMenu').find('.menu-title').css('color', '');
            $('#mainMenu').find('[data-id="' + activeTab.name + '"]').css('color', 'rgb(8, 134, 219)').closest('.has-children').addClass('open');
        }
    });

    // 菜单右键
    $(document).on("contextmenu",".mini-tabs-header .mini-tab",function(e){
        if($(this).text() != "工作门户"){
            $(".rightClick_menu").show();
            $(".rightClick_menu").css({
                'top': e.pageY+'px',
                'left': e.pageX+'px'
            });
            _titleThis = $(this);
            $(".rightClick_menu ul").html(
            	'<li onclick="closeTitle()">关闭标签页</li>' +
                '<li onclick="closeOtherTitle()">关闭其他标签</li>' +
                '<li onclick="searchTitle()">刷新页面</li>'
            );
            return false;
        }
    });
    // 关闭右键菜单
    $(document.body).on("click",function(){
        $(".rightClick_menu").hide();
    });

    $(".sidebar").mCustomScrollbar({ autoHideScrollbar: true });

    new MenuTip(menu);

    // 菜单权限
    $.ajax({
        url: "/menu/menu",
        success: function (result) {
            menu.loadData(result.data);
        }
    });

    //toggle
    $("#toggle, .sidebar-toggle").click(function () {
        $('body').toggleClass('compact');
        mini.layout();
    });

    //dropdown
    $(".dropdown-toggle").click(function (event) {
        $(this).parent().addClass("open");
        return false;
    });

    $(document).click(function (event) {
        $(".dropdown").removeClass("open");
    });
});

//退出登录
function closeLogin(){
    $.ajax({
        type: "POST",
        url: "/login/closeLogin",
        data: {},
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (result) {
            if(result.code == 200){
                window.location.href = "/login/login";
            }else{
                alert("退出异常，请重新刷新页面在继续尝试");
            }
        }
    });
}

function activeTab(item) {
    var tabs = mini.get("mainTabs");
    var tab = tabs.getTab(item.id);
    if (!tab) {
        tab = { 
        		name: item.id, 
        		title: item.text, 
        		url: item.url, 
        		iconCls: item.iconCls, 
        		showCloseButton: true,
                onload: function() {
                    $("iframe").contents().find("body").on("click", function () {
                        $('.rightClick_menu', window.parent.document).hide();
                    });
                }
            };
        tab = tabs.addTab(tab);
    }
    tabs.activeTab(tab);
    setTab();
}

/**
 * 关闭当前标签页
 * */
function closeTitle(){
    var tabs = mini.get("mainTabs");
    var node = tabs.getTabs();
    for (var i = 0; i < node.length; i++) {
        if(tabs.getTab(i).title == _titleThis.text()){
            tabs.removeTab(tabs.getTab(i));
        }
    }
    setTab();
}

/**
 * 关闭其他标签
 * */
function closeOtherTitle(){
    var tabs = mini.get("mainTabs");
    var size = tabs.getTabs().length-1;
    for (var i = size; i > 0; i--) {
        if(tabs.getTab(i).title != _titleThis.text()){
            tabs.removeTab(tabs.getTab(i));
        }
    }
    setTab();
}

/***
 * 刷新当前标签
 */
function searchTitle() {
    var tabs = mini.get("mainTabs");
    var node = tabs.getTabs();
    for (var i = 0; i < node.length; i++) {
        if(tabs.getTab(i).title == _titleThis.text()){
            tabs.reloadTab(tabs.getTab(i));
        }
    }
}

/**
 * 保存当前tab操作到
 */
function setTab(){
    var tabs = mini.get("mainTabs");
    var tabse = tabs.getTabs();
    $.cookie("tabs", "");
    var index_active = 0;
    if($(".mini-tabs-header .mini-tab").length > 1){
        $(".mini-tabs-header .mini-tab").each(function(){
            for (var i = 0; i < tabse.length; i++) {
                if($(this).text() == tabse[i].title && $(this).attr("class").indexOf("mini-tab-active") > -1){
                    index_active = i;
                }
            }
        });
        $.cookie("tabs", mini.encode(tabse));
        $.cookie("tabs_active", index_active);
    }else{
        $.cookie("tabs", "");
    }
}