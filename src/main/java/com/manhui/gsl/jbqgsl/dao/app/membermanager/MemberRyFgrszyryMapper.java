package com.manhui.gsl.jbqgsl.dao.app.membermanager;

import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.member.MemberRyFgrszyry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *非公人士主要荣誉表
 */
@Mapper
public interface MemberRyFgrszyryMapper {

	void save(MemberRyFgrszyry entity);

	int updateById(MemberRyFgrszyry entity);

	/**
	 * 根据关联表 id，查询荣誉信息
	 * @param paramid
	 * @return
	 */
	List<Map<String,Object>> appQueryMapByRyid(Map<String,Object> paramMap);
}
