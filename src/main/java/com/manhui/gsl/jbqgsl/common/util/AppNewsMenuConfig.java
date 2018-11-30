package com.manhui.gsl.jbqgsl.common.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * app端，新闻栏目配置管理
 */
@Configuration
@ConfigurationProperties()
@PropertySource({"classpath:config.properties"})
@Data
@Component
public class AppNewsMenuConfig {

    /**
     * 新闻资讯栏目id
     */
    private  Map<String, Integer> newsInformation;

    /**
     * 组织概况栏目配置
     */
    private  Map<String, Integer> orgOverview;

    /**
     * 工商联部门栏目配置
     */
    private  Map<String, Integer> businessDept;

    /**
     * 惠企资讯栏目
     */
    private  Map<String, Integer> favorableInformation;

    /**
     * 非公党建栏目
     */
    private  Map<String, Integer> partyBuild;

    /**
     * 通知公告
     */

    private Map<String,Integer> noticeBulletin;

    /**
     * pc 端需要展示回执内容栏目
     */

    private Map<String,Integer> receipt;
    /**
     * app端，会务活动栏目
     */
    private Map<String,Integer> app_receipt;

    /**
     * 获得app端新闻栏目
     * @return
     */
    public Map<String,Map<String, Integer>> getAppNewsMenuMap(){
        Map<String,Map<String, Integer>> dataMap = new HashMap<>();
        dataMap.put("newsInformation",newsInformation);
        dataMap.put("orgOverview",orgOverview);
        dataMap.put("businessDept",businessDept);
        dataMap.put("favorableInformation",favorableInformation);
        dataMap.put("partyBuild",partyBuild);
        dataMap.put("noticeBulletin",noticeBulletin);
        dataMap.put("receipt",receipt);
        dataMap.put("app_receipt",app_receipt);
        return dataMap;
    }
}
