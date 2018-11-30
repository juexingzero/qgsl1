package com.manhui.gsl.jbqgsl.controller.app.message;

import lombok.Data;

@Data
public class MessageFlowingResult {
    private String flowing_id;      //流水ID
    private String message_id;      //消息ID
    private String message_type;    //消息类型(1：广播通知，2：工作任务，3：意见回复，默认1)
    private String message_content; //消息内容
    private String send_id;         //发送方ID
    private String send_name;       //发送方名称
    private String send_time;       //发送时间
    private String receive_id;      //接收方ID
    private String receive_name;    //接收方名称
    private String is_read;         //是否已读(0：未读，1：已读，默认0)
    private String read_time;       //已读时间
}
