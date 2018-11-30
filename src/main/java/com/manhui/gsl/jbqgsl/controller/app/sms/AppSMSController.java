package com.manhui.gsl.jbqgsl.controller.app.sms;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.util.RedisUtil;
import com.manhui.gsl.jbqgsl.common.util.SMSUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 手机 app 端短信管理接口
 */
@Controller
@RequestMapping(AppSMSController.ROOT_URL)
public class AppSMSController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_APP + "/appSms";
    private static final Logger logger = LoggerFactory.getLogger( AppSMSController.class );

    @Autowired
    private SMSUtil sMSUtil;
    @Autowired
	private RedisUtil redisUtil;
    
    @ApiOperation( value = "app发送验证码", notes = "app发送验证码" )
    @ApiImplicitParams( {
        @ApiImplicitParam( paramType = "query", required = true, name = "phoneNumber", value = "接收手机号码", dataType = "字符串" ),
        @ApiImplicitParam( paramType = "query", required = true, name = "valiCodeType", value = "验证码类型（1：注册时验证，2：修改密码时验证）", dataType = "字符串" )
    } )
    @RequestMapping( value = "/appSendSms", method = RequestMethod.POST )
    @ResponseBody
    public ResultMessage appSendSms(
            @RequestParam(value = "phoneNumber", required = true)String phoneNumber,
            @RequestParam(value = "valiCodeType", required = true)String valiCodeType) throws Exception {
        //验证电话号码
        if(StringUtils.isBlank(phoneNumber) && phoneNumber.length() != 11){
            return new ResultMessage("号码格式有误");
        }
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phoneNumber);
        boolean isMatch = m.matches();
        if(isMatch){
            //发送短信
            String content_prefix = "";
            String content_suffix = "";
            //注册时验证
            if("1".equals( valiCodeType )) {
                content_prefix = "您的注册验证码为：";
                content_suffix = "，请于5分钟内输入验证，请勿向他人泄露。";
            }
            //修改密码时验证
            else if("2".equals( valiCodeType )){
                content_prefix = "您正在修改密码，短信验证码为：";
                content_suffix = "，请于5分钟内输入验证，请勿向他人泄露。";
            }
            //企业之声咨询时验证
            else if("3".equals( valiCodeType )){
                content_prefix = "您的短信验证码为：";
                content_suffix = "，请于5分钟内输入验证，请勿向他人泄露。";
            }
            String timeStr = new Date().getTime()+"";
            String verification_code = timeStr.substring(timeStr.length()-6,timeStr.length());//截取毫秒时间的后6位
            int state = sMSUtil.sendSms(phoneNumber,content_prefix+verification_code+content_suffix,"");
            logger.info( "------------验证码发送------------\n"+state+" , content: "+content_prefix+verification_code+content_suffix );
            if(state > 0){
                //发送短信成功，保存验证码到redis
                String key = phoneNumber+"_register";
                //设置验证码有效时间
                long fail_time = sMSUtil.verification_code_fail_time*60;
               // RedisUtil redisUtil = new RedisUtil();
                boolean flag = redisUtil.set(key,verification_code,fail_time);
                if(!flag){
                    return new ResultMessage("系统错误，请稍后重试!");
                }
            }else{
                //返回错误新闻
                logger.info("============="+SMSUtil.getMessageStr(state)+"================");
               return new ResultMessage("发送短信错误，请稍后重试！或向我们反馈，我们将尽快处理");
            }
        } else {
            return new ResultMessage("号码格式有误");
        }
        return new ResultMessage();
    }

    /**
     * 短信验证码验证
     * @param phoneNumber 电话号码
     * @param code 验证码
     * @return
     * @throws Exception
     */
    @RequestMapping( "/smsCodeVerification" )
    @ResponseBody
    public ResultMessage smsCodeVerification(String phoneNumber,String code) throws Exception {
        //验证电话号码
        if(StringUtils.isBlank(phoneNumber) && phoneNumber.length() != 11){
            return new ResultMessage("电话号码格式有误！");
        }
        if(StringUtils.isBlank(code)){
            return new ResultMessage("参数错误！");
        }
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phoneNumber);
        boolean isMatch = m.matches();
        if(isMatch){
            //redis 获得验证码
            String key = phoneNumber+"_register";
            //设置验证码有效时间
            //RedisUtil redisUtil = new RedisUtil();
            Object obj = redisUtil.get(key);
            if(obj == null){
                return new ResultMessage("验证码已过期，请重新获取!");
            }
            String sys_code = obj.toString();
            if(code.equals(sys_code)){
                return new ResultMessage();
            }else{
                return new ResultMessage("验证码错误!");
            }
        } else {
            return new ResultMessage("号码格式有误!");
        }
    }
}
