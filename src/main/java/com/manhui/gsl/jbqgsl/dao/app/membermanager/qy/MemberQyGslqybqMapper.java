package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyGslqybq;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import org.apache.ibatis.annotations.Mapper;

/**
 *业会员，工商联企业标签表
 */
@Mapper
public interface MemberQyGslqybqMapper {

	void save(MemberQyGslqybq entity);

	int updateById(MemberQyGslqybq entity);
}
