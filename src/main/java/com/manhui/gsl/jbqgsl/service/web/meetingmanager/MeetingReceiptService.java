package com.manhui.gsl.jbqgsl.service.web.meetingmanager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.manhui.gsl.jbqgsl.model.MeetingReceipt;
import com.manhui.gsl.jbqgsl.model.TopicPassiveInfo;

public interface MeetingReceiptService {

	List<MeetingReceipt> getMeetingReceiptList(String meeting_id,String participate_state,Integer pageIndex, Integer pageSize);
	
	Integer queryMeetingReceiptTotal(String meeting_id,String participate_state,Integer pageIndex, Integer pageSize);
	
	/**
     * 导出参会回执信息
     * 
     * @param meeting_id
     * @return
     */
    String exportTopicEvaluateResult( HttpServletResponse response, String meeting_id );
	
}
