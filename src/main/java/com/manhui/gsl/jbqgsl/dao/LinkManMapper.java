package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.manhui.gsl.jbqgsl.controller.app.linkman.LinkManResult;
import com.manhui.gsl.jbqgsl.controller.app.linkman.TelephoneRecordResult;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.Position;
import com.manhui.gsl.jbqgsl.model.topicevaluate.TelephoneRecord;

/**
 * @类名称 TopicEvaluateMapper.java
 * @类描述 主题评价管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月7日 下午2:30:27
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月7日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface LinkManMapper {
    List<Dept> getDeptList(@Param( "p_dept_id" )String p_dept_id, @Param( "inner_outer_dept" )String inner_outer_dept);

    List<Position> getPositionList();

    List<LinkManResult> getLinkManByPositionList(@Param( "position_id" )String position_id);

	Dept queryDeptInfo(@Param( "dept_id" )String dept_id);

	List<LinkManResult> getLinkManByPositionList2(@Param( "positionIdsList" )List<String> positionIdsList);

	List<LinkManResult> queryLinkManList(TelephoneRecord tr);
}
