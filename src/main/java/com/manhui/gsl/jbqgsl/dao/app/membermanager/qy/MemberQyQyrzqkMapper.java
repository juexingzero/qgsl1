package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyrzqk;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *业会员，企业认证情况表
 */
@Mapper
public interface MemberQyQyrzqkMapper {

	void save(MemberQyQyrzqk entity);

	int updateById(MemberQyQyrzqk entity);

	/**
	 * 根企业基本信息表id 查询数据
	 * @param qyid
	 * @return
	 */
    List<Map<String,Object>> queryByQyid(String qyid);
}
