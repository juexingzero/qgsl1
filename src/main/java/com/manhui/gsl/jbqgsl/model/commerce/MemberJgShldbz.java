package com.manhui.gsl.jbqgsl.model.commerce;

import lombok.Data;
/**
 * @类名称 MemberJgShldbz.java
 * @类描述 商会领导班子表
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
 * @table member_jg_shldbz
 */
@Data
public class MemberJgShldbz {
    private String ID;              //ID
    private String SHBM;            //商会编码（参考“组织机构编码”）
    private String SHBMID;          //商会 ID
    private String JIE;             //届别
    private String RYBM;            //人员 ID
    private String SHZWLX;          //商会职务类型
    private String PX;              //排序
}
