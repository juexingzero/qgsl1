package com.manhui.gsl.jbqgsl.controller.web.system;

import com.manhui.gsl.jbqgsl.common.util.AppUtil;
import com.manhui.gsl.jbqgsl.model.Menu;
import com.manhui.gsl.jbqgsl.service.web.IOrganizationalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping( "/menu" )
@Controller
public class WebMenuController {
    @Resource
    private IOrganizationalService organizationalService;

    /**
     * 读取菜单
     *
     * @param request
     * @return
     */
    @RequestMapping( "/menu" )
    @ResponseBody
    public Map<String, Object> menu( HttpServletRequest request ) {
        Map<String, Object> map = new HashMap<>();
        List<Menu> menusList = new ArrayList<>();
        Menu menu1 = new Menu();
        menu1.setUser_id( AppUtil.getCookieByName( request, "user_id" ) );
        List<Menu> menuList1 = organizationalService.queryUserMunu( menu1 );
        List<Menu> menuList = new ArrayList<>();
        String menu_id = "";
        for ( int i = 1; i < menuList1.size(); i++ ) {
            if ( menu_id.equals( "" ) || !menuList1.get( i ).getP_menu_id().equals( menu_id ) ) {
                if ( i != 1 ) {
                    menu1.setChildren( menuList );
                    menusList.add( menu1 );
                    menuList = new ArrayList<>();
                    menu1 = new Menu();
                }
                menu_id = menuList1.get( i ).getMenu_id();
                menu1.setId( menuList1.get( i ).getMenu_id() );
                menu1.setIconCls( menuList1.get( i ).getMenu_pic_css() );
                menu1.setText( menuList1.get( i ).getMenu_name() );
            }
            else {
                Menu menu = new Menu();
                menu.setId( menuList1.get( i ).getMenu_id() );
                menu.setIconCls( menuList1.get( i ).getMenu_pic_css() );
                menu.setText( menuList1.get( i ).getMenu_name() );
                menu.setUrl( menuList1.get( i ).getMenu_url() );
                menuList.add( menu );
            }
        }
        if ( menu1.getChildren() == null ) {
            menu1.setChildren( menuList );
            menusList.add( menu1 );
        }
        map.put( "data", menusList );
        return map;
    }
}
