package com.manhui.gsl.jbqgsl.model.member;

import lombok.Data;

import java.util.List;

/**
 * 会员 入会管理表
 * table :member_join_manager
 */
@Data
public class MemberJoinManager {
    private String joinId;              //id
    private String createTime;              //创建 时间
    private String updateTime;              //修改时间
    private String createUserId;              //创建人id
    private String createUserName;              //创建人姓名
    private String memberType;              //入会类型，有个人入会，企业入会，团体入会
    private String memberId;              //入会信息关联表id
    private String state;              //状态,新增状态，送审状态，审批通过状态，审批不通过状态
    private String joinObjId;           //入会对象id
    private String joinObjName;         //入会对象名称
    private String position;            //商会职务
    //不与数据库同步
    private List<String> stateList;        //状态集合

    private List<String> joinObjIdList;//入会对象id 集合
    private String approve_fail_reason;//审批未通过原因
    private String approve_fail_info;//审批未通过类别

    private String joinObjLxr;      //加入商会联系
    private String joinObjLxrDh;        //加入商会联系人电话
}
