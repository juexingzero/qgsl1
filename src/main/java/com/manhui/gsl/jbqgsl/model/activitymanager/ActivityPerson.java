package com.manhui.gsl.jbqgsl.model.activitymanager;

import lombok.Data;

/**
 * @Title: ActivityPerson.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(活动人员表)
 * @author WangSheng
 * @date 2018年11月07日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class ActivityPerson {
	
	private String activity_person_id;	//活动人员表id
	private String activity_id;//活动id
	private String member_id;	//会员id
	private String sh_name;		//商会名称
	private String member_name;	//团体/企业名称
	private String member_job;	//商会职务
	private String member_linkman_name;	//联系人
	private String member_linkman_phone;		//手机号码
	private String create_time;	//创建时间
	private String update_time;	//修改时间

}
