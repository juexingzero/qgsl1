package com.manhui.gsl.jbqgsl.controller.web.activity;

import lombok.Data;

/**
 * @类名称 ActivityResult.java
 * @类描述 活动信息（页面展示）
 * @作者 WangSheng
 * @创建时间 2018年11月23日 下午15:36:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     WangSheng    2018年10月17日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * 
 */
@Data
public class ActivityResult {
    private String  activity_id;         //活动ID
    private String  activity_name;      //活动主题
    private String  activity_start_time; //活动开始时间（格式：yyyy-MM-dd HH:mm）
    private String  activity_end_time;   //活动结束时间（格式：yyyy-MM-dd HH:mm）
    private String activity_entry_end_time;//活动报名截止时间（格式：yyyy-MM-dd HH:mm）
    private String  activity_address;    //活动地址
    private String  activity_navigation; //活动导航地址
    private String  longitude_latitude;  //活动导航经纬度（格式：经度，纬度）
    private String activity_link_man;	//活动联系人
    private String activity_link_phone;	//活动联系人电话
    private String  activity_file;       //活动资料
    private Object  activity_image;      //活动图片 回显
    private String  activity_content;    //会议内容
    private String is_sign;				//是否需要签到(0:否 1:是)
}
