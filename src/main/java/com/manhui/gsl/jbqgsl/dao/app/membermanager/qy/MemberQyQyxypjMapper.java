package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyxypj;
import org.apache.ibatis.annotations.Mapper;

/**
 *业会员，企业信用评级表
 */
@Mapper
public interface MemberQyQyxypjMapper {

	void save(MemberQyQyxypj entity);

	int updateById(MemberQyQyxypj entity);
}
