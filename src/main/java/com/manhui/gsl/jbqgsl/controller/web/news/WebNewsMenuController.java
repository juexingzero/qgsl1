package com.manhui.gsl.jbqgsl.controller.web.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.manhui.gsl.jbqgsl.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.enums.news.MenuModel;
import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.common.util.SelectDataMiniui;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.NewsMenu;
import com.manhui.gsl.jbqgsl.service.web.IUserService;
import com.manhui.gsl.jbqgsl.service.web.NewsMenuService;

/**
 * 新闻栏目管理
 */
@Controller
@RequestMapping(WebNewsMenuController.ROOT_URL)
public class WebNewsMenuController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/newsMenu";
    @Resource
    private NewsMenuService newsMenuService;
    @Resource
    private IUserService iUserService;

    /**
     * 新闻 目录结构管理页面
     * 
     * @return
     */
    @RequestMapping( "/queryListPage" )
    public String queryListPage() {
        return "/web/html/news/menu/newsMenuList";
    }

    /**
     * 查询新闻 目录树结构数据
     * 
     * @param menuIdsStr 需要选中的数据
     * @return
     */
    @RequestMapping( "/queryListTree" )
    @ResponseBody
    public List<NewsMenu> queryListTree( NewsMenu menu, String menuIdsStr,HttpServletRequest request) {
        List<NewsMenu> menuList = newsMenuService.queryList( menu );
        //设置默认选中
        if ( StringUtils.isNotBlank( menuIdsStr ) && menuList != null && !menuList.isEmpty() ) {
            String[] menuIds = menuIdsStr.split( "," );
            Integer menuId;
            for ( int i = 0; i < menuIds.length; i++ ) {
                menuId = Integer.parseInt( menuIds[i] );
                for ( NewsMenu m : menuList ) {
                    if ( menuId.equals( m.getMenuId() ) ) {
                        m.setChecked( true );
                    }
                }
            }
        }
        
        //过滤“江北政策”文件录入部门的用户登录后只能看到江北政策栏目
        List<NewsMenu> menuList2 = new ArrayList<NewsMenu>();
        String deptState = deptState(request);
	     if(deptState !=null && "1".equals(deptState)) {
	    	   for (NewsMenu newsMenu : menuList) {
	    		  if(newsMenu.getMenuId().equals(18) ||newsMenu.getMenuId().equals(15) ) {
	    			  menuList2.add(newsMenu);
	    		  }
			}
	    	   return menuList2;
	     }
        return menuList;
    }
    
    //获取登录账号所属部门（内部/外部）
    private String deptState( HttpServletRequest request ) {
        String inner_outer_dept = null;
        String user_id = AppUtil.getCookie( request, "user_id" );
        List<Dept> deptList = iUserService.queryUserDept( user_id );
        if ( deptList != null && deptList.size() == 1 ) {
            for ( Dept dept : deptList ) {
                inner_outer_dept = dept.getInner_outer_dept();
            }
        }
        return inner_outer_dept;
    }
    
    /**
     * \ 跳转添加根 栏目页面
     * 
     * @return
     */
    @RequestMapping( "/addRootPage" )
    public String addRootPage( Model model ) {
        return "/web/html/news/menu/newsMenuAddRoot";
    }

    /**
     * 保存跟节点
     * 
     * @param json
     * @return
     */
    @RequestMapping( "/saveMenuRoot" )
    @ResponseBody
    public String saveMenuRoot( HttpServletRequest request, String json ) {
        String userid = AppUtil.getCookie( request, "user_id" );
        JSONObject obj = JSON.parseObject( json );
        NewsMenu menu = obj.toJavaObject( NewsMenu.class );
        ResultMessage message;
        //修改
        if ( menu.getMenuId() != null ) {
            message = newsMenuService.updateMenu( menu, userid );
        }
        //新增
        else {
            message = newsMenuService.saveMenuRoot( menu, userid );
        }
        return new Gson().toJson( message );
    }

    /**
     * 获得 菜单栏目模型
     * 
     * @return
     */
    @RequestMapping( "/getMenuModel" )
    @ResponseBody
    public String getMenuModel() {
        Map<String, String> codeMap = MenuModel.codeMap; //获得栏目模型
        List<SelectDataMiniui> dataList = new ArrayList<>();
        SelectDataMiniui data;
        for ( Map.Entry<String, String> entry : codeMap.entrySet() ) {
            data = new SelectDataMiniui();
            data.setId( entry.getKey() );
            data.setName( entry.getValue() );
            dataList.add( data );
        }
        Gson gson = new Gson();
        return gson.toJson( dataList );
    }

    /**
     * 添加子节点页面跳转
     * 
     * @param menuId
     * @return
     */
    @RequestMapping( "/addChildPage" )
    public String addChildPage( Integer menuId, Model model ) {
        NewsMenu menu = newsMenuService.queryById( menuId );
        model.addAttribute( "superiorId", menu.getMenuId() );
        model.addAttribute( "superiorName", menu.getMenuName() );
        return "/web/html/news/menu/newsMenuAddChild";
    }

    /**
     * 保存子节点
     * 
     * @param request
     * @param json
     * @return
     */
    @RequestMapping( "/saveChildMenu" )
    @ResponseBody
    public String saveChildMenu( HttpServletRequest request, String json ) {
        JSONObject obj = JSON.parseObject( json );
        NewsMenu menu = obj.toJavaObject( NewsMenu.class );
        String userid = AppUtil.getCookie( request, "user_id" );
        ResultMessage message;
        if ( menu.getMenuId() == null ) {
            message = newsMenuService.saveChildMenu( menu, userid );
        }
        else {
            message = newsMenuService.updateMenu( menu, userid );
        }
        String str = new Gson().toJson( message );
        return str;
    }

    /**
     * 删除节点
     * 
     * @param request
     * @param menuId
     * @return
     */
    @RequestMapping( "/delMenu" )
    @ResponseBody
    public String delMenu( HttpServletRequest request, Integer menuId ) throws Exception {
        String userid = AppUtil.getCookie( request, "user_id" );
        ResultMessage message = newsMenuService.delMenu( menuId, userid );
        return new Gson().toJson( message );
    }

    /**
     * 修改节点
     * 
     * @param menuId
     * @param model
     * @return
     */
    @RequestMapping( "/editPage" )
    public String editPage( Integer menuId, Model model ) throws Exception {
        NewsMenu menu = newsMenuService.queryById( menuId );
        model.addAttribute( "menu", menu );
        if ( menu.getType().equals( 0 ) ) {
            //根节点
            return "/web/html/news/menu/newsMenuEditRoot";
        }
        else if ( menu.getType().equals( 1 ) ) {
            //子节点
            NewsMenu superiormenu = newsMenuService.queryById( menu.getSuperiorId() );
            menu.setSuperiorName( superiormenu.getMenuName() );
            return "/web/html/news/menu/newsMenuEditChild";
        }
        else {
            throw new RuntimeException( "数据异常或已被删除" );
        }
    }
}
