package com.manhui.gsl.jbqgsl.service.web.sysparam.impl;

import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.web.sysparam.SysParamMapper;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @类名称 SysParamServiceImpl.java
 * @类描述 系统参数
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月3日 下午5:20:37
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月3日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class SysParamServiceImpl implements ISysParamService {
    private static final Logger logger = LoggerFactory.getLogger( SysParamServiceImpl.class );
    @Resource
    private SysParamMapper sysParamMapper;

    /**
     * @方法名称 getSysParamList
     * @功能描述 系统参数 - 获取参数列表
     * @作者 kevin
     * @创建时间 2018年9月3日 下午5:21:09
     * @param param_type
     * @return
     */
    @Override
    public List<SysParam> getSysParamList(String param_type ) {
        logger.info( "----- 系统参数-获取参数列表 ==> start -----" );
        List<SysParam> sysParamList = sysParamMapper.getSysParamList( param_type );
        logger.info( "----- 系统参数-获取参数列表 ==> end -----" );
        return sysParamList;
    }

    /**
     * @方法名称 getSysParamMenu
     * @功能描述 系统参数 - 获取参数菜单
     * @作者 kevin
     * @创建时间 2018年10月25日 下午6:21:09
     * @return
     */
    @Override
    public List<SysParam> getSysParamMenu() {

        return sysParamMapper.getSysParamMenu();
    }

    @Override
    public List<SysParam> getSysParamOneList() {
        return sysParamMapper.getSysParamOneList();
    }

    @Override
    public List<SysParam> getSysParamTwoList(String p_param_id) {
        return sysParamMapper.getSysParamTwoList(p_param_id);
    }

    @Override
    public List<SysParam> getSysParamListPage(String p_param_id, Integer pageIndex, Integer pageSize) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("p_param_id",p_param_id);
        conditions.put( "pageNo", pageIndex * pageSize );
        conditions.put( "pageSize", pageSize );
        return sysParamMapper.getSysParamListPage(conditions);
    }

    @Override
    public Integer getSysParamListCount(String p_pram_id) {
        return sysParamMapper.getSysParamListCount(p_pram_id);
    }

    @Override
    public SysParam getSysParamDetail(String param_id) {
        return sysParamMapper.getSysParamDetail(param_id);
    }

    @Override
    public void deleteSysParamData(String param_id) {
        sysParamMapper.deleteSysParamData(param_id);
    }

    @Override
    public void insertSysParamData(SysParam data) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.setParam_id(UUIDUtil.getUUID());
        data.setCreate_time(sdf.format(now));
        sysParamMapper.insertSysParamData(data);
    }

    @Override
    public void updateSysParamData(SysParam data) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.setUpdate_time(sdf.format(now));
        sysParamMapper.updateSysParamData(data);
    }

    @Override
    public SysParam getSysParamByKey(String key) {
        return sysParamMapper.getSysParamByKey(key);
    }

    @Override
    public Map<String, String> getSysParamMap(String p_pram_id) {
        List<SysParam> sysParams = sysParamMapper.getSysParamTwoList(p_pram_id);
        Map<String,String> dataMap = new LinkedHashMap<>();
        if(sysParams != null && !sysParams.isEmpty()){
            for(SysParam e : sysParams){
                dataMap.put(e.getParam_key(),e.getParam_value());
            }
        }
        return dataMap;
    }
    /**
     * 获取param_Key集合
     */
	@Override
	public List<String> getSysParamKeyList(List<String> paramIdList) {
		return sysParamMapper.getSysParamKeyList(paramIdList);
	}
    
}
