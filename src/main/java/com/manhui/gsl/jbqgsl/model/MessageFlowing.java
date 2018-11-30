package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @类名称 MessageFlowing.java
 * @类描述 消息流水
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月23日 上午09:24:15
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
 * @table message_flowing
 */
@Data
public class MessageFlowing {
    //fields
    private String message_id;   //消息ID
    private String flowing_id;   //流水ID
    private String send_id;      //发送方ID
    private String send_name;    //发送方名称
    private String receive_id;   //接收方ID
    private String receive_name; //接收方名称
    private String send_time;    //发送时间
    private String is_read;      //是否已读(0：未读，1：已读，默认0)
    private String read_time;    //已读时间
    //keywords
    private Integer pageNo;      //开始页数
    private Integer pageSize;    //查询总数
    private String  userSuggest_id;        //建议表id
}
