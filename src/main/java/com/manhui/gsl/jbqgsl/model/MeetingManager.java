package com.manhui.gsl.jbqgsl.model;

/**
* @Title: MeetManager.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(会议管理实体类)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
import lombok.Data;

@Data
public class MeetingManager {
	private String meeting_id; //会议id
	private String meeting_theme; //会议主题
	private String meeting_address;//会议地址
	private String meeting_ll;//会议导航经纬度（经度，纬度，用中文逗号隔开）
	private String meeting_navigation;//会议导航
	private String meeting_data;//会议资料
	private String meeting_state;//会议状态（0：未发布，1：未开始，2：会议中，3：已结束，4：已撤销，默认：0）
	private String meeting_content;//会议内容
	private String meeting_minutes;//会议纪要
	private String is_delete;//是否删除（0：未删除，1：删除，默认：0）
	private String allow_select;//允许选择数
	private String or_code;//二维码
	private String is_vote;//是否需要投票（0：需要，1：不需要，默认：0）
	private String is_receipt;//是否回执(0:否1:是)
	private String meeting_starttime;//会议开始时间（格式：yyyy-MM-dd HH:mm）
	private String meeting_endtime;//会议结束时间（格式：yyyy-MM-dd HH:mm）
	private String receipt_time;//回执截止时间（格式：yyyy-MM-dd HH:mm）
	private String create_time;//创建时间
	private String update_time;//修改时间
}
