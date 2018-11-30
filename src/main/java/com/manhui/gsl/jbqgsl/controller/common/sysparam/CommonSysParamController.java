package com.manhui.gsl.jbqgsl.controller.common.sysparam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.ExcelUtil;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.sysparam.SysParam;
import com.manhui.gsl.jbqgsl.service.web.sysparam.ISysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 CommonSysParamController.java
 * @类描述 系统参数
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月3日 下午5:16:23
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月3日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "系统参数" )
@Controller
@RequestMapping( CommonSysParamController.ROOT_URL )
public class CommonSysParamController extends BaseController {
    public static final String ROOT_URL = PARENT_URL_COMMON + "/sysParam";
    @Resource
    private ISysParamService sysParamService;

    @ApiOperation( value = "获取参数列表", notes = "获取参数列表" )
    @ApiImplicitParams( {
            @ApiImplicitParam( paramType = "query", required = true, name = "p_param_id", value = "父参数ID()", dataType = "字符串" )
    } )
    @RequestMapping( value = "getSysParamList", method = RequestMethod.GET )
    @ResponseBody
    public JsonResult getSysParamList(
            @RequestParam( value = "p_param_id", required = true ) String p_param_id ) {
        return new JsonResult(sysParamService.getSysParamTwoList( p_param_id ));
    }

    @ApiOperation(value = "进入参数管理页面", notes = "进入参数管理页面")
    @RequestMapping(value = "toSysParamPage", method = RequestMethod.GET)
    public String toSysParamPage() {
        return "/web/html/sysParam/sysParamList";
    }

    @ApiOperation(value = "参数管理菜单", notes = "参数管理菜单1,2级数据")
    @RequestMapping(value = "SysParamMenu", method = RequestMethod.POST)
    @ResponseBody
    public List<SysParam> sysParaMenu(){
        //只查1,2级数据作为菜单，3级详细菜单另外查询
        return sysParamService.getSysParamMenu();
    }

    @ApiOperation(value = "参数管理列表", notes = "数据获取")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "p_param_id", value = "父级参数ID", dataType = "字符串"),
            @ApiImplicitParam(paramType = "query", required = true, name = "pageIndex", value = "当前页数", dataType = "整数"),
            @ApiImplicitParam(paramType = "query", required = true, name = "pageSize", value = "总页数", dataType = "整数")
    })
    @ResponseBody
    @RequestMapping(value = "paramDataList", method = RequestMethod.POST)
    public Map<String, Object> paramDataList(@RequestParam(required = true, value = "p_param_id") String p_param_id,
                                             HttpServletRequest request,Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<SysParam> data = sysParamService.getSysParamListPage(p_param_id,pageIndex,pageSize);
        Integer count = sysParamService.getSysParamListCount(p_param_id);
        if ( !data.isEmpty() ) {
            map.put( "data", data );
            map.put( "total", count );
        }  else {
            map.put( "data", "" );
        }
        return map;
    }

    @ApiOperation(value = "参数管理编辑", notes = "数据获取")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "param_id", value = "参数ID", dataType = "字符串"),
    })
    @ResponseBody
    @RequestMapping(value = "paramDataDetail", method = RequestMethod.POST)
    public SysParam paramDataDetail(@RequestParam(required = true, value = "param_id") String param_id) {
        return sysParamService.getSysParamDetail(param_id);
    }

    @ApiOperation(value = "参数管理列表", notes = "数据删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "update", required = true, name = "param_id", value = "参数ID", dataType = "字符串"),
    })
    @ResponseBody
    @RequestMapping(value = "paramDataDelete", method = RequestMethod.POST)
    public JsonResult paramDataDelete(@RequestParam(required = true, value = "param_id") String param_id) {
        JsonResult jr = new JsonResult();
        if(param_id != null && !"".equals(param_id)){
            //检查数据是否存在
            sysParamService.deleteSysParamData(param_id);
        }else{
            return jr = new JsonResult("未接收到参数ID");
        }
        return jr;
    }

    @ApiOperation(value = "进入参数管理新增--新增详情页面", notes = "参数新增页面")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "p_param_id", value = "父级参数ID", dataType = "字符串") 
    })
    @RequestMapping(value = "toAddParamPage", method = RequestMethod.GET)
    public String toAddParamPage(@RequestParam(required = true, value = "p_param_id") String p_param_id, HttpServletRequest request) {
        request.setAttribute("flag","add");
        request.setAttribute("p_param_id",p_param_id);
        return "/web/html/sysParam/sysParamAddDetail";
    }

    @ApiOperation(value = "进入参数管理编辑--编辑详情页面", notes = "参数编辑页面")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "param_id", value = "参数ID", dataType = "字符串")
    })
    @RequestMapping(value = "toEditParamPage", method = RequestMethod.GET)
    public String toEditParamPage(
            @RequestParam(required = true, value = "param_id") String param_id,
            HttpServletRequest request) {
        request.setAttribute("flag","edit");
        request.setAttribute("param_id",param_id);
        return "/web/html/sysParam/sysParamAddDetail";
    }

    @ApiOperation(value = "新增数据", notes = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "insert", required = true, name = "data", value = "表单数据", dataType = "Json字符串"),
            @ApiImplicitParam(paramType = "insert", required = true, name = "p_param_id", value = "父级参数ID", dataType = "字符串")})
    @RequestMapping(value = "paramSave", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult paramSave(String json, String p_param_id, HttpServletRequest request, HttpSession session){
        JSONObject parseObject = JSON.parseObject(json);
        SysParam data = parseObject.toJavaObject(SysParam.class);
        data.setP_param_id(p_param_id);
        sysParamService.insertSysParamData(data);
        return new JsonResult();
    }


    @ApiOperation(value = "修改数据", notes = "修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "update", required = true, name = "data", value = "表单数据", dataType = "Json字符串")
    })
    @RequestMapping(value = "paramEdit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult paramEdit(String json, HttpServletRequest request, HttpSession session){
        JSONObject parseObject = JSON.parseObject(json);
        SysParam data = parseObject.toJavaObject(SysParam.class);
        sysParamService.updateSysParamData(data);
        return new JsonResult();
    }

    @ApiOperation(value = "文件导入", notes = "文件导入")
    @RequestMapping(value = "sysParamFileInport", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sysParamFileInport(@RequestParam("myfile") MultipartFile myfile, HttpServletRequest request) throws IOException {
        //文件检测
        String msg = "";
        msg = ExcelUtil.checkFileType(myfile);
        if(!msg.equals("success")){
            return new JsonResult(msg);
        }
        msg = ExcelUtil.checkFileMax(myfile);
        if(!msg.equals("success")){
            return new JsonResult(msg);
        }
        List<String[]> datas = ExcelUtil.getExcelData(myfile);
        int eNum = 0;
        int sNum = 0;
        int row = 0;
        for(String[] data : datas){
            if(row == 0 || row <10){
                row = row + 1;
                continue;
            }
            if(data[0].isEmpty() && data[0] != null){
                break;
            }
            SysParam param = new SysParam();
            param.setParam_id(UUIDUtil.getUUID());
            param.setP_param_id(data[0]);
            param.setParam_key(data[1]);
            param.setParam_value(data[2]);
            param.setParam_describe(data[3]);
            param.setParam_order(data[4]);
            param.setUpdate_time(DateUtil.getTime());
            param.setCreate_time(DateUtil.getTime());
            //保存
            sysParamService.insertSysParamData(param);
            sNum = sNum + 1;
        }
        if(eNum > 0){
            msg = "导入完成"+sNum+"条，失败"+eNum+"条";
        }else if(sNum == 0){
            msg = "导入失败，请检查数据";
        }else if(eNum == 0){
            msg = "导入成功";
        }
        return new JsonResult(msg);
    }

    @ApiOperation(value = "下载模板", notes = "下载模板")
    @RequestMapping(value = "templateDown", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult templateDown(HttpServletRequest request,HttpServletResponse response) throws IOException{
        org.springframework.core.io.Resource res = new ClassPathResource("/resources/excelFile/参数字典批量导入模板.xlsx");
        InputStream is = res.getInputStream();
        String filename = res.getFilename();
        filename = URLEncoder.encode(filename,"UTF-8");
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ filename);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");// 定义输出类型
        OutputStream out = response.getOutputStream();
        //输出文件
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = is.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        //out.flush();
        out.close();
        is.close();
        return null;
    }

    /**
     * 根据param_key 查询数据
     * @param param_key
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getParamByKey", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getParamByKey(String param_key) throws IOException{
        if(StringUtils.isBlank(param_key)){
            return new JsonResult(new Throwable("参数错误"));
        }
        SysParam param = sysParamService.getSysParamByKey(param_key);
        return new JsonResult(param);
    }
}
