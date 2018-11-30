package com.manhui.gsl.jbqgsl.service.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.EvaluateFlowingScoreMapper;
import com.manhui.gsl.jbqgsl.dao.MessageMapper;
import com.manhui.gsl.jbqgsl.dao.TopicEvaluateMapper;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;
import com.manhui.gsl.jbqgsl.model.TopicActiveInfo;
import com.manhui.gsl.jbqgsl.model.TopicEvaluate;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandard;
import com.manhui.gsl.jbqgsl.model.TopicEvaluateStandardDetail;
import com.manhui.gsl.jbqgsl.model.TopicPassiveInfo;

/**
 * @类名称 TopicEvaluateServiceImpl.java
 * @类描述 主题评价管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月7日 下午2:30:58
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月7日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class TopicEvaluateServiceImpl implements ITopicEvaluateService {
    private static final Logger        logger = LoggerFactory.getLogger( TopicEvaluateServiceImpl.class );
    @Resource
    private TopicEvaluateMapper        topicEvaluateMapper;
    @Resource
    private MessageMapper              messageMapper;
    @Resource
    private EvaluateFlowingScoreMapper evaluateFlowingScoreMapper;
    @Value( "${message_content_templet}" )
    public String                      messageContentTemplet;

    /**
     * @方法名称 getTopicEvaluateList
     * @功能描述 主题评价 - 获取主题列表
     * @作者 kevin
     * @创建时间 2018年8月7日 下午2:35:36
     * @修改时间 2018年11月19日 下午16:11:14，增加"双向评价街镇用户"访问该列表的数据过滤
     * @param topic
     * @param user_type
     * @param mobile_no
     * @return
     */
    @Override
    public List<TopicEvaluate> getTopicEvaluateList( TopicEvaluate topic, String user_type, String mobile_no ) {
        logger.info( "----- 主题评价-获取主题列表 ==> start -----" );
        List<TopicEvaluate> teOldList = topicEvaluateMapper.getTopicEvaluateList( topic );
        List<TopicEvaluate> teNewList = new ArrayList<>();
        if ( teOldList != null && teOldList.size() > 0 ) {
            //双向评价街镇用户
            if ( "2".equals( user_type ) ) {
                String topic_ids = topicEvaluateMapper.getPassivesByLinkmanPhone( mobile_no );
                if ( topic_ids != null && !"".equals( topic_ids ) ) {
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
                newTe.setInstitution_name_passives( topicEvaluateMapper.getPassivesByTopicId( newTe.getTopic_id() ) );
                newTe.setInstitution_name_actives( topicEvaluateMapper.getActivesByTopicId( newTe.getTopic_id() ) );
            }
        }
        logger.info( "----- 主题评价-获取主题列表 ==> end -----" );
        return teNewList;
    }

    /**
     * @方法名称 getTopicEvaluateTotal
     * @功能描述 主题评价 - 获取主题列表总数
     * @作者 kevin
     * @创建时间 2018年8月7日 下午2:36:13
     * @param topic
     * @return
     */
    @Override
    public Integer getTopicEvaluateTotal( TopicEvaluate topic ) {
        logger.info( "----- 主题评价-获取主题列表总数 ==> start -----" );
        Integer total = topicEvaluateMapper.getTopicEvaluateTotal( topic );
        logger.info( "----- 主题评价-获取主题列表总数 ==> end -----" );
        return total;
    }

    /**
     * @方法名称 getPassiveList
     * @功能描述 主题评价 - 获取被评价方列表
     * @作者 kevin
     * @创建时间 2018年8月10日 下午5:45:46
     * @param institution_id
     * @param topic_type
     * @return
     */
    @Override
    public List<Map<String, Object>> getPassiveListForCheck( String institution_id, String topic_type ) {
        logger.info( "----- 主题评价-获取被评价方列表 ==> start -----" );
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put( "institution_id", institution_id );
        paramMap.put( "topic_type", topic_type );
        List<Map<String, Object>> passiveList = topicEvaluateMapper.getPassiveListForCheck( paramMap );
        logger.info( "----- 主题评价-获取被评价方列表 ==> end -----" );
        return passiveList;
    }

    /**
     * @方法名称 saveTopicEvaluate
     * @功能描述 主题评价 - 保存发起主题评价内容
     * @作者 kevin
     * @创建时间 2018年8月10日 下午5:05:46
     * @param topic
     * @param standard
     * @param passiveList
     * @param activeList
     * @return
     */
    @Override
    public Integer saveTopicEvaluate(
            TopicEvaluate topic,
            TopicEvaluateStandard standard,
            List<TopicPassiveInfo> passiveList,
            List<TopicActiveInfo> activeList ) {
        Integer flag = 0;
        String topic_id = topic.getTopic_id();
        //更新
        if ( topic_id != null && !"".equals( topic_id ) ) {
            logger.info( "----- 主题评价-更新主题评价内容 ==> start -----" );
            //主题
            flag = this.updateTopicEvaluate( topic );
            //标准
            this.deleteStandard( topic_id );
            this.insertStandard( topic_id, standard );
            //被评价方
            this.deletePassive( topic_id );
            this.insertPassive( topic_id, passiveList );
            //评价方
            this.deleteActive( topic_id );
            this.insertActive( topic_id, activeList );
            logger.info( "----- 主题评价-更新主题评价内容 ==> end -----" );
        }
        //新增
        else {
            logger.info( "----- 主题评价-新增主题评价内容 ==> start -----" );
            topic_id = UUIDUtil.getUUID();
            //主题
            topic.setTopic_id( topic_id );
            topic.setTopic_number( this.createNewTopicNumber() );
            topic.setEvaluate_state( "0" );
            topic.setCreate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
            flag = topicEvaluateMapper.insertTopicEvaluate( topic );
            //标准
            this.insertStandard( topic_id, standard );
            //被评价方
            this.insertPassive( topic_id, passiveList );
            //评价方
            this.insertActive( topic_id, activeList );
            logger.info( "----- 主题评价-新增主题评价内容 ==> end -----" );
        }
        return flag;
    }

    /**
     * @方法名称 createNewTopicNumber
     * @功能描述 创建新的主题编号
     * @作者 kevin
     * @创建时间 2018年8月13日 下午7:54:01
     * @return
     */
    private String createNewTopicNumber() {
        String topic_number = "PJ";
        String currentDate = DateUtil.getDateTime( "yyyyMMdd" );
        String max = topicEvaluateMapper.getMaxTopicNumber();
        if ( max != null && !"".equals( max ) ) {
            String date = max.substring( 2, 10 );
            if ( currentDate.equals( date ) ) {
                String number = max.substring( 11, 13 );
                String newNumber = "";
                int num = Integer.valueOf( number ) + 1;
                if ( num < 10 ) {
                    newNumber = "00" + num;
                }
                else if ( num < 100 ) {
                    newNumber = "0" + num;
                }
                else {
                    newNumber = "" + num;
                }
                topic_number = topic_number + currentDate + newNumber;
            }
            else {
                topic_number = topic_number + currentDate + "001";
            }
        }
        else {
            topic_number = topic_number + currentDate + "001";
        }
        return topic_number;
    }

    /**
     * @方法名称 updateTopicEvaluate
     * @功能描述 更新主题评价信息
     * @作者 kevin
     * @创建时间 2018年8月13日 下午8:14:01
     * @return
     */
    @Override
    public Integer updateTopicEvaluate( TopicEvaluate topic ) {
        topic.setUpdate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
        return topicEvaluateMapper.updateTopicEvaluate( topic );
    }

    /**
     * @方法名称 insertStandard
     * @功能描述 插入主题评价标准及详情
     * @作者 kevin
     * @创建时间 2018年8月21日 下午3:02:00
     * @param topic_id
     * @param standard
     * @return
     */
    private Integer insertStandard( String topic_id, TopicEvaluateStandard standard ) {
        standard.setTopic_id( topic_id );
        standard.setCreate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
        topicEvaluateMapper.insertStandard( standard );
        List<TopicEvaluateStandardDetail> detailList = standard.getEvaluateDetailList();
        if ( detailList != null && detailList.size() > 0 ) {
            for ( TopicEvaluateStandardDetail detail : detailList ) {
                detail.setTopic_id( topic_id );
                //标准参数
                detail.setCreate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
                topicEvaluateMapper.insertStandardDetail( detail );
                List<TopicEvaluateStandardDetail> detailChildList = detail.getEvaluateDetailChildList();
                if ( detailChildList != null && detailChildList.size() > 0 ) {
                    for ( TopicEvaluateStandardDetail children : detailChildList ) {
                        children.setTopic_id( topic_id );
                        topicEvaluateMapper.insertStandardDetail( children );
                    }
                }
            }
        }
        return 1;
    }

    /**
     * @方法名称 deleteStandard
     * @功能描述 删除主题评价标准及详情
     * @作者 kevin
     * @创建时间 2018年8月21日 下午2:46:26
     * @param topic_id
     * @return
     */
    private Integer deleteStandard( String topic_id ) {
        topicEvaluateMapper.deleteStandard( topic_id );
        topicEvaluateMapper.deleteStandardDetail( topic_id );
        return 1;
    }

    /**
     * @方法名称 insertPassive
     * @功能描述 插入被评价方
     * @作者 kevin
     * @创建时间 2018年8月21日 下午3:06:02
     * @param topic_id
     * @param passiveList
     * @return
     */
    private Integer insertPassive( String topic_id, List<TopicPassiveInfo> passiveList ) {
        for ( TopicPassiveInfo passive : passiveList ) {
            passive.setTopic_id( topic_id );
            passive.setCreate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
            topicEvaluateMapper.insertTopicPassiveInfo( passive );
        }
        return 1;
    }

    /**
     * @方法名称 deletePassive
     * @功能描述 删除被评价方
     * @作者 kevin
     * @创建时间 2018年8月21日 下午2:46:26
     * @param topic_id
     * @return
     */
    private Integer deletePassive( String topic_id ) {
        topicEvaluateMapper.deletePassive( topic_id );
        return 1;
    }

    /**
     * @方法名称 insertActive
     * @功能描述 插入评价方
     * @作者 kevin
     * @创建时间 2018年8月21日 下午3:06:02
     * @param topic_id
     * @param activeList
     * @return
     */
    private Integer insertActive( String topic_id, List<TopicActiveInfo> activeList ) {
        for ( TopicActiveInfo active : activeList ) {
            active.setTopic_id( topic_id );
            active.setIs_evaluate( "0" );
            active.setCreate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
            topicEvaluateMapper.insertTopicActiveInfo( active );
        }
        return 1;
    }

    /**
     * @方法名称 deleteActive
     * @功能描述 删除评价方
     * @作者 kevin
     * @创建时间 2018年8月21日 下午2:46:26
     * @param topic_id
     * @return
     */
    private Integer deleteActive( String topic_id ) {
        topicEvaluateMapper.deleteActive( topic_id );
        return 1;
    }

    /**
     * @方法名称 deleteStandardDetail
     * @功能描述 删除主题评价标准详情
     * @作者 kevin
     * @创建时间 2018年8月21日 下午2:46:26
     * @param topic_id
     * @return
     */
    public Integer deleteStandardDetail( String topic_id ) {
        return topicEvaluateMapper.deleteStandardDetail( topic_id );
    }

    /**
     * @方法名称 getTopicEvaluateDetailData
     * @功能描述 获取主题评价详情数据
     * @作者 kevin
     * @创建时间 2018年8月13日 下午8:36:24
     * @return
     */
    @Override
    public Map<String, Object> getTopicEvaluateDetailData( String topic_id ) {
        Map<String, Object> resultMap = new HashMap<>();
        //主题
        TopicEvaluate topic_data = topicEvaluateMapper.getTopicEvaluate( topic_id );
        resultMap.put( "topic_data", topic_data );
        //标准
        TopicEvaluateStandard tes = topicEvaluateMapper.getTopicEvaluateStandard( topic_data.getTopic_id() );
        List<TopicEvaluateStandardDetail> tesdList = topicEvaluateMapper
                .getTopicEvaluateStandardDetail( tes.getTopic_id(), tes.getStandard_id(), null, 1 );
        for ( TopicEvaluateStandardDetail tesd : tesdList ) {
            tesd.setEvaluateDetailChildList(
                    topicEvaluateMapper
                            .getTopicEvaluateStandardDetail( tesd.getTopic_id(), null, tesd.getDetail_id(), 2 ) );
        }
        tes.setEvaluateDetailList( tesdList );
        resultMap.put( "standard_data", tes );
        //被评价方
        List<Map<String, Object>> tpiList = topicEvaluateMapper.getTopicPassiveList( topic_id );
        resultMap.put( "passive_data", tpiList );
        //评价方
        List<Map<String, Object>> taiList = topicEvaluateMapper.getTopicActiveList( topic_id );
        resultMap.put( "active_data", taiList );
        return resultMap;
    }

    /**
     * @方法名称 checkAndUpdateEvaluateState
     * @功能描述 检测并更新评价状态
     * @作者 kevin
     * @创建时间 2018年8月17日 下午2:33:46
     * @param currentDate
     * @return
     */
    @Override
    public Integer checkAndUpdateEvaluateState( String currentDate ) {
        Integer result = 0;
        if ( currentDate == null || "".equals( currentDate ) ) {
            currentDate = DateUtil.getDateTime( Constant.DATEMIN_PATTERN );
        }
        long current = DateUtil.strToDate( currentDate, Constant.DATEMIN_PATTERN ).getTime();
        List<TopicEvaluate> teList = topicEvaluateMapper.getTopicEvaluateList( null );
        if ( teList != null && teList.size() > 0 ) {
            String topic_name = "";
            for ( TopicEvaluate te : teList ) {
                if ( !"0".equals( te.getEvaluate_state() ) ) {
                    long startTime = DateUtil.strToDate( te.getEvaluate_start_time(), Constant.DATEMIN_PATTERN )
                            .getTime();
                    long endTime = DateUtil.strToDate( te.getEvaluate_end_time(), Constant.DATEMIN_PATTERN ).getTime();
                    String state = te.getEvaluate_state();
                    boolean flag = false;
                    //未开始
                    if ( "1".equals( state ) ) {
                        if ( current >= startTime ) {
                            flag = true;
                            te.setEvaluate_state( "2" );
                            //评价开始时给相关的评价方发送消息
                            this.sendMessageToActives( te.getTopic_id(), te.getTopic_name() );
                        }
                    }
                    //评价中
                    else if ( "2".equals( state ) ) {
                        if ( current >= endTime ) {
                            flag = true;
                            te.setEvaluate_state( "3" );
                            //评价结束时计算并更新被评价方的真实平均分
                            if ( "3".equals( te.getEvaluate_state() ) ) {
                                this.countPassiveRealScoreAvg( te.getTopic_id() );
                            }
                        }
                    }
                    //更新评价状态
                    if ( flag ) {
                        te.setUpdate_time( DateUtil.getDateTime( Constant.DATETIME_PATTERN ) );
                        result = topicEvaluateMapper.updateTopicEvaluate( te );
                        topic_name += te.getTopic_name() + "：" + state + "-->" + te.getEvaluate_state() + ", ";
                    }
                }
            }
            if ( topic_name.length() > 2 ) {
                logger.info( "==============>已被更新评价状态的主题：" + topic_name.substring( 0, topic_name.length() - 2 ) );
            }
        }
        return result;
    }

    /**
     * @方法名称 sendMessageToActives
     * @功能描述 评价开始时给相关的评价方发送消息
     * @作者 kevin
     * @创建时间 2018年9月1日 上午10:45:08
     * @param topic_id
     * @param topic_name
     */
    private void sendMessageToActives( String topic_id, String topic_name ) {
        List<Map<String, Object>> activeList = topicEvaluateMapper.getTopicActiveListForSendMessage( topic_id );
        if ( activeList != null && activeList.size() > 0 ) {
            String receive_name = "";
            //添加消息模板信息
            MessageInfo info = new MessageInfo();
            info.setMessage_id( UUIDUtil.getUUID() );
            info.setMessage_type( "2" );
            info.setMessage_mode( "1" );
            info.setMessage_content( "您有一条双向评价“" + topic_name + "”待处理，请知悉！" );
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
            logger.info(
                    "--------------主题评价：" +
                            topic_name +
                            " 开始，发送消息给评价方：" +
                            receive_name.substring( 0, receive_name.length() - 2 ) +
                            "--------------\n" );
        }
    }

    /**
     * 统计被评价方的真实平均分
     * 
     * @param topic_id
     */
    private void countPassiveRealScoreAvg( String topic_id ) {
        //根据topic_id获取被评价方列表
        List<Map<String, Object>> passiveList = topicEvaluateMapper.getTopicPassiveList( topic_id );
        if ( passiveList != null && passiveList.size() > 0 ) {
            String passive_institution_type = "";
            for ( Map<String, Object> passiveMap : passiveList ) {
                passive_institution_type = passiveMap.get( "institution_type" ) + "";
                String institution_id = passiveMap.get( "institution_id" ) + "";
                String institution_name = passiveMap.get( "institution_name" ) + "";
                //被评价方得分详情列表
                List<Map<String, Object>> activeScoreList = evaluateFlowingScoreMapper.getEvaluateFlowingScoreGroupList( topic_id, institution_id, null, null );
                double realScoreAvg = 0.00; //所得平均分
                if ( activeScoreList != null && activeScoreList.size() > 0 ) {
                    /*
                     * 政府部门最终得分=参评企业数所打分数之和/参评企业数
                     * 注：
                     *   参评企业：民营企业
                     */
                    if ( "1".equals( passive_institution_type ) ) {
                        int activeNumber = 0; //参评企业数量
                        double activeScoreSum = 0.00; //参评企业打分之和
                        for ( int i = 0; i < activeScoreList.size(); i++ ) {
                            Map<String, Object> scoreMap = activeScoreList.get( i );
                            String real_score = scoreMap.get( "real_score" ) + "";
                            String is_evaluate = scoreMap.get( "is_evaluate" ) + "";
                            if ( "1".equals( is_evaluate ) ) {
                                activeNumber += 1;
                                activeScoreSum += Double.valueOf( real_score );
                            }
                        }
                        realScoreAvg = Double.valueOf( String.format( "%.2f", activeScoreSum / activeNumber ) );
                    }
                    /*
                     * 政府街镇最终得分=参与评价的被主管企业分数之和/参与评价的被主管企业数
                     */
                    else if ( "2".equals( passive_institution_type ) ) {
                        int activeNumber = 0; //参评企业数量
                        double activeScoreSum = 0.00; //参评企业打分之和
                        for ( int i = 0; i < activeScoreList.size(); i++ ) {
                            Map<String, Object> scoreMap = activeScoreList.get( i );
                            if ( institution_id.equals( scoreMap.get( "street_main_id" ) + "" ) ) { //街镇算分只用其下属的主管企业评分来算
                                String real_score = scoreMap.get( "real_score" ) + "";
                                String is_evaluate = scoreMap.get( "is_evaluate" ) + "";
                                if ( "1".equals( is_evaluate ) ) {
                                    activeNumber += 1;
                                    activeScoreSum += Double.valueOf( real_score );
                                }
                            }
                        }
                        realScoreAvg = Double.valueOf( String.format( "%.2f", activeScoreSum / activeNumber ) );
                    }
                    /*
                     * 民营企业最终得分=(主管部门*50%)+(行政执法部门*40%)+(其他部门*10%)
                     * 注：
                     *   主管部门：区级，且挂有主管企业
                     *   执法部门：区级，无主管企业
                     *   其他部门：镇级
                     */
                    else {
                        double zgScoreSum = 0.00; //主管部门打分之和
                        double zfScoreSum = 0.00; //执法部门打分之和
                        double qtScoreSum = 0.00; //其他部门打分之和
                        for ( int j = 0; j < activeScoreList.size(); j++ ) {
                            Map<String, Object> scoreMap = activeScoreList.get( j );
                            String active_institution_type = scoreMap.get( "institution_type" ) + "";
                            String active_institution_id = scoreMap.get( "institution_id" ) + "";
                            String real_score = scoreMap.get( "real_score" ) + "";
                            //区级
                            if ( "1".equals( active_institution_type ) ) {
                                //主管部门
                                if ( isInstitutionMain( topic_id, active_institution_id ) ) {
                                    zgScoreSum += Double.valueOf( real_score );
                                }
                                //执法部门
                                else {
                                    zfScoreSum += Double.valueOf( real_score );
                                }
                            }
                            //镇级，即其他部门
                            else {
                                qtScoreSum += Double.valueOf( real_score );
                            }
                        }
                        realScoreAvg = Double.valueOf(String.format( "%.2f", zgScoreSum * 0.5 + zfScoreSum * 0.4 + qtScoreSum * 0.1 ) );
                    }
                    //更新被评价方所得平均分
                    if ( realScoreAvg > 0 ) {
                        Integer flag = topicEvaluateMapper.updateTopicPassiveInfo( topic_id, institution_id, realScoreAvg );
                        if ( flag > 0 ) {
                            logger.info( "--------------主题评价结果-平均分计算，被评价方：" + institution_name + "，所得平均分：" + realScoreAvg + "--------------\n" );
                        }
                    }
                }
            }
        }
    }

    //检测该评价方是否是主管部门
    private boolean isInstitutionMain( String topic_id, String active_institution_id ) {
        boolean flag = false;
        List<TopicActiveInfo> taiList = topicEvaluateMapper.isInstitutionMain( topic_id, active_institution_id );
        if ( taiList != null && taiList.size() > 0 ) {
            flag = true;
        }
        return flag;
    }
}
