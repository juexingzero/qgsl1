package com.manhui.gsl.jbqgsl.service.web.sysparam;

import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;

import java.util.List;
import java.util.Map;

/**
 * @类名称 ISysParamService.java
 * @类描述 系统参数
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月3日 下午5:18:21
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
public interface ISysParamService {
    List<SysParam> getSysParamList(String param_type);
    List<SysParam> getSysParamMenu();
    List<SysParam> getSysParamOneList();
    List<SysParam> getSysParamTwoList(String p_param_id);
    List<SysParam> getSysParamListPage(String p_param_id,Integer pageIndex,Integer pageSize);
    Integer getSysParamListCount(String p_pram_id);
    SysParam getSysParamDetail(String param_id);
    void deleteSysParamData(String param_id);
    void insertSysParamData(SysParam data);
    void updateSysParamData(SysParam data);

    SysParam getSysParamByKey(String key);

    Map<String,String> getSysParamMap(String p_pram_id);
    /**
     * 获取param_key
     */
	List<String> getSysParamKeyList(List<String> paramIdList);
}
