package com.manhui.gsl.jbqgsl.dao.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.gsl.jbqgsl.model.AppVersion;

@Mapper
public interface AppVersionMapper {
	
	/**
	 * 查询版本号
	 */
	AppVersion queryVersion(@Param("channal")String channal);
	
	/**
	 * 查询列表
	 */
	List<AppVersion> queryVersionList(Map<String,Object>conditions);
	
	/**
	 * 查询总条数
	 */
	Integer queryVersionCount(Map<String,Object>conditions);
	
	/**
	 * 删除数据
	 */
	void deleteVersion(@Param("id")String id);
	
	/**
	 * 保存数据
	 */
	void saveVersion(AppVersion appVersion);
	
	/**
	 * 更新数据
	 */
	void editVersion(AppVersion appVersion);
	
	/**
	 * 详细数据
	 */
	AppVersion queryAppVersionDetail(String id);
	
	/**
	 * 重置最新标识
	 */
	void cleanLast(String channal);
	/**
	 * 下载
	 */
	AppVersion load(@Param("channel")String channel);
	/**
	 * 根据版本号以及手机型号查看版本
	 */
	AppVersion queryVersionByChannalAndVersion(AppVersion av);
}
