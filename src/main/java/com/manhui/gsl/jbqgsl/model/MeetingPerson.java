package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @Title: MeetingPerson.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(参会人员表)
 * @author WangSheng
 * @date 2018年10月17日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class MeetingPerson {
	
	private String meeting_id;	//会议表id
	private String sh_name;		//商会名称
	private String group_name;	//团体/企业名称
	private String office;		//商会职务
	private String person_name;	//联系人
	private String phone;		//手机号码
	private String vote_options;//投票选项
	private String create_time;	//创建时间
	private String update_time;	//修改时间
	private String member_id;		//人员信息id
	private String sing_time;		//人员信息id

}
