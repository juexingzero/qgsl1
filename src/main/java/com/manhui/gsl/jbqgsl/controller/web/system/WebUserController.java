package com.manhui.gsl.jbqgsl.controller.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.MD5Util;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.User;
import com.manhui.gsl.jbqgsl.service.web.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api( tags = "后台-用户管理" )
@Controller
@RequestMapping( "user" )
public class WebUserController {
    @Resource
    private IUserService userService;

    @ApiOperation(value = "进入用户列表页面", notes = "进入用户列表页面")
    @RequestMapping(value = "toUserListPage", method = RequestMethod.GET)
    public String userListPage() {
        return "/web/html/user/userList";
    }

    @ApiOperation(value = "进入添加用户界面", notes = "进入添加用户界面")
    @RequestMapping(value = "toAddUserPage", method = RequestMethod.GET)
    public String addUserPage() {
        return "/web/html/user/userAdd";
    }

    @ApiOperation(value = "进入选择人员-列表页面", notes = "进入选择人员-列表页面")
    @RequestMapping(value = "toUserSelect", method = RequestMethod.GET)
    public String userSelect() {
        return "/web/html/user/userSelect";
    }

    @ApiOperation(value = "进入多选人员-列表页面", notes = "进入多选人员-列表页面")
    @RequestMapping(value = "toUserbook", method = RequestMethod.GET)
    public String userbook() {
        return "/web/html/user/userBook";
    }

    @ApiOperation(value = "组织架构-职位人员列表", notes = "组织架构-职位人员列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", required = true, name = "position_id", value = "职位编码", dataType = "字符串"),
        @ApiImplicitParam(paramType = "query", required = true, name = "pageIndex", value = "当前页数", dataType = "数字"),
        @ApiImplicitParam(paramType = "query", required = true, name = "pageSize", value = "每页行数", dataType = "数字")
    })
    @RequestMapping(value = "listUserPage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listUserPage( 
        @RequestParam(required = true, value = "position_id") String position_id,
        @RequestParam(required = true, value = "pageIndex") Integer pageIndex,
        @RequestParam(required = true, value = "pageSize") Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setPosition_id( position_id );
        user.setPageNo( pageIndex * pageSize );
        user.setPageSize( pageSize );
        List<User> users = userService.queryPositionUser( user );
        Integer total = userService.queryPositionUserCount( user );
        if ( !users.isEmpty() ) {
            map.put( "data", users );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    @ApiOperation(value = "查询人员列表", notes = "查询人员列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", required = true, name = "key", value = "查询条件", dataType = "字符串"),
        @ApiImplicitParam(paramType = "query", required = true, name = "json", value = "筛选条件", dataType = "数字"),
        @ApiImplicitParam(paramType = "query", required = true, name = "pageIndex", value = "当前页数", dataType = "数字"),
        @ApiImplicitParam(paramType = "query", required = true, name = "pageSize", value = "每页行数", dataType = "数字")
    })
    @RequestMapping(value = "userList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userList( String key, String json, Integer pageIndex, Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setWhere( key );
        if ( json != null && !json.equals( "" ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            String keyWord = jsonObject.get( "keyWord" ).toString();
            if ( keyWord.equals( "user_name" ) ) {
                user.setUser_name( jsonObject.get( "keyValue" ).toString() );
            }
            else if ( keyWord.equals( "user_no" ) ) {
                user.setUser_no( jsonObject.get( "keyValue" ).toString() );
            }
            user.setUser_status( jsonObject.get( "user_status" ).toString() );
        }
        user.setPageNo( pageIndex * pageSize );
        user.setPageSize( pageSize );
        List<User> users = userService.queryUserList( user );
        Integer total = userService.queryUserListCount( user );
        if ( !users.isEmpty() ) {
            map.put( "data", users );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUser( HttpServletRequest request, String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject( json );
        User user = jsonObject.toJavaObject( User.class );
        user.setCreator_id( AppUtil.getCookieByName( request, "user_id" ) );
        user.setUser_status( "1" );
        if ( user.getPassword() != null && !user.getPassword().equals( "" ) ) {
            user.setPassword( MD5Util.encrypt( user.getPassword() ) );
        }
        Integer bools = 0;
        if ( user.getUser_id() != null && !user.getUser_id().equals( "" ) ) {
            bools = userService.updateUser( user );
        }
        else {
            user.setUser_id( UUIDUtil.getUUID() );
            bools = userService.insertUser( user );
        }
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    @RequestMapping(value = "userSelectData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userSelectData( String user_id ) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setUser_id( user_id );
        User user1 = userService.queryUserWhere( user );
        if ( user1 != null ) {
            map.put( "code", 200 );
            map.put( "data", user1 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户编码", dataType = "字符串")
    })
    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteUser( String user_id ) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setUser_id( user_id );
        Integer bools = userService.deleteUser( user );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", required = true, name = "user_id", value = "用户编码", dataType = "字符串"),
        @ApiImplicitParam(paramType = "query", required = true, name = "password", value = "密码", dataType = "字符串")
    })
    @RequestMapping(value = "updateUserPassWord", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateUserPassWord( String user_id, String password ) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setUser_id( user_id );
        user.setPassword( MD5Util.encrypt( password ) );
        Integer bools = userService.updateUser( user );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }

    @ApiOperation(value = "用户部门", notes = "用户部门")
    @RequestMapping(value = "userDept", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userDept( HttpServletRequest request ) {
        Map<String, Object> map = new HashMap<>();
        String user_id = AppUtil.getCookie( request, "user_id" );
        List<Dept> depts = userService.queryUserDept( user_id );
        if ( !depts.isEmpty() ) {
            map.put( "code", 200 );
            map.put( "data", depts.get( 0 ) );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    @ApiOperation(value = "根据部门编码查询人员", notes = "根据部门编码查询人员")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", required = true, name = "dept_id", value = "部门编码", dataType = "字符串")
    })
    @RequestMapping(value = "deptUserList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deptUserList( String dept_id ) {
        Map<String, Object> map = new HashMap<>();
        List<User> users = userService.queryDeptUser( dept_id );
        if ( !users.isEmpty() ) {
            map.put( "code", 200 );
            map.put( "data", users );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }
}
