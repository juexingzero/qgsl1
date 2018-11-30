package com.manhui.gsl.jbqgsl.service.web.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.ExcelExportUtil;
import com.manhui.gsl.jbqgsl.common.util.JacksonUtil;
import com.manhui.gsl.jbqgsl.dao.web.activity.ActivityEntryFlowMapper;
import com.manhui.gsl.jbqgsl.dao.web.activity.ActivitySignFlowMapper;
import com.manhui.gsl.jbqgsl.model.MeetingReceipt;
import com.manhui.gsl.jbqgsl.model.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityEntryFlow;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivitySignFlow;
import com.manhui.gsl.jbqgsl.service.web.activity.ActivityEntryFlowService;
import com.manhui.gsl.jbqgsl.service.web.activity.ActivitySignFlowService;

@Service
public class ActivityEntryFlowServiceImpl implements ActivityEntryFlowService {
	
	private static final Logger logger=LoggerFactory.getLogger(ActivityEntryFlowServiceImpl.class);
	
	@Resource
	private ActivityEntryFlowMapper activityEntryFlowMapper;
	@Value( "${file_download_activity_entry_flow_file}" )
    private String topicEvaluateTempletFile;
    @Value( "${file_download_topic_evaluate_result_path}" )
    private String topicEvaluateResultPath;


	@Override
	public List<ActivityEntryFlow> getActivityEntryFlowLists(String activity_id, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-查询活动报名人员列表 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("activity_id", activity_id);
		conditions.put( "pageNo", pageIndex * pageSize);
        conditions.put( "pageSize", pageSize);
		return activityEntryFlowMapper.getActivityEntryFlowLists(conditions);
	}

	@Override
	public Integer queryActivityEntryFlowTotal(String activity_id, Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-查询活动报名人员列表总数 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("activity_id", activity_id);
		if(pageIndex!=null&&pageSize!=null){
			conditions.put( "pageNo", pageIndex * pageSize);	
		}
        conditions.put( "pageSize", pageSize);
		return activityEntryFlowMapper.queryActivityEntryFlowTotal(conditions);
	}

	@Override
	public String exportActivityEntryFlowResult(HttpServletResponse response, String activity_id) {
		String exportFileName = "";
        exportFileName = this.buildExportFile( activity_id );
    if ( !"".equals( exportFileName ) ) {
        logger.info( "-----------参会回执-导出Excel-结果：-----------\n" + "exportFileName：" + exportFileName );
    }
    return exportFileName;
	}

	
	/**
     * 组建导出文件
     * 
     * @param topic_id
     * @return
     */
    private String buildExportFile( String activity_id ) {
        String exportFileName = "";
        Map<String, Object> beanParams = new HashMap<String, Object>();
        
        Map<String, Object> conditions=new HashMap<>();
		conditions.put("activity_id", activity_id);
        
        List<ActivityEntryFlow> aefList=activityEntryFlowMapper.getActivityEntryFlowLists(conditions);
        for (ActivityEntryFlow m : aefList) {
			if(m.getMember_linkman_sex().equals("XB-010")){
				m.setMember_linkman_sex("男");
			}else{
				m.setMember_linkman_sex("女");
			}
		}
        beanParams.put("aefList", aefList);
        exportFileName = "csdc" +
                "-" +
                DateUtil.getDateTime( "HHmmss" ) +
                "." +
                ( topicEvaluateTempletFile.endsWith( "xlsx" ) == true ? "xlsx" : "xls" );
         ExcelExportUtil
                .ExportExcel( topicEvaluateTempletFile, beanParams, topicEvaluateResultPath + exportFileName );
        logger.info( "----------模板地址：" + JacksonUtil.toJson( aefList )  );
        return exportFileName;
    }

	@Override
	public Integer queryActivityEntryFlowNum(String state) {
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("state", state);
		return activityEntryFlowMapper.queryActivityEntryFlowNum(conditions);
	}
}
