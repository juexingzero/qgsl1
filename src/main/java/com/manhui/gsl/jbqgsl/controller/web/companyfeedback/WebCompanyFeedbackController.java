package com.manhui.gsl.jbqgsl.controller.web.companyfeedback;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.manhui.gsl.jbqgsl.common.JpushClientUtil;
import com.manhui.gsl.jbqgsl.common.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.CompanyFeedback;
import com.manhui.gsl.jbqgsl.service.web.ICompanyFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebCompanyFeedbackController.java
 * @类描述 企业之声
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月4日 上午9:18:24
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月4日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "后台-企业之声" )
@Controller
@RequestMapping( WebCompanyFeedbackController.ROOT_URL )
public class WebCompanyFeedbackController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_WEB + "/companyFeedback";
    @Resource
    private ICompanyFeedbackService   feedbackService;
    private static String jpush = "Jpush_";

    @ApiOperation( value = "进入企业之声列表页面", notes = "进入企业之声列表页面" )
    @RequestMapping( value = "toFeedbackListPage", method = RequestMethod.GET )
    public String toFeedbackListPage() {
        return "/web/html/companyFeedback/feedbackList";
    }

    @ApiOperation( value = "进入反馈详情页面", notes = "进入反馈详情页面" )
    @RequestMapping( value = "toFeedbackDetailPage", method = RequestMethod.GET )
    public String toFeedbackDetailPage(HttpServletRequest request, String feedback_id,boolean flag) {
        request.setAttribute("feedback", feedbackService.getCompanyFeedbackDetail( feedback_id ));
        request.setAttribute("flag",flag);
        return "/web/html/companyFeedback/feedbackDetail";
    }

    @ApiOperation( value = "获取企业之声反馈列表", notes = "获取企业之声反馈列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "json", value = "参数json(company_name、feedback_type、is_answer、feedback_start_time、feedback_start_time)", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageIndex", value = "当前页数", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "pageSize", value = "每页行数", dataType = "字符串" )
    } )
    @RequestMapping( value = "getCompanyFeedbackList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getCompanyFeedbackList(
            @RequestParam( value = "json", required = false ) String json,
            @RequestParam( value = "pageIndex", required = false ) String pageIndex,
            @RequestParam( value = "pageSize", required = false ) String pageSize ) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        if ( json != null && !"".equals( json ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            if ( StringUtils.isNotEmpty( jsonObject.get( "company_name" ) + "" ) ) {
                conditionMap.put( "company_name", jsonObject.get( "company_name" ) + "" );
            }
            String feedback_type = jsonObject.get( "feedback_type" ) + "";
            if ( StringUtils.isNotEmpty( feedback_type ) && !"0".equals( feedback_type )) {
                conditionMap.put( "feedback_type", jsonObject.get( "feedback_type" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "is_answer" ) + "" ) ) {
                conditionMap.put( "is_answer", jsonObject.get( "is_answer" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "feedback_start_time" ) + "" ) ) {
                conditionMap.put( "feedback_start_time", jsonObject.get( "feedback_start_time" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "feedback_end_time" ) + "" ) ) {
                conditionMap.put( "feedback_end_time", jsonObject.get( "feedback_end_time" ) + "" );
            }
        }
        conditionMap.put( "pageIndex", pageIndex );
        conditionMap.put( "pageSize", pageSize );
        PageInfo<CompanyFeedback> pageInfo = feedbackService.getCompanyFeedbackList( conditionMap );
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put( "data", pageInfo.getList() );
        resultMap.put( "total", pageInfo.getTotal() );
        return resultMap;
    }

    @ApiOperation( value = "保存反馈回复内容", notes = "保存反馈回复内容" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "feedback_id", value = "反馈ID", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "is_answer", value = "是否已回复(0：未回复，1：已回复，2：已发布，默认0)", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = false, name = "answer_content", value = "回复内容", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveCompanyFeedback", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult saveCompanyFeedback(HttpServletRequest request, 
            @RequestParam( value = "feedback_id", required = true ) String feedback_id,
            @RequestParam( value = "answer_content", required = false ) String answer_content ) {
        CompanyFeedback feedback = new CompanyFeedback();
        feedback.setFeedback_id( feedback_id );
        feedback.setAnswer_man_id( AppUtil.getCookieByName( request, "user_id" ) );
        feedback.setAnswer_man_name( AppUtil.getCookieByName( request, "user_name" ) );
        feedback.setAnswer_content( answer_content );
        return new JsonResult(feedbackService.saveCompanyFeedback( feedback ));
    }

    /**
     * 修改状态为查看状态
     * @param request
     * @param feedback_id
     * @return
     */
    @RequestMapping( value = "queryCompanyFeedback", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult queryCompanyFeedback(HttpServletRequest request,
                                          @RequestParam( value = "feedback_id", required = true ) String feedback_id) {
        CompanyFeedback feedback = new CompanyFeedback();
        feedback.setFeedback_id( feedback_id );
        feedback.setIs_answer("1");
        return new JsonResult(feedbackService.saveCompanyFeedback( feedback ));
    }
    /**
     * 发布 回复内容
     * @param request
     * @param feedback_id
     * @return
     */
    @RequestMapping( value = "releaseCompanyFeedback", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult releaseCompanyFeedback(HttpServletRequest request,
                                          @RequestParam( value = "feedback_id", required = true ) String feedback_id) {
        CompanyFeedback feedback = new CompanyFeedback();
        feedback.setFeedback_id( feedback_id );
        feedback.setIs_answer( "2" );
        feedback.setAnswer_man_id( AppUtil.getCookieByName( request, "user_id" ) );
        feedback.setAnswer_man_name( AppUtil.getCookieByName( request, "user_name" ) );
        //判断是否有填写回复内容，没有填写将不能发布
        CompanyFeedback entity = feedbackService.getCompanyFeedbackDetail(feedback_id);
        if(entity == null){
            return new JsonResult(new Throwable("数据不存在请刷新重试!"));
        }
        if(StringUtils.isBlank(entity.getAnswer_content())){
            //未填写回复内容
            return new JsonResult(new Throwable("回复内容不能为空!"));
        }

        JsonResult result = new JsonResult(feedbackService.saveCompanyFeedback( feedback ));
        if(result.getState() == 1){
            //发送消息和短信
            String alias[] = new String[]{jpush+entity.getFeedback_man_id()};
            String content = "你发起的企业之声资讯问题已回复："+entity.getAnswer_content();
            JpushClientUtil.pushMsg(alias,"江北区工商联",content);

            SMSUtil sms = new SMSUtil();
            try {
                sms.sendSms(entity.getCompany_linkman_phone(),content,new DateTime().toString("yyyy-MM-dd HH:mm"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @ApiOperation( value = "删除反馈信息", notes = "删除反馈信息" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = true, name = "feedback_id", value = "反馈ID）", dataType = "字符串" )
    } )
    @RequestMapping( value = "deleteCompanyFeedback", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult deleteCompanyFeedback(
            @RequestParam( value = "feedback_id", required = true ) String feedback_id) {
        return new JsonResult(feedbackService.deleteCompanyFeedback( feedback_id ));
    }
}
