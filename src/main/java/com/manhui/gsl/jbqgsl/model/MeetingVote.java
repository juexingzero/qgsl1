package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @Title: MeetingVote.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(会议投票表)
 * @author WangSheng
 * @date 2018年10月17日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class MeetingVote {
	
	private String vote_id;			 //会议投票表id
	private String meeting_id;		 //会议表id
	private String meeting_name;	 //会议名称
	private String vote_starttime;	 //投票开始时间（格式：yyyy-MM-dd HH:mm）
	private String vote_endtime;	 //投票结束时间（格式：yyyy-MM-dd HH:mm）
	private String vote_instructions;//投票说明
	private String meetings;		 //议题
	private String create_time;		 //创建时间
	private String update_time;		 //修改时间
	private String allow_item;		 //允许选择的项(1:单项选择，2：选择两项，3：选择三项，4：选择四项，默认：1)

}
