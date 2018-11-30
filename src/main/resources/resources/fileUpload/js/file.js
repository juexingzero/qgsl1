/**
 * 图片上传插件
 *
 * @version 1.0
 */
// 文件
var uploadFiles = [];
var indexList = [];
var _this = null;
var fileMaxSize = "";
var fileType = "";
var size = 0;
;(function($, document) {
    $.fn.uploadFile = function (parameter) {
        return this.each(function () {
            var defaults = {
                uploadUrl: "/upload/uploadFile",                         // 上传地址
                deleteUrl: "/upload/deleteFile",                         // 删除地址
                downUrl: "/upload/downloadImage",                        // 下载地址
                size: 10,                                                   // 上传数量
                type: "",                                                // 文件类型 |隔开 比如图片类型：gif|jpg|jpeg|png|svg|GIF|JPG|PNG|SVG
                fileSize: "",                                            // 上传文件大小限制
                imagePath: "",                                           // 图片显示 json数据类型 [{'name':'文件名称可传',path:'*图片地址必须传'},{'name':'文件名称可传',path:'*图片地址必须传'}]
                submutUrl: "",                                           // 保存
                success: function () {
                }
            };
            var opts = $.extend(defaults, parameter);
            _this = this;
            // 上传文件数量
            size = opts.size;
            // 文件类型
            fileType = opts.type;
            // 文件上传大小
            var fileStr = opts.fileSize;
            if(fileStr.indexOf("k") > -1 || fileStr.indexOf("K") > -1){
                fileMaxSize = parseInt(fileStr.replace("k", "").replace("K", "").replace("b", "").replace("B", ""))*1000;
            }else if(fileStr.indexOf("m") > -1 || fileStr.indexOf("M") > -1){
                fileMaxSize = parseInt(fileStr.replace("m", "").replace("M", "").replace("b", "").replace("B", ""))*1000*1000;
            }else if(fileStr.indexOf("g") > -1 || fileStr.indexOf("G") > -1){
                fileMaxSize = parseInt(fileStr.replace("g", "").replace("G", "").replace("b", "").replace("B", ""))*1000*1000*1000;
            }
            // 初始化js
            _this.init =function(){

                var html = '<div class="upload">' +
                    '   <div class="uploadBtn">' +
                    '        <div class="uploadFile" id="otherFile"><input type="file" id="uploadFile" multiple/><label class="font" for="uploadFile">选择文件</label></div>' +
                    '        <div id="IEFile" style="display: none;">选择文件</div>' +
                    '        <div class="uploadFile" id="uploadSubmit">上传</div>' +
                    '        <div class="uploadFile" id="save" style="display: none;" style="float: right; margin-right: 0px;">保存</div>' +
                    '    </div>' +
                    '    <div class="image" ondrop="drop(event)" ondragover="allowDrop(event)">' +
                    '        <div class="image_font">将文件拖拽到此处</div>' +
                    '        <div class="image_content">' +
                    '        </div>' +
                    '    </div>' +
                    '</div>';
                $(_this).html(html);

                // 图片加载
                if(opts.imagePath != ""){
                    $(_this).find(".image_font").hide();
                    var paths = eval(opts.imagePath);
                    if(paths != "" && paths.length > 0){
                        for (var i = 0; i < paths.length; i++) {
                            var name = paths[i].name;
                            if(name == null || name == ""){
                                name = paths[i].path.replace("/upload/","");
                            }
                            _this.imageLoad(name, paths[i].path, i);
                        }
                    }
                }

                // 选择图片上传按钮事件
                $(_this).find("#uploadFile").change(function(){
                    var files = $(this).prop('files');
                    for (var i = 0; i < files.length; i++) {
                        imagePreview(files[i]);
                    }
                });

                // 图片下载按钮事件
                $(_this).find(".image_down").click(function(){
                    var path = $(this).parent().find(".image_file img").attr("data-path");
                    _this.downImage(path);
                });

                if(IEVersion() == 8 || IEVersion() == 9){
                    $("#IEFile").show();
                    $("#otherFile").hide();
                    $(".image_font").text("");
                }else{
                    $("#IEFile").hide();
                    $("#otherFile").show();
                }

                var uploader = WebUploader.create({
                    swf: PATH + '/flash/Uploader.swf', // swf文件路径
                    server: opts.uploadUrl, // 文件接收服务端。
                    pick: '#IEFile', // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                    resize: false // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                });

                // 文件选择后执行
                uploader.on('fileQueued', function(file) {
                    uploader.makeThumb( file, function( error, ret ) {
                        if (error) {
                            imagePreviewSWF("", file.source.source);
                        } else {
                            imagePreviewSWF(ret, file.source.source);
                        }
                    });
                });

                // 文件上传成功后执行
                uploader.on('uploadSuccess', function (file, result) {
                    var datas = result.path;
                    var bools = true;
                    $(".image_div ").each(function(){
                        if($(this).find(".image_success").is(":hidden")){
                            $(this).find(".image_file img").attr("data-path",datas[0]);
                            $(this).find(".image_success").show();
                            return false;
                        }
                    });
                    $(".image_div ").each(function(){
                        if($(this).find(".image_success").is(":hidden")){
                            bools = false;
                            return false;
                        }
                    });
                    if(bools){
                        $(_this).find(".loading").hide();
                        //清空队列
                        uploader.reset();
                        uploadFiles = [];
                        indexList = [];
                        opts.success(_this.imageData());
                    }
                });

                // 提交按钮
                $(_this).find("#uploadSubmit").click(function(){
                    if(IEVersion() == 8 || IEVersion() == 9){
                        $(_this).find(".loading").show();
                        uploader.upload();
                    }else{
                        _this.upload();
                    }
                });

                // 保存按钮
                $(_this).find("#save").click(function(){
                    _this.save();
                });

                // 删除图片
                $(_this).on("click",".image_delete",function(){
                    if($(".loading").is(":hidden")){
                        if(!$(this).parent().find(".image_success").is(":hidden")){
                            if(confirm("是否确认要删除吗？")){
                                _this.deleteFile(this);
                            }
                        }else{
                            var len = parseInt($(this).parent().find("input").val());
                            uploadFiles.splice(len,1);
                            indexList.remove(len);
                            $(this).parent().remove();
                            $(_this).find("#uploadFile").val("");
                            if($(_this).find(".image_div").length == 0){
                                $(_this).find(".image_font").show();
                            }
                        }
                    }else{
                        alert("上传中，不能删除");
                    }
                });

                // 滚动条
                //$(_this).find(".image_content").perfectScrollbar();
            };

            // 上传
            _this.upload = function(){
                if(uploadFiles.length == 0){
                    alert("请上传图片再进行提交!");
                    return;
                }
                var formData = new FormData();
                for (var i = 0; i < uploadFiles.length; i++) {
                    formData.append("file",uploadFiles[i]);
                }
                for (var i = 0; i < indexList.length; i++) {
                    formData.append("index",indexList[i]);
                }
                $(".image_content .image_div").each(function(){
                    if($(this).find(".image_success").is(":hidden")){
                        $(this).find(".loading").show();
                    }
                });
                $.ajax({
                    type: "POST",
                    url: opts.uploadUrl,
                    data: formData,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (result) {
                        result = JSON.parse(result);
                        if(result.code == 200){
                            $(_this).find(".loading").hide();
                            var datas = result.path;
                            var i = 0;
                            $(".image_div ").each(function(){
                                if($(this).find(".image_success").is(":hidden")){
                                    $(this).find(".image_file img").attr("data-path",datas[i]);
                                    $(this).find(".image_success").show();
                                    $(this).find(".image_down").show();
                                    i++;
                                }
                            });
                            uploadFiles = [];
                            indexList = [];
                            opts.success(_this.imageData());

                            // 图片下载按钮事件
                            $(_this).find(".image_down").click(function(){
                                var path = $(this).parent().find(".image_file img").attr("data-path");
                                _this.downImage(path);
                            });
                        }else if(result.code == 201){
                            $(_this).find(".loading").hide();
                            alert(result.msg);
                        }else{
                            $(_this).find(".loading").hide();
                            alert("上传有误请重新尝试");
                        }
                    }
                });
            };

            // 删除
            _this.deleteFile = function(ids){
                $.ajax({
                    type: "POST",
                    url: opts.deleteUrl,
                    data: {
                        path : $(ids).parent().parent().find(".image_file img").attr("data-path")
                    },
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    dataType: "json",
                    success: function (result) {
                        if(result.code == 200){
                            $(ids).parent().remove();
                            $(_this).find("#uploadFile").val("");
                            if($(_this).find(".image_div").length == 0){
                                $(_this).find(".image_font").show();
                            }
                        }else{
                            $(_this).find(".loading").hide();
                            alert("删除有误，请重新尝试");
                        }
                    }
                });
            };

            // 保存
            _this.save = function(){
                var paths = [];
                $(".image_div").each(function(){
                    if(!$(this).find(".image_success").is(":hidden")){
                        var path = $(this).find(".image_file img").attr("data-path");
                        paths.push(path);
                    }
                });
                if(paths.length == 0){
                    alert("没有上传文件");
                    return;
                }
                $.ajax({
                    type: "POST",
                    url: opts.submutUrl,
                    data: {
                        path : JSON.stringify(paths)
                    },
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    dataType: "json",
                    success: function (result) {

                    }
                });
            };

            /**
             * 获取图片内容
             * @returns {Array}
             */
            _this.imageData = function(){
                var paths = [];
                $(".image_div").each(function(){
                    if(!$(this).find(".image_success").is(":hidden")){
                        var path = $(this).find(".image_file img").attr("data-path");
                        var fileName = $(this).find(".image_title").html();
                        paths.push({"fileName":fileName,"path":path});
                    }
                });
              return paths;
            };

            /**
             * 图片加载
             * @param fileName
             * @param filePath
             * @param index
             */
            _this.imageLoad = function(fileName, filePath, index){
                var image_class = "";
                var src = "";
                if(checkImgType(filePath)){
                    src = filePath;
                    image_class = "file";
                }else{
                    src = PATH+"image/ic_file.png";
                    image_class = "file";
                }
                var html = ' <div class="image_div">' +
                    '            <div class="image_file"><img class="'+ image_class +'" src="'+ src +'" data-path="'+ filePath +'" /><div class="loading"><div class="laoding-bg"></div><div class="loading-image"><img src="'+ PATH +'image/ic_loading.gif"></div></div></div>' +
                    '            <div class="image_title">'+ fileName +'</div>' +
                    '            <div class="image_delete" style="display: block;"><i class="fa fa-trash"></i></div>' +
                    '            <div class="image_success" style="display: block;"><i class="fa fa-check"></i></div>' +
                    '            <div class="image_down" style="display: block;"><i title="下载文件" class="fa fa-arrow-down"></i></div>' +
                    '            <input type="hidden" value="'+ index +'" />' +
                    '        </div>';
                $(_this).find(".image_content").append(html);
            };

            /**
             * 下载图片
             */
            _this.downImage = function(path){
                window.open(opts.downUrl+"?imageName="+path.replace("/upload/",""));
            };

            // 执行
            _this.init();
        });
    };
})($, document);

/**
 * 图片拖拽
 * @param ev
 */
function allowDrop(ev) {
    ev.preventDefault();
}
/**
 * 图片拖拽完成后执行
 * @param ev
 */
function drop(ev) {
    ev.preventDefault();
    var files = ev.dataTransfer.files;
    for (var i = 0; i < files.length; i++) {
        imagePreview(files[i]);
    }
}

/**
 * 图片上传显示
 */
function imagePreview(file){
    var fileTypeN = file.type.split("/")[1];
    if(fileType != "" && fileType.indexOf(fileTypeN) == -1){
        return alert("图片类型必须为"+fileType);
    }
    var len = $(_this).find(".image_div").length;
    if(len >=size){
        return alert("只能上传"+size+"张图片");
    }
    if(fileMaxSize != "" && file.size > fileMaxSize){
        return alert("图片上传不能超过"+bytesToSize(fileMaxSize));
    }
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = function (e) {
        var base64image = "";
        var image_class = "";
        if(checkImgType(file.name)){
            base64image = reader.result;
        }else{
            base64image = PATH+"image/ic_file.png";
            image_class = "file";
        }
        var html = ' <div class="image_div">' +
            '                <div class="image_file"><img class="'+ image_class +'" src="'+ base64image +'" /><div class="loading"><div class="laoding-bg"></div><div class="loading-image"><img src="'+ PATH +'image/ic_loading.gif"></div></div></div>' +
            '                <div class="image_title">'+ file.name +'</div>' +
            '                <div class="image_delete"><i class="fa fa-trash"></i></div>' +
            '                <div class="image_success"><i class="fa fa-check"></i></div>' +
            '                <div class="image_down"><i title="下载文件" class="fa fa-arrow-down"></i></div>' +
            '                <input type="hidden" value="'+ len +'" />' +
            '            </div>';
        $(_this).find(".image_content").append(html);
        uploadFiles[len] = file;
        indexList.push(len);
        $(_this).find(".image_font").hide();
    }
}

/**
 * 兼容IE8/IE9
 * @param base64Path 图片base64路径
 * @param file 文件
 */
function imagePreviewSWF(base64Path,file){
    var fileTypeN = file.type.split("/")[1];
    if(fileType != "" && fileType.indexOf(fileTypeN) == -1){
        return alert("图片类型必须为"+fileType);
    }
    var len = $(_this).find(".image_div").length;
    if(len >= size){
        return alert("图片不能超过"+size+"个");
    }
    if(fileMaxSize != "" && file.size > fileMaxSize){
        return alert("图片上传不能超过"+bytesToSize(fileMaxSize));
    }
    var len = $(_this).find(".image_div").length;
    var base64image = base64Path;
    var image_class = "";
    if(!checkImgType(file.name)){
        base64image = PATH+"image/ic_file.png";
        image_class = "file";
    }
    var html = ' <div class="image_div">' +
        '                <div class="image_file"><img class="'+ image_class +'" src="'+ base64image +'" /><div class="loading"><div class="laoding-bg"></div><div class="loading-image"><img src="'+ PATH +'image/ic_loading.gif"></div></div></div>' +
        '                <div class="image_title">'+ file.name +'</div>' +
        '                <div class="image_delete"><i class="fa fa-trash"></i></div>' +
        '                <div class="image_success"><i class="fa fa-check"></i></div>' +
        '                <div class="image_down"><i title="下载文件" class="fa fa-arrow-down"></i></div>' +
        '                <input type="hidden" value="'+ len +'" />' +
        '            </div>';
    $(_this).find(".image_content").append(html);
    uploadFiles[len] = file;
    indexList.push(len);
    $(_this).find(".image_font").hide();
}


/*
 * 判断图片类型
 *
 * @param ths
 *          type="file"的javascript对象
 * @return true-符合要求,false-不符合
 */
function checkImgType(imagePath){
    if (imagePath == "") {
        return false;
    } else {
        if (!/\.(gif|jpg|jpeg|png|svg|GIF|JPG|PNG|SVG)$/.test(imagePath)) {
            return false;
        }
    }
    return true;
}

/**
 * 删除数组某个特定值
 *
 * @param val 值
 */
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

/**
 * 判断IE版本
 * */
function IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if(isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion == 7) {
            return 7;
        } else if(fIEVersion == 8) {
            return 8;
        } else if(fIEVersion == 9) {
            return 9;
        } else if(fIEVersion == 10) {
            return 10;
        } else {
            return 6;//IE版本<=7
        }
    } else if(isEdge) {
        return 'edge';//edge
    } else if(isIE11) {
        return 11; //IE11
    }else{
        return -1;//不是ie浏览器
    }
}

/**
 * @Author:     HTL
 * @Email:       Huangyuan413026@163.com
 * @DateTime:    2016-06-02 11:37:05
 * @Description: 是否安装了Flash插件
 * @Source:  http://blog.csdn.net/tong_xinglong/article/details/7345419
 */
function flashChecker() {
    var hasFlash = 0;         //是否安装了flash
    var flashVersion = 0; //flash版本
    var isIE = /*@cc_on!@*/0;      //是否IE浏览器

    if (isIE) {
        var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
        if (swf) {
            hasFlash = 1;
            VSwf = swf.GetVariable("$version");
            flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
        }
    } else {
        if (navigator.plugins && navigator.plugins.length > 0) {
            var swf = navigator.plugins["Shockwave Flash"];
            if (swf) {
                hasFlash = 1;
                var words = swf.description.split(" ");
                for (var i = 0; i < words.length; ++i) {
                    if (isNaN(parseInt(words[i]))) continue;
                    flashVersion = parseInt(words[i]);
                }
            }
        }
    }
    return { f: hasFlash, v: flashVersion };
}

/**
 * 转换文件大小
 * @param bytes
 * @returns {string}
 */
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1024;
    var sizes = ['B','KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    var i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.ceil(bytes / Math.pow(k, i)) + ' ' + sizes[i];
    //toPrecision(3) 后面保留一位小数，如1.0GB                                                                                                                  //return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}