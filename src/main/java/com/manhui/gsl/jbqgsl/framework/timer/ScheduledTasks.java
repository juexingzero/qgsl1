package com.manhui.gsl.jbqgsl.framework.timer;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.RequestUtil;

/**
 * @类名称 ScheduledTasks.java
 * @类描述 定时任务
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月17日 下午2:46:02
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月17日             
 *     ----------------------------------------------
 *       </pre>
 */
@Component
@Configurable
@EnableScheduling
@PropertySource( "classpath:config.properties" )
public class ScheduledTasks {
    //检测并更新主题评价状态
    @Value( "${check_evaluate_state_url}" )
    private String check_evaluate_state_url;
    
    @Value( "${check_meeting_state_url}" )
    private String check_meeting_state_url;

    @Value( "${check_data_state_url}" )
    private String check_data_state_url;

    /**
     * @方法名称 checkAndUpdateEvaluateState
     * @功能描述 ：检测并更新主题评价状态
     * @作者 kevin
     * @创建时间 2018年8月17日 下午2:46:42
     */
    @Scheduled( cron = "0 */1 *  * * * " )
    public void checkAndUpdateEvaluateState() {
        String url = check_evaluate_state_url;
        String json = "{\"currentDate\":\"" + DateUtil.getDateTime( Constant.DATEMIN_PATTERN ) + "\"}";
        String mUrl=check_meeting_state_url;
        RequestUtil.invoker( "POST", url, null, json, false );
        RequestUtil.invoker( "POST", mUrl, null, json, false );
    }

    /**
     * @方法名称 checkAndUpdateDataState
     * @功能描述 ：检测并更新数据上报状态
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月22日19:32:38
     */
    @Scheduled( cron = "0 */1 *  * * * " )
    public void checkAndUpdateDataState() {
        String url = check_data_state_url;
        String json = "{\"currentDate\":\"" + DateUtil.getDateTime( Constant.DATEMIN_PATTERN ) + "\"}";
        RequestUtil.invoker( "POST", url, null, json, false );
    }
}
