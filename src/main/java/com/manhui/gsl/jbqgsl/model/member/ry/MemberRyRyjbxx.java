package com.manhui.gsl.jbqgsl.model.member.ry;

import lombok.Data;

/**
 * @类名称 MemberRyRyjbxx.java
 * @类描述 个人入会 人员基本信息数据子类表
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月31日 上午9:16:23
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年10月31日                创建
 *     ----------------------------------------------
 *       </pre>
 *
 * @table member_ry_ryjbxx
 */

@Data
public class MemberRyRyjbxx {
    private String id;              //ID
    private String rybm;              //人员编码
    private String xm;              //姓名
    private String xb;              //性别
    private String zt;              //状态（参考“状态”）
    private String csrq;              //出生日期
    private String csd;              //出生地（参考“行政区划”）
    private String jg;              //籍贯（参考“行政区划”）
    private String mz;              //民族（参考“民族”）
    private String hyzk;              //婚姻状况（参考“婚姻状况”）
    private String sg;              //身高
    private String xx;              //血型（参考“血型”）
    private String zy;              //职业（参考“职业分类与代码”）
    private String zc;              //职称（参考“职称代码”）
    private String ryfl;              //人员分类（参考“个人分类”）
    private String bz;              //备注
    private String ryzjlx;              //人员证件类型（参考：“证件类型”）
    private String ryzjbm;              //人员证件编码
    private String state;               //状态
    private String peopleType;           //人类型
}
