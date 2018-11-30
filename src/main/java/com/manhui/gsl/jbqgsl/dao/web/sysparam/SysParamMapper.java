package com.manhui.gsl.jbqgsl.dao.web.sysparam;

import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @类名称 SysParamMapper.java
 * @类描述 系统参数
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月3日 下午5:23:57
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年9月3日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface SysParamMapper {
    /**
     * 获取参数列表
     * 
     * @param param_type
     * @return
     */
    List<SysParam> getSysParamList(@Param("param_type") String param_type);

    /**
     * 获取参数菜单
     * @return
     */
    List<SysParam> getSysParamMenu();

    /**
     * 获取一级下拉数据
     * @return
     */
    List<SysParam> getSysParamOneList();

    /**
     * 获取一级数据下二级数据
     * @param p_param_id
     * @return
     */
    List<SysParam> getSysParamTwoList(String p_param_id);

    /**
     * 分页列表数据获取
     * @param contime
     * @return
     */
    List<SysParam> getSysParamListPage(Map<String,Object> contime);

    /**
     * 分页列表总页数获取
     * @param p_pram_id
     * @return
     */
    Integer getSysParamListCount(String p_pram_id);

    /**
     * 获取参数详细信息
     * @param param_id
     * @return
     */
    SysParam getSysParamDetail(String param_id);

    /**
     * 删除参数数据
     * @param param_id
     */
    void deleteSysParamData(String param_id);

    /**
     * 新增参数数据
     * @param data
     */
    void insertSysParamData(SysParam data);

    /**
     * 修改参数数据
     * @param data
     */
    void updateSysParamData(SysParam data);

    SysParam getSysParamByKey(String key);
    /**
     * 获取param_Key集合
     */
	List<String> getSysParamKeyList(@Param("paramIdList")List<String> paramIdList);
}
