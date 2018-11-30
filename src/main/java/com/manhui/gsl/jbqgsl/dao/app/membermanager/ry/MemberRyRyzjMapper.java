package com.manhui.gsl.jbqgsl.dao.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyRyzj;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 个人入会，人员证件表
 */
@Mapper
public interface MemberRyRyzjMapper {

	void save(MemberRyRyzj entity);

	int updateById(MemberRyRyzj entity);

	/**
	 * 通过人人员 基本信息表id 查询数据
	 * @param rybid
	 * @return
	 */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);

	/**
	 * 据人员信息基本表id 修改数据
	 * @param entity
	 * @return
	 */
	int updateByRyid(MemberRyRyzj entity);
}
