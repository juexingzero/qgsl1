package com.manhui.gsl.jbqgsl.service.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.ExcelExportUtil;
import com.manhui.gsl.jbqgsl.common.util.JacksonUtil;
import com.manhui.gsl.jbqgsl.common.util.ZipUtil;
import com.manhui.gsl.jbqgsl.dao.EvaluateFlowingScoreMapper;
import com.manhui.gsl.jbqgsl.dao.TopicEvaluateResultMapper;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScore;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingScoreDetailsUtil;
import com.manhui.gsl.jbqgsl.model.EvaluateFlowingSuggest;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicPassiveInfo;
import com.manhui.gsl.jbqgsl.model.topicevaluate.export.TopicActiveScoreBean;
import com.manhui.gsl.jbqgsl.model.topicevaluate.export.TopicEvaluateExportBean;
import com.manhui.gsl.jbqgsl.model.topicevaluate.export.TopicPassiveAndActiveScoreBean;
import com.manhui.gsl.jbqgsl.model.topicevaluate.export.TopicPassiveScoreBean;
import com.manhui.gsl.jbqgsl.model.topicevaluate.export.TopicStandardDetailScoreBean;

/**
 * @类名称 TopicEvaluateServiceImpl.java
 * @类描述 主题评价结果
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月15日 下午2:05:58
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月15日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class TopicEvaluateServiceResultImpl implements ITopicEvaluateResultService {
    private static final Logger        logger = LoggerFactory.getLogger( TopicEvaluateServiceResultImpl.class );
    @Resource
    private TopicEvaluateResultMapper  topicEvaluateResultMapper;
    @Resource
    private EvaluateFlowingScoreMapper evaluateFlowingScoreMapper;
    @Value( "${file_download_topic_evaluate_templet_file}" )
    private String                     topicEvaluateTempletFile;
    @Value( "${file_download_topic_evaluate_result_path}" )
    private String                     topicEvaluateResultPath;

    /**
     * @方法名称 getTopicEvaluateList
     * @功能描述 主题评价结果 - 获取主题列表
     * @作者 kevin
     * @创建时间 2018年8月15日 下午2:05:58
     * @修改时间 2018年11月19日 下午18:18:09，增加"双向评价街镇用户"访问该列表的数据过滤
     * @param topic
     * @param user_type
     * @param mobile_no
     * @return
     */
    @Override
    public List<TopicEvaluate> getTopicEvaluateResultList( TopicEvaluate topic, String user_type, String mobile_no ) {
        logger.info( "----- 主题评价结果-获取主题列表 ==> start -----" );
        List<TopicEvaluate> teOldList = topicEvaluateResultMapper.getTopicEvaluateResultList( topic );
        List<TopicEvaluate> teNewList = new ArrayList<>();
        if ( teOldList != null && teOldList.size() > 0 ) {
            //双向评价街镇用户
            if ( "2".equals( user_type ) ) {
                String topic_ids = topicEvaluateResultMapper.getPassivesByLinkmanPhone( mobile_no );
                if(topic_ids != null && !"".equals( topic_ids )) {
                    String[] topic_idArr = topic_ids.split( "," );
                    for ( TopicEvaluate oldTe : teOldList ) {
                        if ( Arrays.asList( topic_idArr ).contains( oldTe.getTopic_id() ) ) {
                            teNewList.add( oldTe );
                        }
                    }
                }
            }
            else {
                teNewList = teOldList;
            }
        }
        if ( teNewList != null && teNewList.size() > 0 ) {
            for ( TopicEvaluate newTe : teNewList ) {
                newTe.setInstitution_name_passives(
                        topicEvaluateResultMapper.getPassivesByTopicId( newTe.getTopic_id() ) );
                newTe.setInstitution_name_actives(
                        topicEvaluateResultMapper.getActivesByTopicId( newTe.getTopic_id() ) );
            }
        }
        logger.info( "----- 主题评价结果-获取主题列表 ==> end -----" );
        return teNewList;
    }

    /**
     * @方法名称 getTopicEvaluateTotal
     * @功能描述 主题评价结果 - 获取主题列表总数
     * @作者 kevin
     * @创建时间 2018年8月15日 下午2:05:58
     * @param topic
     * @return
     */
    @Override
    public Integer getTopicEvaluateResultTotal( TopicEvaluate topic ) {
        logger.info( "----- 主题评价-获取主题列表总数 ==> start -----" );
        Integer total = topicEvaluateResultMapper.getTopicEvaluateResultTotal( topic );
        logger.info( "----- 主题评价-获取主题列表总数 ==> end -----" );
        return total;
    }

    /**
     * 查看被评价组织详情
     * 
     * @param topic_id
     * @return
     */
    @Override
    public Map<String, Object> getTopicEvaluateOrgDetails( String topic_id ) {
        Map<String, Object> evaluate = topicEvaluateResultMapper.getTopicEvaluateById( topic_id );
        if ( evaluate == null ) {
            throw new RuntimeException( "数据不存在或已被删除!" );
        }
        return evaluate;
    }

    /**
     * 根据主题id查询 被评价方
     * 
     * @param info
     * @return
     */
    @Override
    public List<TopicPassiveInfo> getTopicPassiveInfoListTopic_id(
            TopicPassiveInfo info,
            String user_type,
            String mobile_no ) {
        List<TopicPassiveInfo> tpiOldlist = topicEvaluateResultMapper.getTopicPassiveInfoListTopic_id( info );
        List<TopicPassiveInfo> tpiNewlist = new ArrayList<>();
        //双向评价街镇用户
        if ( "2".equals( user_type ) ) {
            for ( TopicPassiveInfo tpi : tpiOldlist ) {
                if ( mobile_no.equals( tpi.getInstitution_linkman_phone() ) ) {
                    tpiNewlist.add( tpi );
                }
            }
        }
        else {
            tpiNewlist = tpiOldlist;
        }
        return tpiNewlist;
    }

    /**
     * 根据主题id、机构id 查询数据
     * 
     * @param topic_id
     * @param institution_id
     * @return
     */
    @Override
    public TopicPassiveInfo getTopicPassiveInfoByTopicInstitutionId( String topic_id, String institution_id ) {
        return topicEvaluateResultMapper.getTopicPassiveInfoByTopicInstitutionId( topic_id, institution_id );
    }

    /**
     * 根据主题id查询 被评价方总数
     * 
     * @param info
     * @return
     */
    @Override
    public Integer getTopicPassiveInfoTotalByTopic_id( TopicPassiveInfo info ) {
        return topicEvaluateResultMapper.getTopicPassiveInfoTotalByTopic_id( info );
    }

    /**
     * 根据主题id 和被评价方id 查询 评价流水合并列表
     * 
     * @return
     */
    @Override
    public List<Map<String, Object>> getEvaluateFlowingScoreGroupList(
            String topic_id,
            String passive_id,
            Integer pageNo,
            Integer pageSize,
            String user_type ) {
        List<Map<String, Object>> mapOldList = evaluateFlowingScoreMapper
                .getEvaluateFlowingScoreGroupList( topic_id, passive_id, pageNo, pageSize );
        List<Map<String, Object>> mapNewList = new ArrayList<>();
        //双向评价街镇用户，评价方列表只显示被其主管的企业
        if ( "2".equals( user_type ) ) {
            for ( Map<String, Object> oldMap : mapOldList ) {
                if ( passive_id.equals( oldMap.get( "street_main_id" ) + "" ) ) {
                    mapNewList.add( oldMap );
                }
            }
        }
        else {
            mapNewList = mapOldList;
        }
        return mapNewList;
    }

    /**
     * 根据主题id 和被评价方id 查询 评价流水合并总数
     * 
     * @param score
     * @return
     */
    @Override
    public Integer getEvaluateFlowingScoreGroupTotal( String topic_id, String passive_id ) {
        return evaluateFlowingScoreMapper.getEvaluateFlowingScoreGroupTotal( topic_id, passive_id );
    }

    /**
     * 查询 分数详情
     * 
     * @param score
     * @return
     */
    @Override
    public List<EvaluateFlowingScoreDetailsUtil> getEvaluateFlowingScoreDetails( EvaluateFlowingScore score ) {
        score = evaluateFlowingScoreMapper.getevaluateFlowingScoreById( score.getScore_id() );
        if ( score == null ) {
            throw new RuntimeException( "数据异常，请刷新重试" );
        }
        List<EvaluateFlowingScoreDetailsUtil> dataList = evaluateFlowingScoreMapper
                .getEvaluateFlowingScoreDetails( score );
        return dataList;
    }

    /**
     * @方法名称 getEvaluateFlowingSuggest
     * @功能描述 获取评价流水意见
     * @作者 kevin
     * @创建时间 2018年8月26日 下午2:56:36
     * @param topic_id
     * @param passive_id
     * @param actice_id
     * @return
     */
    @Override
    public EvaluateFlowingSuggest getEvaluateFlowingSuggest(
            String topic_id,
            String topic_standard_id,
            String passive_id,
            String actice_id ) {
        return evaluateFlowingScoreMapper
                .getEvaluateFlowingSuggest( topic_id, topic_standard_id, passive_id, actice_id );
    }

    /**
     * @方法名称 getStandradScoreDetail
     * @功能描述 获取标准打分详情
     * @作者 kevin
     * @创建时间 2018年8月26日 下午4:56:36
     * @param topic_id
     * @param topic_standard_id
     * @param passive_id
     * @param actice_id
     * @return
     */
    @Override
    public List<EvaluateFlowingScore> getStandradScoreDetail(
            String topic_id,
            String topic_standard_id,
            String passive_id,
            String actice_id ) {
        return evaluateFlowingScoreMapper.getStandradScoreDetail( topic_id, topic_standard_id, passive_id, actice_id );
    }

    /**
     * 导出主题评价结果
     * 
     * @param topic_ids
     * @return
     */
    @Override
    public String exportTopicEvaluateResult(
            HttpServletResponse response,
            String topic_ids,
            String user_type,
            String mobile_no ) {
        String exportFileName = "";
        //批量导出
        if ( topic_ids.indexOf( "," ) != -1 ) {
            String[] topic_id_arr = topic_ids.split( "," );
            List<String> exportFileList = new ArrayList<>();
            for ( String topic_id : topic_id_arr ) {
                exportFileName = this.buildExportFile( topic_id, user_type, mobile_no );
                exportFileList.add( topicEvaluateResultPath + exportFileName );
            }
            //压缩
            String zipFileName = "topicEvaluate" + "-" + DateUtil.getDateTime( "HHmmss" ) + ".zip";
            boolean flag = ZipUtil.compress( topicEvaluateResultPath + zipFileName, exportFileList );
            if ( flag ) {
                exportFileName = zipFileName;
            }
        }
        //单个导出
        else {
            exportFileName = this.buildExportFile( topic_ids, user_type, mobile_no );
        }
        if ( !"".equals( exportFileName ) ) {
            logger.info( "-----------主题评价结果-导出Excel-结果：-----------\n" + "exportFileName：" + exportFileName );
        }
        return exportFileName;
    }

    /**
     * 组建导出文件
     * 
     * @param topic_id
     * @return
     */
    private String buildExportFile( String topic_id, String user_type, String mobile_no ) {
        String exportFileName = "";
        Map<String, Object> beanParams = new HashMap<String, Object>();
        /******************************** 得分报表 *********************************/
        TopicEvaluateExportBean scoreReport = new TopicEvaluateExportBean();
        //主题基本信息
        Map<String, Object> topic = topicEvaluateResultMapper.getTopicEvaluateById( topic_id );
        if ( topic != null ) {
            scoreReport.setTopic_id( topic.get( "topic_id" ) + "" );
            scoreReport.setTopic_number( topic.get( "topic_number" ) + "" );
            scoreReport.setTopic_name( topic.get( "topic_name" ) + "" );
            scoreReport.setEvaluate_start_time( topic.get( "evaluate_start_time" ) + "" );
            scoreReport.setEvaluate_end_time( topic.get( "evaluate_end_time" ) + "" );
        }
        //被评价方得分列表
        TopicPassiveInfo info = new TopicPassiveInfo();
        info.setTopic_id( topic_id );
        List<TopicPassiveInfo> passiveOldList = topicEvaluateResultMapper.getTopicPassiveInfoListTopic_id( info );
        List<TopicPassiveInfo> passiveNewList = new ArrayList<>();
        if ( "2".equals( user_type ) ) { //双向评价街镇用户，被评价方列表只显示其自身
            for ( TopicPassiveInfo tpi : passiveOldList ) {
                if ( mobile_no.equals( tpi.getInstitution_linkman_phone() ) ) {
                    passiveNewList.add( tpi );
                }
            }
        }
        else {
            passiveNewList = passiveOldList;
        }
        List<TopicPassiveScoreBean> tpsbList = new ArrayList<>();
        if ( passiveNewList != null && passiveNewList.size() > 0 ) {
            for ( int i = 0; i < passiveNewList.size(); i++ ) {
                TopicPassiveInfo tpi = passiveNewList.get( i );
                TopicPassiveScoreBean tpsb = new TopicPassiveScoreBean();
                tpsb.setPassive_order( String.valueOf( i + 1 ) );
                tpsb.setPassive_id( tpi.getInstitution_id() );
                tpsb.setPassive_name( tpi.getInstitution_name() );
                tpsb.setPassive_score( String.valueOf( tpi.getReal_score_avg() ) );
                tpsbList.add( tpsb );
            }
            scoreReport.setPassiveScoreList( tpsbList );
        }
        //被评价方得分详情列表
        if ( tpsbList != null && tpsbList.size() > 0 ) {
            List<TopicStandardDetailScoreBean> scoreDetailList = new ArrayList<>();
            List<TopicPassiveAndActiveScoreBean> tpaasbList = new ArrayList<>();
            for ( TopicPassiveScoreBean tpsb : tpsbList ) {
                TopicPassiveAndActiveScoreBean tpaasb = new TopicPassiveAndActiveScoreBean();
                tpaasb.setPassive_order( tpsb.getPassive_order() );
                tpaasb.setPassive_name( tpsb.getPassive_name() );
                tpaasb.setPassive_score( tpsb.getPassive_score() );
                List<Map<String, Object>> flowingScoreOldList = evaluateFlowingScoreMapper
                        .getEvaluateFlowingScoreGroupList( topic_id, tpsb.getPassive_id(), null, null );
                List<Map<String, Object>> flowingScoreNewList = new ArrayList<>();
                if ( "2".equals( user_type ) ) { //双向评价街镇用户，评价方列表只显示被其主管的企业
                    for ( Map<String, Object> oldMap : flowingScoreOldList ) {
                        if ( tpsb.getPassive_id().equals( oldMap.get( "street_main_id" ) + "" ) ) {
                            flowingScoreNewList.add( oldMap );
                        }
                    }
                }
                else {
                    flowingScoreNewList = flowingScoreOldList;
                }
                //评价方数量
                scoreReport.setActives( String.valueOf( flowingScoreNewList.size() ) );
                if ( flowingScoreNewList != null && flowingScoreNewList.size() > 0 ) {
                    List<TopicActiveScoreBean> activeScoreList = new ArrayList<>();
                    for ( int j = 0; j < flowingScoreNewList.size(); j++ ) {
                        Map<String, Object> flowingScore = flowingScoreNewList.get( j );
                        TopicActiveScoreBean tasb = new TopicActiveScoreBean();
                        tasb.setActive_order( String.valueOf( j + 1 ) );
                        tasb.setActive_name( flowingScore.get( "institution_name" ) + "" );
                        String is_evaluate = flowingScore.get( "is_evaluate" ) + "";
                        if ( "1".equals( is_evaluate ) ) {
                            tasb.setActive_score( flowingScore.get( "real_score" ) + "" );
                        }
                        else {
                            tasb.setActive_score( "未评价" );
                        }
                        activeScoreList.add( tasb );
                        //标准打分详情
                        List<Map<String, Object>> efsList = evaluateFlowingScoreMapper.getStandradScoreDetailForExport(
                                flowingScore.get( "topic_id" ) + "",
                                flowingScore.get( "topic_standard_id" ) + "",
                                flowingScore.get( "passive_id" ) + "",
                                flowingScore.get( "actice_id" ) + "" );
                        if ( efsList != null && efsList.size() > 0 ) {
                            for ( int k = 0; k < efsList.size(); k++ ) {
                                Map<String, Object> efsMap = efsList.get( k );
                                TopicStandardDetailScoreBean tsdsb = new TopicStandardDetailScoreBean();
                                tsdsb.setDetail_order( String.valueOf( k + 1 ) );
                                tsdsb.setPassive_name( efsMap.get( "passive_name" ) + "" );
                                tsdsb.setActive_name( efsMap.get( "active_name" ) + "" );
                                tsdsb.setDetail_content( efsMap.get( "topic_standard_detail_name" ) + "" );
                                tsdsb.setReal_score( efsMap.get( "real_score" ) + "" );
                                if ( k == 0 ) {
                                    tsdsb.setWork_content( efsMap.get( "work_content" ) + "" );
                                    tsdsb.setSuggest_initiate( efsMap.get( "suggest_initiate" ) + "" );
                                }
                                scoreDetailList.add( tsdsb );
                            }
                        }
                    }
                    tpaasb.setActiveScoreList( activeScoreList );
                }
                tpaasbList.add( tpaasb );
            }
            scoreReport.setPassiveAndActiveScoreList( tpaasbList );
            beanParams.put( "scoreDetailList", scoreDetailList );
            logger.info( "-----------主题评价结果-导出Excel-得分明细json：topic_id：" + topic_id + "-----------\n" + JacksonUtil.toJson( scoreDetailList ) );
        }
        beanParams.put( "scoreReport", scoreReport );
        logger.info( "-----------主题评价结果-导出Excel-得分报表json：topic_id：" + topic_id + "-----------\n" + JacksonUtil.toJson( scoreReport ) );
        exportFileName = scoreReport.getTopic_number() + "-" + DateUtil.getDateTime( "HHmmss" ) + "." + ( topicEvaluateTempletFile.endsWith( "xlsx" ) == true ? "xlsx" : "xls" );
        ExcelExportUtil.ExportExcel( topicEvaluateTempletFile, beanParams, topicEvaluateResultPath + exportFileName );
        return exportFileName;
    }
}
