package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.Func;
import com.manhui.gsl.jbqgsl.model.FuncUrl;
import com.manhui.gsl.jbqgsl.model.Icon;
import com.manhui.gsl.jbqgsl.model.Menu;
import com.manhui.gsl.jbqgsl.model.Position;
import com.manhui.gsl.jbqgsl.model.PositionFunc;
import com.manhui.gsl.jbqgsl.model.User;

public interface IOrganizationalService {
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
     * 查询部门岗位列表
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
     * 插入部门
     *
     * @param dept
     * @return
     */
    Integer insertDept( Dept dept );

    /**
     * 删除部门
     *
     * @param dept_id
     * @return
     */
    Integer deleteDept( String dept_id );

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
     * 修改部门
     *
     * @param dept
     * @return
     */
    Integer updateDept( Dept dept );

    /**
     * 功能菜单列表
     *
     * @param menu
     * @return
     */
    List<Menu> queryMenuList( Menu menu );

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
     * @param positionFuncs
     * @return
     */
    Integer insertPositionFunc( List<PositionFunc> positionFuncs );

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
     * 忘记密码 验证用户信息
     *
     * @return
     */
    int checkSysUser(User user);
    /**
     * 忘记密码 --修改密码
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
}
