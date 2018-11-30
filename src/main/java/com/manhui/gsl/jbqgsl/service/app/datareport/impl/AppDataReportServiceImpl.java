package com.manhui.gsl.jbqgsl.service.app.datareport.impl;

import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.datareport.result.DataReportList;
import com.manhui.gsl.jbqgsl.dao.app.datareport.AppDataReportMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberJoinManagerMapper;
import com.manhui.gsl.jbqgsl.model.datareport.DataEnterprise;
import com.manhui.gsl.jbqgsl.service.app.datareport.IAppDataReportService;
import com.manhui.gsl.jbqgsl.service.web.datareport.IDataReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @类名称 DataReportServiceImpl.java
 * @类描述 <pre>数据上报模块service层接口实现，主要处理业务逻辑</pre>
 * @作者  LiuBin
 * @创建时间 2018年11月26日22:15:24
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00  LiuBin 	2018年11月20日    创建
 *     ----------------------------------------------
 * </pre>
 */
@Service
public class AppDataReportServiceImpl implements IAppDataReportService {
    private static final Logger logger = LoggerFactory.getLogger( AppDataReportServiceImpl.class );

    @Resource
    private AppDataReportMapper appDataReportMapper;
    @Resource
    private MemberJoinManagerMapper memberJoinManagerMapper;
    /**
	 * 待上报
	 */
	@Override
	public List<DataReportList> queryDataReportList(String user_id) {
		logger.info("数据上报---待上报开始了-------");
		//根据user_id获取到会员ID
		Map<String,Object> dataMap = memberJoinManagerMapper.queryMemberId(user_id);
		if(dataMap==null) {
			return null;
		}
		String member_id = String.valueOf(dataMap.get("memberId"));
		List<DataReportList> dataReportList = appDataReportMapper.queryToEvaluateDataReport(member_id);
		logger.info("数据上报---待上报--结束了-------");
		return dataReportList;
	}
	/**
	 * 已上报
	 */
	@Override
	public List<DataReportList> getAlreadyDataReportList(String user_id) {
		logger.info("数据上报---已上报---开始了-------");
		Map<String,Object> dataMap = memberJoinManagerMapper.queryMemberId(user_id);
		if(dataMap==null) {
			return null;
		}
		String member_id = String.valueOf(dataMap.get("memberId"));
		List<DataReportList> dataReportList = appDataReportMapper.queryToAlreadyEvaluateDataReport(member_id);
		logger.info("数据上报---已上报--结束了-------");
		return dataReportList;
	}
	/**
	 * 数据上报--保存
	 */
	@Override
	public int saveDataReportList(DataEnterprise dataEnterprise) {
		int flag = 0;
		logger.info("数据上报---保存---开始了-------");
		//1:修改关系表中的状态
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		conditionMap.put("report_state", "1");
		conditionMap.put("id", dataEnterprise.getId());
		conditionMap.put("report_time", DateUtil.getTime());
		flag = appDataReportMapper.updateMemberDataRelation(conditionMap);
		if(flag > 0) {
			flag = appDataReportMapper.saveDataReportList(dataEnterprise);
		}
		logger.info("数据上报---保存---结束了-------");
		return flag;
	}
	/**
	 * 数据上传--根据表member_data_relation中的data_report_id(上报数据ID)查找
	 */
	@Override
	public DataEnterprise queryMemberDataEnterprise(String data_report_id) {
		return appDataReportMapper.queryMemberDataEnterprise(data_report_id);
	}
	/**
	 * 数据上传--获取模板简介
	 */
	@Override
	public Map<String, Object> dataTemplateIntroduce(String template_id) {
		return appDataReportMapper.queryTemplateIntroduce(template_id);
	}
	/**
	 * 根据上报数据ID查看该会员是否已经填写
	 */
	@Override
	public String queryDataRelationState(String id) {
		return appDataReportMapper.queryDataRelationState(id);
	}

 
}
