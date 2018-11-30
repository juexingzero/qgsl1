package com.manhui.gsl.jbqgsl.service.web.meetingmanager.impl;

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
import com.manhui.gsl.jbqgsl.dao.web.meetingmanager.MeetingReceiptMapper;
import com.manhui.gsl.jbqgsl.model.MeetingReceipt;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingReceiptService;

@Service
public class MeetingReceiptServiceImpl implements MeetingReceiptService {
	
	private static final Logger logger=LoggerFactory.getLogger(MeetingReceiptServiceImpl.class);
	
	@Resource
	private MeetingReceiptMapper meetingReceiptMapper;
	@Value( "${file_download_topic_evaluate_templet_file}" )
    private String                     topicEvaluateTempletFile;
    @Value( "${file_download_topic_evaluate_result_path}" )
    private String                     topicEvaluateResultPath;

	@Override
	public List<MeetingReceipt> getMeetingReceiptList(String meeting_id,String participate_state,Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取参会回执列表数据  -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
		conditions.put("participate_state", participate_state);
		if(pageIndex!=null&&pageSize!=null){
			conditions.put( "pageNo", pageIndex * pageSize);
	        conditions.put( "pageSize", pageSize);
		}
		return meetingReceiptMapper.getMeetingReceiptList(conditions);
	}

	@Override
	public Integer queryMeetingReceiptTotal(String meeting_id,String participate_state,Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取参会回执列表总数 -----" );
		Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
		conditions.put("participate_state", participate_state);
		if(pageIndex!=null&&pageSize!=null){
			conditions.put( "pageNo", pageIndex * pageSize);
	        conditions.put( "pageSize", pageSize);
		}
		return meetingReceiptMapper.queryMeetingReceiptTotal(conditions);
	}

	@Override
	public String exportTopicEvaluateResult(HttpServletResponse response, String meeting_id) {
		String exportFileName = "";
            exportFileName = this.buildExportFile( meeting_id );
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
    private String buildExportFile( String meeting_id ) {
        String exportFileName = "";
        Map<String, Object> beanParams = new HashMap<String, Object>();
        
        Map<String, Object> conditions=new HashMap<>();
		conditions.put("meeting_id", meeting_id);
        
        List<MeetingReceipt> mrList=meetingReceiptMapper.getMeetingReceiptList(conditions);
        for (MeetingReceipt m : mrList) {
			if(m.getIs_leave().equals("0")){
				m.setIs_leave("否");
			}else{
				m.setIs_leave("是");
			}
			if(m.getParticipate_state().equals("0")){
				m.setParticipate_state("参加");
			}else{
				m.setParticipate_state("不参加");
			}
		}
        beanParams.put("mrList", mrList);
        exportFileName = "csdc" +
                "-" +
                DateUtil.getDateTime( "HHmmss" ) +
                "." +
                ( topicEvaluateTempletFile.endsWith( "xlsx" ) == true ? "xlsx" : "xls" );
         ExcelExportUtil
                .ExportExcel( topicEvaluateTempletFile, beanParams, topicEvaluateResultPath + exportFileName );
        logger.info( "----------模板地址：" + JacksonUtil.toJson( mrList )  );
        return exportFileName;
    }
}
