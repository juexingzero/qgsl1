package com.manhui.gsl.jbqgsl.controller.web.meetingmanager;

import java.util.List;

import lombok.Data;

/**
* @Title: voteResult.java
* @Package com.manhui.gsl.jbqgsl.controller.web.meetingmanager.voteResult
* @Description: TODO(查看投票页面--投票实体类)
* @author WangSheng
* @date 2018年10月31日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class voteResult {

	private String meeting_id; //会议id
	private String meeting_theme; //会议主题
	private String vote_starttime;//投票开始时间（格式：yyyy-MM-dd HH:mm）
	private String vote_endtime;//投票结束时间（格式：yyyy-MM-dd HH:mm）
	private String meetings;	//投票议题
	private Integer votePerson;//投票人数
	private List<Integer> voteNum;//每个选项的票数
	private List<String> optionsList;	//选项内容
	private List<String> optionsProportion;//所占比例
	
}
