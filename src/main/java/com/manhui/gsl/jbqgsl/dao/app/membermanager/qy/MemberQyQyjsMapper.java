package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *业会员，企业介绍表
 */
@Mapper
public interface MemberQyQyjsMapper {

	void save(MemberQyQyjs entity);

	int updateById(MemberQyQyjs entity);

	/**
	 * 根据 企业基本信息表id 查询数据
	 * @param qyid
	 * @return
	 */
    List<Map<String,Object>> queryByQyid(String qyid);

	/**
	 *查询企业介绍（只应对企业入会的，企业介绍内容）
	 *只查询，企业发展历程，企业简介，企业文化
	 * @param qyid qyid 根据企业基本信息表id 查询，并且企业简介内容不能为空
	 * @return
	 */
    Map<String,Object> queryCompanyApplyJsData(String qyid);
}
