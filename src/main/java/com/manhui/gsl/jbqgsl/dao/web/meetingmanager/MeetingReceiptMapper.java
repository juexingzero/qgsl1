package com.manhui.gsl.jbqgsl.dao.web.meetingmanager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.manhui.gsl.jbqgsl.model.MeetingReceipt;

/**
* @Title: MeetingReceiptMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.membermanager
* @Description: TODO(参会回执数据交互层)
* @author WangSheng
* @date 2018年10月24日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Mapper
public interface MeetingReceiptMapper {
	
	List<MeetingReceipt> getMeetingReceiptList(Map<String, Object> conditions);
	
	Integer queryMeetingReceiptTotal( Map<String, Object> conditions );
	
}
