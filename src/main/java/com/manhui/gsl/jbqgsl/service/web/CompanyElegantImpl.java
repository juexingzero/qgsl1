package com.manhui.gsl.jbqgsl.service.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.companyelegant.CompanyElegantResult;
import com.manhui.gsl.jbqgsl.dao.CompanyElegantMapper;
import com.manhui.gsl.jbqgsl.model.CompanyElegant;

/**
 * @类名称 CompanyElegantImpl.java
 * @类描述 企业风采
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月5日 上午9:33:25
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月5日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class CompanyElegantImpl implements ICompanyElegantService {
    private static final Logger  logger = LoggerFactory.getLogger( CompanyElegantImpl.class );
    @Resource
    private CompanyElegantMapper companyElegantMapper;

    @Override
    public PageInfo<CompanyElegant> getCompanyElegantList( Map<String, Object> conditionMap ) {
        logger.info( "----- 企业风采-获取企业列表 ==> start -----" );
        PageInfo<CompanyElegant> info;
        String index = conditionMap.get( "pageIndex" ) + "";
        String size = conditionMap.get( "pageSize" ) + "";
        if ( StringUtils.isNotEmpty( index ) && StringUtils.isNotEmpty( size ) ) {
            int pageIndex = Integer.valueOf( index );
            int pageSize = Integer.valueOf( size );
            PageHelper.startPage( pageIndex + 1, pageSize );
        }
        List<CompanyElegant> feedbackList = companyElegantMapper.getCompanyElegantList( conditionMap );
        if ( feedbackList != null && feedbackList.size() > 0 ) {
            info = new PageInfo<CompanyElegant>( feedbackList );
        }
        else {
            info = new PageInfo<CompanyElegant>();
        }
        logger.info( "----- 企业风采-获取企业列表 ==> end -----" );
        return info;
    }

    @Override
    public List<CompanyElegantResult> getCompanyElegantListForApp( Map<String, Object> conditionMap ) {
        logger.info( "----- 企业风采-获取企业列表，用于APP ==> start -----" );
        List<CompanyElegantResult> elegantList = companyElegantMapper.getCompanyElegantListForApp( conditionMap );
        if(elegantList != null && elegantList.size() > 0) {
            for(CompanyElegantResult cer : elegantList) {
                String company_logo = cer.getCompany_logo();
                if(company_logo != null && !"".equals( company_logo )) {
                    company_logo = company_logo.substring(8,company_logo.length());
                    cer.setCompany_logo( "/upload/downloadImage?imageName="+company_logo );
                }
            }
        }
        logger.info( "----- 企业风采-获取企业列表，用于APP ==> end -----" );
        return elegantList;
    }

    @Override
    public CompanyElegant getCompanyElegantDetail( String elegant_id ) {
        logger.info( "----- 企业风采-获取企业详情 ==> start -----" );
        CompanyElegant elegant = companyElegantMapper.getCompanyElegantDetail( elegant_id );
        //处理图片显示
        String company_image = elegant.getCompany_image();
        if ( StringUtils.isNotBlank( company_image ) ) {
            String imgs[] = company_image.split( "," );
            List<Map<String, String>> listMap = new ArrayList<>();
            for ( int i = 0; i < imgs.length; i++ ) {
                Map<String, String> imgMap = new HashMap<>();
                imgMap.put( "name", "" );
                imgMap.put( "path", imgs[i] );
                listMap.add( imgMap );
            }
            elegant.setImgObj( new Gson().toJson( listMap ) );
        }else {
            elegant.setImgObj( "" );
        }
        logger.info( "----- 企业风采-获取企业详情 ==> end -----" );
        return elegant;
    }

    @Override
    public CompanyElegantResult getCompanyElegantDetailForApp( String elegant_id ) {
        logger.info( "----- 企业风采-获取企业详情，用于APP ==> start -----" );
        CompanyElegantResult cer = companyElegantMapper.getCompanyElegantDetailForApp( elegant_id );
        logger.info( "----- 企业风采-获取企业详情，用于APP ==> end -----" );
        return cer;
    }

    @Override
    public Integer saveCompanyElegant( CompanyElegant elegant ) {
        Integer flag = 0;
        if ( elegant.getElegant_id() != null && !"".equals( elegant.getElegant_id() ) ) {
            logger.info( "----- 企业风采-更新企业信息 ==> start -----" );
            elegant.setRelease_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
            flag = companyElegantMapper.updateCompanyElegant( elegant );
            logger.info( "----- 企业风采-更新企业信息 ==> end -----" );
        }
        else {
            logger.info( "----- 企业风采-新增企业信息 ==> start -----" );
            elegant.setElegant_id( UUIDUtil.getUUID() );
            elegant.setRelease_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
            flag = companyElegantMapper.insertCompanyElegant( elegant );
            logger.info( "----- 企业风采-新增企业信息 ==> end -----" );
        }
        return flag;
    }

    @Override
    public Integer deleteCompanyElegant( String elegant_id ) {
        logger.info( "----- 企业风采-删除企业信息 ==> start -----" );
        Integer flag = companyElegantMapper.deleteCompanyElegant( elegant_id );
        logger.info( "----- 企业风采-删除企业信息 ==> end -----" );
        return flag;
    }
}
