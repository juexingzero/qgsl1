package com.manhui.gsl.jbqgsl.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.app.AppVersionMapper;
import com.manhui.gsl.jbqgsl.model.AppVersion;

@Service
public class AppVersionServiceImpl implements IAppVersionService {
	
	private static final Logger logger = LoggerFactory.getLogger( AppVersionServiceImpl.class );
	
	@Resource
	private AppVersionMapper appVersionMapper;

	@Override
	public AppVersion queryVersion(String channal) {
		return appVersionMapper.queryVersion(channal);
	}

	@Override
	public List<AppVersion> queryVersionList(String version, String channal, Integer pageIndex, Integer pageSize) {
		Map<String, Object> conditions = new HashMap<>();
        conditions.put( "version", version );
        conditions.put( "channal", channal );
        conditions.put( "pageNo", pageIndex * pageSize );
        conditions.put( "pageSize", pageSize );
		return appVersionMapper.queryVersionList(conditions);
	}

	@Override
	public Integer queryVersionCount(String version, String channal, Integer pageIndex, Integer pageSize) {
		Map<String, Object> conditions = new HashMap<>();
        conditions.put( "version", version );
        conditions.put( "channal", channal );
		return appVersionMapper.queryVersionCount(conditions);
	}

	@Override
	public void deleteVersion(String id) {
		appVersionMapper.deleteVersion(id);
	}

	@Override
	public void saveVersion(AppVersion appVersion) {
		appVersionMapper.saveVersion(appVersion);
	}

	@Override
	public void editVersion(AppVersion appVersion) {
		appVersionMapper.editVersion(appVersion);
	}

	@Override
	public AppVersion queryAppVersionDetail(String id) {
		return appVersionMapper.queryAppVersionDetail(id);
	}

	@Override
	public AppVersion load(String channel, int flag) {
		AppVersion av = new AppVersion();
		if("1".equals(flag)) { //"1"表示下载
			av = appVersionMapper.load(channel);
		}
		return av;
	}

	@Override
	public AppVersion queryVersion(AppVersion av) {
		return appVersionMapper.queryVersionByChannalAndVersion(av);
	}

}
