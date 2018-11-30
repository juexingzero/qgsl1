package com.manhui.gsl.jbqgsl.controller.web.meetingmanager;

import lombok.Data;

/**
* @Title: AttendeesResult.java
* @Package com.manhui.gsl.jbqgsl.controller.web.meetingmanager.AttendeesResult
* @Description: TODO(参会人员选择页面--人员实体类)
* @author WangSheng
* @date 2018年10月24日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class AttendeesResult {

	private String memberType;			//会员类型（HYLX-01：个人，HYLX-02：团体，HYLX-03：企业，默认1）
	private String joinObjId;			//入会对象id
	private String joinObjName;			//入会对象名称
	private String position;			//申请商会职务
	private String createTime;			//创建时间
	private String user_name;			//公司名称
	private String user_id;				//用户id
	private String contact;				//会员名称/姓名
	private String user_phone;			//用户手机
	private String joinId;				//会员id
	
}
