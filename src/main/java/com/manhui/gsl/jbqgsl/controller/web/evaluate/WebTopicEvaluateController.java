package com.manhui.gsl.jbqgsl.controller.web.evaluate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.TopicActiveInfo;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;
import com.manhui.gsl.jbqgsl.model.TopicPassiveInfo;
import com.manhui.gsl.jbqgsl.service.web.ITopicEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebTopicEvaluateController.java
 * @类描述 主题评价管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月7日 下午2:31:35
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月7日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "后台-主题评价管理" )
@Controller
@RequestMapping( WebTopicEvaluateController.ROOT_URL )
public class WebTopicEvaluateController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/topicEvaluate";
    @Resource
    private ITopicEvaluateService topicEvaluateService;

    @ApiOperation( value = "进入主题评价列表页面", notes = "进入主题评价列表页面" )
    @RequestMapping( value = "toTopicEvaluateListPage", method = RequestMethod.GET )
    public String toTopicEvaluateListPage() {
        return "/web/html/topicEvaluate/topicEvaluateList";
    }

    @ApiOperation( value = "进入发起评价页面", notes = "进入发起评价页面" )
    @RequestMapping( value = "toInitiateEvaluatePage", method = RequestMethod.GET )
    public String toInitiateEvaluatePage() {
        return "/web/html/topicEvaluate/initiateEvaluate";
    }

    @ApiOperation( value = "进入机构选择页面", notes = "进入机构选择页面" )
    @RequestMapping( value = "/toInstitutionSelectPage", method = RequestMethod.GET )
    public String toInstitutionPassivePage() {
        return "/web/html/topicEvaluate/institutionSelect";
    }

    @ApiOperation( value = "进入主题评价详情页面", notes = "进入主题评价详情页面" )
    @RequestMapping( value = "/toTopicEvaluateDetailPage", method = RequestMethod.GET )
    public String toTopicEvaluateDetailPage(HttpServletRequest request, String topic_id, String operation_flag) {
        request.setAttribute("topic_id", topic_id);
        request.setAttribute("operation_flag", operation_flag);
        return "/web/html/topicEvaluate/initiateEvaluate";
    }

    @ApiOperation( value = "进入标准详情页面", notes = "进入标准详情页面" )
    @RequestMapping( value = "/toStandardDetail", method = RequestMethod.GET )
    public String toStandardDetail(HttpServletRequest request, String standard_id) {
        request.setAttribute("standard_id", standard_id);
        return "/web/html/topicEvaluate/standardDetail";
    }

    @ApiOperation( value = "进入主题评价编辑页面", notes = "进入主题评价编辑页面" )
    @RequestMapping( value = "/toTopicEvaluateEditPage", method = RequestMethod.GET )
    public String toTopicEvaluateEditPage(HttpServletRequest request, String topic_id, String operation_flag) {
        request.setAttribute("topic_id", topic_id);
        request.setAttribute("operation_flag", operation_flag);
        return "/web/html/topicEvaluate/initiateEvaluate";
    }

    @ApiOperation( value = "获取机构列表", notes = "获取机构列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "json", value = "过滤条件", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageIndex", value = "当前页数", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageSize", value = "每页行数", dataType = "字符串" )
    } )
    @RequestMapping( value = "getTopicEvaluateList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getTopicEvaluateList( String json, Integer pageIndex, Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        TopicEvaluate topic = new TopicEvaluate();
        String user_type = "";
        String mobile_no = "";
        if ( json != null && !"".equals( json ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            if ( StringUtils.isNotEmpty( jsonObject.get( "topic_name" ) + "" ) ) {
                topic.setTopic_name( jsonObject.get( "topic_name" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "evaluate_state" ) + "" ) ) {
                topic.setEvaluate_state( jsonObject.get( "evaluate_state" ).toString() );
            }
            user_type = jsonObject.get( "user_type" ) + "";
            mobile_no = jsonObject.get( "mobile_no" ) + "";
        }
        if ( pageIndex != null && pageSize != null ) {
            topic.setPageNo( pageIndex * pageSize );
            topic.setPageSize( pageSize );
        }
        List<TopicEvaluate> institutionList = topicEvaluateService.getTopicEvaluateList( topic, user_type, mobile_no );
        Integer institutionTotal = topicEvaluateService.getTopicEvaluateTotal( topic );
        if ( institutionList != null && institutionList.size() > 0 ) {
            map.put( "data", institutionList );
            map.put( "total", institutionTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    @ApiOperation( value = "获取被评价方列表", notes = "获取被评价方列表" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = false, name = "institution_id", value = "机构ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "getPassiveListForCheck", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getPassiveListForCheck(String institution_id, String topic_type) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> psssiveList = topicEvaluateService.getPassiveListForCheck(institution_id, topic_type);
        if ( psssiveList != null && psssiveList.size() > 0 ) {
            map.put( "data", psssiveList );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }
    
    @ApiOperation( value = "保存发起主题评价内容", notes = "保存发起主题评价内容" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "json", value = "主题评价内容", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveTopicEvaluate", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> saveTopicEvaluate(String topic_json, String standard_json, String passive_json, String active_json ) {
        Map<String, Object> map = new HashMap<>();
        TopicEvaluate topic = JSON.parseObject( topic_json ).toJavaObject( TopicEvaluate.class );
        TopicEvaluateStandard standard = JSON.parseObject( standard_json ).toJavaObject( TopicEvaluateStandard.class );
        List<TopicPassiveInfo> passiveList = JSONObject.parseArray( passive_json, TopicPassiveInfo.class );
        List<TopicActiveInfo> activeList = JSONObject.parseArray( active_json, TopicActiveInfo.class );
        Integer flag = topicEvaluateService.saveTopicEvaluate( topic, standard, passiveList, activeList );
        if ( flag > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }

    @ApiOperation( value = "更新主题评价信息", notes = "更新主题评价信息" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "topic_id", value = "主题ID", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "evaluate_state", value = "评价状态(0：待发布，1：未开始，2：评价中，3：已结束，4：已撤销，默认0)", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "del_flag", value = "删除标识(0：未删除，1：已删除，默认0)", dataType = "字符串" )
    } )
    @RequestMapping( value = "updateTopicEvaluate", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> updateTopicEvaluate(
            @RequestParam(value = "topic_id", required = false)String topic_id, 
            @RequestParam(value = "evaluate_state", required = false)String evaluate_state, 
            @RequestParam(value = "del_flag", required = false)String del_flag ) {
        Map<String, Object> map = new HashMap<>();
        TopicEvaluate topic = new TopicEvaluate();
        topic.setTopic_id( topic_id );
        topic.setEvaluate_state( evaluate_state );
        topic.setDel_flag( del_flag );
        Integer flag = topicEvaluateService.updateTopicEvaluate( topic );
        if ( flag > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "更新有误，请重新尝试" );
        }
        return map;
    }

    @ApiOperation( value = "获取主题评价详情数据", notes = "获取主题评价详情数据" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "topic_id", value = "主题ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "getTopicEvaluateDetailData", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getTopicEvaluateDetailData(
            @RequestParam(value = "topic_id", required = true)String topic_id ) {
        Map<String, Object> resultMap = topicEvaluateService.getTopicEvaluateDetailData( topic_id );
        return new JsonResult(resultMap);
    }

    @ApiOperation( value = "定时任务：检测并更新评价状态", notes = "定时任务：检测并更新评价状态" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "currentDate", value = "当前时间（格式：yyyy-MM-dd HH:mm）", dataType = "字符串" )
    } )
    @RequestMapping( value = "checkAndUpdateEvaluateState", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult checkAndUpdateEvaluateState(@RequestBody Map<String,Object> params ) {
        String currentDate = params.get("currentDate").toString();
        Integer result = topicEvaluateService.checkAndUpdateEvaluateState( currentDate );
        return new JsonResult(result);
    }
}
