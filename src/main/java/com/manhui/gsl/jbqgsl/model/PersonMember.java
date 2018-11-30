


package com.manhui.gsl.jbqgsl.model;//

import lombok.Data;//

/**
* @Title: PersonMember.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年10月22日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class PersonMember {
//	个人会员注册--个人资料
	private  String user_id;//用户ID
	private  String member_id;//用户ID
	private  String concats_name;//姓名
	private  String sex;//性别（0：女，1：男，默认0）
	private  String birthday;//出生日期
	private  String id_card_number;//省份证号码
	private  String education;//学历
	private  String position;//职称
	private  String political_outlook;//政治面貌
	private  String ethnic;//民族
	private  String nationality;//国籍
	private  String company_name;//工作单位
	private  String company_position;//"职务
	private  String industry;//从事行业
	private  String native_place;//籍贯
	private  String family_address;//家庭地址
	private  String is_marry;//婚否（0：未婚，1：已婚，默认0）
	private  String concats_telephone;//手机号码
	private  String concats_phone;//座机
	private  String email;//邮箱
	private  String organization_title;//（1：主席，2：副主席，3：副会长，4：秘书长，5：常委，6：执委，7:请选择 8:目前正在申请,没有职务默认1）
	private  String main_social_position;//主要社会职务
	private  String personal_profile;//个人简介
	private  String honors;//所获荣誉
	private  String person_info_state;//个人信息状态(0:未填写,1:已填写)
	//证件资料
	private  String register_img;//登记照片
	private  String id_card_image_face;//身份证正面
	private  String id_card_image_back;//身份证反面
	private  String card_info_state;//个人证件资料(0:未填写,1:已填写)
	private  String all_info_state;//所有状态(0:表示个人资料以及证件资料没有填写,新增,1:标志修改)
	
	private  String member_type;//会员类型（1：个人，2：企业，默认1）
	private  String first_approve_time;//第一次申请时间
	private  String second_approve_time;//第二次申请时间
	private  String data_source;//(后台1,App端0)
	private  String apply_time;//申请时间
	private String approve_status;//审核状态（0：未审核，1：初审通过，2：初审拒绝，3：二审通过，4：二审拒绝，默认0）
	private  String create_time;//创建时间
	private  String update_time;//修改时间
	
	private String first_approve_fail_part; //第一次审评失败部分(1:企业资料,2:法人资料,3:证件资料4:个人资料)
	private String second_approve_fail_part;//初审未通过原因
	private String first_approve_fail_reason;//第二次审评失败部分(1:企业资料,2:法人资料,3:证件资料4:个人资料)
	private String second_approve_fail_reason;//二审未通过原因
	private String is_effect;//是否生效（0：否，1：是，默认1）
	private String approve_organization_id;//申请商会id
	private String approve_organization_name;//申请商会名称
	
	
}
