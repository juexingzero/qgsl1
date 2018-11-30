package com.manhui.gsl.jbqgsl.controller.web.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.Func;
import com.manhui.gsl.jbqgsl.model.FuncUrl;
import com.manhui.gsl.jbqgsl.model.Icon;
import com.manhui.gsl.jbqgsl.model.Menu;
import com.manhui.gsl.jbqgsl.model.Position;
import com.manhui.gsl.jbqgsl.model.PositionFunc;
import com.manhui.gsl.jbqgsl.model.User;
import com.manhui.gsl.jbqgsl.service.web.IOrganizationalService;

/**
 * 组织架构权限控制类
 *
 * 1.组织架构结构图 2.组织架构 3.功能配置 4.岗位授权 5.用户管理 6.字典管理
 **/
@Controller
@RequestMapping( "/organ" )
public class WebOrganizationalController {
    @Resource
    private IOrganizationalService organizationalService;

    /**
     * 组织架构结构图
     */
    @RequestMapping( "/organStructure" )
    public String organStructure() {
        return "/web/html/organizational/authorization";
    }

    /**
     * 组织架构
     */
    @RequestMapping( "/organizeList" )
    public String organizeList() {
        return "/web/html/organizational/organizeList";
    }
    /**
     * 组织架构栏目展示
     */
    @RequestMapping( "/organizeMenuList" )
    public String organizeMenuList() {
    	return "/web/html/organizational/organizeMenuList";
    }

    /**
     * 功能配置
     */
    @RequestMapping( "/menuSetting" )
    public String menuSetting() {
        return "/web/html/organizational/menuSetting";
    }

    /**
     * 添加部门
     */
    @RequestMapping( "/addDepartmentPage" )
    public String addDepartmentPage() {
        return "/web/html/organizational/addDepartment";
    }

    /**
     * 添加岗位
     */
    @RequestMapping( "/addPost" )
    public String addPost() {
        return "/web/html/organizational/addPost";
    }

    /**
     * 选择人员-列表
     */
    @RequestMapping( "/userSelect" )
    public String userSelect() {
        return "/web/html/user/userSelect";
    }

    /**
     * 选择部门-列表
     */
    @RequestMapping( "/departmentSelect" )
    public String departmentSelect() {
        return "/web/html/organizational/departmentSelect";
    }

    /**
     * 添加菜单
     */
    @RequestMapping( "/addMenu" )
    public String addMenu() {
        return "/web/html/organizational/addMenu";
    }

    /**
     * 添加功能
     */
    @RequestMapping( "/addFunctionSett" )
    public String addFunctionSett() {
        return "/web/html/organizational/addFunctionSett";
    }

    /**
     * 添加功能Url
     */
    @RequestMapping( "/addFunctionUrl" )
    public String addFunctionUrl() {
        return "/web/html/organizational/addFunctionUrl";
    }

    /**
     * 功能授权
     */
    @RequestMapping( "/positionAuthList" )
    public String positionAuthList() {
        return "/web/html/organizational/positionAuthList";
    }

    /**
     * 用户管理
     */
    @RequestMapping( "/userListPage" )
    public String userListPage() {
        return "/web/html/user/userList";
    }

    /**
     * 选择岗位
     */
    @RequestMapping( "/positionListPage" )
    public String positionListPage() {
        return "/web/html/organizational/positionSelect";
    }

    /**
     * 图标选择
     */
    @RequestMapping( "/icon" )
    public String icon() {
        return "/web/html/organizational/icon";
    }

    /**
     * 多选人员
     */
    @RequestMapping( "/userbook" )
    public String userbook() {
        return "/web/html/user/userBook";
    }

    /**
     * 组织架构结构图
     */
    @RequestMapping( "/deptImage" )
    @ResponseBody
    public Map<String, Object> deptImage() {
        Map<String, Object> map = new HashMap<>();
        Dept dept = new Dept();
        dept.setP_dept_id( "-1" );
        List<Dept> depts = organizationalService.queryDeptWhere( dept );
        if ( !depts.isEmpty() ) {
            Dept dept1 = depts.get( 0 );
            List<Dept> depts1 = organizationalService.queryDeptTree( dept1 );
            Integer size = organizationalService.querySysDeptSize( dept );
            map.put( "code", 200 );
            map.put( "data", depts1 );
            map.put( "size", size );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 部门列表
     * 
     * @param dept_name 部门名称
     *
     * @return json
     */
    @RequestMapping( "/deptList" )
    @ResponseBody
    public Map<String, Object> deptList( String dept_name ) {
        Map<String, Object> map = new HashMap<>();
        Dept dept = new Dept();
        dept.setDept_name( dept_name );
        List<Dept> depts = organizationalService.querySysDept( dept );
        Integer size = organizationalService.querySysDeptSize( dept );
        map.put( "data", depts );
        map.put( "size", size );
        map.put( "total", size );
        return map;
    }

    /**
     * 获取上级部门
     *
     * @param p_dept_id 上级部门编码
     * @param dept_id 部门编码
     * @return json
     */
    @RequestMapping( "/topDepartment" )
    @ResponseBody
    public Map<String, Object> topDepartment( String p_dept_id, String dept_id ) {
        Map<String, Object> map = new HashMap<>();
        Dept dept = new Dept();
        dept.setP_dept_id( p_dept_id );
        dept.setDept_id( dept_id );
        List<Dept> depts = organizationalService.querySysDept( dept );
        if ( !depts.isEmpty() ) {
            map.put( "code", 200 );
            Dept dept1 = depts.get( 0 );
            if ( dept_id != null && !dept_id.equals( "" ) ) {
                Dept dept2 = new Dept();
                dept2.setP_dept_id( dept1.getP_dept_id() );
                List<Dept> depts1 = organizationalService.querySysDept( dept2 );
                dept1.setP_dept_name( depts1.get( 0 ).getDept_name() );
            }
            map.put( "data", dept1 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 根据部门编码查询部门信息
     *
     * @param dept_id 部门编码
     * @return json
     */
    @RequestMapping( "/selectPost" )
    @ResponseBody
    public Map<String, Object> selectPost( String dept_id ) {
        Map<String, Object> map = new HashMap<>();
        Dept dept = new Dept();
        dept.setDept_id( dept_id );
        List<Dept> depts = organizationalService.querySysDept( dept );
        if ( !depts.isEmpty() ) {
            map.put( "code", 200 );
            map.put( "data", depts.get( 0 ) );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 组织架构-部门列表
     * 
     * @param dept_id 部门编码
     * @return json
     */
    @RequestMapping( "/deptLists" )
    @ResponseBody
    public List<Dept> deptLists( String dept_id ) {
        Dept dept = new Dept();
        dept.setDept_id( dept_id );
        List<Dept> depts = organizationalService.querySysDept( dept );
        return depts;
    }
    /**
     * 组织架构-编辑部门列表
     * 
     * @param dept_id 部门编码
     * @return json
     */
    @RequestMapping( "/deptMenuLists" )
    @ResponseBody
    public List<Dept> deptMenuLists( String dept_id ) {
    	List<Dept> depts = new ArrayList<Dept>();
    	Dept dept = new Dept();
    	Dept dept2 = new Dept();
    	dept.setDept_id( dept_id );
    	depts = organizationalService.querySysDept( dept );
    	dept2.setOrder_no(-1);
    	dept2.setDept_name( "一级部门" );
    	dept2.setP_dept_id( "1" );
    	dept2.setDept_id(UUIDUtil.getUUID());
    	depts.add(dept2);
    	Collections.sort((List<Dept>) depts);
    	return depts;
    }

    /**
     * 组织架构-职位列表
     * 
     * @param dept_id 部门编码
     * @param pageIndex 开始页数
     * @param pageSize 查询条数
     * @return json
     */
    @RequestMapping( "/listPage" )
    @ResponseBody
    public Map<String, Object> positions( String dept_id, Integer pageIndex, Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        Position position = new Position();
        position.setDept_id( dept_id );
        position.setPageNo( pageIndex * pageSize );
        position.setPageSize( pageSize );
        List<Position> positions = organizationalService.queryPosition( position );
        Integer total = organizationalService.queryPositionCount( position );
        if ( !positions.isEmpty() ) {
            map.put( "data", positions );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    /**
     * 添加部门
     * 
     * @param request HttpServletRequest
     * @param json 提交参数
     * @return json
     */
    @RequestMapping( "/addDepartment" )
    @ResponseBody
    public Map<String, Object> addDepartment( HttpServletRequest request, String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject( json );
        Dept dept = jsonObject.toJavaObject( Dept.class );
        Integer bools = 0;
        if ( dept.getDept_id() != null && !dept.getDept_id().equals( "" ) ) {
            bools = organizationalService.updateDept( dept );
        }
        else {
            dept.setDept_id( UUIDUtil.getUUID() );
            dept.setLeader_type( "U" );
            dept.setCreator_id( AppUtil.getCookieByName( request, "user_id" ) );
            bools = organizationalService.insertDept( dept );
        }
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 删除部门
     *
     * @param dept_id 部门编码
     * @return json
     */
    @RequestMapping( "/deleteDepartment" )
    @ResponseBody
    public Map<String, Object> deleteDepartment( String dept_id ) {
        Map<String, Object> map = new HashMap<>();
        Integer bools = organizationalService.deleteDept( dept_id );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 插入岗位
     *
     * @param request HttpServletRequest
     * @param json 传入参数
     * @return json
     */
    @RequestMapping( "/addPosition" )
    @ResponseBody
    public Map<String, Object> addPosition( HttpServletRequest request, String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject( json );
        Position position = jsonObject.toJavaObject( Position.class );
        Integer bools = 0;
        if ( position.getPosition_id() != null && !position.getPosition_id().equals( "" ) ) {
            bools = organizationalService.updatePosition( position );
        }
        else {
            position.setPosition_id( UUIDUtil.getUUID() );
            position.setCreator_id( AppUtil.getCookieByName( request, "user_id" ) );
            bools = organizationalService.insertPosition( position );
        }
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 删除岗位
     *
     * @param position_id 岗位编码
     * @return json
     */
    @RequestMapping( "/deletePosition" )
    @ResponseBody
    public Map<String, Object> deletePosition( String position_id ) {
        Map<String, Object> map = new HashMap<>();
        Position position = new Position();
        position.setPosition_id( position_id );
        Integer bools = organizationalService.deletePosition( position );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 查询岗位数据
     *
     * @param position_id 岗位编码
     * @return json
     */
    @RequestMapping( "/selectPosition" )
    @ResponseBody
    public Map<String, Object> selectPosition( String position_id ) {
        Map<String, Object> map = new HashMap<>();
        Position position = new Position();
        position.setPosition_id( position_id );
        List<Position> positions = organizationalService.queryPosition( position );
        if ( !positions.isEmpty() ) {
            map.put( "code", 200 );
            map.put( "data", positions.get( 0 ) );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 岗位添加人员
     * 
     * @param position_id 岗位编码
     * @param user_id 用户编码
     * @return json
     */
    @RequestMapping( "/addPositionToUser" )
    @ResponseBody
    public Map<String, Object> addPositionToUser( String position_id, String user_id ) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setUser_id( user_id );
        user.setPosition_id( position_id );
        List<User> users = organizationalService.queryPositionUser( user );
        if ( !users.isEmpty() ) {
            map.put( "code", 201 );
            return map;
        }
        Position position = new Position();
        position.setPosition_id( position_id );
        position.setUser_id( user_id );
        position.setIs_primary( 0 );
        Integer bools = organizationalService.insertPositionToUser( position );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 删除岗位或者人员
     * 
     * @param user_id 用户编码
     * @param position_id 职位编码
     * @return json
     */
    @RequestMapping( "/deletePositionToUser" )
    @ResponseBody
    public Map<String, Object> deletePositionToUser( String user_id, String position_id ) {
        Map<String, Object> map = new HashMap<>();
        Position position = new Position();
        position.setUser_id( user_id );
        position.setPosition_id( position_id );
        Integer bools = organizationalService.deletePositionToUser( position );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 菜单列表
     *
     * @return json
     */
    @RequestMapping( "/menuList" )
    @ResponseBody
    public List<Menu> menuList() {
        Menu menu = new Menu();
        List<Menu> menuList = organizationalService.queryMenuList( menu );
        return menuList;
    }

    /**
     * 查询菜单
     *
     * @param menu_id 菜单编码
     * @param p_menu_id 父级菜单编码
     * @return json
     */
    @RequestMapping( "/selectMenu" )
    @ResponseBody
    public Map<String, Object> selectMenu( String menu_id, String p_menu_id ) {
        Map<String, Object> map = new HashMap<>();
        if ( menu_id != null && !menu_id.equals( "" ) ) {
            Menu menu = new Menu();
            menu.setMenu_id( menu_id );
            List<Menu> menuList = organizationalService.queryMenuWhere( menu );
            if ( !menuList.isEmpty() ) {
                Menu menu2 = menuList.get( 0 );
                if ( !menu2.getP_menu_id().equals( "-1" ) ) {
                    Menu menu1 = new Menu();
                    menu1.setMenu_id( p_menu_id );
                    Menu menu3 = organizationalService.queryMenuWhere( menu1 ).get( 0 );
                    menu2.setP_menu_name( menu3.getMenu_name() );
                }
                else {
                    menu2.setP_menu_name( "" );
                }
                map.put( "code", 200 );
                map.put( "data", menu2 );
            }
        }
        else if ( p_menu_id != null && !p_menu_id.equals( "" ) ) {
            Menu menu = new Menu();
            menu.setMenu_id( p_menu_id );
            List<Menu> menuList = organizationalService.queryMenuWhere( menu );
            Menu menu1 = menuList.get( 0 );
            if ( menu1.getMenu_id().equals( p_menu_id ) ) {
                menu1.setP_menu_id( p_menu_id );
            }
            menu1.setP_menu_name( menu1.getMenu_name() );
            map.put( "code", 200 );
            map.put( "data", menu1 );
        }
        else {
            Menu menu2 = new Menu();
            menu2.setP_menu_id( "" );
            menu2.setP_menu_name( "" );
            map.put( "code", 200 );
            map.put( "data", menu2 );
        }
        return map;
    }

    /**
     * 插入菜单
     * 
     * @param request HttpServletRequest
     * @param json 传入参数
     * @return json
     */
    @RequestMapping( "/addMenuData" )
    @ResponseBody
    public Map<String, Object> addMenuData( HttpServletRequest request, String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject( json );
        Menu menu = jsonObject.toJavaObject( Menu.class );
        String menu_id = menu.getMenu_id();
        if ( !menu.getP_menu_id().equals( "-1" ) ) {
            menu.setMenu_id( menu.getP_menu_id() + "." + menu_id );
        }
        Integer bools = 0;
        if ( menu.getUpdate_bools().equals( "true" ) ) {
            bools = organizationalService.updateMenu( menu );
        }
        else {
            List<Menu> menuList = organizationalService.queryMenuWhere( menu );
            if ( menuList.isEmpty() ) {
                menu.setCreator_id( AppUtil.getCookieByName( request, "user_id" ) );
                bools = organizationalService.insertMenu( menu );
            }
            else {
                map.put( "code", 401 );
                map.put( "msg", "菜单ID重复" );
                return map;
            }
        }
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }

    /**
     * 删除菜单
     *
     * @param menu_id 菜单编码
     * @return json
     */
    @RequestMapping( "/deleteMenu" )
    @ResponseBody
    public Map<String, Object> deleteMenu( String menu_id ) {
        Map<String, Object> map = new HashMap<>();
        Menu menu = new Menu();
        menu.setMenu_id( menu_id );
        Integer bools = organizationalService.deleteMenu( menu );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "删除失败，请重新尝试" );
        }
        return map;
    }

    /**
     * 根据菜单id查询功能列表
     * 
     * @param menu_id 菜单编码
     * @param pageIndex 开始查询数
     * @param pageSize 查询条数
     * @return json
     */
    @RequestMapping( "/funcList" )
    @ResponseBody
    public Map<String, Object> funcList( String menu_id, Integer pageIndex, Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        Func func = new Func();
        func.setPageNo( pageIndex * pageSize );
        func.setPageSize( pageSize );
        func.setMenu_id( menu_id );
        List<Func> funcs = organizationalService.queryFuncList( func );
        Integer total = organizationalService.queryFuncListCount( func );
        if ( !funcs.isEmpty() ) {
            map.put( "data", funcs );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    /**
     * 查询菜单和功能
     * 
     * @param func_id 功能编码
     * @param menu_id 菜单编码
     * @return json
     */
    @RequestMapping( "/selectFunc" )
    @ResponseBody
    public Map<String, Object> selectFunc( String func_id, String menu_id ) {
        Map<String, Object> map = new HashMap<>();
        if ( func_id != null && !func_id.equals( "" ) ) {
            Func func = new Func();
            func.setFunc_id( func_id );
            Func func1 = organizationalService.queryFuncWhere( func );
            Menu menu = new Menu();
            menu.setMenu_id( func1.getMenu_id() );
            Menu menu1 = organizationalService.queryMenuWhere( menu ).get( 0 );
            func1.setMenu_name( menu1.getMenu_name() );
            func1.setMenu_id( menu1.getMenu_id() );
            map.put( "code", 200 );
            map.put( "data", func1 );
        }
        else {
            Menu menu = new Menu();
            menu.setMenu_id( menu_id );
            Menu menu1 = organizationalService.queryMenuWhere( menu ).get( 0 );
            Func func = new Func();
            func.setMenu_name( menu1.getMenu_name() );
            func.setMenu_id( menu1.getMenu_id() );
            map.put( "code", 200 );
            map.put( "data", func );
        }
        return map;
    }

    /**
     * 插入功能配置
     *
     * @param request HttpServletRequest
     * @param json 提交参数
     * @return json
     */
    @RequestMapping( "/insertFunc" )
    @ResponseBody
    public Map<String, Object> insertFunc( HttpServletRequest request, String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject( json );
        Func func = jsonObject.toJavaObject( Func.class );
        Integer bools = 0;
        func.setFunc_id( func.getMenu_id() + "." + func.getFunc_id() );
        func.setCreator_id( AppUtil.getCookieByName( request, "user_id" ) );
        if ( func.getUpdate_bools().equals( "true" ) ) {
            bools = organizationalService.updateFunc( func );
        }
        else {
            bools = organizationalService.insertFunction( func );
        }
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }

    /**
     * 删除功能配置
     *
     * @param func_id 功能编码
     * @return json
     */
    @RequestMapping( "/deleteFunc" )
    @ResponseBody
    public Map<String, Object> deleteFunc( String func_id ) {
        Map<String, Object> map = new HashMap<>();
        Func func = new Func();
        func.setFunc_id( func_id );
        Integer bools = organizationalService.deleteFunc( func );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "删除失败，请重新尝试" );
        }
        return map;
    }

    /**
     * 功能url列表
     *
     * @param func_id 功能编码
     * @param pageIndex 开始查询条数
     * @param pageSize 查询条数
     * @return json
     */
    @RequestMapping( "/funcUrlList" )
    @ResponseBody
    public Map<String, Object> funcUrlList( String func_id, Integer pageIndex, Integer pageSize ) {
        Map<String, Object> map = new HashMap<>();
        FuncUrl funcUrl = new FuncUrl();
        funcUrl.setFunc_id( func_id );
        funcUrl.setPageNo( pageIndex * pageSize );
        funcUrl.setPageSize( pageSize );
        List<FuncUrl> funcUrls = organizationalService.queryFuncUrlList( funcUrl );
        Integer total = organizationalService.queryFuncUrlListCount( funcUrl );
        if ( !funcUrls.isEmpty() ) {
            map.put( "data", funcUrls );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    /**
     * 查询功能URL
     *
     * @param url_id 访问地址编码
     * @param func_id 功能编码
     * @return json
     */
    @RequestMapping( "/selectFuncUrl" )
    @ResponseBody
    public Map<String, Object> selectFuncUrl( String url_id, String func_id ) {
        Map<String, Object> map = new HashMap<>();
        if ( url_id != null && !url_id.equals( "" ) ) {
            FuncUrl funcUrl = new FuncUrl();
            funcUrl.setUrl_id( url_id );
            FuncUrl funcUrl1 = organizationalService.queryFuncUrlWhere( funcUrl );
            Func func = new Func();
            func.setFunc_id( funcUrl1.getFunc_id() );
            Func func1 = organizationalService.queryFuncWhere( func );
            funcUrl1.setFunc_name( func1.getFunc_name() );
            map.put( "code", 200 );
            map.put( "data", funcUrl1 );
        }
        else {
            Func func = new Func();
            func.setFunc_id( func_id );
            Func func1 = organizationalService.queryFuncWhere( func );
            FuncUrl funcUrl = new FuncUrl();
            funcUrl.setFunc_id( func1.getFunc_id() );
            funcUrl.setFunc_name( func1.getFunc_name() );
            map.put( "code", 200 );
            map.put( "data", funcUrl );
        }
        return map;
    }

    /**
     * 添加或者修改功能URL
     *
     * @param json 插入参数
     * @return json
     */
    @RequestMapping( "/addFuncUrl" )
    @ResponseBody
    public Map<String, Object> addFuncUrl( HttpServletRequest request, String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject( json );
        FuncUrl funcUrl = jsonObject.toJavaObject( FuncUrl.class );
        funcUrl.setRecord_log( "Y" );
        funcUrl.setUrl_id( funcUrl.getFunc_id() + "." + funcUrl.getUrl_id() );
        funcUrl.setCreator_id( AppUtil.getCookieByName( request, "user_id" ) );
        Integer bools = 0;
        if ( funcUrl.getUpdate_bools().equals( "true" ) ) {
            bools = organizationalService.updateFuncUrl( funcUrl );
        }
        else {
            bools = organizationalService.insertFuncUrl( funcUrl );
        }
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }

    /**
     * 删除功能url
     *
     * @param url_id 权限地址编码
     * @return json
     */
    @RequestMapping( "/deleteFuncUrl" )
    @ResponseBody
    public Map<String, Object> deleteFuncUrl( String url_id ) {
        Map<String, Object> map = new HashMap<>();
        FuncUrl funcUrl = new FuncUrl();
        funcUrl.setUrl_id( url_id );
        Integer bools = organizationalService.deleteFuncUrl( funcUrl );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "删除失败，请重新尝试" );
        }
        return map;
    }

    /**
     * 岗位树
     * 
     * @return json
     */
    @RequestMapping( "/queryPositionTree" )
    @ResponseBody
    public List<Position> queryPositionTree( String dept_id ) {
        Position position = new Position();
        position.setDept_id( dept_id );
        List<Position> positions = organizationalService.queryPositionTree( position );
        return positions;
    }

    /**
     * 权限列表
     *
     * @return json
     */
    @RequestMapping( "/jurisdictionList" )
    @ResponseBody
    public List<Menu> jurisdictionList() {
        List<Menu> menuList = organizationalService.queryMenuJurisdiction();
        for ( int i = 0; i < menuList.size(); i++ ) {
            Func func = new Func();
            func.setMenu_id( menuList.get( i ).getMenu_id() );
            List<Func> funcs = organizationalService.queryMenuFunction( func );
            if ( funcs.size() > 0 ) {
                menuList.get( i ).setFunctions( funcs );
            }
            else if ( menuList.get( i ).getChild_count() == 0 ) {//没有功能点的末级菜单不显示出来
                menuList.remove( menuList.get( i ) );
            }
        }
        return menuList;
    }

    /**
     * 查询职位权限
     *
     * @param position_id 岗位编码
     * @return json
     */
    @RequestMapping( "/selectPositionFnc" )
    @ResponseBody
    public Map<String, Object> selectPositionFnc( String position_id ) {
        Map<String, Object> map = new HashMap<>();
        PositionFunc positionFunc = new PositionFunc();
        positionFunc.setPosition_id( position_id );
        List<PositionFunc> positionFuncs = organizationalService.queryPositionFunc( positionFunc );
        if ( !positionFuncs.isEmpty() ) {
            map.put( "code", 200 );
            map.put( "data", positionFuncs );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 插入职位权限
     * 
     * @param json 插入参数
     * @return json
     */
    @RequestMapping( "/insertPositionFnc" )
    @ResponseBody
    public Map<String, Object> insertPositionFnc( String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONArray jsonArray = JSON.parseArray( json );
        List<PositionFunc> positionFuncs = jsonArray.toJavaList( PositionFunc.class );
        Integer bools = organizationalService.insertPositionFunc( positionFuncs );
        if ( bools > 0 ) {
            map.put( "code", 200 );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "提交有误，请重新尝试" );
        }
        return map;
    }

    /**
     * 选择岗位列表
     * 
     * @param where 查询条件
     * @param pageIndex 开始查询条数
     * @param pageSize 查询条数
     * @return json
     */
    @RequestMapping( "/positionList" )
    @ResponseBody
    public Map<String, Object> positionList( String position_name,String dept_name, Integer pageIndex, Integer pageSize ,String sortField,String sortOrder) {
        Map<String, Object> map = new HashMap<>();
        Position position = new Position();
        position.setPosition_name(position_name);
        position.setSortField(sortField);
        position.setSortOrder(sortOrder);
        position.setDept_name(dept_name);
        position.setPageNo( pageIndex * pageSize );
        position.setPageSize( pageSize );
        List<Position> positions = organizationalService.queryPositionToDept( position );
        Integer total = organizationalService.queryPositionToDeptCount( position );
        if ( !positions.isEmpty() ) {
            map.put( "data", positions );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    /**
     * 查询所有图标
     *
     * @return json
     */
    @RequestMapping( "/selectIcon" )
    @ResponseBody
    public Map<String, Object> selectIcon() {
        Map<String, Object> map = new HashMap<>();
        List<Icon> icons = organizationalService.queryIcon();
        if ( !icons.isEmpty() ) {
            map.put( "code", 200 );
            map.put( "data", icons );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }
}
