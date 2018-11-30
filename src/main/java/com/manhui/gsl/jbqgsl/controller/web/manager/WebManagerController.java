package com.manhui.gsl.jbqgsl.controller.web.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api( tags = "后台-首页" )
@Controller
@RequestMapping( "manager" )
public class WebManagerController {
    @RequestMapping( value = "toIndex", method = RequestMethod.GET )
    @ApiOperation( value = "进入首页界面", notes = "进入首页界面" )
    public String index() {
        return "/web/html/manager/index";
    }
}
