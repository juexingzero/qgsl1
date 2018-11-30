package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQybfjbqk;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQygybfqk;
import org.apache.ibatis.annotations.Mapper;

/**
 *企业会员，企业公益帮扶情况表
 */
@Mapper
public interface MemberQyQygybfqkMapper {

	void save(MemberQyQygybfqk entity);

	int updateById(MemberQyQygybfqk entity);
}
