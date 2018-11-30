package com.manhui.gsl.jbqgsl.dao.app.membermanager.qy;

import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQyjs;
import com.manhui.gsl.jbqgsl.model.member.qy.MemberQyQylxrxx;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *业会员，企业联系人信息表
 */
@Mapper
public interface MemberQyQylxrxxMapper {

	void save(MemberQyQylxrxx entity);

	int updateById(MemberQyQylxrxx entity);

	/**
	 * 根据企业基本信息表id 查询数据
	 * @param qyid
	 * @return
	 */
    List<Map<String,Object>> queryByQyid(String qyid);
}
