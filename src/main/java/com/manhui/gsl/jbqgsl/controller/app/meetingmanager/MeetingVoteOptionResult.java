
package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import java.util.List;

import lombok.Data;

/**
* @ClassName: MeetingVoteResult
* @Description: TODO(会议投票选项实体类)
* @author LiuBin
* @date 2018年10月31日
*/
@Data
public class MeetingVoteOptionResult {
	private String meetings;//会议议题
	private int num;//投票有几项
	private String allow_item; //(1：单项选择，2：选择两项，3：选择三项，4：选择四项，默认：1)
//	----会议参会人员表--
	private String meeting_id; //会议ID
	private String vote_options; //投票选项
	private List<MeetingVoteOptionListResult> dataList; //(1：单项选择，2：选择两项，3：选择三项，4：选择四项，默认：1)
	
	
	
}
