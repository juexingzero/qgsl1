package com.manhui.gsl.jbqgsl.dao.web.datareport;

import com.manhui.gsl.jbqgsl.model.datareport.DataRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @类名称 DataRelationMapper.java
 * @类描述 <pre>数据上报-详细列表模块dao层，主要负责跟数据库的交互</pre>
 * @作者  Jiangxiaosong
 * @创建时间 2018年11月23日15:45:45
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	2018年11月23日    创建
 *     ----------------------------------------------
 * </pre>
 */
@Mapper
public interface DataRelationMapper {
    /**
     * 获取上报详细数据
     */
    List<Map<String,Object>> queryDataReportList(Map<String,Object> data);
    /***
     * 获取上报详细分页
     */
    Integer queryDataReportListCount(Map<String,Object> data);

    /**
     * 获取详细内容页面数据
     */
    Map<String, Object> queryEnterpriseDataReportDetail(String data_report_id);

    /**
     * 保存详细数据
     */
    void saveEnterpriseData(DataRelation data);
}
