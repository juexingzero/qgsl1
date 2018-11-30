package com.manhui.gsl.jbqgsl.dao.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyDpsf;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 个人入会，党派身份管理表
 */
@Mapper
public interface MemberRyDpsfMapper {

	void save(MemberRyDpsf entity);

	int updateById(MemberRyDpsf entity);

	/**
	 * 根据人员 表id 查询数据
	 * @param rybid
	 * @return
	 */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);

	/**
	 * 根据人员基本信息表id 修改数据
	 * @param entity
	 * @return
	 */
	int updateByRyid(MemberRyDpsf entity);
}
