package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @Title: MeetingReceipt.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(参会回执表)
 * @author WangSheng
 * @date 2018年10月17日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class MeetingReceipt {
	
	private String meeting_id;			//会议管理表id
	private String member_id;			//会员id
	private String receipt_id;			//参会回执表id
	private String participate_state;	//参与类型（0：参加，1：不参加，默认：0）
	private String is_leave;			//是否请假（0：否，1：是，默认：0）
	private String leave_reason;			//是否请假（0：否，1：是，默认：0）
	private String company_name;		//公司名称
	private String person;				//企业联系人
	private String person_num;			//参会人数
	private String person_content;		//参会人员信息
	private String plate_number;		//车牌号码
	private String create_time;			//创建时间
	private String update_time;			//修改时间

}
