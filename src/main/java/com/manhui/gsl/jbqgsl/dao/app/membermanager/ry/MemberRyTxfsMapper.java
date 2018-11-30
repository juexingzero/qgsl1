package com.manhui.gsl.jbqgsl.dao.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyTxfs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 个人入会，通讯方式记录表
 */
@Mapper
public interface MemberRyTxfsMapper {

	void save(MemberRyTxfs entity);

	int updateById(MemberRyTxfs entity);

	/**
	 * 根据人员信息表id 查询数据
	 * @param rybid
	 * @return
	 */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);

	/**
	 *根据人员基本信息表id 修改数据
	 * @param entity
	 * @return
	 */
	int updateByRyid(MemberRyTxfs entity);
}
