package com.manhui.gsl.jbqgsl.controller.common.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.FileUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping( "upload" )
@Controller
@PropertySource( {
        "classpath:config.properties"
} )


public class CommonFileController {
    @Value( "${file_upload_path}" )
    private String fileUploadPath;
    //apk文件 APP端
    @Value( "${file_download_apk}" )
    private String fileDownloadApk;
    //后台-用户头像上传地址
    @Value( "${file_download_back_userImg}" )
    private String fileBackUserImg;
    @Value( "${file_download_topic_evaluate_result_path}" )
    private String fileDownloadTopicEvaluate;

    @ApiOperation( value = "进入上传测试页面", notes = "进入上传测试页面" )
    @RequestMapping( value = "test", method = RequestMethod.GET )
    public String test() {
        return "/web/html/image/image";
    }

    /**
     * 上传文件
     *
     * @param multipartFiles 文件
     * @return
     */
    @RequestMapping( value = "uploadFile", method = RequestMethod.POST )
    @ResponseBody
    public String uploadFile( @RequestParam( "file" ) MultipartFile[] multipartFiles ) {
        JSONObject json = new JSONObject();
        // 存储目录路径
        String filePath = "/upload/";
        String realPath = fileUploadPath + "/";
        List<String> path = new ArrayList<>();
        for ( MultipartFile file : multipartFiles ) {
            if ( !file.isEmpty() ) {
                try {
                    File upFile = new File( realPath );
                    /** 根据真实路径创建目录 **/
                    if ( !upFile.exists() ) {
                        upFile.mkdirs();
                    }
                    String filename = file.getOriginalFilename();
                    filename = filename.substring( filename.lastIndexOf( "." ) );
                    // 4位随机值
                    int rodom = ( int ) ( ( Math.random() * 9 + 1 ) * 1000 );
                    // 拼接图片链接
                    StringBuilder sb = new StringBuilder();
                    sb.append( "FILE_OK_" );
                    sb.append( new Date().getTime() + "" + rodom );
                    sb.append( filename );
                    String fileName = sb.toString();
                    String fileSavePath = realPath + sb.toString();
                    File file2 = new File( fileSavePath );
                    file.transferTo( file2 );
                    String imgUrl = ( filePath + fileName );
                    path.add( imgUrl );
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
            else {
                json.put( "code", 401 );// 参数为空
                return json.toString();
            }
        }
        json.put( "code", 200 );
        json.put( "path", path );
        return json.toString();
    }
    /**
     * 上传apk文件
     *
     * @param multipartFiles 文件
     * @return
     */
    @RequestMapping( value = "uploadApkFile", method = RequestMethod.POST )
    @ResponseBody
    public String uploadApkFile( @RequestParam( "file" ) MultipartFile[] multipartFiles ) {
    	JSONObject json = new JSONObject();
    	// 存储目录路径
    	String filePath = "/apk/";
    	String realPath = fileDownloadApk + "/";
    	List<String> path = new ArrayList<>();
    	for ( MultipartFile file : multipartFiles ) {
    		if ( !file.isEmpty() ) {
    			try {
    				File upFile = new File( realPath );
    				/** 根据真实路径创建目录 **/
    				if ( !upFile.exists() ) {
    					upFile.mkdirs();
    				}
    				String filename = file.getOriginalFilename();
    				filename = filename.substring( filename.lastIndexOf( "." ) );
    				// 4位随机值
    				int rodom = ( int ) ( ( Math.random() * 9 + 1 ) * 1000 );
    				// 拼接图片链接
    				StringBuilder sb = new StringBuilder();
    				sb.append("jbqgsl");
    				sb.append(filename );
    				String fileName = sb.toString();
    				String fileSavePath = realPath + sb.toString();
    				File file2 = new File( fileSavePath );
    				file.transferTo( file2 );
    				String imgUrl = ( filePath + fileName );
    				path.add( imgUrl );
    			}
    			catch ( Exception e ) {
    				e.printStackTrace();
    			}
    		}
    		else {
    			json.put( "code", 401 );// 参数为空
    			return json.toString();
    		}
    	}
    	json.put( "code", 200 );
    	json.put( "path", path );
    	return json.toString();
    }
    /**
     * 上传后台人员头像
     *
     * @param multipartFiles 文件
     * @return
     */
    @RequestMapping( value = "backUserImg", method = RequestMethod.POST )
    @ResponseBody
    public String upBackUserImg( @RequestParam( "head_img" ) MultipartFile[] multipartFiles ) {
    	JSONObject json = new JSONObject();
    	// 存储目录路径
    	String filePath = "/back/";
    	String realPath = fileBackUserImg + "/";
    	List<String> path = new ArrayList<>();
    	for ( MultipartFile file : multipartFiles ) {
    		if ( !file.isEmpty() ) {
    			try {
    				File upFile = new File( realPath );
    				/** 根据真实路径创建目录 **/
    				if ( !upFile.exists() ) {
    					upFile.mkdirs();
    				}
    				String filename = file.getOriginalFilename();
    				filename = filename.substring( filename.lastIndexOf( "." ) );
    				// 4位随机值
    				int rodom = ( int ) ( ( Math.random() * 9 + 1 ) * 1000 );
    				// 拼接图片链接
    				StringBuilder sb = new StringBuilder();
    				sb.append("back");
    				sb.append( new Date().getTime() + "" + rodom );
    				sb.append(filename );
    				String fileName = sb.toString();
    				String fileSavePath = realPath + sb.toString();
    				File file2 = new File( fileSavePath );
    				file.transferTo( file2 );
    				String imgUrl = ( filePath + fileName );
    				path.add( imgUrl );
    			}
    			catch ( Exception e ) {
    				e.printStackTrace();
    			}
    		}
    		else {
    			json.put( "code", 401 );// 参数为空
    			return json.toString();
    		}
    	}
    	json.put( "code", 200 );
    	json.put( "path", path );
    	return json.toString();
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return
     */
    @RequestMapping( "/deleteFile" )
    @ResponseBody
    public Map<String, Object> deleteFile( String path ) {
        Map<String, Object> map = new HashMap<>();
        if ( path == null || path.equals( "" ) ) {
            map.put( "code", 401 );
            return map;
        }
        try {
            String realPath = Objects.requireNonNull( ClassUtils.getDefaultClassLoader().getResource( "" ) ).getPath() +
                    path;
            File file = new File( realPath );
            file.delete();
            map.put( "code", 200 );
        }
        catch ( Exception e ) {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 保存文件
     *
     * @param path 路径数组
     * @return
     */
    @RequestMapping( "/saveFile" )
    @ResponseBody
    public Map<String, Object> saveFile( String[] path ) {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    /**
     * 文件下载
     * 
     * @param imageName 文件路径
     * @param response response/upload
     * @return String
     */
    @RequestMapping( value = "/downloadImage", method = RequestMethod.GET )
    public String downloadImage( String imageName, HttpServletResponse response ) {
        String fileUrl = fileUploadPath + imageName;
        if ( fileUrl != null ) {
            File file = new File( fileUrl );
            if ( file.exists() ) {
                response.setContentType( "application/force-download" );// 设置强制下载不打开
                response.addHeader( "Content-Disposition", "attachment;fileName=" + imageName );// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream( file );
                    bis = new BufferedInputStream( fis );
                    OutputStream os = response.getOutputStream();
                    int i = bis.read( buffer );
                    while ( i != -1 ) {
                        os.write( buffer, 0, i );
                        i = bis.read( buffer );
                    }
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
                finally {
                    if ( bis != null ) {
                        try {
                            bis.close();
                        }
                        catch ( IOException e ) {
                            e.printStackTrace();
                        }
                    }
                    if ( fis != null ) {
                        try {
                            fis.close();
                        }
                        catch ( IOException e ) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @ApiOperation( value = "文件下载", notes = "文件下载" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "fileType", value = "文件类型（apk：apk安装包，topic：主题评价）", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "fileName", value = "文件名称", dataType = "字符串" )
    } )
    @RequestMapping( value = "download", method = RequestMethod.GET )
    @ResponseBody
    public JsonResult download(
            @RequestParam( value = "fileType", required = true ) String fileType,
            @RequestParam( value = "fileName", required = true ) String fileName,
            HttpServletResponse response ) {
        String filePath = "";
        if("apk".equals( fileType )) {
            filePath = fileDownloadApk;
        }else if("topicEvaluate".equals( fileType )) {
            filePath = fileDownloadTopicEvaluate;
        }
        boolean flag = FileUtil.download( response, filePath, fileName );
        return new JsonResult( flag );
    }
}
