package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjbxx;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *企业会员，企业基本信息表
 */
@Mapper
public interface MemberQyQyjbxxMapper {

	void save(MemberQyQyjbxx entity);

	int updateById(MemberQyQyjbxx entity);

	/**
	 *
	 * @param jbxxzj
	 * @return
	 */
    MemberQyQyjbxx queryById(String jbxxzj);

	/**
	 * 查询企业 入会基本信息
	 * @param joinId
	 * @return
	 */
    Map<String,Object> queryCompanyApplyData(String joinId);
}
