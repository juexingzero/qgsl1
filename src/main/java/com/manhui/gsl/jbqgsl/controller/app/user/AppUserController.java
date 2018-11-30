package com.manhui.gsl.jbqgsl.controller.app.user;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import com.manhui.gsl.jbqgsl.common.enums.LoggerType;
import com.manhui.gsl.jbqgsl.model.SysLoggerModel;
import com.manhui.gsl.jbqgsl.service.web.SysLoggerServiceImpl;
import javafx.beans.binding.ObjectBinding;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.MD5Util;
import com.manhui.gsl.jbqgsl.common.util.RedisUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.service.app.IAppUserService;
import com.manhui.gsl.jbqgsl.service.app.sysuser.IAppSysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebIndustryController.java
 * @类描述 行业数据模块controller层，主要负责请求的接收及响应
 * @作者 LiuBin
 * @创建时间 2018年8月13日11:35:11
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	LiuBin 	 2018年8月13日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api(tags = "登陆 注册 修改密码")
@Controller
@RequestMapping(AppUserController.ROOT_URL)
@ResponseBody
public class AppUserController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_APP + "/user";
	static Logger log = LoggerFactory.getLogger(AppUserController.class);
	public static final String REGEX_PHONE = "1\\d{10}$";
	public static final String REGEX_PWD = "^.{6,}$";
	@Resource
	private IAppUserService appUserService;
	@Resource
	private IAppSysUserService appSysUserService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private SysLoggerServiceImpl sysLoggerService;
	@Value("${redis_user_number}")
	private String redis_user_number;

	@SuppressWarnings("deprecation")
	@ApiOperation(value = "用户注册", notes = "用户手机注册")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "user_phone", value = "注册手机号", required = true, dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", name = "login_password", value = "注册密码", required = true, dataType = "字符串")

	})
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public JsonResult register(@RequestParam(value = "user_phone", required = true) String user_phone,
			@RequestParam(value = "login_password", required = true) String login_password) {

		AppUser user = new AppUser();
		user.setUser_phone(user_phone);
		user.setLogin_password(login_password);
		user.setUser_id(UUIDUtil.getUUID());
		user.setCreate_time(DateUtil.getTime());
		user.setLogin_num(0);
		if (!checkUserPwd(user,0)) {// 验证帐号密码 此处为手机注册 flag为0
            return new JsonResult(new RuntimeException("用户名或密码不合法！"));
		}
		// 验证手机号码,用户名,登录账号 不重复
		int phoneCount = appUserService.getUserPhone(user);
		if (phoneCount != 0) {
            return new JsonResult(new RuntimeException("该手机已经注册,请确认！"));
		}
		user.setLogin_password(MD5Util.parse(user.getLogin_password()));

		if (appUserService.save(user) < 1) {
            return new JsonResult(new RuntimeException("注册失败！"));
		}
		Map<String, Object> map = appUserService.getByUser(user);
		if (map.isEmpty()) {
            return new JsonResult(new RuntimeException("用户名或密码错误！"));
		}
		String token = MD5Util.parse(user.getUser_phone() + user.getLogin_password());
		map.remove("login_password");// 清除密码信息
		map.put("token", token);
		redisUtil.set(token, map, 60 * 30);
		return new JsonResult(map);

	}

	@ApiOperation(value = "用户登录", notes = "用户手机登录")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "user_phone", value = "注册手机号", required = false, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "login_password", value = "注册密码", required = true, dataType = "字符串"),
		@ApiImplicitParam(paramType = "query", name = "user_name", value = "企业名,部门名,对应sys_user表中的login_name", required = false, dataType = "字符串")

	})
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JsonResult login(@RequestParam(value = "user_phone", required = false) String user_phone,
			@RequestParam(value = "user_name", required = false) String user_name,
			@RequestParam(value = "login_password", required = true) String login_password,
							@RequestParam(value = "phoneId", required = false) String phoneId) {
		//判断app端是手机号码登录,还是公司名 部门登录 默认为0 以手机登录
		int flag = 0;
		AppUser user = new AppUser(); 
		if(user_name !=null) {
			flag = 1;//1表示app端以公司名称或者部门名称进行登录
			user.setUser_name(user_name);
		}
		if(user_phone !=null) {
			user.setUser_phone(user_phone);
		}
		user.setLogin_password(login_password);
		user.setUser_phone(user_phone);
		if (!checkUserPwd(user,flag)) {
            return new JsonResult(new RuntimeException("用户名或密码不合法！"));
		}
		user.setLogin_password(MD5Util.parse(user.getLogin_password()));
		//在app_user表中查
		HashMap<String, Object> map = appUserService.getByUser(user);
		if (map == null || map.isEmpty()) {
			//查找sysUser表
			HashMap<String, Object> sysMap = appSysUserService.querySysUser(user);
			if(sysMap == null || sysMap.isEmpty()) {
				return new JsonResult(new RuntimeException("用户名错误或密码错误！"));
			}
			//拷贝过来
			map=sysMap;
			String token = MD5Util.parse(user.getUser_phone() + user.getLogin_password());
			if ("1".equals(String.valueOf(map.get("del_flag")))) {
				return new JsonResult(new RuntimeException("该账号已被删除,请联系管理员！"));
			}
			map.remove("login_password");// 清除密码信息
			redisUtil.set(token, map, 60 * 30);// 有效期30分钟,添加数据到缓存。
			map.put("token", token);
			return new JsonResult(map);
		}
		
		if ("1".equals(String.valueOf(map.get("del_flag")))) {
			return new JsonResult(new RuntimeException("该账号已被删除,请联系管理员！"));
		}
		Integer login_num = Integer.valueOf(map.get("login_num") + "");
		user.setLogin_num(login_num += 1);
		user.setLast_time(DateUtil.getTime());

		if(StringUtils.isNotBlank(phoneId) && !phoneId.equals(map.get("phoneId"))){
			//修改手机id
			user.setPhoneId(phoneId);
		}

		int update = appUserService.update(user);
		if (update < 1) {
            return new JsonResult(new RuntimeException("登陆次数,修改失败！"));
		}
		String token = MD5Util.parse(user.getUser_phone() + user.getLogin_password());
		map.remove("login_password");// 清除密码信息
		redisUtil.set(token, map, 60 * 30);// 有效期30分钟,添加数据到缓存。
		map.put("token", token);

		//增加登录日志
		SysLoggerModel sysLogger = new SysLoggerModel();
		sysLogger.setUserId(map.get("user_id").toString());
		sysLogger.setType(LoggerType.login.getCode());
		sysLogger.setUserName(map.get("user_name")+"");
		sysLogger.setUserType("1");
		sysLogger.setLogger_content("用户："+map.get("user_name")+"手机APP登录。登录时间："+
				new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		sysLogger.setOperating_obj_id("手机app登录");
		sysLogger.setOperating_describe("手机app登录");
		sysLoggerService.save(sysLogger);

		//更新用户数量,redis_user_number_map根据的大小获得用户数量
		Object user_number_obj = redisUtil.get(redis_user_number);
		Map<String,Object> redis_user_number_map;
		if(user_number_obj == null){
			redis_user_number_map = new HashMap<>();
		}else{
			redis_user_number_map = (Map<String,Object>)user_number_obj;
		}
		String redis_key = redis_user_number+"_"+token;
		//设值
		String login_time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");//登录时间
		redis_user_number_map.put(redis_key,login_time);
		redisUtil.set(redis_user_number,redis_user_number_map);

		//获得 用户在 app 的角色类型
		String userRoleType = appUserService.getUserRoleType(map.get("user_id")+"");
		map.put("userRoleType",userRoleType);
		return new JsonResult(map);
  
	}

	@ApiOperation(value = "查看人员基本信息", notes = "查看人员基本信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "token", value = "token令牌", required = true, dataType = "字符串"), })
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public JsonResult getLoginUserInfo(@RequestParam(value = "token", required = true) String token) {
		if (!redisUtil.hasKey(token)) {
			throw new RuntimeException("没有登陆！请先登陆！");
		}
		return new JsonResult(redisUtil.get(token));
	}

	@ApiOperation(value = "检查token状态", notes = "检查token状态")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "token", value = "token令牌", required = true, dataType = "字符串"), })
	@RequestMapping(value = "/checkToken", method = RequestMethod.POST)
	public JsonResult checkToken(String token) {
		if (!redisUtil.hasKey(token)) {
			throw new RuntimeException("token已失效！");
		}
		return new JsonResult();
	}

	@ApiOperation(value = "修改密码", notes = "修改密码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "user_phone", value = "手机号码", required = false, dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", name = "login_password", value = "登录密码", required = true, dataType = "字符串"), })
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/changePWD", method = RequestMethod.POST)
	public JsonResult update(@RequestParam(value = "user_phone", required = false) String user_phone,
			@RequestParam(value = "user_name", required = false) String user_name,
			@RequestParam(value = "login_password", required = true) String login_password) {
		AppUser user = new AppUser();
		//判断app端是手机号码登录,还是公司名 部门登录 默认为0 以手机登录
		int flag = 0;
		if(user_name !=null) {
			flag = 1;//1表示app端以公司名称或者部门名称进行登录
			user.setUser_name(user_name);
		}
		if(user_phone !=null) {
			user.setUser_phone(user_phone);
		}
		user.setUser_phone(user_phone);
		user.setLogin_password(login_password);
		user.setUpdate_time(DateUtil.getTime());
		if (!checkUserPwd(user,flag)) {// 验证帐号密码
            return new JsonResult(new RuntimeException("用户名或密码不合法！"));
		}
		user.setLogin_password(MD5Util.parse(user.getLogin_password()));
		if (appUserService.update(user) < 1) {
            return new JsonResult(new RuntimeException("修改密码失败！"));
		}
		return new JsonResult("修改密码成功！");
	}

	@ApiOperation(value = "退出登录", notes = "退出登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "token", value = "token令牌", required = true, dataType = "字符串"), })
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	private JsonResult signOut(String token) {
		if (token == null) {
			throw new RuntimeException("传入参数不正确");
		}

		if (redisUtil.hasKey(token)) {
			redisUtil.del(token);
		}
		//删除登录数量
		Object user_number_obj = redisUtil.get(redis_user_number);
		Map<String,Object> redis_user_number_map;
		if(user_number_obj != null){
			redis_user_number_map = (Map<String,Object>)user_number_obj;
			if(!redis_user_number_map.isEmpty()){
				Object redis_user_number_obj = redis_user_number_map.get(redis_user_number+"_"+token);
				if(redis_user_number_obj != null){
					String redis_user_number_value = redis_user_number_obj.toString();
					redis_user_number_map.remove(redis_user_number_value);
					redisUtil.set(redis_user_number,redis_user_number_map);
				}
			}
		}
		return new JsonResult("退出登陆成功！");
	}

	@ApiOperation(value = "公开个人信息", notes = "公开个人信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "isPublic", value = "是否公开", required = true, dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", name = "user_id", value = "用户id", required = true, dataType = "字符串"), })
	@RequestMapping(value = "/publicUserInfo", method = RequestMethod.POST)
	private JsonResult showUserInfo(String isPublic, String user_id) {
		if (isPublic == null || user_id == null) {
            throw new RuntimeException("传入参数不正确");
		}
		AppUser appUser = new AppUser();
		appUser.setIs_public(isPublic);
		appUser.setUser_id(user_id);
		appUser.setUpdate_time(DateUtil.getTime());
		if (appUserService.update(appUser) < 1) {
            throw new RuntimeException("个人信息公开修改失败");
		}
		return new JsonResult("个人信息公开修改成功！");
	}

	/**
	 * 验证帐号密码有效性
	 * 
	 * @param user 资料
	 * @return 结果
	 */
	private boolean checkUserPwd(AppUser user,int flag) {
		if(flag ==0) {//用手机号码登录 
			if (user.getUser_phone() == null || user.getLogin_password() == null) {
				throw new RuntimeException("手机号或密码不能为空！");
			}
			// 验证手机号
			if (user.getUser_phone() != null && !Pattern.matches(REGEX_PHONE, user.getUser_phone())) {
				return false;
			}
		}
		// 验证密码
		if (!Pattern.matches(REGEX_PWD, user.getLogin_password())) {
			return false;
		}
		return true;
	}

}
