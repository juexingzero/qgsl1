package com.manhui.gsl.jbqgsl.controller.web.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;
import com.manhui.gsl.jbqgsl.service.web.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebMessageManagerController.java
 * @类描述 消息管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月23日 上午09:26:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月23日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "后台-消息管理" )
@Controller
@RequestMapping( WebMessageManagerController.ROOT_URL )
public class WebMessageManagerController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/message";
    @Resource
    private IMessageService messageService;

    @ApiOperation( value = "进入消息管理列表页面", notes = "进入消息管理列表页面" )
    @RequestMapping( value = "toMessageInfoListPage", method = RequestMethod.GET )
    public String toMessageInfoListPage() {
        return "/web/html/message/messageInfoList";
    }

    @ApiOperation(value = "进入消息信息添加", notes = "进入消息信息添加")
    @RequestMapping(value = "toMessageInfoAddPage", method = RequestMethod.GET)
    public String toMessageInfoAddPage() {
        return "/web/html/message/messageInfoAdd";
    }

    @ApiOperation( value = "进入消息接收方的用户选择页面", notes = "进入消息接收方的用户选择页面" )
    @RequestMapping( value = "/toAppUserSelectPage", method = RequestMethod.GET )
    public String toAppUserSelectPage() {
        return "/web/html/message/appUserSelect";
    }

    @ApiOperation( value = "获取消息信息列表", notes = "获取消息信息列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "json", value = "查询条件", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageIndex", value = "当前页数", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageSize", value = "每页行数", dataType = "字符串" )
    } )
    @RequestMapping( value = "getMessageInfoList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getMessageInfoList( 
            @RequestParam(value = "json", required = false)String json,
            @RequestParam(value = "pageIndex", required = false)Integer pageIndex,
            @RequestParam(value = "pageSize", required = false)Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        MessageInfo info = new MessageInfo();
        if ( json != null && !"".equals( json ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            if ( StringUtils.isNotEmpty( jsonObject.get( "message_type" ) + "" ) ) {
                info.setMessage_type( jsonObject.get( "message_type" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "message_mode" ) + "" ) ) {
                info.setMessage_mode( jsonObject.get( "message_mode" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "message_content" ) + "" ) ) {
                info.setMessage_content( jsonObject.get( "message_content" ) + "" );
            }
        }
        if ( pageIndex != null && pageSize != null ) {
            info.setPageNo( pageIndex * pageSize );
            info.setPageSize( pageSize );
        }
        List<MessageInfo> infoList = messageService.getMessageInfoList( info );
        Integer infoTotal = messageService.getMessageInfoTotal( info );
        if ( infoList != null && infoList.size() > 0 ) {
            map.put( "data", infoList );
            map.put( "total", infoTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    @ApiOperation( value = "保存消息信息", notes = "保存消息信息" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "json", value = "消息信息", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveMessageInfo", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> saveMessageInfo( HttpServletRequest request, String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject( json );
        MessageInfo info = jsonObject.toJavaObject( MessageInfo.class );
        info.setCreator_id( AppUtil.getCookieByName( request, "user_id" ) );
        info.setCreator_name( AppUtil.getCookieByName( request, "user_name" ) );
        Integer bools = messageService.saveMessageInfo( info );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }
    
    @ApiOperation( value = "获取消息信息", notes = "获取消息信息" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "message_id", value = "消息ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "getMessageInfo", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getMessageInfo( 
            @RequestParam(value = "message_id", required = true)String message_id ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "data", messageService.getMessageInfo( message_id ) );
        return map;
    }
    
    @ApiOperation(value = "删除消息信息", notes = "删除消息信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", required = true, name = "message_ids", value = "消息ID", dataType = "字符串")
    })
    @RequestMapping(value = "deleteMessageInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteMessageInfo( 
            @RequestParam(value = "message_ids", required = true)String message_ids ) {
        return new JsonResult(messageService.deleteMessageInfo( message_ids ));
    }

    @ApiOperation( value = "获取消息流水列表", notes = "获取消息流水列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "json", value = "查询条件", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageIndex", value = "当前页数", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageSize", value = "每页行数", dataType = "字符串" )
    } )
    @RequestMapping( value = "getMessageFlowingList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getMessageFlowingList( 
            @RequestParam(value = "json", required = false)String json,
            @RequestParam(value = "pageIndex", required = false)Integer pageIndex,
            @RequestParam(value = "pageSize", required = false)Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        MessageFlowing flowing = new MessageFlowing();
        if ( json != null && !"".equals( json ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            if ( StringUtils.isNotEmpty( jsonObject.get( "message_id" ) + "" ) ) {
                flowing.setMessage_id( jsonObject.get( "message_id" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "send_name" ) + "" ) ) {
                flowing.setSend_name( jsonObject.get( "send_name" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "receive_name" ) + "" ) ) {
                flowing.setReceive_name( jsonObject.get( "receive_name" ) + "" );
            }
        }
        if ( pageIndex != null && pageSize != null ) {
            flowing.setPageNo( pageIndex * pageSize );
            flowing.setPageSize( pageSize );
        }
        List<MessageFlowing> flowingList = messageService.getMessageFlowingList( flowing );
        Integer flowingTotal = messageService.getMessageFlowingTotal( flowing );
        if ( flowingList != null && flowingList.size() > 0 ) {
            map.put( "data", flowingList );
            map.put( "total", flowingTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    @ApiOperation( value = "保存发送消息流水", notes = "保存发送消息流水" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "message_id", value = "消息ID", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "institution_json", value = "机构信息", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveMessageFlowing", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> saveMessageFlowing( HttpServletRequest request, String message_id, String institution_json ) {
        Map<String, Object> map = new HashMap<>();
        List<AppUser> appUserList = JSONObject.parseArray( institution_json, AppUser.class );
        Integer bools = messageService.saveMessageFlowing( request, message_id, appUserList );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }
    
    @ApiOperation( value = "获取消息接收方的用户列表", notes = "获取消息接收方的用户列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "json", value = "查询条件", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageIndex", value = "当前页数", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageSize", value = "每页行数", dataType = "字符串" )
    } )
    @RequestMapping( value = "getAppUserList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getAppUserList( 
            @RequestParam(value = "json", required = false)String json,
            @RequestParam(value = "pageIndex", required = false)Integer pageIndex,
            @RequestParam(value = "pageSize", required = false)Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        AppUser user = new AppUser();
        if ( json != null && !"".equals( json ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            if ( StringUtils.isNotEmpty( jsonObject.get( "user_name" ) + "" ) ) {
                user.setUser_name( jsonObject.get( "user_name" ) + "" );
            }
        }
        if ( pageIndex != null && pageSize != null ) {
            user.setPageNo( pageIndex * pageSize );
            user.setPageSize( pageSize );
        }
        List<AppUser> userList = messageService.getAppUserList( user );
        Integer userTotal = messageService.getAppUserTotal( user );
        if ( userList != null && userList.size() > 0 ) {
            map.put( "data", userList );
            map.put( "total", userTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }
}
