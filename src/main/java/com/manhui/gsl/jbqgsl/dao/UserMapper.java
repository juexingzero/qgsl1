package com.manhui.gsl.jbqgsl.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.Func;
import com.manhui.gsl.jbqgsl.model.FuncUrl;
import com.manhui.gsl.jbqgsl.model.Icon;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.Menu;
import com.manhui.gsl.jbqgsl.model.Position;
import com.manhui.gsl.jbqgsl.model.PositionFunc;
import com.manhui.gsl.jbqgsl.model.User;

@Mapper
public interface UserMapper {
    /**
     * 部门树
     *
     * @return
     */
    List<Dept> queryDeptTree( Dept dept );

    /**
     * 查询部门列表
     *
     * @return
     */
    List<Dept> querySysDept( Dept dept );

    /**
     * 根据条件部门
     *
     * @return
     */
    List<Dept> queryDeptWhere( Dept dept );

    /**
     * 查询部门总数
     *
     * @return
     */
    Integer querySysDeptSize( Dept dept );

    /**
     * 查询部门岗位
     *
     * @return
     */
    List<Position> queryPosition( Position position );

    /**
     * 查询部门岗位列表
     *
     * @return
     */
    Integer queryPositionCount( Position position );

    /**
     * 查询部门人员
     *
     * @return
     */
    List<User> queryPositionUser( User user );

    /**
     * 查询部门人员总数
     *
     * @return
     */
    Integer queryPositionUserCount( User user );

    /**
     * 人员列表
     *
     * @return
     */
    List<User> queryUserList( User user );

    /**
     * 人员列表总数
     *
     * @return
     */
    Integer queryUserListCount( User user );

    /**
     * 根据条件查询人员信息
     *
     * @return
     */
    User queryUserWhere( User user );

    /**
     * 插入部门
     *
     * @param dept
     * @return
     */
    Integer insertDept( Dept dept );

    /**
     * 修改部门
     *
     * @param dept
     * @return
     */
    Integer updateDept( Dept dept );

    /**
     * 删除部门
     *
     * @param dept
     * @return
     */
    Integer deleteDept( Dept dept );

    /**
     * 插入岗位
     *
     * @param position
     * @return
     */
    Integer insertPosition( Position position );

    /**
     * 修改岗位
     *
     * @param position
     * @return
     */
    Integer updatePosition( Position position );

    /**
     * 删除岗位
     *
     * @param position
     * @return
     */
    Integer deletePosition( Position position );

    /**
     * 岗位添加人员
     *
     * @param position
     * @return
     */
    Integer insertPositionToUser( Position position );

    /**
     * 删除岗位或者人员
     *
     * @param position
     * @return
     */
    Integer deletePositionToUser( Position position );

    /**
     * 功能菜单列表
     *
     * @param menu
     * @return
     */
    List<Menu> queryMenuList( Menu menu );

    /**
     * 菜单树形结构列表
     *
     * @param menu
     * @return
     */
    List<Menu> queryMenuTree( Menu menu );

    /**
     * 功能菜单列表
     *
     * @param menu
     * @return
     */
    List<Menu> queryMenuWhere( Menu menu );

    /**
     * 插入菜单
     *
     * @param menu
     * @return
     */
    Integer insertMenu( Menu menu );

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    Integer updateMenu( Menu menu );

    /**
     * 删除菜单
     *
     * @param menu
     * @return
     */
    Integer deleteMenu( Menu menu );

    /**
     * 功能配置列表
     *
     * @param func
     * @return
     */
    List<Func> queryFuncList( Func func );

    /**
     * 功能配置列表总数
     *
     * @param func
     * @return
     */
    Integer queryFuncListCount( Func func );

    /**
     * 根据条件查询功能
     *
     * @param func
     * @return
     */
    Func queryFuncWhere( Func func );

    /**
     * 插入功能配置
     *
     * @param func
     * @return
     */
    Integer insertFunction( Func func );

    /**
     * 修改功能配置
     *
     * @param func
     * @return
     */
    Integer updateFunc( Func func );

    /**
     * 删除功能配置
     *
     * @param func
     * @return
     */
    Integer deleteFunc( Func func );

    /**
     * 添加功能URL
     *
     * @param funcUrl
     * @return
     */
    Integer insertFuncUrl( FuncUrl funcUrl );

    /**
     * 修改功能URL
     *
     * @param funcUrl
     * @return
     */
    Integer updateFuncUrl( FuncUrl funcUrl );

    /**
     * 删除功能URL
     *
     * @param funcUrl
     * @return
     */
    Integer deleteFuncUrl( FuncUrl funcUrl );

    /**
     * 功能URL列表
     *
     * @param funcUrl
     * @return
     */
    List<FuncUrl> queryFuncUrlList( FuncUrl funcUrl );

    /**
     * 功能URL列表总数
     *
     * @param funcUrl
     * @return
     */
    Integer queryFuncUrlListCount( FuncUrl funcUrl );

    /**
     * 根据条件查询功能URL
     *
     * @param funcUrl
     * @return
     */
    FuncUrl queryFuncUrlWhere( FuncUrl funcUrl );

    /**
     * 岗位树
     *
     * @param position
     * @return
     */
    List<Position> queryPositionTree( Position position );

    /**
     * 权限菜单
     *
     * @return
     */
    List<Menu> queryMenuJurisdiction();

    /**
     * 功能权限
     *
     * @return
     */
    List<Func> queryMenuFunction( Func func );

    /**
     * 查询权限
     *
     * @return
     */
    List<PositionFunc> queryPositionFunc( PositionFunc positionFunc );

    /**
     * 插入职位权限
     *
     * @param positionFunc
     * @return
     */
    Integer insertPositionFunc( PositionFunc positionFunc );

    /**
     * 删除职位权限
     *
     * @param positionFunc
     * @return
     */
    Integer deletePositionFunc( PositionFunc positionFunc );

    /**
     * 查询岗位和部门
     *
     * @return
     */
    List<Position> queryPositionToDept( Position position );

    /**
     * 根据岗位查询部门总数
     *
     * @return
     */
    Integer queryPositionToDeptCount( Position position );

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    Integer insertUser( User user );

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    Integer updateUser( User user );

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    Integer deleteUser( User user );

    /**
     * 登录验证
     *
     * @param user
     * @return
     */
    User queryLoginPassword( User user );

    /**
     * 根据用户ID查询用户菜单
     *
     * @param menu
     * @return
     */
    List<Menu> queryUserMunu( Menu menu );

    /**
     * 插入用户图标
     *
     * @param icon
     * @return
     */
    Integer insertIcon( Icon icon );

    /**
     * 查询用户图标
     *
     * @return
     */
    List<Icon> queryIcon();

    /**
     * 用户部门
     * 
     * @param user_id 用户编码
     * @return
     */
    List<Dept> queryUserDept( String user_id );

    /**
     * 根据部门编码查询人员
     * 
     * @param dept_id 部门编码
     * @return
     */
    List<User> queryDeptUser( String dept_id );
    /**
     * 忘记密码  用户信息验证
     * 
     * @return
     */
    int checkSysUser(User user);
    /**
     * 忘记密码  修改密码
     * 
     * @return
     */
    int updatePwd(User user);

    /**
     * 获取内外部门标识，根据用户ID
     *  
     * @方法名称 getInnerOuterDeptFlagByUserId
     * @功能描述 
     * @作者    kevin
     * @创建时间 2018年9月27日 下午2:49:06
     * @param user_id
     * @return
     */
    public String getInnerOuterDeptFlagByUserId(String user_id);
    /**
     * 批量导入机构时,进行数据验证
     */
	User queryUser(Institution institution);
	 /**
     * 机构表进行数据修改时,进行数据验证
     */
	User checkUser(Institution institution);
	/**
	 * 插入到sys_user表后,机构名称与sys_dept表中的部门名称匹配成功,根据dept_id来获取到岗位id
	 */
	List<Position> queryPositionByDept(Dept dept2);
}
