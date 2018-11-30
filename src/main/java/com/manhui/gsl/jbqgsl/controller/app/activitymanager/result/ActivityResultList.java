
package com.manhui.gsl.jbqgsl.controller.app.activitymanager.result;

import lombok.Data;

/**
* @ClassName: ActivityList
* @Description: TODO(我的活动列表展示)
* @author LiuBin
* @date 2018年11月20日
*/
@Data
public class ActivityResultList {
	private String activity_id;//活动ID
	private String entry_id;//报名ID
	private String sign_id;//签到ID
	private String activity_name;//活动名称
	private String activity_start_time;//活动开始时间
	private String activity_end_time;//活动结束时间
	private String activity_image;//活动图片
	
	//下一个页面
	private String activity_entry_end_time;//活动报名截止时间
	private String is_sign;// 是否需要签到(0:否 1:是)
	private String activity_address;//活动地址
	private String activity_navigation;//活动导航地址
	private String longitude_latitude;//活动经纬地址
	private String activity_link_man;//活动联系人
	private String activity_link_phone;//活动联系人电话
	private String activity_content;//活动内容
	
}
