package com.manhui.gsl.jbqgsl.model.commerce;

import lombok.Data;

/**
 * @类名称 MemberJgShxx.java
 * @类描述 商会信息
 * @作者 Jiangxiaosong
 * @创建时间 2018年11月1日17:59:40
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     Jiangxiaosong    2018年11月1日17:59:53                创建
 *     ----------------------------------------------
 *       </pre>
 *
 * @table member_jg_shxx
 */
@Data
public class MemberJgShxx {
    private String ID;              //ID
    private String SHBM;           //商会编码（参考“组织机构编码”）
    private String SHMC;           //商会名称
    private String ZZJGBM;          //组织机构编码（参考“组织机构编码”）
    private String SHLX;            //商会类型名称
    private String SFZMZBMDJ;       //是否在民政部门登记
    private String SFJLDZZ;         //是否建立党组织
    private String DJMZ;            //登记名字
    private String DJGRDH;          //登记个人电话
    private String JJZS;            //基金总数
    private String CZ;              //财政
    private String ZZJLN;           //组织建立年
    private String ZZJLSJ;          //组织建立时间
    private String BGDD;            //办公地点
    private String LYZJ;            //领域 ID(2级关联1级ID)
    private String LXR;             //联系人 ID
    private String SJ;              //手机
    private String DH;              //电话
    private String YX;              //邮箱
    private String DZ;              //地址
    private String PX;              //排序
    private String JS;              //介绍
}
