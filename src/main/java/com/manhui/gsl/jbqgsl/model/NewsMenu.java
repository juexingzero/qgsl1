package com.manhui.gsl.jbqgsl.model;

import lombok.Data;
import java.util.List;

/**
 * 新闻栏目管理 表 tb_news_menu
 */
@Data
public class NewsMenu {
    private Integer       menuId;      //栏目id
    private String        menuName;    //栏目名称
    private Integer       superiorId;  //上级Id
    private String        createUserId;//创建人
    private String        createTime;  //创建时间
    private Integer       state;       //状态(0：未使用，1：已使用，2：已删除，引用 枚举 NewsMenu.menuState)
    private Integer       sequence;    //顺序，越大越靠前，可重复
    private String        menuUrl;     //栏目链接
    private String        updateUserid;//修改人
    private String        updateTime;  //修改时间
    private String        fileImg;     //栏目图片路径
    private String        fileImgName; //栏目图片原名称
    private String        menuModel;   //栏目模型
    private Integer       type;        //类型(0：根节点，1：子节点)
    private String        remark;      //备注
    private List<Integer> stateList;   //状态集合
    private String        superiorName;//上级name
    private Boolean       checked;     //是否选中
    private Object        imgObj;      //图片回显

    private List<Integer> menuIdList;//id集合
}
