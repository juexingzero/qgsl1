package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQybfjbqk;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjbxx;
import org.apache.ibatis.annotations.Mapper;

/**
 *企业会员，企业帮扶基本情况表
 */
@Mapper
public interface MemberQyQybfjbqkMapper {

	void save(MemberQyQybfjbqk entity);

	int updateById(MemberQyQybfjbqk entity);
}
