package com.manhui.gsl.jbqgsl.service.web.activity.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.manhui.gsl.jbqgsl.dao.web.activity.ActivityInfoMapper;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityInfo;
import com.manhui.gsl.jbqgsl.service.web.activity.IActivityInfoService;

/**
 * @类名称 TopicEvaluateServiceImpl.java
 * @类描述 活动管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月17日 下午17:34:31
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年10月17日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class ActivityInfoServiceImpl implements IActivityInfoService {
    private static final Logger logger = LoggerFactory.getLogger( ActivityInfoServiceImpl.class );
    @Resource
    private ActivityInfoMapper        activityInfoMapper;
	@Override
	public List<ActivityInfo> getActivityInfoList(String activity_theme, List<String> activity_stateList,
			Integer pageIndex, Integer pageSize) {
		logger.info( "----- 系统参数-获取活动管理列表数据  -----" );
		 Map<String, Object> conditions = new HashMap<>();
	        conditions.put( "activity_theme", activity_theme );
	        conditions.put( "activity_state", activity_stateList );
	        conditions.put( "pageNo", pageIndex * pageSize);
	        conditions.put( "pageSize", pageSize);
		return activityInfoMapper.getActivityInfoList(conditions);
	}
	@Override
	public Integer queryActivityInfoTotal(String activity_theme, List<String> activity_stateList, Integer pageIndex,
			Integer pageSize) {
		logger.info( "----- 系统参数-获取活动管理列表总数  -----" );
		 Map<String, Object> conditions = new HashMap<>();
	        conditions.put( "activity_theme", activity_theme );
	        conditions.put( "activity_state", activity_stateList );
	        conditions.put( "pageNo", pageIndex * pageSize);
	        conditions.put( "pageSize", pageSize);
		return activityInfoMapper.queryActivityInfoTotal(conditions);
	}
	@Override
	public Integer updateActivityInfo(ActivityInfo ai) {
		logger.info( "----- 系统参数-根据条件修改活动数据  -----" );
		return activityInfoMapper.updateActivityInfo(ai);
	}
	@Override
	public Integer addActivityInfo(ActivityInfo ai) {
		logger.info( "----- 系统参数-根据条件修改活动数据  -----" );
		return activityInfoMapper.addActivityInfo(ai);
	}
	@Override
	public ActivityInfo queryActivityInfo(ActivityInfo ai) {
		logger.info( "----- 系统参数-根据条件查询活动数据  -----" );
		ActivityInfo a=activityInfoMapper.queryActivityInfo(ai);
		//处理图片显示
        String activity_image = a.getActivity_image();
        if ( StringUtils.isNotBlank( activity_image ) ) {
            String imgs[] = activity_image.split( "," );
            List<Map<String, String>> listMap = new ArrayList<>();
            for ( int i = 0; i < imgs.length; i++ ) {
                Map<String, String> imgMap = new HashMap<>();
                imgMap.put( "name", "" );
                imgMap.put( "path", imgs[i] );
                listMap.add( imgMap );
            }
            a.setImgObj( new Gson().toJson( listMap ) );
        }else {
            a.setImgObj( "" );
        }
		return a;
	}
	@Override
	public Integer queryActivityInfoNum(ActivityInfo ai) {
		logger.info( "----- 系统参数-根据条件查询活动场次  -----" );
		return activityInfoMapper.queryActivityInfoNum(ai);
	}

}
