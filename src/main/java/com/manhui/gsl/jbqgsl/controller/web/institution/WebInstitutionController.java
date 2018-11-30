package com.manhui.gsl.jbqgsl.controller.web.institution;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.manhui.gsl.jbqgsl.common.util.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.service.web.IInstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebInstitutionController.java
 * @类描述 <pre>参评机构模块controller层，主要负责请求的接收及响应</pre>
 * @作者  kevin kwmo1005@163.com
 * @创建时间 2018年8月3日 下午4:19:13
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月3日                创建
 *     ----------------------------------------------
 * </pre>
 */
@Api( tags = "后台-参评机构" )
@Controller
@RequestMapping( WebInstitutionController.ROOT_URL )
public class WebInstitutionController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/institution";
    @Resource
    private IInstitutionService institutionService;

    @ApiOperation(value = "进入机构列表页面", notes = "进入机构列表页面")
    @RequestMapping(value = "toInstitutionListPage", method = RequestMethod.GET)
    public String userListPage() {
        return "/web/html/institution/institutionTabs"; 
    }
    
    @ApiOperation(value = "分别进入机构列表", notes = "分别进入机构列表")
    @RequestMapping(value = "toInstitutionListPages", method = RequestMethod.GET)
    public String InstitutionListPages() {
        return "/web/html/institution/institutionList"; 
    }
    
    @ApiOperation(value = "分别进入街镇列表", notes = "分别进入街镇列表")
    @RequestMapping(value = "toStreetListPages", method = RequestMethod.GET)
    public String StreetListPages() {
        return "/web/html/institution/streetList"; 
    }
    
    @ApiOperation(value = "分别进入企业列表", notes = "分别进入企业列表")
    @RequestMapping(value = "toEnterpriseListPages", method = RequestMethod.GET)
    public String EnterpriseListPages() {
        return "/web/html/institution/enterpriseList"; 
    }

    @ApiOperation(value = "进入添加机构界面", notes = "进入添加机构界面")
    @RequestMapping(value = "toInstitutionSavePage", method = RequestMethod.GET)
    public String addUserPage() {
        return "/web/html/institution/institutionSave";
    }

	@ApiOperation(value = "进入添加企业机构界面", notes = "进入添加企业机构界面")
	@RequestMapping(value = "toInstitutionEnterpriseSavePage", method = RequestMethod.GET)
	public String toInstitutionEnterpriseSavePage() {
		return "/web/html/institution/enterpriseSave";
	}
    
    @ApiOperation(value = "进入编辑机构界面", notes = "进入编辑机构界面")
    @RequestMapping(value = "toInstitutionEditPage", method = RequestMethod.GET)
    public String editInstitutionPage(String institution_id,HttpServletRequest request) {
    	request.setAttribute("institution_id", institution_id);
        return "/web/html/institution/institutionDetail";
    }

    @ApiOperation(value = "查询列表数据", notes = "查询列表数据")
    @RequestMapping(value = "institutionList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>institutionList( String institution_type,HttpServletRequest request,Integer pageIndex, Integer pageSize ) {
    	String institution_name = request.getParameter("institution_name");
    	String mobile = request.getParameter("user_mobile");
        Map<String, Object> map = new HashMap<>();
        //检查是否街镇用户登录
		String user_type = AppUtil.getCookieByName( request, "user_type" );
		List<Institution> institutionList = new ArrayList<>();
		Integer institutionTotal = 0;
		if("2".equals(user_type) && "3".equals(institution_type)){
			//双向评价用户(街道)查询到关联企业ID
			Institution data = institutionService.queryInstitutionByMoblie(mobile);
			institutionList = institutionService.queryInstitutionList( institution_name,institution_type,data.getInstitution_id(),pageIndex, pageSize);
			institutionTotal = institutionService.queryInstitutionTotal( institution_name ,institution_type,data.getInstitution_id(),pageIndex, pageSize);
		}else{
			institutionList = institutionService.queryInstitutionList( institution_name,institution_type,null,pageIndex, pageSize);
			institutionTotal = institutionService.queryInstitutionTotal( institution_name ,institution_type,null,pageIndex, pageSize);
		}
        if ( !institutionList.isEmpty() ) {
            map.put( "data", institutionList );
            map.put( "total", institutionTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }
    
    @ApiOperation(value = "查询机构详细数据", notes = "查询机构详细数据")
    @RequestMapping(value = "institutionDetail", method = RequestMethod.POST)
    @ResponseBody
    public Institution institutionDetail( String institution_id ) {
        Institution data = institutionService.queryInstitution( institution_id );
        if(data.getInstitution_main_id() != null && !"".equals(data.getInstitution_main_id())){
        	Institution mainInstitution = institutionService.queryInstitution(data.getInstitution_main_id());
        	data.setInstitution_main_name(mainInstitution.getInstitution_name());
        }
        return data;
    }
    
    @ApiOperation(value = "删除数据", notes = "删除数据")
	@RequestMapping(value = "deleteInstitution", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteInstitution(String institution_id){
		JsonResult result = new JsonResult();
		result = institutionService.deleteInstitution(institution_id);
		return result;
	}
    
    @ApiOperation(value = "编辑数据", notes = "编辑数据")
	@RequestMapping(value = "institutionDetailEdit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult institutionDetailEdit(String json, HttpServletRequest request){
		JsonResult result = new JsonResult();
		JSONObject parseObject = JSON.parseObject(json);
		Institution institution = parseObject.toJavaObject(Institution.class);
		institution.setUpdate_time(DateUtil.getTime());
		//需要校验所有的机构名称不能重复（区级部门、街镇、企业组合起来验证重复性），重复不能保存
		//List<Institution> check = institutionService.queryInstitutionByNameAndPhone(institution.getInstitution_name());
		/*if(check != null && check.size() >0){
			return result= new JsonResult("机构已存在!请重新编辑");
		}*/
		result = institutionService.editInstitution(institution);
		return result;
	}
    
    @ApiOperation(value = "新增数据", notes = "新增数据")
	@RequestMapping(value = "institutionDetailSave", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult institutionDetailSave(String json,HttpServletRequest request){
		JsonResult result = new JsonResult();
		JSONObject parseObject = JSON.parseObject(json);
		Institution institution = parseObject.toJavaObject(Institution.class);
		institution.setInstitution_id(UUIDUtil.getUUID());
		institution.setUpdate_time(DateUtil.getTime());
		institution.setCreate_time(DateUtil.getTime());
		//机构名称与联系人手机号判断唯一性
		List<Institution> check = institutionService.queryInstitutionByNameAndPhone(institution.getInstitution_name());
		if(check != null&& check.size() >0){
			return result= new JsonResult("机构已存在！请重新编辑");
		}
		result = institutionService.saveInstitution(institution,request);
		return result = new JsonResult();
	}
    
    
    @ApiOperation(value = "下载模板", notes = "下载模板")
	@RequestMapping(value = "templateDown", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult templateDown(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	org.springframework.core.io.Resource res = new ClassPathResource("/resources/excelFile/参评机构批量导入模板.xls");
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
    
    @ApiOperation(value = "文件导入", notes = "文件导入")
	@RequestMapping(value = "fileInport", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult fileInport(@RequestParam("myfile") MultipartFile myfile,HttpServletRequest request,
    								HttpServletResponse response) throws IOException{
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
    		Institution institution = new Institution();
    		institution.setInstitution_id(UUIDUtil.getUUID());
    		if(data[0].equals("区级部门")){
    			institution.setInstitution_type("1");
    		}else if(data[0].equals("街镇")){
    			institution.setInstitution_type("2");
    		}
    		/*else if(data[0].equals("企业")){
    			institution.setInstitution_type("3");
    		}*/
    		else{
    			eNum = eNum + 1;
    			continue;
    		}
    		if(!data[1].isEmpty() && data[1].length() <= 60){
    			institution.setInstitution_name(data[1]);
    		}else{
    			eNum = eNum + 1;
    			continue;
    		}
    		
    		if(data[0].equals("街镇") || "区级部门".equals(data[0])){
    			//eNum = eNum + 1;
    			//continue;
    			institution.setIndustry_id("");
    		}else {
				eNum = eNum + 1;
				continue;
			}
    		/*else{
    			if(data[2].equals("工业")){
        			institution.setIndustry_id("1");
        		}else if(data[2].equals("建筑业")){
        			institution.setIndustry_id("2");
        		}else if(data[2].equals("交通运输")){
        			institution.setIndustry_id("3");
        		}else if(data[2].equals("物流业")){
        			institution.setIndustry_id("4");
        		}else if(data[2].equals("批发零售业")){
        			institution.setIndustry_id("5");
        		}else if(data[2].equals("住宿餐饮业")){
        			institution.setIndustry_id("6");
        		}else if(data[2].equals("金融业")){
        			institution.setIndustry_id("7");
        		}else if(data[2].equals("房地产业")){
        			institution.setIndustry_id("8");
        		}else if(data[2].equals("营利性服务业")){
        			institution.setIndustry_id("9");
        		}else if(data[2].equals("其他民营企业")){
        			institution.setIndustry_id("10");
        		}else {
        			eNum = eNum + 1;
        			continue;
        		}
    		}*/
    			
    		if(!data[3].isEmpty()){
    			institution.setInstitution_linkman_name(data[3]);
    		}else{
    			eNum = eNum + 1;
    			continue;
    		}
    		if(!data[4].isEmpty() && data[4].length() == 11){
    			institution.setInstitution_linkman_phone(data[4]);
    		}else{
    			eNum = eNum + 1;
    			continue;
    		}
    		institution.setInstitution_linkman_email(data[5]);
    		institution.setUpdate_time(DateUtil.getTime());
    		institution.setCreate_time(DateUtil.getTime());
    		//机构名称与联系人手机号判断唯一性
    		List<Institution> check = institutionService.queryInstitutionByNameAndPhone(institution.getInstitution_name());
    		if(check != null && check.size() > 0){
    			eNum = eNum + 1;
    			continue;
    		}
    		//保存
    		institutionService.saveInstitution(institution,request);
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
    
    
    @ApiOperation(value = "进入主管企业界面", notes = "进入主管企业界面")
    @RequestMapping(value = "toInstitutionMainPage", method = RequestMethod.GET)
    public String toInstitutionMainPage(String institution_id,HttpServletRequest request) {
    	request.setAttribute("institution_id", institution_id);
        return "/web/html/institution/manageEnterprise";
    }


	@ApiOperation(value = "进入街镇主管企业界面", notes = "进入街镇主管企业界面")
	@RequestMapping(value = "toStreetInstitutionMainPage", method = RequestMethod.GET)
	public String toStreetInstitutionMainPage(String institution_id,HttpServletRequest request) {
		request.setAttribute("institution_id", institution_id);
		return "/web/html/institution/streetManageEnterprise";
	}

    
    @ApiOperation(value = "查询企业列表数据", notes = "查询企业列表数据")
    @RequestMapping(value = "manageEnterpriseList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> manageEnterpriseList( String institution_id,String industry_id,String institution_name,Integer pageIndex, Integer pageSize ) {

        Map<String, Object> map = new HashMap<>();
        List<Institution> institutionList = institutionService.queryInstitutionByInstitutionMainId(institution_id, industry_id, institution_name, pageIndex, pageSize);
        Integer institutionTotal = institutionService.queryInstitutionCountByInstitutionMainId( institution_id, industry_id, institution_name);
        if ( !institutionList.isEmpty() ) { 
            map.put( "data", institutionList );
            map.put( "total", institutionTotal );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }
    
    
    @ApiOperation(value = "全部取消选择", notes = "全部取消选择")
	@RequestMapping(value = "cancelAllSelect", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cancelAllSelect(String institution_id){
		JsonResult result = new JsonResult();
		//获取所有当前部门所管理企业的ID
		List<Institution> institutionList = institutionService.queryInstitutionByInstitutionMainId(institution_id, null, null, null, null);
		if(institutionList != null && institutionList.size() > 0){
			//清空设定主管部门ID
			for(Institution data : institutionList){
				institutionService.cleanInstitutionMainId(data.getInstitution_id());
			}
		}else{
			return result = new JsonResult("参数错误，无法取消选择");
		}
		return result;
	}
    
    
    @ApiOperation(value = "取消选择", notes = "取消选择")
	@RequestMapping(value = "cancelSelect", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cancelSelect(String institution_id){
		JsonResult result = new JsonResult();
		if(institution_id != null && !"".equals(institution_id)){
			//清空设定主管部门ID
			institutionService.cleanInstitutionMainId(institution_id);
		}else{
			return result = new JsonResult("参数错误，无法取消选择");
		}
		return result;
	}
    
    
    @ApiOperation(value = "企业选择", notes = "企业选择")
	@RequestMapping(value = "select", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult select(String institution_id,String main_institution){
		JsonResult result = new JsonResult();
		if(institution_id != null && !"".equals(institution_id)){
			//添加关联部门ID
			institutionService.saveInstitutionMainId(institution_id, main_institution);
		}else{
			return result = new JsonResult("参数错误，无法选择企业");
		}
		return result;
	}


	@ApiOperation(value = "查询企业列表数据", notes = "查询企业列表数据")
	@RequestMapping(value = "streetManageEnterpriseList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> streetManageEnterpriseList( String institution_id,String industry_id,String institution_name,Integer pageIndex, Integer pageSize ) {

		Map<String, Object> map = new HashMap<>();
		List<Institution> institutionList = institutionService.queryStreetInstitutionByInstitutionMainId(institution_id, industry_id, institution_name, pageIndex, pageSize);
		Integer institutionTotal = institutionService.queryStreetInstitutionCountByInstitutionMainId( institution_id, industry_id, institution_name);
		if ( !institutionList.isEmpty() ) {
			map.put( "data", institutionList );
			map.put( "total", institutionTotal );
		}
		else {
			map.put( "data", "" );
		}
		return map;
	}

	@ApiOperation(value = "全部取消选择", notes = "全部取消选择")
	@RequestMapping(value = "cancelAllEnterpriseSelect", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult cancelAllEnterpriseSelect(String institution_id){
		JsonResult result = new JsonResult();
		//获取所有当前部门所管理企业的ID
		List<Institution> institutionList = institutionService.queryInstitutionByInstitutionMainId(institution_id, null, null, null, null);
		if(institutionList != null && institutionList.size() > 0){
			//清空设定主管部门ID
			for(Institution data : institutionList){
				institutionService.cleanStreetMainId(data.getInstitution_id());
			}
		}else{
			return result = new JsonResult("参数错误，无法取消选择");
		}
		return result;
	}


	@ApiOperation(value = "取消选择", notes = "取消选择")
	@RequestMapping(value = "cancelEnterpriseSelect", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult cancelEnterpriseSelect(String institution_id){
		JsonResult result = new JsonResult();
		if(institution_id != null && !"".equals(institution_id)){
			//清空设定主管部门ID
			institutionService.cleanStreetMainId(institution_id);
		}else{
			return result = new JsonResult("参数错误，无法取消选择");
		}
		return result;
	}


	@ApiOperation(value = "企业选择", notes = "企业选择")
	@RequestMapping(value = "selectEnterprise", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult selectEnterprise(String institution_id,String main_institution){
		JsonResult result = new JsonResult();
		if(institution_id != null && !"".equals(institution_id)){
			//添加关联部门ID
			institutionService.saveStreetMainId(institution_id, main_institution);
		}else{
			return result = new JsonResult("参数错误，无法选择企业");
		}
		return result;
	}


	@ApiOperation(value = "企业机构添加列表", notes = "数据获取")
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "XM", value = "姓名", dataType = "字符串"),
			@ApiImplicitParam(paramType = "query", required = true, name = "YDDH", value = "手机号码", dataType = "字符串")
	})
	@RequestMapping(value = "queryEnterpriseAddList", method = RequestMethod.POST)
	public List<Map<String,Object>> queryEnterpriseAddList(String XM, String YDDH,
														   HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("XM", XM);
		map.put("YDDH", YDDH);
		List<Map<String, Object>> data = institutionService.queryEnterpriseMemberInfo(map);
		return data;
	}

	@ApiOperation(value = "新增企业数据", notes = "新增企业数据")
	@RequestMapping(value = "enterpriseDetailSave", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult enterpriseDetailSave(String json,String mobile,HttpServletRequest request){
		JsonResult result = new JsonResult();
		JSONArray ts = JSONArray.parseArray(json);
		String user_type = AppUtil.getCookieByName( request, "user_type" );
		Institution mainData = institutionService.queryInstitutionByMoblie(mobile);
		for(int i = 0 ; i<ts.size() ; i++){
			JSONObject data = ts.getJSONObject(i);
			Map mapData = data;
			Institution institution = new Institution();
			institution.setInstitution_id(UUIDUtil.getUUID());
			institution.setInstitution_name(mapData.get("qymc") == null ? "":mapData.get("qymc").toString());
			institution.setInstitution_type("3");
			institution.setInstitution_describe(mapData.get("qyjc") == null ? "":mapData.get("qyjc").toString());
			institution.setInstitution_linkman_name(mapData.get("lxrxm") == null ? "":mapData.get("lxrxm").toString());
			institution.setInstitution_linkman_phone(mapData.get("sjhm") == null ? "":mapData.get("sjhm").toString());
			institution.setInstitution_linkman_email(mapData.get("qyyx") == null ? "":mapData.get("qyyx").toString());
			if("2".equals(user_type)){
				institution.setStreet_main_id(mainData.getInstitution_id());
			}
			institution.setUpdate_time(DateUtil.getTime());
			institution.setCreate_time(DateUtil.getTime());
			result = institutionService.saveEnterpriseInstitution(institution);
			if(result.getData() != null && !"".equals(result.getData())){
				return result;
			}
		}
		return result;
	}
}
