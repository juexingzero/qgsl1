package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyywqk;
import org.apache.ibatis.annotations.Mapper;

/**
 *业会员，企业业务情况表
 */
@Mapper
public interface MemberQyQyywqkMapper {

	void save(MemberQyQyywqk entity);

	int updateById(MemberQyQyywqk entity);
}
