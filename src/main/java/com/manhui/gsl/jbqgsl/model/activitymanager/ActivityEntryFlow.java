package com.manhui.gsl.jbqgsl.model.activitymanager;

import lombok.Data;

/**
 * @Title: ActivityEntryFlow.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(活动报名流水表)
 * @author WangSheng
 * @date 2018年11月26日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class ActivityEntryFlow {

	private String entry_id;			//报名id
	private String activity_id;			//活动id
	private String member_id;			//会员id
	private String member_name;			//团体/企业名称
	private String member_linkman_name;	//联系人姓名
	private String member_linkman_sex;	//联系人性别(男:XB-01 女:XB-02)默认:男
	private String member_linkman_title;//联系人职务
	private String member_linkman_phone;//联系人手机
	private String entry_num;			//报名人数
	private String remarks;				//备注
	private String sign_time;			//签到时间
	private String create_time;			//创建时间
	private String update_time;			//修改时间

}
