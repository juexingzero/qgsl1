package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @类名称 MessageInfo.java
 * @类描述 消息信息
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月23日 上午09:16:07
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月23日                创建
 *     ----------------------------------------------
 *       </pre>
 * 
 * @table message_info
 */
@Data
public class MessageInfo {
    //fields
    private String  message_id;      //消息ID
    private String  message_type;    //消息类型(1：广播通知，2：工作任务，3：意见回复，默认1)
    private String  message_mode;    //消息形式(1：文本，2：图片，3：语音，4：视频，5：混合，默认1)
    private String  message_content; //消息内容
    private String  creator_id;      //创建人ID
    private String  creator_name;    //创建人名称
    private String  create_time;     //创建时间
    private String  update_time;     //修改时间
    //keywords
    private Integer pageNo;          //开始页数
    private Integer pageSize;        //查询总数
}
