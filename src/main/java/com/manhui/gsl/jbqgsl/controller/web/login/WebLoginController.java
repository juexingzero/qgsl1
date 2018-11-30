package com.manhui.gsl.jbqgsl.controller.web.login;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.MD5Util;
import com.manhui.gsl.jbqgsl.model.User;
import com.manhui.gsl.jbqgsl.service.web.IOrganizationalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api( tags = "后台-登录退出" )
@Controller
@RequestMapping( "login" )
public class WebLoginController {
    @Resource
    private IOrganizationalService organizationalService;

    @ApiOperation(value = "进入登录页面", notes = "进入登录页面")
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "/web/html/login/login";
    }
    
    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @RequestMapping(value = "forgetPwd", method = RequestMethod.GET)
    public String forgetPwd() {
        return "/web/html/login/forgetPwd";
    }
    
    @ApiOperation(value = "提交登录", notes = "提交登录")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", required = true, name = "userName", value = "用户名称", dataType = "字符串"),
    	@ApiImplicitParam(paramType = "query", required = true, name = "passWord", value = "用户密码", dataType = "字符串"),
    	@ApiImplicitParam(paramType = "query", required = true, name = "remFlag", value = "记住密码标志(记住密码 true,不记住 false)", dataType = "布尔类型")
    })
    @RequestMapping(value = "loginSubmit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginSubmit( HttpServletResponse response, String userName, String passWord) {
    	Map<String, Object> map = new HashMap<>();
    	User user = new User();
    	user.setLogin_name( userName );
    	user.setPassword( MD5Util.encrypt( passWord ) );
    	User user1 = organizationalService.queryLoginPassword( user );
    	if ( user1 != null ) {
    		AppUtil.setUserCookie( response, "user_id", user1.getUser_id() );
            AppUtil.setUserCookie( response, "user_name", user1.getUser_name() );
            AppUtil.setUserCookie( response, "user_type", user1.getUser_type() );
    		map.put( "code", 200 );
            map.put( "msg", "登录成功" );
            map.put( "userName", user1.getUser_name() );
    	}
    	else {
    		map.put( "code", 401 );
    		map.put( "msg", "账号或者密码错误" );
    	}
    	return map;
    }
    
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @RequestMapping(value = "closeLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> closeLogin( HttpServletResponse response ) {
        Map<String, Object> map = new HashMap<>();
        AppUtil.closeUserCookie( response, "user_id" );
        AppUtil.closeUserCookie( response, "user_name" );
        map.put( "code", 200 );
        return map;
    }
    
    @ApiOperation(value = "验证用户名以及手机号码", notes = "验证用户名以及手机号码")
    @RequestMapping(value = "checkSysUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkSysUser( String login_name,String mobile_no ) {
    	Map<String, Object> map = new HashMap<>();
    	User user = new User();
    	user.setLogin_name(login_name);
    	user.setMobile_no(mobile_no);
    	int num = organizationalService.checkSysUser(user);
    	if(num ==1) {
    		map.put("code", 200);
    	}else {
    		map.put( "code", 401 );
    		map.put("data", "登录账号或联系手机号码不存在，请重新填写");
    	}
    	return map;
    }
    
    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePwd( String login_name, String mobile_no, String password ) {
    	Map<String, Object> map = new HashMap<>();
    	User user = new User();
    	user.setLogin_name(login_name);
    	user.setMobile_no(mobile_no);
    	user.setPassword(MD5Util.encrypt(password));
    	int num = organizationalService.updatePwd(user);
	    if(num==1) {
	    	map.put("code", 200);
	    	map.put("message", "修改密码成功");
	    }else {
	    	map.put("code", 401);
	    	map.put("message", "修改密码失败");
	    }
    	return map;
    }
}
