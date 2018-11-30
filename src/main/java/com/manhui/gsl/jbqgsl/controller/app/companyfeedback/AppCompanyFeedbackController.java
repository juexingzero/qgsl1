package com.manhui.gsl.jbqgsl.controller.app.companyfeedback;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.CompanyFeedback;
import com.manhui.gsl.jbqgsl.service.web.ICompanyFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 AppCompanyFeedbackController.java
 * @类描述 企业之声
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月4日 上午16:12:21
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
@Api( tags = "企业之声" )
@Controller
@RequestMapping( AppCompanyFeedbackController.ROOT_URL )
public class AppCompanyFeedbackController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_APP + "/companyFeedback";
    @Resource
    private ICompanyFeedbackService   feedbackService;

    @ApiOperation( value = "获取企业之声反馈信息列表", notes = "获取企业之声反馈信息列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = false, name = "feedback_type", value = "问题类型（param_key：1、2、3...）", dataType = "字符串" )
    } )
    @RequestMapping( value = "getCompanyFeedbackList", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getCompanyFeedbackList(
            @RequestParam( value = "feedback_type", required = false ) String feedback_type ) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        if ( feedback_type != null && !"0".equals( feedback_type )) {
            conditionMap.put( "feedback_type", feedback_type );
        }
        return new JsonResult(feedbackService.getCompanyFeedbackListForApp( conditionMap ));
    }

    @ApiOperation( value = "获取反馈信息详情", notes = "获取反馈信息详情" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = true, name = "feedback_id", value = "反馈ID", dataType = "字符串" )
    } )
    @RequestMapping( value = "getCompanyFeedbackDetail", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult getCompanyFeedbackDetail(
            @RequestParam( value = "feedback_id", required = true ) String feedback_id) {
        return new JsonResult(feedbackService.getCompanyFeedbackDetailForApp( feedback_id ));
    }

    @ApiOperation( value = "保存反馈信息内容", notes = "保存反馈信息内容" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "feedback_type", value = "问题类型（param_key：1、2、3...）", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "company_name", value = "企业名称", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "company_linkman_name", value = "企业联系人姓名", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "company_linkman_phone", value = "企业联系人手机", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "feedback_man_id", value = "反馈人ID（用户ID）", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "feedback_man_name", value = "反馈人（用户姓名）", dataType = "字符串" ),
            @ApiImplicitParam( paramType = "query", required = true, name = "feedback_content", value = "反馈内容", dataType = "字符串" )
    } )
    @RequestMapping( value = "saveCompanyFeedback", method = RequestMethod.POST )
    @ResponseBody
    public JsonResult saveCompanyFeedback(
            @RequestParam( value = "feedback_type", required = true ) String feedback_type,
            @RequestParam( value = "company_name", required = true ) String company_name,
            @RequestParam( value = "company_linkman_name", required = true ) String company_linkman_name,
            @RequestParam( value = "company_linkman_phone", required = true ) String company_linkman_phone,
            @RequestParam( value = "feedback_man_id", required = true ) String feedback_man_id,
            @RequestParam( value = "feedback_man_name", required = true ) String feedback_man_name,
            @RequestParam( value = "feedback_content", required = true ) String feedback_content ) {
        CompanyFeedback feedback = new CompanyFeedback();
        feedback.setFeedback_type( feedback_type );
        feedback.setCompany_name( company_name );
        feedback.setCompany_linkman_name( company_linkman_name );
        feedback.setCompany_linkman_phone( company_linkman_phone );
        feedback.setFeedback_man_id( feedback_man_id );
        feedback.setFeedback_man_name( feedback_man_name );
        feedback.setFeedback_content( feedback_content );
        feedback.setIs_answer( "0" );
        return new JsonResult(feedbackService.saveCompanyFeedback( feedback ));
    }
}
