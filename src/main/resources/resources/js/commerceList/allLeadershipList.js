mini.parse();
var datagrid = mini.get("datagrid1");
/**
 * 将评价类型转成文字
 * @param e
 * @returns
 */
/*function onLxModel(e) {
    if(e.record.SHZWLX == "1"){
        return "主席";
    }else if(e.record.SHZWLX == "2"){
        return "党组书记";
    }else if(e.record.SHZWLX == "3"){
        return "副主席";
    }else if(e.record.SHZWLX == "4"){
        return "秘书长";
    }else if(e.record.SHZWLX == "5"){
        return "副会长";
    }else if(e.record.SHZWLX == "6"){
        return "常委";
    }else if(e.record.SHZWLX == "7"){
        return "执委";
    }
}*/


/**
 * 拼接操作 删除 编辑
 * @param e
 * @returns
 */
function onActionRenderer(e) {
	var grid = e.sender;
	var record = e.record;
	var uid = record._uid;
	if (record.index != "*") {
		var row = grid.getRowByUID(uid);
		var id = row.id;
        return '<a href="javascript:seeRow(\''+ grid.id + '\',\'' + uid + '\')" style="text-decoration:none;" class="cus_btn_grid">详情</a>';
	}
	return "";
}


/**
 * 添加
 * @returns
 */
function add() {
	var shbmid = mini.getbyName("shbmid").getValue();
	var jie = datagrid.data.length+1;
    var text = TransformToChinese(jie);
    if(text.length >= 2){
        var t = text.substr(0,2);
        console.log(t);
        if(t == "一十"){
            text = text.substr(1,2);
        }
    }
    var px = datagrid.data.length;
    console.log(text);
	var data = {
        "SHBMID":shbmid,
		"JIE":"第"+text+"届",
        "PX":(px+1)
	}
    if (confirm("将会新增“第"+text+"届”领导班子，新增后届别不能删除，且上一届领导班子失效，建议在新一届会员代表大会选举完成后执行操作。确认继续新增吗？")) {
        Ajax({
            type : "post",
            url : "/web/v1/leadership/saveLeadership",
            data : {
                json : mini.encode(data)
            },
            success : function(msg) {
                if (errorCheck(msg)) {
                    //保存成功后调取
                    loadGridData();
                }
            }
        });
    }
/*	mini.open({
		//targetWindow: window,
		url: "/web/v1/leadership/toLeadershipAddPage?SHBMID="+shbmid,
		title: "新增评价",
		width: 600,
		height: 250,
		allowResize:false,
		ondestroy: function (action) {
			datagrid.reload();
		}
	});*/

}

function loadGridData(){
    var shbmid = mini.getbyName("shbmid").getValue();
    datagrid.load({"SHBMID":shbmid});
}


$(function(){
	loadGridData();
});



//查看功能
function seeRow(gridId,row_uid){
	var grid = mini.get(gridId);
	var row = grid.getRowByUID(row_uid);
    var shbmid = mini.getbyName("shbmid").getValue();
    var jie = row.jie;
    var px = row.px;
    window.location.href = "/web/v1/leadership/toLeadershipDetailPage?SHBMID="+ shbmid +"&&JIE="+jie+"&&PX="+px;
/*    mini.open({
        //targetWindow: window,
        url: "/web/v1/leadership/toLeadershipDetailPage?SHBMID="+shbmid,
        title: "编辑评价",
        width: 800,
        height: 700,
        allowResize:false,
        ondestroy: function (action) {
            datagrid.reload();
        }
    });*/
}


function TransformToChinese(num){
    num = Math.floor(num);
    var unitPos = 0;
    var strIns = '', chnStr = '';
    var needZero = false;

    if(num === 0){
        return chnNumChar[0];
    }
    while(num > 0){
        var section = num % 10000;
        if(needZero){
            chnStr = chnNumChar[0] + chnStr;
        }
        strIns = sectionToChinese(section);
        strIns += (section !== 0) ? chnUnitSection[unitPos] : chnUnitSection[0];
        chnStr = strIns + chnStr;
        needZero = (section < 1000) && (section > 0);
        num = Math.floor(num / 10000);
        unitPos++;
    }
    return chnStr;
}

//定义在每个小节的内部进行转化的方法，其他部分则与小节内部转化方法相同
function sectionToChinese(section){
    var str = '', chnstr = '',zero= false,count=0;   //zero为是否进行补零， 第一次进行取余由于为个位数，默认不补零
    while(section>0){
        var v = section % 10;  //对数字取余10，得到的数即为个位数
        if(v ==0){                    //如果数字为零，则对字符串进行补零
            if(zero){
                zero = false;        //如果遇到连续多次取余都是0，那么只需补一个零即可
                chnstr = chnNumChar[v] + chnstr;
            }
        }else{
            zero = true;           //第一次取余之后，如果再次取余为零，则需要补零
            str = chnNumChar[v];
            str += chnUnitChar[count];
            chnstr = str + chnstr;
        }
        count++;
        section = Math.floor(section/10);
    }
    return chnstr;
}

var chnNumChar = ["零","一","二","三","四","五","六","七","八","九"];
var chnUnitSection = ["","万","亿","万亿","亿亿"];
var chnUnitChar = ["","十","百","千"];

