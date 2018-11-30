package com.manhui.gsl.jbqgsl.model.activitymanager;

import lombok.Data;

/**
 * @类名称 ActivityInfo.java
 * @类描述 活动信息
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月17日 下午15:36:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年10月17日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table activity_info
 */
@Data
public class ActivityInfo {
    //fields
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
    private String  activity_image;      //活动图片
    private String  activity_content;    //会议内容
    private String  sign_qrcode;         //签到二维码
    private String  activity_state;      //活动状态（0：未发布，1：未开始，2：进行中，3：已结束，4：已撤销，默认：0）
    private String  is_delete;           //是否删除（0：否，1：是，默认：0）
    private String is_sign;				//是否需要签到(0:否 1:是)
    private String  create_time;         //创建时间
    private String  update_time;         //修改时间
    
    private Object ImgObj;				//图片回显
    //keywords
    private Integer pageNo;              //开始页数
    private Integer pageSize;            //查询总数
}
