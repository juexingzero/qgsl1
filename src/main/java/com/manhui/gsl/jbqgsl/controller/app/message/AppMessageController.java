package com.manhui.gsl.jbqgsl.controller.app.message;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.service.app.IAppUserSuggestService;
import com.manhui.gsl.jbqgsl.service.web.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 AppMessageController.java
 * @类描述 组织机构及对应联系人
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月23日 下午8:21:35
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月22日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "消息通知" )
@Controller
@RequestMapping( AppMessageController.ROOT_URL )
public class AppMessageController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_APP + "/message";
    @Resource
    private IMessageService    messageService;
    @Resource
	private IAppUserSuggestService userSuggestService;

    @ApiOperation( value = "获取当前登录人的消息列表", notes = "获取当前登录人的消息列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "user_id", value = "用户ID", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "message_type", value = "消息类型(1：广播通知，2：工作任务，3：意见回复)", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "message_content", value = "消息内容", dataType = "字符串" )
    } )
    @RequestMapping( value = "getMessageList", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getMessageList(
            @RequestParam(value = "user_id", required = true)String user_id,
            @RequestParam(value = "message_type", required = false)String message_type,
            @RequestParam(value = "message_content", required = false)String message_content) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put( "receive_id", user_id );
        conditionMap.put( "message_type", message_type );
        conditionMap.put( "message_content", message_content );
        return new JsonResult( messageService.getMessageFlowingListForApp(conditionMap) );
    }
    
    @ApiOperation( value = "获取意见表详情", notes = "获取意见表详情" )
    @ApiImplicitParams( {
    	@ApiImplicitParam( paramType = "query", required = true, name = "userSuggest_id", value = "用户意见建议表标志", dataType = "字符串" )
    } )
    @RequestMapping( value = "getUserSuggestDetail", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getUserSuggestDetail(
    		@RequestParam(value = "userSuggest_id", required = true)String userSuggest_id) {
    	Map<String, Object> conditionMap = new HashMap<String, Object>();
    	conditionMap.put( "userSuggest_id", userSuggest_id );
    	return new JsonResult( userSuggestService.getUserSuggestDetail(conditionMap) );
    }


    @ApiOperation( value = "更新消息流水信息", notes = "更新消息流水信息" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "user_id", value = "用户ID", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "flowing_id", value = "消息流水ID", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "is_read", value = "是否已读(0：未读，1：已读，默认0)", dataType = "字符串" )
    } )
    @RequestMapping( value = "updateMessageFlowing", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult updateMessageFlowing(
            @RequestParam(value = "user_id", required = true)String user_id,
            @RequestParam(value = "flowing_id", required = true)String flowing_id,
            @RequestParam(value = "is_read", required = true)String is_read) {
        MessageFlowing mf = new MessageFlowing();
        mf.setReceive_id( user_id );
        mf.setFlowing_id( flowing_id );
        mf.setIs_read( is_read );
        mf.setRead_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
        return new JsonResult( messageService.updateMessageFlowingForApp(mf));
    }
}
