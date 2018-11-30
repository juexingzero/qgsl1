
package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import lombok.Data;

/**
* @ClassName: MeetingVoteResult
* @Description: TODO(会议投票实体类)
* @author LiuBin
* @date 2018年10月31日
*/
@Data
public class MeetingVoteObjectResult {
	private String meeting_id; //会议投票ID
	private String member_id; //会员ID
	private String vote_id; //投票id
	private String meeting_name; //会议名称
	private String vote_starttime;//投票开始时间
	private String vote_endtime;//投票结束时间
	private String vote_instructions; //投票说明
	private String allow_item; //允许选几项
	private String meetings; //投票议题
	private String create_time; //创建时间
	private String update_time; //修改时间
}
