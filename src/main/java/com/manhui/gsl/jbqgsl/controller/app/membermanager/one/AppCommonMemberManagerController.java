package com.manhui.gsl.jbqgsl.controller.app.membermanager.one;

import com.manhui.gsl.jbqgsl.common.enums.common.CommonEnum;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.commerce.Commerce;
import com.manhui.gsl.jbqgsl.controller.app.membermanager.one.result.QyxxBaseResult;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.app.commerce.IAppCommerceService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberRyFgrszyryService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.qy.AppMemberQyQyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyRyjbxxService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.st.AppMemberJgStjbxxService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * @类描述 会员管理--公共管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月5日 上午9:23:21
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月5日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api(tags = "会员管理--公共管理")
@RestController
@RequestMapping(AppCommonMemberManagerController.ROOT_URL)
public class AppCommonMemberManagerController extends BaseController {
	public static final String ROOT_URL = PARENT_URL_APP + "/commonMember";
	@Value("${file_companyMember_path}")
	private String fileCompanyMemberImg;

	@Autowired
	private AppMemberJoinManagerService appMemberJoinManagerService;

	/**
	 * 荣誉管理
 	 */
	@Resource
	private AppMemberRyFgrszyryService appMemberRyFgrszyryService;
	/**
	 * 企业入会基本信息管理
	 */
	@Resource
	private AppMemberQyQyjbxxService appMemberQyQyjbxxService;

	/**
	 * 个人入会基本信息管理
	 */
	@Resource
	private AppMemberRyRyjbxxService appMemberRyRyjbxxService;

	/**
	 * 查询商会列表
	 */
	@Resource
	private IAppCommerceService commerceService;

	@Resource
	private AppMemberJgStjbxxService appMemberJgStjbxxService;


	/**
	 *获得商会列表,和是否允许加入商会判断
	 * @param userId
	 * @return
	 */
	@RequestMapping(value ="/getJoinMemberList",method = RequestMethod.POST)
	@ResponseBody
	 public JsonResult getJoinMemberList(@RequestParam( value = "userId", required = true ) String userId){
	 	if(StringUtils.isBlank(userId)){
			return new JsonResult(new Throwable("参数错误"));
		}
		//验证当前人员是否能新增数据
		MemberJoinManager joinManager = new MemberJoinManager();
	 	joinManager.setCreateUserId(userId);

		List<MemberJoinManager> joinManagerList = appMemberJoinManagerService.queryAllList(joinManager);

		String HYSP_00 = MemberEnum.mmberState.ADD.getCode();//新增状态
		String HYSP_01 = MemberEnum.mmberState.PENDING.getCode();//待审批状态
		String HYSP_03 = MemberEnum.mmberState.FAILED.getCode();//审批不通过状态

		Map<String,Object> resultDataMap = new HashMap<>();
		Map<String,String> messageMap = new HashMap<>();
		boolean flag = true;
		if(joinManagerList != null && !joinManagerList.isEmpty()){
			for(MemberJoinManager e : joinManagerList){
				if(e.getState().equals(HYSP_00)){
					//进入修改
					messageMap.put("state",e.getState());
					messageMap.put("message","还有未提交申请的入会数据!");
					messageMap.put("joinId",e.getJoinId());
					messageMap.put("memberType",e.getMemberType());
					flag = false;
					break;
				}
				if(e.getState().equals(HYSP_01)){
					//进入详情
					messageMap.put("state",e.getState());
					messageMap.put("message",e.getApprove_fail_reason());
					messageMap.put("joinId",e.getJoinId());
					messageMap.put("memberType",e.getMemberType());
					flag = false;
					break;
				}
			}
			if(flag){
				for(MemberJoinManager e : joinManagerList){
					if(e.getState().equals(HYSP_03)){
						messageMap.put("state",e.getState());
						messageMap.put("message","还有审批未通过的入会数据!");
						messageMap.put("joinId",e.getJoinId());
						messageMap.put("memberType",e.getMemberType());
						break;
					}
				}
			}
		}
		resultDataMap.put("message",messageMap);

		List<Map<String,Object>> resultList = new ArrayList<>();
		if(flag){
			Map<String,Object> resultMap;
			List<Map<String,Object>> dataList;
			Map<String,Object> dataMap;
			List<Commerce> commerceList = commerceService.queryCommerceList();
			String state;
			String resultState;
			for (Commerce e : commerceList){
				resultMap = new HashMap<>();
				dataList = new ArrayList<>();
				resultMap.put("joinObjId",e.getID());
				resultMap.put("joinObjName",e.getSHMC());
				state = "";

				List<Commerce> childList = e.getZChildCommerce();
				if(childList != null || !childList.isEmpty()){
					for(Commerce t : childList){
						resultState = "";
						dataMap = new HashMap<>();
						dataMap.put("joinObjId",t.getID());
						dataMap.put("joinObjName",t.getSHMC());
						dataMap.put("joinId", "");
						dataMap.put("memberType","");
						if(joinManagerList != null && !joinManagerList.isEmpty()) {
							for (MemberJoinManager r : joinManagerList) {
								if (t.getID().equals(r.getJoinObjId())) {
									state = r.getState();
									resultState = r.getState();
									//替换空值
									dataMap.put("joinId", r.getJoinId());
									dataMap.put("memberType",r.getMemberType());
								}

							}
						}
						dataMap.put("state", resultState);
						dataList.add(dataMap);
					}
				}
				resultMap.put("dataList",dataList);
				resultMap.put("state",state);
				resultList.add(resultMap);
			}
		}

		resultDataMap.put("superiorDataList",resultList);
	 	return new JsonResult(resultDataMap);
	 }

	/**
	 * 查询个人已经加入商会
	 * @param userId
	 * @return
	 */
	@RequestMapping(value ="/getUserJoinMemberList",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getUserJoinMemberList(@RequestParam( value = "userId", required = true ) String userId){

		if(StringUtils.isBlank(userId)){
			return new JsonResult(new Throwable("参数错误"));
		}
		//验证当前人员是否能新增数据
		List<String> stateList = new ArrayList<>();
		MemberJoinManager joinManager = new MemberJoinManager();
		joinManager.setCreateUserId(userId);
		stateList.add(MemberEnum.mmberState.APPROVED.getCode());
		stateList.add(MemberEnum.mmberState.PENDING.getCode());
		stateList.add(MemberEnum.mmberState.FAILED.getCode());
		joinManager.setStateList(stateList);
		List<MemberJoinManager> joinManagerList = appMemberJoinManagerService.queryAllList(joinManager);

		List<Map<String,Object>> resultList = new ArrayList<>();
		Map<String,Object> resultMap;

		if(joinManagerList != null && !joinManagerList.isEmpty()){
			for(MemberJoinManager e : joinManagerList){
				resultMap = new HashMap<>();
				resultMap.put("joinId",e.getJoinId());
				resultMap.put("joinObjId",e.getJoinObjId());
				resultMap.put("joinObjName",e.getJoinObjName());
				resultMap.put("memberType",e.getMemberType());
				resultMap.put("createTime",e.getCreateTime());
				resultMap.put("joinObjLxr",e.getJoinObjLxr());
				resultMap.put("joinObjLxrDh",e.getJoinObjLxrDh());
				resultMap.put("state",e.getState());
				resultList.add(resultMap);
			}
		}

		return new JsonResult(resultList);
	}

	/**
	 * 获得用户入会申请数据
	 * @param joinId
	 * @return
	 */
	@RequestMapping(value ="/getApplyData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getApplyData(@RequestParam( value = "joinId", required = true ) String joinId,
								   @RequestParam( value = "memberType", required = true ) String memberType){
		Map<String,String> enumMap = MemberEnum.mmberType.codeMap;
		if(StringUtils.isBlank(joinId) || StringUtils.isBlank(memberType)){
			return new JsonResult(new Throwable("参数错误!"));
		}
		if(enumMap.get(memberType) == null){
			return new JsonResult(new Throwable("入会类型错误错误!"));
		}
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("joinId",joinId);
		paramMap.put("memberType",memberType);
		Map<String,Object> dataMap = appMemberJoinManagerService.getAppApplyData(paramMap);
		if(dataMap == null || dataMap.isEmpty()){
			return new JsonResult(new Throwable("数据不存在或已被删除,请刷新重试"));
		}

		return new JsonResult(dataMap);
	}

	/**
	 * 提交入会申请
	 * @param joinId
	 * @param memberType
	 * @return
	 */
	@RequestMapping(value ="/sendApplyData",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendApplyData(@RequestParam( value = "joinId", required = true ) String joinId,
								   @RequestParam( value = "memberType", required = true ) String memberType){

		if(StringUtils.isBlank(joinId) || StringUtils.isBlank(memberType)){
			return new JsonResult(new Throwable("参数错误!"));
		}
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("joinId",joinId);
		paramMap.put("memberType",memberType);
		JsonResult judgeResult = appMemberJoinManagerService.sendApplyJudge(paramMap);
		if(judgeResult.getState() == 1){
			MemberJoinManager joinManager = new MemberJoinManager();
			joinManager.setJoinId(joinId);
			joinManager.setState(MemberEnum.mmberState.PENDING.getCode());//待审批状态
			appMemberJoinManagerService.updateById(joinManager);
			judgeResult = new JsonResult();
		}else{
			return judgeResult;
		}
		return judgeResult;
	}

	/**
	 * 根据id删除荣誉
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delRy",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delRy(String ids){
		if(StringUtils.isBlank(ids)){
			return new JsonResult(new Throwable("参数错误!"));
		}
		String ryids[] = ids.split(",");
		MemberRyFgrszyry ry;
		for(int i = 0;i<ryids.length;i++){
			ry = new MemberRyFgrszyry();
			ry.setId(ryids[i]);
			ry.setState(CommonEnum.isNot.NO.getCode());
			int cout = appMemberRyFgrszyryService.updateById(ry);
			if(cout<1){
				return new JsonResult(new Throwable("数据异常，请联系管理员!"));
			}
		}
		return new JsonResult();
	}


	/**
	 * 入会，继续入会
	 * @param joinId 历史商会id
	 * @param createUserId
	 * @param joinObjId
	 * @param joinObjName
	 * @param position
	 * @return
	 */
	@RequestMapping(value ="/continueAddMember",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult continueAddMember(@RequestParam( value = "joinId", required = true ) String joinId,
							   @RequestParam( value = "createUserId", required = true ) String createUserId,
										@RequestParam( value = "createUserName", required = true ) String createUserName,
							   @RequestParam( value = "joinObjId", required = true ) String joinObjId,
							   @RequestParam( value = "joinObjName", required = true ) String joinObjName,
							   @RequestParam( value = "position", required = true ) String position){

		//验证当前人员是否能保存数据
		MemberJoinManager joinManager = new MemberJoinManager();
		joinManager.setCreateUserId(createUserId);
		joinManager.setJoinObjId(joinObjId);
		List<MemberJoinManager> joinManagerList = appMemberJoinManagerService.queryAllList(joinManager);
		if(joinManagerList != null && !joinManagerList.isEmpty()){
			return new JsonResult(new Throwable(joinObjName+"已经入会，不能重复申请！"));
		}
		MemberJoinManager joinManager2 = new MemberJoinManager();
		joinManager2.setCreateUserId(createUserId);
		joinManagerList = appMemberJoinManagerService.queryAllList(joinManager2);
		MemberJoinManager memberJoinManager = joinManagerList.get(0);
		if(memberJoinManager != null && !memberJoinManager.getState().equals(MemberEnum.mmberState.APPROVED.getCode())){
			return new JsonResult(new Throwable(memberJoinManager.getJoinObjName()+"入会审批未通过，不能继续加入商会！"));
		}
		joinManager2.setJoinId(joinId);
		joinManagerList = appMemberJoinManagerService.queryAllList(joinManager2);
		if(joinManagerList == null || joinManagerList.isEmpty()){
			return new JsonResult(new Throwable("继续入会商会数据不存在！"));
		}
		//新增数据
		joinManager.setCreateUserName(createUserName);
		joinManager.setJoinObjName(joinObjName);
		joinManager.setPosition(position);
		joinManager.setMemberType(memberJoinManager.getMemberType());
		JsonResult jsonResult = appMemberJoinManagerService.continueAddMember(joinManager,joinId);

		return jsonResult;
	}


	/**
	 * 入会申请文件上传
	 * @param fileList
	 * @return
	 */
	@RequestMapping(value ="/uploadFile",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult uploadFile(@RequestParam(value = "fileList", required = true) MultipartFile[] fileList){

		if(fileList == null){
			return new JsonResult(new Throwable("参数错误!"));
		}
		List<Map<String,Object>> resultList = new ArrayList<>();
		Map<String,Object> resultMap;
		String filePath = "/member/";
		String realPath = fileCompanyMemberImg + "/";
		for(MultipartFile file : fileList){
			resultMap = new HashMap<>();
			try {
				File upFile = new File(realPath);
				/** 根据真实路径创建目录 **/
				if (!upFile.exists()) {
					upFile.mkdirs();
				}
				String filename = file.getOriginalFilename();
				filename = filename.substring(filename.lastIndexOf("."));
				// 4位随机值
				int rodom = (int) ((Math.random() * 9 + 1) * 1000);
				// 拼接图片链接
				String fileName = "member_"+System.currentTimeMillis()+filename;
				String fileSavePath = realPath + fileName;
				File file2 = new File(fileSavePath);
				file.transferTo(file2);
				String fileUrl = (filePath + fileName);
				resultMap.put("fileUrl",fileUrl);
				resultList.add(resultMap);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("企业会员证件资料保存出错");
			}
		}
		return new JsonResult(resultList);
	}

}
