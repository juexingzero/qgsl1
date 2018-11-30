

package com.manhui.gsl.jbqgsl.model;//

import lombok.Data;//

/**
* @Title: CompanyMember.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(团体会员实体类)
* @author LiuBin
* @date 2018年10月22日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class GroupMember {
	//企业资料
	private String user_id ;// 		用户id
	private String chinese_name;//	企业名称
	private String english_name;//	法人代表
	private String card_type;//     统一社会信用证号(18位)
	private String card_number;//  税务号（老税号为15位数，统一社会信用代码（新税号）为18位数。老税号310打头，统一社会信用代码（新税号）91打头）
	private String register_date;//企业性质
	private String bussiness_managerUnit;//成立时间（格式：yyyy-MM-dd）
	private String register_managerUnit;//行业分类
	private String member_num;//职工人数
	private String company_member_num;//党员数
	private String group_member_num;//电话
	private String personal_member_num;//传真
	private String supervision_meeting;//邮编
	private String partGroupName;//企业地址
	private String partGroupCreatTime;//大专以上文化人数量
	private String partMemberNum;//安置下岗人数
	private String partHeader;//党、团工会情况
	private String partHeaderPhone;//网址
	private String staff_name1;//邮箱
	private String staff_title1;//是否被认证为高新技术企业（0：否，1：是，默认1）
	private String staff_phone;//高新技术认证部门
	private String staff_name2;//是否获得外贸自营进出口权（0：否 1：是，默认1）
	private String staff_name3;//进出口权 批准部门
	private String second_approve_time;//是否通过质量管理、质量保证系列认证标准（0：否，1：是，默认1）
	private String briefIntroduce_post;//质量认证部门
	private double group_organization_work_brief;//注册资金（万元，默认0.0）
	private double inspection_result;//资产（万元，默认0.0)
	private double email;//所有者权益（万元，默认0.0）
	private double bussiness_fax;//销售收入（万元，默认0.0）
	private String law_name;//主要经营范围
	private String staff_phone3;//参加社会保险情况
	private String staff_title3;//公益事业贡献
	private String law_sex;//何时获得何种奖励、荣誉称号
	private String law_birthday;//企业资料状态 (0:未填写,1:填写)
	
	//法人资料
	private String ethnic;//法人姓名
	private String law_native_place;//法人性别(0：女，1：男，默认0)
	private String law_partisan;//法人出生日期（格式：yyyy-MM-dd）
	private String law_join_partTime;//法人籍贯
	private String law_education;//法人地址
	private String law_telePhone;//法人身份证号码
	private String law_title;//法人民族
	private String company_post;//法人党派（1：中国共产党，2：中国国民党革命委员会，3：民主同盟等等共八个详情参考sys_param表）
	private String gsl_post;//法人学历（1：小学，2：初中，3：高中，4：专科，5：本科，6：研究生，7：博士，8：博士后，默认5）
	private String npc_post;//"法人职称
	private String cppcc_post;//法人职务
	private String highest_education_image;//法人邮编
	private String group_register_image;//法人电话
	private String register_img;//法人手机
	private String id_card_image_face;//办企业前工作单位
	private String id_card_image_back;//法人人大职务
	private String apply_time;//政协职务
	private String approve_status;//社会职务
	private String law_info_state;//法人简历
	private String card_info_state; //法人资料(0:未填写,1:填写)
	//法人证件资料
	private String all_info_state;//法人登记照片
	private String is_effect;//法人身份证正面照片
	private String second_approve_fail_reason;//法人身份证反面照片
	private String second_approve_fail_part;//统一社会信用证复印件
	private String first_approve_fail_reason;//证件资料(0:未填写,1:已填写)
	//法人联系人
	private String first_approve_fail_part;//企业联系人
	private String first_approve_time;//商会职务1：主席，2：副主席，3：副会长，4：秘书长，5：常委，6：执委，7:请选择 8:目前正在申请,没有职务默认1）
	private String create_time;//"联系人职位
	private String update_time;//联系人手机
	private String post_num;//联系人座机
	private String communication_address;//个人资料(0:未填写,1:已填写)
	private String staff_phone2;//状态总和(0:插入新增,1:修改)
	
	//数据库表中的字段
	private String member_id; //成员ID
	private String member_type; //1:个人 2:企业
	private String data_source;//数据来源 --(后台1,App端0)
	
	private String approve_organization_id;//申请商会id
	private String approve_organization_name;//申请商会名称
	
	
}
