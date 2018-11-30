package com.manhui.gsl.jbqgsl.model.activitymanager;

/**
* @Title: ActivityNews.java
* @Package com.manhui.gsl.jbqgsl.model
* @Description: TODO(活动新闻表)
* @author WangSheng
* @date 2018年11月07日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
import lombok.Data;

@Data
public class ActivityNews {
	private String activity_id; //活动ID
	private String release_column; //发布栏目
	private String news_title; //新闻标题
	private String information_source; //信息来源
	private String news_type; //新闻类型（0：头条新闻，1：图片新闻，默认：0）
	private String release_time; //发布时间（格式：yyyy-MM-dd HH:mm）
	private String content_abstract; //内容摘要
	private String news_picture; //内容摘要
	private String content_detail; //内容详情
	private String create_time;//创建时间
	private String update_time;//修改时间
}
