package com.manhui.gsl.jbqgsl.service.web.commerce.impl;

import javax.annotation.Resource;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.MD5Util;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.AppUserMapper;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.web.commerce.CommerceManagerMapper;
import com.manhui.gsl.jbqgsl.model.commerce.CommerceCategory;
import com.manhui.gsl.jbqgsl.service.web.commerce.ICommerceManagerService;

import java.util.List;

/**
 * @类名称 CommerceManagerServiceImpl.java
 * @类描述 商会管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月25日 上午10:43:31
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年10月25日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class CommerceManagerServiceImpl implements ICommerceManagerService {
    private static final Logger    logger = LoggerFactory.getLogger( CommerceManagerServiceImpl.class );
    @Resource
    private CommerceManagerMapper commerceCategoryMapper;

    @Resource
    private AppUserMapper appUserMapper;

    /**
     * @方法名称 queryCommerceList
     * @功能描述 商会列表-获取数据
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月2日10:25:46
     * @return
     */
    @Override
    public List<MemberJgShxx> queryCommerceList() {
        return commerceCategoryMapper.queryCommerceList();
    }

    /**
     * @方法名称 queryCommerceData
     * @功能描述 商会类别-获取有下属商会数据
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月2日10:25:46
     * @return
     */
    @Override
    public List<MemberJgShxx> queryCommerceData(String LYZJ) {
        return commerceCategoryMapper.queryCommerceData(LYZJ);
    }

    /**
     * @方法名称 queryCommerceCategoryDetail
     * @功能描述 商会详细-获取数据
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月2日10:25:46
     * @return
     */
    @Override
    public MemberJgShxx queryCommerceDetail(String ID) {
        return commerceCategoryMapper.queryCommerceDetail(ID);
    }

    /**
     * @方法名称 saveCommerceCategory
     * @功能描述 商会类别-保存类别
     * @作者 kevin
     * @创建时间 2018年10月25日 上午10:45:21
     * @param category
     * @return
     */
    @Override
    public Integer saveCommerceCategory( MemberJgShxx category ) {
        logger.info( "----- 商会类别-新增类别 ==> start -----" );
        category.setID(UUIDUtil.getUUID().toString());
        Integer flag = commerceCategoryMapper.insertCommerceCategory( category );
        logger.info( "----- 商会类别-新增类别 ==> end -----" );
        return flag;
    }

    /**
     * @方法名称 editCommerceCategory
     * @功能描述 商会类别-编辑类别
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月2日10:25:46
     * @return
     */
    @Override
    public Integer editCommerceCategory(MemberJgShxx category) {
        logger.info( "----- 商会类别-修改类别 ==> start -----" );
        Integer flag =  commerceCategoryMapper.updateCommerceCategory(category);
        logger.info( "----- 商会类别-修改类别 ==> end -----" );
        return flag;
    }
    /**
     * @方法名称 deleteCommerceCategory
     * @功能描述 商会类别-删除类别
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月2日14:15:48
     * @return
     */
    @Override
    public Integer deleteCommerce(String ID) {
        return commerceCategoryMapper.deleteCommerce(ID);
    }

    /**
     * @方法名称 saveCommerce
     * @功能描述 商会-保存商会
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月2日14:15:48
     * @param category
     * @return
     */
    @Override
    public Integer saveCommerce(MemberJgShxx category) {
        logger.info( "----- 商会类别-新增类别 ==> start -----" );
        // 先将用户数据存入app_user用户表里
        AppUser user = new AppUser();
        user.setUser_id(UUIDUtil.getUUID());
        user.setLogin_username(category.getSJ());
        String password = category.getSJ().substring(5, 11);
        //MD5加密
        user.setLogin_password(MD5Util.encrypt(password));
        user.setUser_name(category.getLXR());
        user.setUser_sex("0");
        user.setUser_phone(category.getSJ());
        user.setUser_email(category.getYX());
        user.setUser_type("2");
        user.setIs_public("1");
        user.setCreate_time(DateUtil.getTime());
        appUserMapper.saveUser(user);
        category.setID(UUIDUtil.getUUID().toString());
        Integer flag = commerceCategoryMapper.saveCommerce( category );
        logger.info( "----- 商会类别-新增类别 ==> end -----" );
        return flag;
    }

    /**
     * @方法名称 editCommerce
     * @功能描述 商会-编辑商会
     * @作者 Jiangxiaosong
     * @创建时间 2018年11月2日10:25:46
     * @return
     */
    @Override
    public Integer editCommerce(MemberJgShxx category) {
        logger.info( "----- 商会类别-修改类别 ==> start -----" );
        Integer flag =  commerceCategoryMapper.updateCommerce(category);
        logger.info( "----- 商会类别-修改类别 ==> end -----" );
        return flag;
    }

    @Override
    public List<MemberJgShxx> checkCommerceName(String SHMC) {
        return commerceCategoryMapper.checkCommerceName(SHMC);
    }
}
