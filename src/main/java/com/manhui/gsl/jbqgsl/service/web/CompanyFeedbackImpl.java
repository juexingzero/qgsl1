package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.companyfeedback.CompanyFeedbackResult;
import com.manhui.gsl.jbqgsl.dao.CompanyFeedbackMapper;
import com.manhui.gsl.jbqgsl.model.CompanyFeedback;

/**
 * @类名称 CompanyFeedbackImpl.java
 * @类描述 企业之声
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月4日 上午10:38:25
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月4日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class CompanyFeedbackImpl implements ICompanyFeedbackService {
    private static final Logger   logger = LoggerFactory.getLogger( CompanyFeedbackImpl.class );
    @Resource
    private CompanyFeedbackMapper companyFeedbackMapper;

    @Override
    public PageInfo<CompanyFeedback> getCompanyFeedbackList( Map<String, Object> conditionMap ) {
        logger.info( "----- 企业之声-获取企业之声列表 ==> start -----" );
        PageInfo<CompanyFeedback> info;
        String index = conditionMap.get( "pageIndex" ) + "";
        String size = conditionMap.get( "pageSize" ) + "";
        if ( StringUtils.isNotEmpty( index ) && StringUtils.isNotEmpty( size ) ) {
            int pageIndex = Integer.valueOf( index );
            int pageSize = Integer.valueOf( size );
            PageHelper.startPage( pageIndex+1, pageSize );
        }
        List<CompanyFeedback> feedbackList = companyFeedbackMapper.getCompanyFeedbackList( conditionMap );
        if ( feedbackList != null && feedbackList.size() > 0 ) {
            info = new PageInfo<CompanyFeedback>( feedbackList );
        }
        else {
            info = new PageInfo<CompanyFeedback>();
        }
        logger.info( "----- 企业之声-获取企业之声列表 ==> end -----" );
        return info;
    }

    @Override
    public List<CompanyFeedbackResult> getCompanyFeedbackListForApp( Map<String, Object> conditionMap ) {
        logger.info( "----- 企业之声-获取企业之声列表，用于APP ==> start -----" );
        List<CompanyFeedbackResult> feedbackList = companyFeedbackMapper.getCompanyFeedbackListForApp( conditionMap );
        if(feedbackList != null && feedbackList.size() > 0) {
            for(CompanyFeedbackResult feedback : feedbackList) {
                String feedback_type = feedback.getFeedback_type();
                if("1".equals( feedback_type )) {
                    feedback.setFeedback_type( "经济服务" );
                }else if("2".equals( feedback_type )) {
                    feedback.setFeedback_type( "政策咨询" );
                }else if("3".equals( feedback_type )) {
                    feedback.setFeedback_type( "法律维权" );
                }else if("4".equals( feedback_type )) {
                    feedback.setFeedback_type( "商会建设" );
                }else if("5".equals( feedback_type )) {
                    feedback.setFeedback_type( "党建工作" );
                }else if("6".equals( feedback_type )) {
                    feedback.setFeedback_type( "人事人才" );
                }else if("7".equals( feedback_type )) {
                    feedback.setFeedback_type( "其他" );
                }
            }
        }
        logger.info( "----- 企业之声-获取企业之声列表，用于APP ==> end -----" );
        return feedbackList;
    }

    @Override
    public CompanyFeedback getCompanyFeedbackDetail( String feedback_id ) {
        logger.info( "----- 企业之声-获取企业之声详情 ==> start -----" );
        CompanyFeedback feedback = companyFeedbackMapper.getCompanyFeedbackDetail( feedback_id );
        logger.info( "----- 企业之声-获取企业之声详情 ==> end -----" );
        return feedback;
    }

    @Override
    public CompanyFeedbackResult getCompanyFeedbackDetailForApp( String feedback_id ) {
        logger.info( "----- 企业之声-获取企业之声详情，用于APP ==> start -----" );
        CompanyFeedbackResult cfr = companyFeedbackMapper.getCompanyFeedbackDetailForApp( feedback_id );
        logger.info( "----- 企业之声-获取企业之声详情，用于APP ==> end -----" );
        return cfr;
    }

    @Override
    public Integer saveCompanyFeedback( CompanyFeedback feedback ) {
        Integer flag = 0;
        if ( feedback.getFeedback_id() != null && !"".equals( feedback.getFeedback_id() ) ) {
            logger.info( "----- 企业之声-更新反馈信息 ==> start -----" );
            feedback.setAnswer_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
            flag = companyFeedbackMapper.updateCompanyFeedback( feedback );
            logger.info( "----- 企业之声-更新反馈信息 ==> end -----" );
        }
        else {
            logger.info( "----- 企业之声-新增反馈信息 ==> start -----" );
            feedback.setFeedback_id( UUIDUtil.getUUID() );
            feedback.setFeedback_number( this.createNewFeedbackNumber() );
            feedback.setFeedback_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
            flag = companyFeedbackMapper.insertCompanyFeedback( feedback );
            logger.info( "----- 企业之声-新增反馈信息 ==> end -----" );
        }
        return flag;
    }

    @Override
    public Integer deleteCompanyFeedback( String feedback_id ) {
        logger.info( "----- 企业之声-删除反馈信息 ==> start -----" );
        Integer flag = companyFeedbackMapper.deleteCompanyFeedback( feedback_id );
        logger.info( "----- 企业之声-删除反馈信息 ==> end -----" );
        return flag;
    }

    private String createNewFeedbackNumber() {
        String topic_number = "ZS";
        String currentDate = DateUtil.getDateTime( "yyyyMMdd" );
        String max = companyFeedbackMapper.getMaxFeedbackNumber();
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
}
