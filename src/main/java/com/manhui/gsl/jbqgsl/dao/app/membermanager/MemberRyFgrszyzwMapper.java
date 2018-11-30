package com.manhui.gsl.jbqgsl.dao.app.membermanager;

import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyzw;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *非公人事主要职务表
 */
@Mapper
public interface MemberRyFgrszyzwMapper {

	void save(MemberRyFgrszyzw entity);

	int updateById(MemberRyFgrszyzw entity);

	/**
	 *根据关联表id 查询数据
	 * @param paramid
	 * @return
	 */
    List<Map<String,Object>> appQueryMapByParamid(String paramid);

	/**
	 * 根据关联表paramid 修改数据
	 * @param entity
	 * @return
	 */
	int updateByParamid(MemberRyFgrszyzw entity);
}
