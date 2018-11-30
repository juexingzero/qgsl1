package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;

import com.manhui.gsl.jbqgsl.model.AppVersion;

/**
 *	app版本号后端接口
 */
public interface IAppVersionService {
	
	/**
	 * 检查最新版本号
	 */
	AppVersion queryVersion(String channal);
	
	/**
	 * 查询列表
	 */
	List<AppVersion> queryVersionList(String version,String channal,Integer pageIndex,Integer pageSize);
	
	/**
	 * 查询总条数
	 */
	Integer queryVersionCount(String version,String channal,Integer pageIndex,Integer pageSize);
	
	/**
	 * 删除数据
	 */
	void deleteVersion(String id);
	
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
	 * 下载最新版本
	 */
	AppVersion load(String channel, int flag);
	/**
	 * 验证版本号是否存在
	 */
	AppVersion queryVersion(AppVersion av);
}
