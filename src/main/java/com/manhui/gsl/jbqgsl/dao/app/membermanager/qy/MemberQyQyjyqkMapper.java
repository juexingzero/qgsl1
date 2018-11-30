package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjyqk;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQylxrxx;
import org.apache.ibatis.annotations.Mapper;

/**
 *业会员，企业经营情况表
 */
@Mapper
public interface MemberQyQyjyqkMapper {

	void save(MemberQyQyjyqk entity);

	int updateById(MemberQyQyjyqk entity);
}
