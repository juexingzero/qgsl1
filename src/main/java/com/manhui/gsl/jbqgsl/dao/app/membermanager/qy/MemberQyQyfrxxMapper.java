package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyfrxx;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyrzqk;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 *企业会员，企业法人信息表
 */
@Mapper
public interface MemberQyQyfrxxMapper {

	void save(MemberQyQyfrxx entity);

	int updateById(MemberQyQyfrxx entity);

	/**
	 * 根据企业 基本信息表id查询数据
	 * @param qyid
	 * @return
	 */
    Map<String,Object> qeuryByQyid(String qyid);
}
