package com.manhui.gsl.jbqgsl.service.web.datareport.impl;

import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.*;
import com.manhui.gsl.jbqgsl.dao.MessageMapper;
import com.manhui.gsl.jbqgsl.dao.app.membermanager.MemberJoinManagerMapper;
import com.manhui.gsl.jbqgsl.dao.web.datareport.DataRelationMapper;
import com.manhui.gsl.jbqgsl.dao.web.datareport.DataTemplteMapper;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;
import com.manhui.gsl.jbqgsl.model.datareport.DataRelation;
import com.manhui.gsl.jbqgsl.model.datareport.DataTemplate;
import com.manhui.gsl.jbqgsl.model.member.MemberJoinManager;
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
 * @作者  Jiangxiaosong
 * @创建时间 2018年11月20日22:15:24
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00  Jiangxiaosong 	2018年11月20日    创建
 *     ----------------------------------------------
 * </pre>
 */
@Service
@PropertySource( "classpath:config.properties" )
public class DataReportServiceImpl implements IDataReportService {
    private static final Logger logger = LoggerFactory.getLogger( DataReportServiceImpl.class );

    @Resource
    private DataTemplteMapper dataTemplteMapper;

    @Resource
    private DataRelationMapper dataRelationMapper;

    @Resource
    private MemberJoinManagerMapper memberJoinManagerMapper;

    @Resource
    private MessageMapper messageMapper;

    @Value( "${file_download_data_report_templet_file}" )
    private String  dataReportTempletFile;
    @Value( "${file_download_data_report_result_path}" )
    private String  dataReportResultPath;

    @Override
    public List<DataTemplate> queryDataTempleteList(Map<String, Object> data) {
        return dataTemplteMapper.queryDataTempleteList(data);
    }

    @Override
    public Integer queryDataTemplateCount(String title) {
        return dataTemplteMapper.queryDataTemplateCount(title);
    }

    @Override
    public DataTemplate queryDataTemplateByTemplateId(String template_id) {
        return dataTemplteMapper.queryDataTemplateByTemplateId(template_id);
    }

    @Override
    public JsonResult saveDataTemplate(DataTemplate template) {
        dataTemplteMapper.saveDataTemplate(template);
        //将会员关联到中间表
        MemberJoinManager query = new MemberJoinManager();
        String type = template.getTemplate_type();
        query.setState("HYSP-02");
        if("1".equals(type)){
            //个人
            query.setMemberType("HYLX-01");
        }else if("2".equals(type)){
            //团体
            query.setMemberType("HYLX-02");
        }else{
            //企业
            query.setMemberType("HYLX-03");
        }
        List<MemberJoinManager> datalist = memberJoinManagerMapper.queryAllList(query);
        for(MemberJoinManager mjm : datalist){
            DataRelation data = new DataRelation();
            data.setId(UUIDUtil.getUUID());
            data.setTemplate_id(template.getTemplate_id());
            if("1".equals(type)){
                //个人
                data.setGr_id(mjm.getMemberId());
            }else if("2".equals(type)){
                //团体
                data.setTt_id(mjm.getMemberId());
            }else{
                //企业
                data.setQy_id(mjm.getMemberId());
            }
            data.setData_report_id(UUIDUtil.getUUID());
            data.setReport_state("0");
            dataRelationMapper.saveEnterpriseData(data);
        }
        return new JsonResult();
    }

    @Override
    public JsonResult editDataTemplate(DataTemplate template) {
        dataTemplteMapper.editDataTemplate(template);
        return new JsonResult();
    }

    @Override
    public JsonResult deleteDataTemplate(String template_id) {
        dataTemplteMapper.deleteDataTemplate(template_id);
        return new JsonResult();
    }

    @Override
    public JsonResult ChangeDataTemplateState() {
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm");
        List<DataTemplate> datas=dataTemplteMapper.queryDataTemplateCheckState();
        for (DataTemplate data : datas) {
            if(data.getStart_time().equals(sdf.format(new Date()))){
                DataTemplate dt=new DataTemplate();
                dt.setState("1");
                dt.setTemplate_id(data.getTemplate_id());
                dataTemplteMapper.editDataTemplate(dt);
                //为开始关联企业相关联系人发送消息
                this.sendMessageToApps(data.getTemplate_id(),data.getTemplate_type(),data.getTemplate_title());
            }
            if(data.getEnd_time().equals(sdf.format(new Date()))){
                DataTemplate dt=new DataTemplate();
                dt.setState("2");
                dt.setTemplate_id(data.getTemplate_id());
                dataTemplteMapper.editDataTemplate(dt);
            }
        }
        return new JsonResult();
    }


    /**
     * @方法名称 sendMessageToApps
     * @功能描述 数据上报开始时给相关的联系人发送消息
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月22日18:44:42
     * @param template_id
     * @param template_type
     * @param template_title
     */
    private void sendMessageToApps( String template_id,String template_type,String template_title ) {
        List<Map<String, Object>> activeList = new ArrayList<>();
        if("1".equals(template_type)){
            //个人数据
        }else if("2".equals(template_type)){
            //团体数据
        }else{
            //企业数据
            activeList = dataTemplteMapper.getActiveListForSendMessage( template_id );
        }
        logger.info("数据上报app发信开始");
        if ( activeList != null && activeList.size() > 0 ) {
            String receive_name = "";
            //添加消息模板信息
            MessageInfo info = new MessageInfo();
            info.setMessage_id( UUIDUtil.getUUID() );
            info.setMessage_type( "2" );
            info.setMessage_mode( "1" );
            info.setMessage_content( "收到一条数据上报“" + template_title + "”待处理，请知悉！" );
            info.setCreator_id( "jbqgsl_system" );
            info.setCreator_name( "系统通知" );
            info.setCreate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
            messageMapper.insertMessageInfo( info );
            for ( Map<String, Object> activeMap : activeList ) {
                //发型消息
                MessageFlowing flowing = new MessageFlowing();
                flowing.setMessage_id( info.getMessage_id() );
                flowing.setFlowing_id( UUIDUtil.getUUID() );
                flowing.setSend_id( info.getCreator_id() );
                flowing.setSend_name( info.getCreator_name() );
                flowing.setSend_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
                flowing.setReceive_id( activeMap.get( "user_id" ) + "" );
                flowing.setReceive_name( activeMap.get( "user_name" ) + "" );
                flowing.setIs_read( "0" );
                messageMapper.insertMessageFlowing( flowing );
                receive_name += flowing.getReceive_name() + ", ";
            }
        }
        logger.info("数据上报app发信结束");
    }


    @Override
    public String exportDataReportResult(HttpServletResponse response, String template_id,String template_type) {
        String exportFileName = "";
        if("1".equals(template_type)){
            //个人数据上报
        }else if("2".equals(template_type)){
            //团体数据上报
        }else{
            exportFileName = this.buildExportFile( template_id );
        }
        if ( !"".equals( exportFileName ) ) {
            logger.info( "-----------参会回执-导出Excel-结果：-----------\n" + "exportFileName：" + exportFileName );
        }
        return exportFileName;
    }

    @Override
    public List<Map<String, Object>> queryDataReportList(Map<String, Object> data) {
        return dataRelationMapper.queryDataReportList(data);
    }

    @Override
    public Integer queryDataReportListCount(Map<String, Object> data) {
        return dataRelationMapper.queryDataReportListCount(data);
    }

    @Override
    public Map<String, Object> queryEnterpriseDataReportDetail(String data_report_id) {
        return dataRelationMapper.queryEnterpriseDataReportDetail(data_report_id);
    }

    /**
     * 组建导出文件
     *
     * @param template_id
     * @return
     */
    private String buildExportFile( String template_id ) {
        String exportFileName = "";
        Map<String, Object> beanParams = new HashMap<String, Object>();

        Map<String, Object> conditions=new HashMap<>();
        conditions.put("template_id", template_id);

        List<Map<String,Object>> dataList=dataTemplteMapper.getDataReportExcelData(conditions);
        beanParams.put("dataList", dataList);
        exportFileName = "csdc" +
                "-" +
                DateUtil.getDateTime( "HHmmss" ) +
                "." +
                ( dataReportTempletFile.endsWith( "xlsx" ) == true ? "xlsx" : "xls" );
        ExcelExportUtil
                .ExportExcel( dataReportTempletFile, beanParams, dataReportResultPath + exportFileName );
        logger.info( "----------模板地址：" + JacksonUtil.toJson( dataList )  );
        return exportFileName;
    }
}
