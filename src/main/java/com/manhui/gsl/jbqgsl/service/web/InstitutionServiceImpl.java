package com.manhui.gsl.jbqgsl.service.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.MD5Util;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.InstitutionMapper;
import com.manhui.gsl.jbqgsl.dao.UserMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppUserMapper;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.Position;
import com.manhui.gsl.jbqgsl.model.User;

/**
 * @类名称 InstitutionServiceImpl.java
 * @类描述
 * 
 *      <pre>
 * 参评机构模块service层接口实现，主要处理业务逻辑
 *      </pre>
 * 
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月3日 下午4:28:00
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月3日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class InstitutionServiceImpl implements IInstitutionService {
    private static final Logger logger = LoggerFactory.getLogger( InstitutionServiceImpl.class );
    @Resource
    private InstitutionMapper   institutionMapper;
    @Resource
    private UserMapper          userMapper;
    @Resource
    private AppUserMapper       appUserMapper;
    @Value( "${evaluate_user_position_id}" )
    public String               evaluate_user_position_id;

    @Override
    public List<Institution> queryInstitutionList(
            String institution_name,
            String institution_type,
            String street_main_id,
            Integer pageIndex,
            Integer pageSize ) {
        logger.info( "----- 获取参评机构列表 ==> start -----" );
        Map<String, Object> conditions = new HashMap<>();
        conditions.put( "institution_name", institution_name );
        conditions.put( "institution_type", institution_type );
        conditions.put( "street_main_id", street_main_id );
        conditions.put( "pageNo", pageIndex * pageSize );
        conditions.put( "pageSize", pageSize );
        List<Institution> institutionList = institutionMapper.queryInstitutionList( conditions );
        logger.info( "----- 获取参评机构列表 ==> end -----" );
        return institutionList;
    }

    @Override
    public Integer queryInstitutionTotal(
            String institution_name,
            String institution_type,
            String street_main_id,
            Integer pageIndex,
            Integer pageSize ) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put( "institution_name", institution_name );
        conditions.put( "institution_type", institution_type );
        conditions.put( "street_main_id", street_main_id );
        conditions.put( "pageNo", pageIndex * pageSize );
        conditions.put( "pageSize", pageSize );
        Integer total = institutionMapper.queryInstitutionTotal( conditions );
        return total;
    }

    @Override
    public JsonResult deleteInstitution( String institution_id ) {
        int count = institutionMapper.deleteInstitution( institution_id );
        //删除app_user表
        AppUser user = new AppUser();
        user.setInstitution_id( institution_id );
        user.setUpdate_time( DateUtil.getTime() );
        user.setDel_flag( "1" );
        if ( count == 1 ) {
            appUserMapper.updateUserInfo( user );
        }
        //删除sys_user
        User sysUser = new User();
        sysUser.setUser_id( institution_id );
        Integer deleteUser = userMapper.deleteUser( sysUser );
        //删除成功后,删除掉sys_user_position这张表
        if ( deleteUser > 0 ) {
            Position position = new Position();
            position.setUser_id( sysUser.getUser_id() );
            userMapper.deletePosition( position );
        }
        return new JsonResult();
    }

    @Override
    public Institution queryInstitution( String institution_id ) {
        return institutionMapper.queryInstitution( institution_id );
    }

    @Override
    public JsonResult editInstitution( Institution institution ) {
        AppUser user = appUserMapper.queryUser( institution.getInstitution_id() );
        if ( !user.getUser_phone().equals( institution.getInstitution_linkman_phone() ) ) {
            //验证新手机号是否已经注册
            AppUser appUser = appUserMapper.checkUserPhone( institution.getInstitution_linkman_phone() );
            if ( appUser != null ) {
                return new JsonResult( "该手机号码已经注册,请重新输入" );
            }
            user.setUser_phone( institution.getInstitution_linkman_phone() );
            user.setLogin_username( institution.getInstitution_linkman_phone() );
            user.setInstitution_id( institution.getInstitution_id() );
            user.setUser_name( institution.getInstitution_name() );
            user.setUpdate_time( DateUtil.getTime() );
            String login_password = institution.getInstitution_linkman_phone().substring( 5 );
            user.setLogin_password( MD5Util.encrypt( login_password ) );
            appUserMapper.updateUserInfo( user );
        }
        //修改sys_user表中数据---主要是验证机构修改后的电话号码
        User queryUser = userMapper.checkUser( institution );
        if ( queryUser != null ) {
            User sysUser = new User();
            sysUser.setUser_id( institution.getInstitution_id() );//将机构id当做用户id使用,以方便后面的删除,编辑
            sysUser.setUser_name( institution.getInstitution_linkman_name() );
            sysUser.setLogin_name( institution.getInstitution_linkman_phone() );
            sysUser.setPassword( MD5Util.encrypt( institution.getInstitution_linkman_phone().substring( 5 ) ) );
            sysUser.setMobile_no( institution.getInstitution_linkman_phone() );
            sysUser.setUser_type( "0" );
            sysUser.setAccount_status( "1" );
            sysUser.setUser_status( "1" );
            //			sysUser.setAlter_time(DateUtil.strToDate(new Date().toLocaleString(), "yyyy-MM-dd HH:mm:ss"));
            userMapper.updateUser( sysUser );
        }
        //修改机构表
        institutionMapper.editInstitution( institution );
        return new JsonResult();
    }

    @Override
    public JsonResult saveInstitution( Institution institution, HttpServletRequest request ) {
        //先将用户数据存入app_user用户表里
        AppUser user = new AppUser();
        user.setUser_id( UUIDUtil.getUUID() );
        user.setInstitution_id( institution.getInstitution_id() );
        user.setLogin_username( institution.getInstitution_linkman_phone() );
        String password = institution.getInstitution_linkman_phone().substring( 5, 11 );
        user.setLogin_password( MD5Util.encrypt( password ) );
        user.setUser_name( institution.getInstitution_name() );
        user.setUser_sex( "0" );
        user.setUser_phone( institution.getInstitution_linkman_phone() );
        user.setUser_email( institution.getInstitution_linkman_email() );
        user.setUser_type( "2" );
        user.setIs_public( "1" );
        user.setCreate_time( DateUtil.getTime() );
        appUserMapper.saveUser( user );
        //类型为街镇时保存到sys_User表
        if ( "2".equals( institution.getInstitution_type() ) ) {
            User sys_user = new User();
            sys_user.setUser_id( UUIDUtil.getUUID() );
            sys_user.setUser_name( institution.getInstitution_name() );
            sys_user.setLogin_name( institution.getInstitution_linkman_phone() );
            sys_user.setPassword( MD5Util.encrypt( password ) );
            sys_user.setEmail( institution.getInstitution_linkman_email() );
            sys_user.setMobile_no( institution.getInstitution_linkman_phone() );
            sys_user.setUser_type( "2" );
            sys_user.setAccount_status( "1" );
            sys_user.setUser_status( "1" );
            sys_user.setCreator_id( "9faac380235548998d4bc2b8b4a15193" );
            sys_user.setCreate_time( new Date() );
            userMapper.insertUser( sys_user );
            //为双向评价街镇用户分配权限-kevin
            Position position = new Position();
            position.setUser_id( sys_user.getUser_id() );
            position.setPosition_id( evaluate_user_position_id );
            position.setIs_primary( 1 );
            userMapper.insertPositionToUser( position );
        }
        //将新增的区级机构同步到sys_user表中
        if ( institution != null && "1".equals( institution.getInstitution_type() ) ) {
            User user1 = userMapper.queryUser( institution );
            if ( user1 != null ) {
                return new JsonResult( new RuntimeException( "该用户已存在,请验证" ) );
            }
            User sysUser = new User();
            sysUser.setUser_id( institution.getInstitution_id() );//将机构id当做用户id使用,以方便后面的删除,编辑
            sysUser.setUser_name( institution.getInstitution_linkman_name() );
            sysUser.setLogin_name( institution.getInstitution_linkman_phone() );
            sysUser.setPassword( MD5Util.encrypt( institution.getInstitution_linkman_phone().substring( 5 ) ) );
            sysUser.setMobile_no( institution.getInstitution_linkman_phone() );
            sysUser.setUser_type( "0" );
            sysUser.setAccount_status( "1" );
            sysUser.setUser_status( "1" );
            sysUser.setCreator_id( AppUtil.getCookieByName( request, "user_id" ) );
            Integer insertnum = userMapper.insertUser( sysUser );
            if ( insertnum > 0 ) {
                //与sys_dept中的dept_name字段进行匹配,匹配成功后获取到
                Dept dept = new Dept();
                dept.setInner_outer_dept( "1" );//外部机构
                List<Dept> queryDeptWhere = userMapper.queryDeptWhere( dept );
                if ( queryDeptWhere != null && !queryDeptWhere.isEmpty() ) {
                    for ( Dept dept2 : queryDeptWhere ) {
                        Position position = null;
                        if ( dept2.getDept_name().equals( institution.getInstitution_name() ) ) {//匹配部门名称--获取到部门id
                            position = new Position();
                            position.setDept_id( dept2.getDept_id() );
                            List<Position> queryPosition = userMapper.queryPosition( position );
                            //获取到该部门下面的岗位id,在position_user表中插入数据
                            if ( queryPosition != null && !queryPosition.isEmpty() ) {
                                for ( Position po : queryPosition ) {
                                    position.setUser_id( institution.getInstitution_id() );
                                    position.setIs_primary( 1 );
                                    position.setPosition_id( po.getPosition_id() );
                                    userMapper.insertPositionToUser( position );
                                }
                            }
                        }
                    }
                }
            }
        }
        institutionMapper.saveInstitution( institution );
        return new JsonResult();
    }

    @Override
    public JsonResult saveEnterpriseInstitution( Institution institution ) {
        //修改appUserInfo表数据
        AppUser user = new AppUser();
        user.setUser_type( "2" );
        user.setUser_name( institution.getInstitution_name() );
        user.setUser_phone( institution.getInstitution_linkman_phone() );
        appUserMapper.updateUserInfo( user );
        List<Institution> checkName = institutionMapper
                .queryInstitutionByNameAndPhone( institution.getInstitution_name() );
        if ( checkName != null && checkName.size() > 0 ) {
            return new JsonResult( "机构已存在，请重新选择！" );
        }
        //保存企业机构
        institutionMapper.saveInstitution( institution );
        return new JsonResult();
    }

    @Override
    public List<Institution> queryInstitutionByNameAndPhone( String institution_name ) {
        return institutionMapper.queryInstitutionByNameAndPhone( institution_name );
    }

    @Override
    public List<Institution> queryInstitutionByInstitutionMainId(
            String institution_id,
            String industry_id,
            String institution_name,
            Integer pageIndex,
            Integer pageSize ) {
        logger.info( "----- 获取参评机构列表 ==> start -----" );
        Map<String, Object> conditions = new HashMap<>();
        conditions.put( "institution_main_id", institution_id );
        conditions.put( "institution_name", institution_name );
        conditions.put( "industry_id", industry_id );
        if ( pageIndex != null && pageSize != null ) {
            conditions.put( "pageNo", pageIndex * pageSize );
            conditions.put( "pageSize", pageSize );
        }
        List<Institution> institutionList = institutionMapper.queryInstitutionByInstitutionMainId( conditions );
        logger.info( "----- 获取参评机构列表 ==> end -----" );
        return institutionList;
    }

    @Override
    public Integer queryInstitutionCountByInstitutionMainId(
            String institution_id,
            String industry_id,
            String institution_name ) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put( "institution_main_id", institution_id );
        conditions.put( "institution_name", institution_name );
        conditions.put( "industry_id", industry_id );
        Integer total = institutionMapper.queryInstitutionCountByInstitutionMainId( conditions );
        return total;
    }

    @Override
    public List<Institution> queryStreetInstitutionByInstitutionMainId(
            String institution_id,
            String industry_id,
            String institution_name,
            Integer pageIndex,
            Integer pageSize ) {
        logger.info( "----- 获取参评机构列表 ==> start -----" );
        Map<String, Object> conditions = new HashMap<>();
        conditions.put( "institution_main_id", institution_id );
        conditions.put( "institution_name", institution_name );
        conditions.put( "industry_id", industry_id );
        if ( pageIndex != null && pageSize != null ) {
            conditions.put( "pageNo", pageIndex * pageSize );
            conditions.put( "pageSize", pageSize );
        }
        List<Institution> institutionList = institutionMapper.queryStreetInstitutionByInstitutionMainId( conditions );
        logger.info( "----- 获取参评机构列表 ==> end -----" );
        return institutionList;
    }

    @Override
    public Integer queryStreetInstitutionCountByInstitutionMainId(
            String institution_id,
            String industry_id,
            String institution_name ) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put( "institution_main_id", institution_id );
        conditions.put( "institution_name", institution_name );
        conditions.put( "industry_id", industry_id );
        Integer total = institutionMapper.queryStreetInstitutionCountByInstitutionMainId( conditions );
        return total;
    }

    @Override
    public void cleanInstitutionMainId( String institution_id ) {
        institutionMapper.cleanInstitutionMainId( institution_id );
    }

    @Override
    public void saveInstitutionMainId( String institution_id, String main_institution ) {
        institutionMapper.saveInstitutionMainId( institution_id, main_institution );
    }

    @Override
    public void cleanStreetMainId( String institution_id ) {
        institutionMapper.cleanStreetMainId( institution_id );
    }

    @Override
    public void saveStreetMainId( String institution_id, String main_institution ) {
        institutionMapper.saveStreetMainId( institution_id, main_institution );
    }

    @Override
    public List<Map<String, Object>> queryEnterpriseMemberInfo( Map<String, Object> map ) {
        return institutionMapper.queryEnterpriseMemberInfo( map );
    }

    @Override
    public Institution queryInstitutionByMoblie( String mobile ) {
        return institutionMapper.queryInstitutionByMoblie( mobile );
    }
}
