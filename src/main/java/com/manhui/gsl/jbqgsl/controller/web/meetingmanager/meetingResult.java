package com.manhui.gsl.jbqgsl.controller.web.meetingmanager;

import java.util.List;

import lombok.Data;

/**
* @Title: meetingResult.java
* @Package com.manhui.gsl.jbqgsl.controller.web.meetingmanager.meetingResult
* @Description: TODO(会议详情页面--会议实体类)
* @author WangSheng
* @date 2018年10月29日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class meetingResult {

	private String meeting_id; //会议id
	private String vote_id;		//投票表id
	private String meeting_theme; //会议主题
	private String meeting_address;//会议地址
	private String meeting_ll;//会议导航经纬度（经度，纬度，用中文逗号隔开）
	private String meeting_navigation;//会议导航
	private List<String> dataList;//会议资料
	private String meeting_state;//会议状态（0：未发布，1：未开始，2：会议中，3：已结束，4：已撤销，默认：0）
	private String meeting_content;//会议内容
	private String allow_select;//允许选择数
	private String is_vote;//是否需要投票（0：需要，1：不需要，默认：0）
	private String meeting_starttime;//会议开始时间（格式：yyyy-MM-dd HH:mm）
	private String meeting_endtime;//会议结束时间（格式：yyyy-MM-dd HH:mm）
	private String receipt_time;//回执截止时间（格式：yyyy-MM-dd HH:mm）
	private String meetings;	//会议主题
	private List<String> optionsList;	//选项内容
	private String xxlength;	//选项内容长度
	private String wjlength;	//文件数量
	
}
