package com.manhui.gsl.jbqgsl.controller.web.usersuggest;

import java.util.HashMap;
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
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.UserSuggest;
import com.manhui.gsl.jbqgsl.service.web.IUserSuggestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 CommonSysParamController.java
 * @类描述 企业之声
 * @作者 LiuBin
 * @创建时间 2018年9月5日 上午11:32:5
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	LiuBin 	 2018年9月4日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api(tags = "后台-意见反馈")
@Controller
@RequestMapping(WebUserSuggestController.ROOT_URL)
public class WebUserSuggestController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_WEB + "/userSuggest";
	@Resource
	private IUserSuggestService userSuggestService;

	@ApiOperation(value = "进入意见反馈列表页面", notes = "进入意见反馈列表页面")
	@RequestMapping(value = "toUserSuggestListPage", method = RequestMethod.GET)
	public String touserSuggestListPage() {
		return "/web/html/userSuggest/userSuggestList";
	}

	@ApiOperation(value = "进入意见反馈详情页面", notes = "进入意见反馈详情页面")
	@RequestMapping(value = "toUserSuggestDetailPage", method = RequestMethod.GET)
	public String toUserSuggestDetailPage(HttpServletRequest request, String id) {
		request.setAttribute("userSuggest", userSuggestService.getUserSuggestDetail(id));
		return "/web/html/userSuggest/userSuggestDetail";
	}

	@ApiOperation(value = "获取意见反馈列表", notes = "获取意见反馈列表")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = false, name = "json", value = "参数json(is_answer)", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = false, name = "pageIndex", value = "当前页数", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = false, name = "pageSize", value = "每页行数", dataType = "字符串") })
	@RequestMapping(value = "getUserSuggestList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserSuggestList(
			@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "pageSize", required = false) String pageSize) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		if (json != null && !"".equals(json)) {
			JSONObject jsonObject = JSON.parseObject(json);
			if (StringUtils.isNotEmpty(jsonObject.get("is_answer") + "")) {
				conditionMap.put("is_answer", jsonObject.get("is_answer") + "");
			}
		}
		conditionMap.put("pageIndex", pageIndex);
		conditionMap.put("pageSize", pageSize);
		PageInfo<UserSuggest> pageInfo = userSuggestService.getUserSuggestList(conditionMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", pageInfo.getList());
		resultMap.put("total", pageInfo.getTotal());
		return resultMap;
	}

	@ApiOperation(value = "保存反馈回复内容", notes = "保存反馈回复内容")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "id", value = "反馈ID", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "answer_content", value = "回复内容", dataType = "字符串") })
	@RequestMapping(value = "saveUserSuggest", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult saveUserSuggest(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "user_id", required = true) String user_id,
			@RequestParam(value = "user_name", required = true) String user_name,
			@RequestParam(value = "answer_content", required = true) String answer_content,
			@RequestParam(value = "content", required = true) String content) {
		UserSuggest userSuggest = new UserSuggest();
		userSuggest.setId(id);
		userSuggest.setIs_answer("1");
		userSuggest.setUser_id(user_id);
		userSuggest.setUser_name(user_name);
		userSuggest.setAnswer_man_id(AppUtil.getCookieByName(request, "user_id"));
		userSuggest.setAnswer_man_name(AppUtil.getCookieByName(request, "user_name"));
		userSuggest.setAnswer_content(answer_content);
		userSuggest.setContent(content);
		return new JsonResult(userSuggestService.saveUserSuggest(userSuggest));
	}

	@ApiOperation(value = "删除反馈信息", notes = "删除反馈信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "id", value = "反馈ID）", dataType = "字符串") })
	@RequestMapping(value = "deleteUserSuggest", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteCompanyFeedback(@RequestParam(value = "id", required = true) String id) {
		return new JsonResult(userSuggestService.deleteUserSuggest(id));
	}
}
