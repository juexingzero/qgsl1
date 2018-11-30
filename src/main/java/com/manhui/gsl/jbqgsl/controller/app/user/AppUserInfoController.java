package com.manhui.gsl.jbqgsl.controller.app.user;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.FileUploadUtil;
import com.manhui.gsl.jbqgsl.common.util.FileUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.common.util.ZxingUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.service.app.IAppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @Title: AppUserInfoController.java
* @Package com.manhui.gsl.jbqgsl.controller.app
* @Description: TODO(个人信息修改控制层)
* @author LiuBin
* @date 2018年8月14日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Api(tags = "我的-个人信息保存修改")
@Controller
@RequestMapping(AppUserInfoController.ROOT_URL)
@ResponseBody
public class AppUserInfoController extends BaseController{
	public static final String ROOT_URL =PARENT_URL_APP+ "/userInfo";
	
	@Value( "${file_upload_path2}" )
	private String      fileUploadPath;
	@Resource
	private IAppUserService appUserService;
	@Autowired
	private ZxingUtil zxingUtil;
	
	@ApiOperation(value = "查询用户个人信息", notes = "用户个人信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "user_id", value = "用户id", required = true, dataType = "字符串")
	})
	@RequestMapping(value="queryUserInfo",method=RequestMethod.POST)
	public JsonResult queryUserInfo(String user_id) {
		if (StringUtils.isEmpty(user_id)) {
			throw new RuntimeException("请先登录");
		}
		AppUser  user = appUserService.queryUserMemberModelById(user_id);
		return new JsonResult(user);
	}
	

  
	@ApiOperation(value = "用户个人头像", notes = "上传修改用户个人头像 ")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "user_id", value = "用户id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "photo", value = "头像图片", required = true, dataType = "文件类型")
	})
    @RequestMapping( value = "/updateUserHeadImg", method = RequestMethod.POST )
    @ResponseBody
    public String uploadFile( 
    		@RequestParam( "user_id" ) String user_id,
    		@RequestParam( "photo" ) MultipartFile photo ) {
        JSONObject json = new JSONObject();
        AppUser user = new AppUser();
		user.setUser_id(user_id);
		//分隔符
		String separator = File.separator;
        String filePath = "/heads/";
        String realPath = fileUploadPath +"/";
        //二维码路径
        String qrPath ="/qrpath/";
        //头像
        String headPath =realPath+filePath;
        String path = null;
            if ( !photo.isEmpty() ) {
                /*if (file.getSize() > 1000 * 1024 * 4) {
                    // 文件大小不得超过4M
                    map.put("message", 110);
                    return map;
                }*/
                try {
                    File upFile1 = new File( realPath+filePath );
                    File upFile2 = new File( realPath+qrPath );
                    
                    /** 根据真实路径创建目录 **/
                    if ( !upFile1.exists() ) {
                        upFile1.mkdirs();
                    }
                    if ( !upFile2.exists() ) {
                    	upFile2.mkdirs();
                    }
                    String filename = photo.getOriginalFilename();
                    filename = filename.substring( filename.lastIndexOf( "." ) );
                    // 4位随机值
                    int rodom = ( int ) ( ( Math.random() * 9 + 1 ) * 1000 );
                    // 拼接图片链接
                    StringBuilder sb = new StringBuilder();
                    sb.append( "headImg" );
                    sb.append( new Date().getTime() + "" + rodom );
                    sb.append( filename );
                    String fileName = sb.toString();
                    String fileSavePath = realPath+filePath + sb.toString();
                    File file2 = new File(fileSavePath);
                    photo.transferTo( file2 );
                    String imgUrl = ( filePath+fileName );
                    path= imgUrl;
                    user.setHead_img(path);
    				user.setUpdate_time(DateUtil.getTime());
    				// 头像注册文件的跟踪记录
    				//根据头像生成二维码
    				userInfoErWeiMa(user, realPath+path, realPath+qrPath);
    				Map<String, Object> orginUser = appUserService.getByUser(user);
    				//修改个人信息中的头像以及二维码
    				appUserService.updateUserHeadPhoto(user);
    				//删除原头像文件以及原二维码
    				String[]files = {realPath+orginUser.get("head_img").toString(),realPath+orginUser.get("qr_code_img").toString()};
    				if(files !=null &&files.length==2) {
    					for (String file : files) {
    						FileUtil.delete(file);
						}
    					
    				}
    				
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
            else {
                json.put( "code", 401 );// 参数为空
                return json.toString();
            }
        
        json.put( "code", 200 );
        json.put( "path", path );
        return json.toString();
    }

	@ApiOperation(value = "修改用户个人信息", notes = "修改用户个人信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "user_id", value = "用户id", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "user_sex", value = "用户性别(0：男，1：女，默认0)", required = true, dataType = "字符型"),
		@ApiImplicitParam(paramType = "query", name = "user_name", value = "用户姓名", required = false, dataType = "字符型"),
		@ApiImplicitParam(paramType = "query", name = "user_phone", value = "手机号", required = true, dataType = "字符型"),
		@ApiImplicitParam(paramType = "query", name = "user_email", value = "邮箱", required = true, dataType = "字符类型")
	})
	@RequestMapping(value = "/updateUserInfo",method=RequestMethod.POST)
	public JsonResult updateUserInfo(
			@RequestParam(value = "user_id", required = true)String user_id,
			@RequestParam(value = "user_sex", required = false)String user_sex,
			@RequestParam(value = "user_name", required = false)String user_name,
			@RequestParam(value = "user_phone", required = false)String user_phone,
			@RequestParam(value = "user_email", required = false)String user_email){
		AppUser user = appUserService.queryUserMemberModelById(user_id);
		if(user_id == null) {
			return new JsonResult("请先登录");
		}
		String realPath = fileUploadPath + "/";
	       //二维码路径
		
	    String qrPath1 ="/qrpath/";
		String qrPath2= realPath+qrPath1;
		if(user.getQr_code_img() ==null|| "".equals(user.getQr_code_img())) {
			try {
				userInfoErWeiMa(user, "", qrPath2);
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult("二维码生成失败");
			}
		}
		if(user_phone !=null || user_email !=null ||user_sex!=null) {
			user.setUser_phone(user_phone);
			user.setUser_email(user_email);
			user.setUser_sex(user_sex);
			user.setUser_name(user_name);
			checkParam(user);
		}
		int update = appUserService.update(user);
		if(update !=1) {
			throw new RuntimeException("修改个人信息失败");
		}
		return new JsonResult("修改个人信息成功");
	}
	

	/**
	 * 根据头像生成二维码
	 * param userMemberModel 要封装的内容
	 * parentPath :插入图片路径
	 * qrPath: 存储路径
	 */
	private void userInfoErWeiMa(AppUser user, String img, String qrPath) throws Exception {
		String qrName3 = UUIDUtil.getUUID();

		ZxingUtil.encode(user.getUser_id(), img , qrPath, true,
				qrName3);
		user.setQr_code_img("/qrpath/" + qrName3 + ".jpg");
		
	}
	
	 /**
     * 验证参数有效性
     * @param model 参数
	 * @return 
     */
    private void checkParam(AppUser model){
    	 String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    	 String REGEX_PHONE= "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    	 String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    	  //验证手机号
         if(model.getUser_phone()!=null && !Pattern.matches(REGEX_PHONE,model.getUser_phone())){
        	 throw new RuntimeException("电话号码格式不正确");     
        }
         //验证邮箱
         if(model.getUser_email()!=null && !"".equals(model.getUser_email())&& !Pattern.matches(REGEX_EMAIL, model.getUser_email())) {
        	 throw new RuntimeException("邮箱格式不正确");
         }

    }
}
