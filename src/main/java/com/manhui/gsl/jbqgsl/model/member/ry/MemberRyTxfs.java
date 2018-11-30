package com.manhui.gsl.jbqgsl.model.member.ry;

import lombok.Data;

/**
 *  个人入会 通讯方式记录表
 * @table member_ry_txfs
 */

@Data
public class MemberRyTxfs {
    private String id;              //ID
    private String rybm;              //人员 ID
    private String gzdwtxdz;              //工作单位通信地址
    private String gzdwyzbm;              //工作单位邮政编码
    private String gsmc;               //公司名称
    private String gszw;                //公司职务
    private String gzdh;              //工作电话
    private String jtzz;              //家庭住址
    private String jtzzyzbm;              //家庭住址邮政编码
    private String se;              //省（参考“行政区划”）
    private String si;              //市（参考“行政区划”）
    private String xa;              //县（参考“行政区划”）
    private String yddh;              //移动电话
    private String zzdh;              //住宅电话
    private String dzyx;              //电子邮箱
    private String czhm;              //传真号码
    private String gddh;              //固定电话（座机）
    private String qq;              //QQ
    private String wxh;              //微信号
    private String ryid;            //人员表id

}
