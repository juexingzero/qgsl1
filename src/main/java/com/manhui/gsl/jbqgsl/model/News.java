package com.manhui.gsl.jbqgsl.model;

import java.util.List;
import com.manhui.gsl.jbqgsl.common.util.PageModel;
import lombok.Data;

/**
 * 新闻发布管理 表 tb_news_menu
 */
@Data
public class News extends PageModel {
    private Integer       newsId;        //新闻id
    private String        newsName;      //新闻标题
    private String        newsImg;       //首页图片地址
    private String        newsImgName;   //首页图片原名称
    private String        infoSource;    //信息来源
    private Integer       priority;      //优先级
    private String        newsType;      //新闻类型 枚举newsType
    private String        externalUrl;   //外部链接地址
    private String        remark;        //内容摘要
    private String        newsContent;   //新闻内容
    private String        createTime;    //创建时间
    private Integer       state;         //状态，调用枚举 NewsEnum.NewsState
    private String        releaseUserId; //发布人id
    private String        releaseArea;   //发布区域
    private String        releaseTime;   //发布时间
    private String        updateUserId;  //修改人
    private String        updateTime;    //修改时间
    private Integer commentNums;//评论数量
    private Integer collectionNums;//收藏数量
    private Integer viewNums;//查看数量
    private Integer likeNums;//点赞数量
    private Integer receiptNums;//回执数量
    private String videoUrl;//视频地址
    private String imgType;//是图片还是视频类型,img=图片类型，video=视频类型
    //不与数据库同步字段
    private String        menuNames;     //所属栏目
    private String        menuIds;       //栏目id
    private String menuModel;//栏目模型
    private List<Integer> stateList;     //状态集合
    private Object        imgObj;        //用于图片回显
    private Integer menuId;//栏目id

    private List<Integer> menuIdList;

    private String deptId;//发布新闻人员部门id
    private String releaseUserName;//发布人姓名
    private String deptName;//发布人部门
    private String head_img;//商会班子头像

    private String stateStr;//状态字符串
}
