

package com.manhui.gsl.jbqgsl.model;//

import lombok.Data;//

/**
* @Title: CompanyMember.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(企业会员实体类)
* @author LiuBin
* @date 2018年10月22日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class CompanyMember {
	//企业资料
	private String user_id ;// 		用户id
	private String company_name;//	企业名称
	private String law_president;//	法人代表
	private String lc_number;//     统一社会信用证号(18位)
	private String tax_number;//  税务号（老税号为15位数，统一社会信用代码（新税号）为18位数。老税号310打头，统一社会信用代码（新税号）91打头）
	private String enterprise_type;//企业性质
	private String founding_time;//成立时间（格式：yyyy-MM-dd）
	private String industry;//行业分类
	private String worker_num;//职工人数
	private String part_num;//党员数
	private String company_telephone;//电话
	private String company_fax;//传真
	private String zip_code;//邮编
	private String company_address;//企业地址
	private String above_junior_college_num;//大专以上文化人数量
	private String no_work_num;//安置下岗人数
	private String labour_union;//党、团工会情况
	private String company_web;//网址
	private String company_email;//邮箱
	private String high_new_technology_enterprises;//是否被认证为高新技术企业（0：否，1：是，默认1）
	private String certification_department;//高新技术认证部门
	private String import_export_foreign_trade;//是否获得外贸自营进出口权（0：否 1：是，默认1）
	private String approve_depart;//进出口权 批准部门
	private String is_iso;//是否通过质量管理、质量保证系列认证标准（0：否，1：是，默认1）
	private String iso_register_depart;//质量认证部门
	private double registered_money;//注册资金（万元，默认0.0）
	private double assets;//资产（万元，默认0.0)
	private double owner_right;//所有者权益（万元，默认0.0）
	private double sales_revenue;//销售收入（万元，默认0.0）
	private String business_participants;//主要经营范围
	private String social_insurance;//参加社会保险情况
	private String public_service;//公益事业贡献
	private String award_honors;//何时获得何种奖励、荣誉称号
	private String company_info_state;//企业资料状态 (0:未填写,1:填写)
	
	//法人资料
	private String law_name;//法人姓名
	private String law_sex;//法人性别(0：女，1：男，默认0)
	private String law_birthday;//法人出生日期（格式：yyyy-MM-dd）
	private String law_native_place;//法人籍贯
	private String law_address;//法人地址
	private String law_id_card_number;//法人身份证号码
	private String law_ethnic;//法人民族
	private String law_party;//法人党派（1：中国共产党，2：中国国民党革命委员会，3：民主同盟等等共八个详情参考sys_param表）
	private String law_education;//法人学历（1：小学，2：初中，3：高中，4：专科，5：本科，6：研究生，7：博士，8：博士后，默认5）
	private String law_title;//"法人职称
	private String law_duty;//法人职务
	private String law_zip_code;//法人邮编
	private String law_phone;//法人电话
	private String law_telephone;//法人手机
	private String before_work_part;//办企业前工作单位
	private String law_people_congress_position;//法人人大职务
	private String law_cppcc_position;//政协职务
	private String law_social_position;//社会职务
	private String law_resume;//法人简历
	private String law_info_state; //法人资料(0:未填写,1:填写)
	//法人证件资料
	private String register_img;//法人登记照片
	private String id_card_image_face;//法人身份证正面照片
	private String id_card_image_back;//法人身份证反面照片
	private String lc_number_image;//统一社会信用证复印件
	private String card_info_state;//证件资料(0:未填写,1:已填写)
	//法人联系人
	private String concats_name;//企业联系人
	private String organization_title;//商会职务1：主席，2：副主席，3：副会长，4：秘书长，5：常委，6：执委，7:请选择 8:目前正在申请,没有职务默认1）
	private String concats_title;//"联系人职位
	private String concats_telephone;//联系人手机
	private String concats_phone;//联系人座机
	private String concats_info_state;//个人资料(0:未填写,1:已填写)
	private String all_info_state;//状态总和(0:插入新增,1:修改)
	
	//数据库表中的字段
	private String member_id; //成员ID
	private String is_effect; //0:作废,1:生效
	private String member_type; //1:个人 2:企业
	private String apply_time;//申请时间
	private String create_time;//创建时间
	private String update_time;//修改时间
	private String data_source;//数据来源 --(后台1,App端0)
	private String approve_status;//审核状态（0：未审核，1：初审通过，2：初审拒绝，3：二审通过，4：二审拒绝，默认0）
	
	private String first_approve_fail_part; //第一次审评失败部分(1:企业资料,2:法人资料,3:证件资料4:个人资料)
	private String second_approve_fail_part;//初审未通过原因
	private String first_approve_fail_reason;//第二次审评失败部分(1:企业资料,2:法人资料,3:证件资料4:个人资料)
	private String second_approve_fail_reason;//二审未通过原因
	private String approve_organization_id;//申请商会id
	private String approve_organization_name;//申请商会名称
	
	
}
