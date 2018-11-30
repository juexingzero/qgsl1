package com.manhui.gsl.jbqgsl.dao.app.membermanager.st;

import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjbxx;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjs;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 社团入会，社团介绍表
 */
@Mapper
public interface MemberJgStjsMapper {

	void save(MemberJgStjs entity);

	int updateById(MemberJgStjs entity);

	/**
	 * 根据社团id 查询数据
	 * @param stid
	 * @return
	 */
    Map<String,Object> queryCompanyApplyData(String stid);
}
