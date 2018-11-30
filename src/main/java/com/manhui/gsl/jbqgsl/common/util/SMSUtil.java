package com.manhui.gsl.jbqgsl.common.util;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 短信 操作
 */
@Configuration
@ConfigurationProperties()
@PropertySource("classpath:config.properties")
@Data
@Component
public class SMSUtil {

    //短信接口调用账号
    @Value("${sms_interface_account}")
    private String sms_interface_account;
    //短信接口调用密码
    @Value("${sms_interface_pwd}")
    private String sms_interface_pwd;

    //短信注册内容前缀
    @Value("${sms_interface_register_account_prefix}")
    public String sms_interface_register_account_prefix;
    //短信注册内容后缀
    @Value("${sms_interface_register_account_suffix}")
    public String sms_interface_register_account_suffix;

    //短信验证码失效时间
    @Value("${verification_code_fail_time}")
    public int verification_code_fail_time;
    /**
     * 发送短信
     * @param phone 电话号码,多个已英文逗号隔开
     * @Param content 发送内容
     * @Param send_time 发送时间(定时发送) 为空时立即发送，格式长度必须14位，20180912152435代表2018年9月12日15时24分35秒
     * @return
     */
    public int sendSms(String phone,String content,String send_time) throws Exception{
        URL url = null;
        String send_content=URLEncoder.encode(content.replaceAll("<br/>", " "), "GBK");//发送内容
        url = new URL("https://sdk2.028lk.com/sdk2/BatchSend2.aspx?CorpID="+sms_interface_account+"&Pwd="+sms_interface_pwd+"&Mobile="+phone+"&Content="+send_content+"&Cell=&SendTime="+send_time);
        BufferedReader in = null;
        int inputLine = 0;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = new Integer(in.readLine()).intValue();
        } catch (Exception e) {
            System.out.println("网络异常,发送短信失败！");
            inputLine=-2;
        }
        return inputLine;
    }


    public static String getMessageStr(int state) throws Exception{
        if(state>0 ){
            return "发送成功";
        }else if(state == -1){
            return "发送短信失败，账号未注册,请联系管理员！";
        }else if(state  == -2 || state == -3 || state == -6){
            return "发送短信失败,请联系管理员！";
        }else if(state == -5){
            return "余额不足，请充值！";
        }else if(state == -7){
            return "末尾未加签名,请联系管理员！";
        }else if(state == -8){
            return "发送内容过长，请重新输入！";
        }else if(state == -9){
            return "发送手机号码为空，或手机号不正确！";
        }else if(state == -10){
            return "定时时间不能小于系统当前时间!";
        }else if(state == -11){
            return "黑名单手机号!";
        }else if(state == -100){
            return "IP黑名单,请联系管理员!";
        }else if(state == -102){
            return "账号黑名单,请联系管理员!";
        }else if(state == -103){
            return "IP未导白,请联系管理员!";
        }

        return "系统异常";
    }
}
