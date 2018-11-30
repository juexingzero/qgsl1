
package com.manhui.gsl.jbqgsl.controller.app.usersuggest;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.PageObject;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.UserSuggest;
import com.manhui.gsl.jbqgsl.service.app.IAppUserSuggestService;

/**
* @Title: AppUserSuggest.java
* @Package com.manhui.gsl.jbqgsl.controller.app
* @Description: TODO(用户意见反馈表)
* @author LiuBin
* @date 2018年8月22日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Controller
@RequestMapping(AppUserSuggest.ROOT_URL)
@ResponseBody
public class AppUserSuggest extends BaseController{
	public static final String ROOT_URL = PARENT_URL_APP + "/userSuggest";
	@Resource
	private IAppUserSuggestService service;
	
	/**
     * 保存意见
     * @param model 意见对象
     * @return 保存结果
     */
    @RequestMapping("/save")
    public JsonResult save(UserSuggest model){
        if(model==null || model.getContent()==null || model.getContent().isEmpty()){
            throw new RuntimeException("意见内容不能为空！");
        }
        int rows=service.save(model);
        if(rows<1){
            throw new RuntimeException("保存失败！");
        }
        return new JsonResult("保存成功");
    }


    /**
     * 读取意见列表
     * @param model 列表查询条件
     * @return 查询结果
     */
    @RequestMapping("/list")
    public JsonResult list(UserSuggest model){
        return new JsonResult(service.list(model));
    }
}
