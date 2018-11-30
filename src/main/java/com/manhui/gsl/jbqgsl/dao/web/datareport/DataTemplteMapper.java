package com.manhui.gsl.jbqgsl.dao.web.datareport;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.model.datareport.DataTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @类名称 DataTemplteMapper.java
 * @类描述 <pre>数据上报-模板列表模块dao层，主要负责跟数据库的交互</pre>
 * @作者  Jiangxiaosong
 * @创建时间 2018年11月20日22:26:28
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Jiangxiaosong 	2018年11月20日    创建
 *     ----------------------------------------------
 * </pre>
 */
@Mapper
public interface DataTemplteMapper {
    /**
     * 获取上报模板信息
     */
    List<DataTemplate> queryDataTempleteList(Map<String,Object> data);
    /**
     * 获取上报模板分页
     */
    Integer queryDataTemplateCount(@Param("title") String title);

    /**
     * 获取数据上报模板详细
     */
    DataTemplate queryDataTemplateByTemplateId(String template_id);

    /**
     * 保存数据上报模板
     */
    void saveDataTemplate(DataTemplate template);

    /**
     * 编辑数据上报模板
     */
    void editDataTemplate(DataTemplate template);

    /**
     * 模板删除
     */
    void deleteDataTemplate(String template_id);

    /**
     *  初始状态开始时间，结束时间检查
     */
    List<DataTemplate> queryDataTemplateCheckState();

    /**
     *  向APP端发送消息的数据
     */
    List<Map<String, Object>> getActiveListForSendMessage(String template_id);

    /**
     * 导出excel数据
     */
    List<Map<String,Object>> getDataReportExcelData(Map<String,Object> conditions);



}
