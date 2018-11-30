package com.manhui.gsl.jbqgsl.service.app.membermanager.ry.impl;

import com.manhui.gsl.jbqgsl.common.enums.common.CommonEnum;
import com.manhui.gsl.jbqgsl.common.enums.member.MemberEnum;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.ry.MemberRyRyzjMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.ry.MemberRyXlyxwMapper;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyXlyxw;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyRyzjService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ry.AppMemberRyXlyxwService;
import com.manhui.gsl.jbqgsl.service.web.membermanager.IMemberManagerService;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员 个人入会，人员证件管理
 */
@Service
public class AppMemberRyRyzjServiceImpl implements AppMemberRyRyzjService {
	@Resource
	private MemberRyRyzjMapper memberRyRyzjMapper;
	@Resource
	private ISysParamService iSysParamService;
	@Resource
	private AppMemberJoinManagerService appMemberJoinManagerService;
	/**
	 * 审批流水表
	 */
	@Resource
	private IMemberManagerService iMemberManagerService;

	@Override
	public void save(MemberRyRyzj entity) {
		memberRyRyzjMapper.save(entity);
	}

    /**
     * 根据人员信息表id 查询数据
	 * @param rybid
     * @return
     */
	@Override
	public List<Map<String, Object>> appQueryMapByRyid(String rybid) {
		if(StringUtils.isBlank(rybid)){
			throw new RuntimeException("queryMapByRyid--参数异常!");
		}
		return memberRyRyzjMapper.appQueryMapByRyid(rybid);
	}

	@Override
	public int updateById(MemberRyRyzj entity) {
		return memberRyRyzjMapper.updateById(entity);
	}

	/**
	 *根据人员信息基本表id 修改数据
	 * @param entity
	 */
	@Override
	public void updateByRyid(MemberRyRyzj entity) {
		int count = memberRyRyzjMapper.updateByRyid(entity);
	}

	@Override
	public JsonResult addIndividualMemberZjData(List<MemberRyRyzj> list, String ryid,String joinId) {
		List<Map<String,Object>> resultList = new ArrayList<>();
		Map<String,Object> resultMap;

		if(list != null && !list.isEmpty() && StringUtils.isNotBlank(ryid)){
			Map<String,String> paramMap = MemberEnum.documentType.codeMap;
			boolean falg = false;
			for(MemberRyRyzj e : list){
				if(StringUtils.isBlank(paramMap.get(e.getRyzjlx()))){
					throw new RuntimeException("系统参数，证件类型错误！");
				}
				if(StringUtils.isBlank(e.getId())){
					//新增
					String uuid = UUIDUtil.getUUID();
					e.setId(uuid);
					e.setRyid(ryid);
					//e.setRyzjlx(e.getRyzjlx());
					e.setState(CommonEnum.isNot.YES.getCode());
					memberRyRyzjMapper.save(e);
				}else{
					//修改
					//验证是否允许修改
					if(StringUtils.isNotBlank(joinId)){
						JsonResult jsonResult = appMemberJoinManagerService.verifyJoinManagerState(joinId,null);
						if(jsonResult.getState() != 1){
							return jsonResult;
						}
					}

					if(StringUtils.isBlank(e.getFileUrl())){
						//如果文件路径为空，那么说明，用户删除了上传文件,状态设置为删除状态
						e.setState(CommonEnum.isNot.NO.getCode());
					}else {
						e.setState(CommonEnum.isNot.YES.getCode());
					}

					memberRyRyzjMapper.updateById(e);

					falg = true;
				}
				resultMap = new HashMap<>();
				resultMap.put("ryzjlx",e.getRyzjlx());
				resultMap.put("id",e.getId());
				resultMap.put("fileUrl",e.getFileUrl());
				resultMap.put("pan",e.getPan());
				resultList.add(resultMap);
			}

			if(falg){
				//修改审批流水表 审批未通过类别
				Map<String,Object> paramMap2 = new HashMap<>();
				paramMap.put("approve_fail_info",MemberEnum.checkType.ZJXX.getCode());
				paramMap.put("member_id",joinId);
				iMemberManagerService.updateApprove_fail_infoByMemberId(paramMap2);
			}
		}
		return new JsonResult(resultList);
	}
}
