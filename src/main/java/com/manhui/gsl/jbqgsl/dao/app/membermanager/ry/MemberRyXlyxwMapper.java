package com.manhui.gsl.jbqgsl.dao.app.membermanager.ry;

import com.manhui.gsl.jbqgsl.model.member.ry.MemberRyXlyxw;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 个人入会，学位学历记录表
 */
@Mapper
public interface MemberRyXlyxwMapper {

	void save(MemberRyXlyxw entity);

	int updateById(MemberRyXlyxw entity);

	/**
	 * 根据人员表id 查询数据
	 * @param rybid
	 * @return
	 */
    List<Map<String,Object>> appQueryMapByRyid(String rybid);

	/**
	 * 根基人员基本信息表id ，修改数据
	 * @param entity
	 * @return
	 */
	int updateByRyid(MemberRyXlyxw entity);
}
