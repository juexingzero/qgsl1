package com.manhui.gsl.jbqgsl.dao.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyNbrybg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 个人入会，学位学历记录表
 */
@Mapper
public interface MemberRyNbrybgMapper {

	void save(MemberRyNbrybg entity);

	int updateById(MemberRyNbrybg entity);

	/**
	 * 根据人员基本信息表id 查询数据
	 * @param rybid
	 * @return
	 */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);
}
