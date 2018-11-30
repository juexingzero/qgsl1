package com.manhui.gsl.jbqgsl.dao.app.membermanager.st;

import com.manhui.gsl.jbqgsl.controller.app.memberapproval.MemberBaseInfo;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
import com.manhui.gsl.jbqgsl.model.member.st.MemberJgStjbxx;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 社团入会，基本信息表
 */
@Mapper
public interface MemberJgStjbxxMapper {

	void save(MemberJgStjbxx entity);

	int updateById(MemberJgStjbxx entity);

    MemberJgStjbxx queryById(String id);

    /**
     * 查询入会数据
     * @param joinId
     * @return
     */
    Map<String,Object> queryLeaguelMemberData(String joinId);
}
