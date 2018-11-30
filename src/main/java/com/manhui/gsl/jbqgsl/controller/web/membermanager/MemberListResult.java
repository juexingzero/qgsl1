package com.manhui.gsl.jbqgsl.controller.web.membermanager;

import lombok.Data;

/**
 * @类名称 MemberListResult.java
 * @类描述 会员列表
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年11月5日 下午6:22:31
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年11月5日               创建
 *     ----------------------------------------------
 *       </pre>
 */
@Data
public class MemberListResult {
    private String joinId;              //会员商会中间表ID
    private String app_user_id;         //创建人id
    private String app_user_name;       //创建人姓名
    private String commerce_id;         //商会ID
    private String commerce_name;       //商会名称
    private String member_id;           //会员ID
    private String member_name;         //会员名称/姓名
    private String member_type;         //会员类型（1：个人、2：企业、3：团体，默认1）
    private String linkman_name;        //联系人姓名
    private String linkman_phone;       //联系人手机
    private String apply_time;          //申请时间
    private String approve_id;          //会员审批记录表ID
    private String approve_status;      //审核状态（HYSP-01：待审批，HYSP-02：审批通过，HYSP-03：审批拒绝）
    private String approve_time;        //审核时间
    private String approve_fail_info;   //审批未通过资料类别（参考：“会员审批不通过类别”）
    private String approve_fail_reason; //审批未通过原因
}
