package com.manhui.gsl.jbqgsl.controller.web.membermanager;

import java.util.ArrayList;
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
import com.manhui.gsl.jbqgsl.common.util.JacksonUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.commerce.Commerce;
import com.manhui.gsl.jbqgsl.service.app.commerce.IAppCommerceService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyRyjbxxService;
import com.manhui.gsl.jbqgsl.service.web.membermanager.IMemberManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebMemberManagerController.java
 * @类描述 会员管理
 * @作者  kevin kwmo1005@163.com
 * @创建时间 2018年11月5日 下午6:14:18
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年11月5日               创建
 *     ----------------------------------------------
 * </pre>
 */
@Api(tags = "后台-会员管理")
@Controller
@RequestMapping(WebMemberManagerController.ROOT_URL)
public class WebMemberManagerController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_WEB + "/memberManager";
    private Object G_ZJZL = null;
    private Object G_ZYRY = null;
    private Object Q_ZJZL = null;
    private Object Q_SJQK = null;
    private Object Q_QYJS = null;
	@Resource
	private IMemberManagerService memberManagerService;
    @Resource
    private IAppCommerceService   commerceService;
    @Resource
    private AppMemberRyRyjbxxService appMemberRyRyjbxxService;
    @Resource
    private AppMemberQyQyjbxxService appMemberQyQyjbxxService;
    
    @ApiOperation(value = "进入会员管理页面", notes = "进入会员管理页面")
    @ApiImplicitParams( {
            @ApiImplicitParam( required = true, paramType = "query", name = "menu_type", value = "菜单类型（1：会员档案，2：会员审批）", dataType = "字符串" )
    } )
    @RequestMapping(value = "/toMemberListPage", method = RequestMethod.GET)
    public String toMemberListPage(
            @RequestParam( required = true, value = "menu_type" ) String menu_type,
            HttpServletRequest request) {
        request.setAttribute("menu_type", menu_type);
        return "/web/html/memberManager/memberList";
    }
    
    @ApiOperation(value = "获取商会列表", notes = "获取商会列表")
    @RequestMapping(value = "getCommerceList", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getCommerceList() {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> mapDefault = new HashMap<String, Object>();
        mapDefault.put( "commerce_id", "0" );
        mapDefault.put( "commerce_name", "--请选择--" );
        resultList.add( mapDefault );
        List<Commerce> commerceList = commerceService.queryCommerceList();
        if(commerceList != null && commerceList.size() > 0) {
            for(Commerce commerce : commerceList) {
                List<Commerce> childList = commerce.getZChildCommerce();
                if(childList != null && childList.size() > 0) {
                    for(Commerce child : childList) {
                        Map<String, Object> mapNew = new HashMap<String, Object>();
                        mapNew.put( "commerce_id", child.getID() );
                        mapNew.put( "commerce_name", child.getSHMC() );
                        resultList.add( mapNew );
                    }
                }
            }
        }
        return resultList;
    }
    
    @ApiOperation(value = "获取会员信息列表", notes = "获取会员信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam( required = false, paramType = "query", name = "json", value = "参数json", dataType = "字符串" ),
            @ApiImplicitParam( required = false, paramType = "query", name = "pageIndex", value = "当前页数", dataType = "字符串" ),
            @ApiImplicitParam( required = false, paramType = "query", name = "pageSize", value = "每页行数", dataType = "字符串" )})
    @RequestMapping(value = "getMemberList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMemberList(
            @RequestParam( required = false, value = "json" ) String json,
            @RequestParam( required = false, value = "pageIndex") String pageIndex,
            @RequestParam( required = false, value = "pageSize" ) String pageSize) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        if ( json != null && !"".equals( json ) ) {
            JSONObject jsonObject = JSON.parseObject( json );
            String commerce_name = jsonObject.get( "commerce_name" ) + "";
            if ( StringUtils.isNotEmpty(commerce_name) && !"0".equals( commerce_name )) {
                conditionMap.put( "commerce_name", jsonObject.get( "commerce_name" ) + "" );
            }
            String member_type = jsonObject.get( "member_type" ) + "";
            if ( StringUtils.isNotEmpty(member_type) && !"0".equals( member_type )) {
                conditionMap.put( "member_type", jsonObject.get( "member_type" ) + "" );
            }
            String approve_status = jsonObject.get( "approve_status" ) + "";
            if ( StringUtils.isNotEmpty( approve_status ) && !"0".equals( approve_status ) ) {
                conditionMap.put( "approve_status", jsonObject.get( "approve_status" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "member_name" ) + "" ) ) {
                conditionMap.put( "member_name", jsonObject.get( "member_name" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "linkman_name" ) + "" ) ) {
                conditionMap.put( "linkman_name", jsonObject.get( "linkman_name" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "linkman_phone" ) + "" ) ) {
                conditionMap.put( "linkman_phone", jsonObject.get( "linkman_phone" ) + "" );
            }
            if ( StringUtils.isNotEmpty( jsonObject.get( "menu_type" ) + "" ) ) {
                conditionMap.put( "menu_type", jsonObject.get( "menu_type" ) + "" );
            }
        }
        conditionMap.put( "pageIndex", pageIndex );
        conditionMap.put( "pageSize", pageSize );
        return memberManagerService.getMemberList( conditionMap );
    }
    
    @ApiOperation(value = "进入会员管理详情页面", notes = "进入会员管理详情页面")
    @ApiImplicitParams({
            @ApiImplicitParam( required = true, paramType = "query", name = "joinId", value = "会员商会中间表主键标志", dataType = "字符串"),
            @ApiImplicitParam( required = true, paramType = "query", name = "app_user_id", value = "入会创建人ID", dataType = "字符串"),
            @ApiImplicitParam( required = true, paramType = "query", name = "app_user_name", value = "入会创建人姓名", dataType = "字符串"),
            @ApiImplicitParam( required = false, paramType = "query", name = "member_id", value = "会员ID", dataType = "字符串"),
            @ApiImplicitParam( required = false, paramType = "query", name = "member_type", value = "会员类型（HYLX-01：个人，HYLX-02：团体，HYLX-03：企业）", dataType = "字符串"),
            @ApiImplicitParam( required = false, paramType = "query", name = "approve_status", value = "审批状态（HYSP-01：待审批，HYSP-02：审批通过，HYSP-03：审批拒绝）", dataType = "字符串"),
            @ApiImplicitParam( required = false, paramType = "query", name = "approve_fail_info", value = "审批未通过资料类别（参考：“会员审批不通过类别”）", dataType = "字符串"),
            @ApiImplicitParam( required = false, paramType = "query", name = "approve_fail_reason", value = "审批拒绝原因", dataType = "字符串"),
            @ApiImplicitParam( required = true, paramType = "query", name = "operator_type", value = "操作类型（add：新增，edit：编辑，show：查看，approve：审批）", dataType = "字符串") })
    @RequestMapping(value = "toMemberDetailPage", method = RequestMethod.GET)
    public String toManagerDetailPage(
            @RequestParam( required = true, value = "joinId") String joinId,
            @RequestParam( required = true, value = "app_user_id") String app_user_id,
            @RequestParam( required = true, value = "app_user_name") String app_user_name,
            @RequestParam( required = false, value = "member_id") String member_id,
            @RequestParam( required = false, value = "member_type") String member_type,
            @RequestParam( required = false, value = "approve_status") String approve_status,
            @RequestParam( required = false, value = "approve_fail_info") String approve_fail_info,
            @RequestParam( required = false, value = "approve_fail_reason") String approve_fail_reason,
            @RequestParam( required = true, value = "operator_type") String operator_type, 
            HttpServletRequest request) {
        Map<String, Object> dataMap = null;
        request.setAttribute("joinId", joinId);
        request.setAttribute("app_user_id", app_user_id);
        request.setAttribute("app_user_name", app_user_name);
        request.setAttribute("member_id", member_id);
        request.setAttribute("member_type", member_type);
        request.setAttribute("approve_status", approve_status);
        request.setAttribute("approve_fail_info", approve_fail_info);
        request.setAttribute("approve_fail_reason", approve_fail_reason);
        request.setAttribute("operator_type", operator_type);
        request.setAttribute("requestUrl", String.valueOf( request.getRequestURL() ).split( "web" )[0]);
        //个人
        if ("HYLX-01".equals(member_type)) {
            dataMap = appMemberRyRyjbxxService.queryIndividualMemberData(joinId);
            G_ZJZL = dataMap.get( "zj" );
            G_ZYRY = dataMap.get( "ry" );
            request.setAttribute("dataMap", dataMap);
            return "/web/html/memberManager/memberPersonDetail";
        }
        //团体
        else if ("HYLX-02".equals(member_type)) {
            return "/web/html/memberManager/memberGroupDetail";
        }
        //企业
        else if ("HYLX-03".equals(member_type)) {
            dataMap = appMemberQyQyjbxxService.queryCompanyMemberData(joinId);
            Q_ZJZL = dataMap.get( "qyzj" );
            Q_SJQK = dataMap.get( "qyry" );
            Q_QYJS = dataMap.get( "qyjs" );
            String json = JacksonUtil.toJson( dataMap );
            request.setAttribute("dataMap", dataMap);
            return "/web/html/memberManager/memberCompanyDetail";
        }
        return "";
    }

    @ApiOperation( value = "获取商会职务内容", notes = "获取商会职务内容" )
    @ApiImplicitParams( {
            @ApiImplicitParam( required = true, paramType = "query", name = "app_user_id", value = "入会创建人ID", dataType = "字符串"),
            @ApiImplicitParam( required = true, paramType = "query", name = "app_user_name", value = "入会创建人姓名", dataType = "字符串"),
            @ApiImplicitParam( required = true, paramType = "query", name = "member_type", value = "会员类型（HYLX-01：个人，HYLX-02：团体，HYLX-03：企业）", dataType = "字符串")
    } )
    @RequestMapping( value = "getCommerceForMemberList", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> getCommerceForMemberList(
            @RequestParam( required = true, value = "app_user_id") String app_user_id,
            @RequestParam( required = true, value = "app_user_name") String app_user_name,
            @RequestParam( required = true, value = "member_type") String member_type ) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put( "app_user_id", app_user_id );
        conditionMap.put( "app_user_name", app_user_name );
        Map<String, Object> shzwMap = memberManagerService.getCommerceForMemberList( conditionMap );
        resultMap.put( "shzw", shzwMap );
        //个人
        if ("HYLX-01".equals(member_type)) {
            resultMap.put( "zjzl", G_ZJZL );
            resultMap.put( "zyry", G_ZYRY );
        }
        //团体
        else if ("HYLX-02".equals(member_type)) {
        }
        //企业
        else if ("HYLX-03".equals(member_type)) {
            resultMap.put( "zjzl", Q_ZJZL );
            resultMap.put( "sjqk", Q_SJQK );
            resultMap.put( "qyjs", Q_QYJS );
        }
        return resultMap;
    }

    @ApiOperation( value = "更新会员信息", notes = "更新会员信息" )
    @ApiImplicitParams( {
            @ApiImplicitParam( required = true, paramType = "query", name = "joinId", value = "会员商会中间表主键标志", dataType = "字符串"),
            @ApiImplicitParam( required = false, paramType = "query", name = "state", value = "会员状态标识(HYSP-04：删除)", dataType = "字符串" )
    } )
    @RequestMapping( value = "updateMember", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> updateMember(
            @RequestParam( required = true, value = "joinId")String joinId, 
            @RequestParam( required = false, value = "state")String state ) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put( "joinId", joinId );
        conditionMap.put( "approve_status", state );
        Integer flag = memberManagerService.updateMember( conditionMap );
        if ( flag > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "更新有误，请重新尝试" );
        }
        return map;
    }

    @ApiOperation(value = "进入会员审批页面", notes = "进入会员审批页面")
    @ApiImplicitParams({
        @ApiImplicitParam( required = true, paramType = "query", name = "joinId", value = "会员商会中间表主键标志", dataType = "字符串"),
        @ApiImplicitParam( required = true, paramType = "query", name = "app_user_id", value = "创建人ID", dataType = "字符串" ),
        @ApiImplicitParam( required = true, paramType = "query", name = "app_user_name", value = "创建人姓名", dataType = "字符串" ),
        @ApiImplicitParam( required = true, paramType = "query", name = "member_id", value = "会员ID", dataType = "字符串"),
        @ApiImplicitParam( required = true, paramType = "query", name = "member_type", value = "会员类型（1：个人，2：企业，3：团体）", dataType = "字符串"),
        @ApiImplicitParam( required = true, paramType = "query", name = "approve_fail_info", value = "审批不合格资料信息", dataType = "字符串") })
    @RequestMapping(value = "/toMemberApprovePage", method = RequestMethod.GET)
    public String toMemberApprovePage(
            @RequestParam( required = true, value = "joinId") String joinId,
            @RequestParam( required = true, value = "app_user_id")String app_user_id,
            @RequestParam( required = true, value = "app_user_name")String app_user_name,
            @RequestParam( required = true, value = "member_id") String member_id,
            @RequestParam( required = true, value = "member_type") String member_type,
            @RequestParam( required = true, value = "approve_fail_info") String approve_fail_info, 
            HttpServletRequest request) {
        request.setAttribute("joinId", joinId);
        request.setAttribute("app_user_id", app_user_id);
        request.setAttribute("app_user_name", app_user_name);
        request.setAttribute("member_id", member_id);
        request.setAttribute("member_type", member_type);
        request.setAttribute("approve_fail_info", approve_fail_info);
        return "/web/html/memberManager/memberApprove";
    }

    @ApiOperation( value = "审批会员信息", notes = "审批会员信息" )
    @ApiImplicitParams( {
            @ApiImplicitParam( required = true, paramType = "query", name = "joinId", value = "会员商会中间表主键标志", dataType = "字符串"),
            @ApiImplicitParam( required = true, paramType = "query", name = "app_user_id", value = "创建人ID", dataType = "字符串" ),
            @ApiImplicitParam( required = true, paramType = "query", name = "app_user_name", value = "创建人姓名", dataType = "字符串" ),
            @ApiImplicitParam( required = true, paramType = "query", name = "member_id", value = "会员ID", dataType = "字符串"),
            @ApiImplicitParam( required = true, paramType = "query", name = "member_type", value = "会员类型（HYLX-01：个人，HYLX-02：团体，HYLX-03：企业）", dataType = "字符串"),
            @ApiImplicitParam( required = true, paramType = "query", name = "approve_status", value = "审批状态（HYSP-01：待审批，HYSP-02：审批通过，HYSP-03：审批拒绝）", dataType = "字符串"),
            @ApiImplicitParam( required = false, paramType = "query", name = "approve_fail_info", value = "审批未通过资料类别(参考：“会员审批不通过类别”)", dataType = "字符串" ),
            @ApiImplicitParam( required = false, paramType = "query", name = "approve_fail_reason", value = "审批未通过原因", dataType = "字符串" )
    } )
    @RequestMapping( value = "approveMember", method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> approveMember(
            @RequestParam( required = true, value = "joinId")String joinId,
            @RequestParam( required = true, value = "app_user_id")String app_user_id,
            @RequestParam( required = true, value = "app_user_name")String app_user_name,
            @RequestParam( required = true, value = "member_id")String member_id, 
            @RequestParam( required = true, value = "member_type")String member_type, 
            @RequestParam( required = true, value = "approve_status")String approve_status, 
            @RequestParam( required = false, value = "approve_fail_info")String approve_fail_info, 
            @RequestParam( required = false, value = "approve_fail_reason")String approve_fail_reason,
            HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put( "joinId", joinId );
        conditionMap.put( "app_user_id", app_user_id );
        conditionMap.put( "app_user_name", app_user_name );
        conditionMap.put( "approver_id", AppUtil.getCookieByName( request, "user_id" ) );
        conditionMap.put( "approver_name", AppUtil.getCookieByName( request, "user_name" ) );
        conditionMap.put( "member_id", member_id );
        conditionMap.put( "member_type", member_type );
        conditionMap.put( "approve_status", approve_status );
        conditionMap.put( "approve_fail_info", approve_fail_info );
        conditionMap.put( "approve_fail_reason", approve_fail_reason );
        Integer flag = memberManagerService.approveMember( conditionMap );
        if ( flag > 0 ) {
            resultMap.put( "code", 200 );
        }
        else {
            resultMap.put( "code", 401 );
            resultMap.put( "msg", "更新有误，请重新尝试" );
        }
        return resultMap;
    }

    @ApiOperation(value = "进入荣誉详情页面", notes = "进入荣誉详情页面")
    @ApiImplicitParams({
        @ApiImplicitParam( required = true, paramType = "query", name = "id", value = "ID", dataType = "字符串"),
        @ApiImplicitParam( required = false, paramType = "query", name = "rymc", value = "授奖名称", dataType = "字符串"),
        @ApiImplicitParam( required = false, paramType = "query", name = "zyrylb", value = "荣誉类别", dataType = "字符串"),
        @ApiImplicitParam( required = false, paramType = "query", name = "ryjb", value = "荣誉级别", dataType = "字符串"),
        @ApiImplicitParam( required = false, paramType = "query", name = "je", value = "届别", dataType = "字符串"),
        @ApiImplicitParam( required = false, paramType = "query", name = "hqsj", value = "获取日期", dataType = "字符串"),
        @ApiImplicitParam( required = false, paramType = "query", name = "ryzsbh", value = "荣誉证书编号", dataType = "字符串"),
        @ApiImplicitParam( required = false, paramType = "query", name = "bfdw", value = "颁发单位", dataType = "字符串"),
        @ApiImplicitParam( required = false, paramType = "query", name = "hdyy", value = "获得原因", dataType = "字符串"),
        @ApiImplicitParam( required = true, paramType = "query", name = "pageType", value = "页面类型（person：个人，group：团体，company：企业）", dataType = "字符串")
    } )
    @RequestMapping(value = "/toMemberHonorDetailPage", method = RequestMethod.GET)
    public String toMemberHonorDetailPage(
            @RequestParam( required = true, value = "id") String id,
            @RequestParam( required = false, value = "rymc") String rymc,
            @RequestParam( required = false, value = "zyrylb") String zyrylb,
            @RequestParam( required = false, value = "ryjb") String ryjb,
            @RequestParam( required = false, value = "je") String je,
            @RequestParam( required = false, value = "hqsj") String hqsj,
            @RequestParam( required = false, value = "ryzsbh") String ryzsbh,
            @RequestParam( required = false, value = "bfdw") String bfdw,
            @RequestParam( required = false, value = "hdyy") String hdyy,
            @RequestParam( required = true, value = "pageType") String pageType,
            HttpServletRequest request) {
        request.setAttribute("id", id);
        request.setAttribute("rymc", rymc);
        request.setAttribute("zyrylb", zyrylb);
        request.setAttribute("ryjb", ryjb);
        request.setAttribute("je", je);
        request.setAttribute("hqsj", hqsj);
        request.setAttribute("ryzsbh", ryzsbh);
        request.setAttribute("bfdw", bfdw);
        request.setAttribute("hdyy", hdyy);
        request.setAttribute("hdyy", hdyy);
        request.setAttribute("pageType", pageType);
        return "/web/html/memberManager/memberHonorDetail";
    }

    @ApiOperation(value = "进入介绍详情页面", notes = "进入介绍详情页面")
    @ApiImplicitParams({
        @ApiImplicitParam( required = true, paramType = "query", name = "id", value = "ID", dataType = "字符串"),
        @ApiImplicitParam( required = true, paramType = "query", name = "fieldName", value = "授奖名称", dataType = "字符串"),
        @ApiImplicitParam( required = true, paramType = "query", name = "fieldContent", value = "荣誉类别", dataType = "字符串"),
        @ApiImplicitParam( required = true, paramType = "query", name = "operator_type", value = "操作类型（add：新增，edit：编辑，show：查看）", dataType = "字符串")
    } )
    @RequestMapping(value = "/toMemberIntroduceDetailPage", method = RequestMethod.GET)
    public String toMemberIntroduceDetailPage(
            @RequestParam( required = true, value = "id") String id,
            @RequestParam( required = true, value = "fieldName") String fieldName,
            @RequestParam( required = true, value = "fieldContent") String fieldContent,
            @RequestParam( required = true, value = "operator_type") String operator_type,
            HttpServletRequest request) {
        request.setAttribute("id", id);
        request.setAttribute("fieldName", fieldName);
        request.setAttribute("fieldContent", fieldContent);
        request.setAttribute("operator_type", operator_type);
        return "/web/html/memberManager/memberIntroduceDetail";
    }
}
