package com.manhui.gsl.jbqgsl.model.datareport;

import lombok.Data;

/**
 * @类名称 DataEnterprise.java
 * @类描述 企业数据上报表
 * @作者 Jiangxiaosong
 * @创建时间 2018年11月21日18:18:55
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     Jiangxiaosong    2018年11月21日       创建
 *     ----------------------------------------------
 *       </pre>
 *
 * @table member_data_enterprise
 */
@Data
public class DataEnterprise {
    private String id;                  //ID
    private String qy_id;              //企业ID
    private String template_id;       //模板ID
    private String zyywly1;            //主营业务领域1
    private String zyywly2;            //主营业务领域2
    private String zyywly3;            //主营业务领域3
    private String zyszebl1;           //占营收总额比例1
    private String zyszebl2;           //占营收总额比例2
    private String zyszebl3;           //占营收总额比例3
    private String zycp;                //主要产品
    private String bndysze;             //本年度营收总额
    private String bndlrze;             //本年度利润总额
    private String bndshjlr;            //本年度税后净利润
    private String bndzcze;             //本年度资产总额
    private String bndgdzcjz;           //本年度固定资产净值
    private String bndjzcze;            //本年度净资产总额
    private String bndjsze;             //本年度缴税总额
    private String bndyffy;             //本年度研发费用
    private String bndckze;             //本年度出口总额
    private String bndhwsr;             //本年度海外收入
    private String bndnmygrs;           //本年度年末员工人数
    private String bndnmhwgy;           //本年度年末海外雇员
    private String sndysze;              //上年度营收总额
    private String sndlrze;              //上年度利润总额
    private String sndshjlr;            //上年度税后净利润
    private String sndzcze;              //上年度资产总额
    private String sndgdzcjz;            //上年度固定资产净值
    private String sndjzcze;            //上年度净资产总额
    private String sndjsze;             //上年度缴税总额
    private String sndyffy;             //上年度研发费用
    private String sndckze;             //上年度出口总额
    private String sndhwsr;             //上年度海外收入
    private String sndnmygrs;           //上年度年末员工人数
    private String sndnmhwgy;           //上年度年末海外雇员
}
