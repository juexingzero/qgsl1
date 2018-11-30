var PATH = __CreateJSPath("fileUpload.js");
// 导入必要的包
document.write('<link href="' + PATH + 'font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + PATH + 'css/fileUpload.css" rel="stylesheet" type="text/css" />');
document.write('<script src="' + PATH + 'js/jquery-1.7.2.min.js" type="text/javascript" ></script>');
document.write('<script src="' + PATH + 'js/file.js" type="text/javascript" ></script>');
// 百度flash上传
document.write('<script src="' + PATH + 'flash/webuploader.js" type="text/javascript" ></script>');
document.write('<link href="' + PATH + 'flash/webuploader.css" rel="stylesheet" type="text/css" />');


function __CreateJSPath(js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}