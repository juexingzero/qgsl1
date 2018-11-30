package com.manhui.gsl.jbqgsl.controller.web.evaluate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScoreDetailsUtil;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicPassiveInfo;
import com.manhui.gsl.jbqgsl.service.web.ITopicEvaluateResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebTopicEvaluateResultController.java
 * @类描述 主题评价结果
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月14日 下午7:31:35
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月14日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "后台-主题评价结果" )
@Controller
@RequestMapping( WebTopicEvaluateResultController.ROOT_URL )
public class WebTopicEvaluateResultController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/topicEvaluateResult";
    @Resource
    private ITopicEvaluateResultService terService;

    @ApiOperation( value = "进入主题评价结果列表页面", notes = "进入主题评价结果列表页面" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = true, name = "topic_type", value = "主题类型", dataType = "字符串" )
    } )
    @RequestMapping( value = "toTopicEvaluateResultListPage", method = RequestMethod.GET )
    public String toTopicEvaluateListPage(HttpServletRequest request, String topic_type) {
        request.setAttribute("topic_type", topic_type);
        return "/web/html/topicEvaluateResult/topicEvaluateResultList";
    }

    @ApiOperation( value = "获取机构列表", notes = "获取机构列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "json", value = "过滤条件", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageIndex", value = "当前页数", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageSize", value = "每页行数", dataType = "字符串" )
    } )
    @RequestMapping( value = "getTopicEvaluateResultList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getTopicEvaluateList( String json, Integer pageIndex, Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        TopicEvaluate topic = new TopicEvaluate();
        String user_type = "";
        String mobile_no = "";
        if ( json != null && !"".equals( json ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            if ( StringUtils.isNotEmpty( jsonObject.get( "topic_type" ) + "" ) ) {
                topic.setTopic_type( jsonObject.get( "topic_type" ).toString() );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "topic_name" ) + "" ) ) {
                topic.setTopic_name( jsonObject.get( "topic_name" ).toString() );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "evaluate_state" ) + "" ) ) {
                topic.setEvaluate_state( jsonObject.get( "evaluate_state" ).toString() );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "evaluate_start_time" ) + "" ) ) {
                topic.setEvaluate_start_time( jsonObject.get( "evaluate_start_time" ).toString() );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "evaluate_end_time" ) + "" ) ) {
                topic.setEvaluate_end_time( jsonObject.get( "evaluate_end_time" ).toString() );
            }
            user_type = jsonObject.get( "user_type" ) + "";
            mobile_no = jsonObject.get( "mobile_no" ) + "";
        }
        if ( pageIndex != null && pageSize != null ) {
            topic.setPageNo( pageIndex * pageSize );
            topic.setPageSize( pageSize );
        }
        List<TopicEvaluate> institutionList = terService.getTopicEvaluateResultList( topic, user_type, mobile_no );
        Integer institutionTotal = terService.getTopicEvaluateResultTotal( topic );
        if ( institutionList != null && institutionList.size() > 0 ) {
            map.put( "data", institutionList );
            map.put( "total", institutionTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    /**
     *查看被评价组织详情,页面展示
     * @return
     */
    @RequestMapping( value = "getTopicEvaluateOrgDetails", method = RequestMethod.GET )
    public String getTopicEvaluateOrgDetails(String topic_id, ModelMap model) {
        model.addAttribute("evaluate", terService.getTopicEvaluateOrgDetails(topic_id));
        return "/web/html/topicEvaluateResult/topicEvaluateOrgDetails";
    }

    /**
     * 查询被评价方 列表
     * @param topic_id
     * @return
     */
    @RequestMapping( value = "getTopicPassiveInfoList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getTopicPassiveInfoList(String topic_id, String user_type, String mobile_no, Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(topic_id)){
            map.put( "data", null );
            return map;
        }
        TopicPassiveInfo info = new TopicPassiveInfo();
        info.setTopic_id(topic_id);
        if ( pageIndex != null && pageSize != null ) {
            info.setPageNo( pageIndex * pageSize );
            info.setPageSize( pageSize );
        }
        List<TopicPassiveInfo> list = terService.getTopicPassiveInfoListTopic_id(info, user_type, mobile_no);
        Integer total = terService.getTopicPassiveInfoTotalByTopic_id(info);
        if(list == null ){
            list = new ArrayList<>();
        }
        map.put( "data", list );
        map.put( "total", total );
        return map;
    }

    /**
     *查询 参与了评价的单位列表页面显示
     * @return
     */
    @RequestMapping( value = "getTopicEvaluateDetailsList", method = RequestMethod.GET )
    public String getTopicEvaluateDetailsList(String topic_id, String institution_id, String page_type, ModelMap model) {
        model.addAttribute("passiveInfo", terService.getTopicPassiveInfoByTopicInstitutionId(topic_id, institution_id));
        model.addAttribute("page_type", page_type);
        return "/web/html/topicEvaluateResult/topicEvaluateDetailsList";
    }

    /**
     * 获得评价方 信息列表
     * @param topic_id 主题id
     * @return
     */
    @RequestMapping( value = "getEvaluateFlowingScoreList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getEvaluateFlowingScoreList(String topic_id, String passive_id, Integer pageIndex, Integer pageSize, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Integer pageNo=null;
        if ( pageIndex != null && pageSize != null ) {
            pageNo = pageIndex * pageSize;
        }
        String user_type = AppUtil.getCookieByName( request, "user_type" );
        List<Map<String, Object>> list = terService.getEvaluateFlowingScoreGroupList(topic_id, passive_id, pageNo, pageSize, user_type);
        Integer total = terService.getEvaluateFlowingScoreGroupTotal(topic_id, passive_id);
        if(list == null ){
            list = new ArrayList<>();
        }
        map.put( "data", list );
        map.put( "total", total );
        return map;
    }

    /**
     * 获得评价详情分数
     * @param score
     * @return
     */
    @RequestMapping( value = "getEvaluateFlowingScoreDetails", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getEvaluateFlowingScoreDetails(EvaluateFlowingScore score) {
        Map<String, Object> map = new HashMap<>();
        List<EvaluateFlowingScoreDetailsUtil> list = terService.getEvaluateFlowingScoreDetails(score);
        if(list == null ){
            list = new ArrayList<>();
        }
        map.put( "data", list );
        return map;
    }

    /**
     * 进入展示标准打分详情
     *
     * @return
     */
    @RequestMapping( value = "toStandardScoreDetail", method = RequestMethod.GET )
    public String toStandardScoreDetail(String topic_id, String passive_id, String topic_standard_id, String actice_id, String actice_name, String real_score_sum, ModelMap model) {
        model.addAttribute("actice_name", actice_name);
        model.addAttribute("real_score_sum", real_score_sum);
        model.addAttribute("suggest", terService.getEvaluateFlowingSuggest(topic_id, topic_standard_id, passive_id, actice_id));
        return "/web/html/topicEvaluateResult/standardScoreDetail";
    }

    /**
     * 获取标准打分详情
     *
     * @return
     */
    @RequestMapping( value = "getStandradScoreDetail", method = RequestMethod.POST )
    @ResponseBody
    public List<EvaluateFlowingScore> getStandradScoreDetail(String topic_id, String topic_standard_id, String passive_id, String actice_id) {
        return terService.getStandradScoreDetail(topic_id, topic_standard_id, passive_id, actice_id);
    }

    /**
     * 导出主题评价结果
     * @return
     */
    @RequestMapping( value = "exportTopicEvaluateResult", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> exportTopicEvaluateResult(HttpServletResponse response, String topic_ids, String user_type, String mobile_no) {
        Map<String, Object> map = new HashMap<>();
        String exportResult = terService.exportTopicEvaluateResult(response, topic_ids, user_type,mobile_no);
        if(!"".equals( exportResult )){
            map.put( "code", 200 );
            map.put( "data", exportResult );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "导出有误，请重新尝试" );
        }
        return map;
    }
}
