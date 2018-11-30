package com.manhui.gsl.jbqgsl.service.web.activity;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityEntryFlow;

public interface ActivityEntryFlowService {
	
	List<ActivityEntryFlow> getActivityEntryFlowLists(String activity_id,Integer pageIndex, Integer pageSize);
	
	Integer queryActivityEntryFlowTotal( String activity_id,Integer pageIndex, Integer pageSize );
	
	/**
     * 导出活动报名信息
     * 
     * @param activity_id
     * @return
     */
    String exportActivityEntryFlowResult( HttpServletResponse response, String activity_id );
    
    //查询人数和企业数
    Integer queryActivityEntryFlowNum(String state);

}
