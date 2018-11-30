package com.manhui.gsl.jbqgsl.model;

import com.manhui.gsl.jbqgsl.common.util.PageModel;
import lombok.Data;

/**
 * 新闻操作表
 * 收藏，点赞，评论，
 * tb_news_operating
 */
@Data
public class NewsOperating extends PageModel {
    private Integer operatingId;
    //用户id
    private String userId;
    //用户姓名
    private String userName;
    //新闻id
    private Integer newsId;
    //新闻名称
    private String newsName;
    //类型，1=评论,2=收藏,3=查看,4=点赞
    private Integer type;
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;
    //1=保存，2=删除
    private Integer state;
    //内容
    private String content;
    //评论状态,1=待审核，2=合格通过，3=不合格，不通过
    private Integer commentState;
    //评论 不合法原因
    private String noReason;//
    //手机id
    private String phoneId;
    //不与数据库同步
    private String head_img;
    private String assignPeople; //指派人

    private Integer commentNums;//评论数量
    private Integer collectionNums;//收藏数量
    private Integer viewNums;//查看数量
    private Integer likeNums;//点赞数量
    private String        releaseTime;   //发布时间
    private String        newsImg;       //首页图片地址
    private String imgType;//是图片还是视频类型,img=图片类型，video=视频类型
    private String        infoSource;    //信息来源

    private String newsIds;//多个新闻id 用逗号隔开
    private String typeStr;//类型转换

    private String commentStateStr;
    private String menuId;
    private String menuName;
}
