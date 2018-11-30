
package com.manhui.gsl.jbqgsl.controller.app.meetingmanager;

import lombok.Data;

/**
* @ClassName: MeetingVoteResult
* @Description: TODO(会议投票选项实体类)
* @author LiuBin
* @date 2018年10月31日
*/
@Data
public class MeetingVoteOptionListResult {
	//----  会议投票选项表 meeting_vote_options-- 
	private String options_id; //选项id
	private String options; //选项内容
	//-----参会人员表  meeting_person---
	private String meeting_id;//会议ID
	private String vote_options;//投票选项具体答案
	//---会议投票表 meeting_vote--
	private String allow_item;//单选还是多选 1 2 3 4 5 
	private String meetings;//会议纪要
	
	
	
	
}
