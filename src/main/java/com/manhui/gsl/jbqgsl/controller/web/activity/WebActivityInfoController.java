package com.manhui.gsl.jbqgsl.controller.web.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.common.util.ZxingUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.commerce.Commerce;
import com.manhui.gsl.jbqgsl.controller.web.meetingmanager.AttendeesResult;
import com.manhui.gsl.jbqgsl.controller.web.meetingmanager.meetingResult;
import com.manhui.gsl.jbqgsl.model.MeetingManager;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;
import com.manhui.gsl.jbqgsl.model.MeetingReceipt;
import com.manhui.gsl.jbqgsl.model.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.model.MeetingVote;
import com.manhui.gsl.jbqgsl.model.MeetingVoteOptions;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityEntryFlow;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityInfo;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivityPerson;
import com.manhui.gsl.jbqgsl.model.activitymanager.ActivitySignFlow;
import com.manhui.gsl.jbqgsl.service.app.commerce.IAppCommerceService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ParamModel;
import com.manhui.gsl.jbqgsl.service.web.activity.ActivityEntryFlowService;
import com.manhui.gsl.jbqgsl.service.web.activity.ActivityPersonService;
import com.manhui.gsl.jbqgsl.service.web.activity.ActivitySignFlowService;
import com.manhui.gsl.jbqgsl.service.web.activity.IActivityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 WebActivityInfoController.java
 * @类描述 活动管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月17日 下午17:31:35
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年10月17日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Api( tags = "后台-活动管理" )
@Controller
@RequestMapping( WebActivityInfoController.ROOT_URL )
public class WebActivityInfoController extends BaseController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/activity";
    @Resource
    private IActivityInfoService activityInfoService;
    
    @Resource
    private ActivityPersonService activityPersonService;
    
    @Resource
    private ActivitySignFlowService activitySignFlowService;
    
    @Resource
    private ActivityEntryFlowService activityEntryFlowService;
    
    @Resource
    private AppMemberJoinManagerService appMemberJoinManagerService;

    @ApiOperation( value = "进入活动管理列表页面", notes = "进入活动管理列表页面" )
    @RequestMapping( value = "toActivityListPage", method = RequestMethod.GET )
    public String toTopicEvaluateListPage(Model model,
			@RequestParam( value = "starttime", required = false ) String starttime,
			@RequestParam( value = "endtime", required = false ) String endtime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//减少8小时
				Calendar ca = Calendar.getInstance();
				if(starttime!=null){
					Date logDate1 = sdf.parse(starttime);
					ca.setTime(logDate1);
					ca.add(Calendar.HOUR_OF_DAY, -8);
					starttime=sd.format(ca.getTime());
				}
				if(endtime!=null){
					Date logDate1 = sdf.parse(endtime);
					ca.setTime(logDate1);
					ca.add(Calendar.HOUR_OF_DAY, -8);
					endtime=sd.format(ca.getTime());
				}
				ActivityInfo ai=new ActivityInfo();
				ai.setActivity_start_time(starttime);
				ai.setActivity_end_time(endtime);
				Integer mmTotal=activityInfoService.queryActivityInfoNum(ai);
				Integer pTotal=activityEntryFlowService.queryActivityEntryFlowNum("0");
				Integer cTotal=activityEntryFlowService.queryActivityEntryFlowNum("1");
				
				model.addAttribute("mmTotal", mmTotal);
				model.addAttribute("pTotal", pTotal);
				model.addAttribute("cTotal", cTotal);
        return "/web/html/activity/activityList";
    }

	   @ApiOperation(value = "查询列表数据", notes = "查询列表数据")
	    @RequestMapping(value = "getActivityInfoList", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String, Object>getActivityInfoList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "activity_theme", required = false ) String activity_theme,
	    		 @RequestParam( value = "activity_state", required = false )String  activity_state ) {
	        Map<String, Object> map = new HashMap<>();
	        List<String> activity_stateList=new ArrayList<>();
	         if(activity_state.equals("1")){
	        	 activity_stateList.add("0");
	        }else if(activity_state.equals("2")){
	        	activity_stateList.add("1");
	        }else if(activity_state.equals("3")){
	        	activity_stateList.add("2");
	        }else if(activity_state.equals("4")){
	        	activity_stateList.add("3");
	        	activity_stateList.add("4");
	        }
	        
	         if(activity_theme!=null){
	        	 activity_theme="%"+activity_theme+"%";
	         }
	         
	        List<ActivityInfo> ActivityInfoList = activityInfoService.getActivityInfoList(activity_theme, activity_stateList, pageIndex, pageSize);
	      
	        for (ActivityInfo a : ActivityInfoList) {
				if(a.getActivity_state().equals("0")){
					a.setActivity_state("未发布");
				}else if(a.getActivity_state().equals("1")){
					a.setActivity_state("未开始");
				}else if(a.getActivity_state().equals("2")){
					a.setActivity_state("进行中");
				}else if(a.getActivity_state().equals("3")){
					a.setActivity_state("已结束");
				}else if(a.getActivity_state().equals("4")){
					a.setActivity_state("已撤销");
				}
			}

	        Integer ActivityInfoTotal = activityInfoService.queryActivityInfoTotal(activity_theme, activity_stateList, pageIndex, pageSize);
	        if ( !ActivityInfoList.isEmpty() ) { 
	            map.put( "data", ActivityInfoList );
	            map.put( "total", ActivityInfoTotal );
	        }
	        else {
	            map.put( "data", "" );
	        }
	        return map;
	    }
	   
	   
	   @ApiOperation(value = "发布/撤销活动", notes = "发布/撤销活动")
	   @RequestMapping( value = "releaseActivity", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult releaseActivity(
	    		@RequestParam( value = "activity_id", required = true ) String activity_id,
	    		@RequestParam( value = "activity_state", required = true ) String activity_state) {
		   ActivityInfo ai=new ActivityInfo();
		   ai.setActivity_id(activity_id);
		   ai.setActivity_state(activity_state);
	        return new JsonResult(activityInfoService.updateActivityInfo(ai));
	    }
	   
	   
	   @ApiOperation( value = "获取活动管理人员详情数据", notes = "获取活动管理人员详情数据" )
	    @RequestMapping( value = "getActivityPersonDetailData", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult getActivityPersonDetailData(
	            @RequestParam(value = "activity_id", required = true)String activity_id ) {
	        List<ActivityPerson> apList= activityPersonService.queryActivityPersonList(activity_id);
	        List<AttendeesResult> arList=new ArrayList<>();
	        for (ActivityPerson m : apList) {
	        	AttendeesResult ar=new AttendeesResult();
	        	ar.setUser_id(m.getMember_id());
	        	ar.setContact(m.getMember_linkman_name());
	        	ar.setUser_name(m.getMember_name());
	        	ar.setUser_phone(m.getMember_linkman_phone());
	        	ar.setJoinObjName(m.getSh_name());
	        	ar.setPosition(m.getMember_job());
				arList.add(ar);
			}
	        Map<String, Object> resultMap=new HashMap<>();
	        resultMap.put("arList", arList);
	        return new JsonResult(resultMap);
	    }
	   
	   
	    @ApiOperation(value = "查看活动管理详情数据", notes = "查看活动管理详情数据")
	    @RequestMapping(value = "ActivityInfoDetail", method = RequestMethod.POST)
	    @ResponseBody
	    public ActivityResult ActivityInfoDetail( String activity_id ) {
	    	ActivityResult data=new ActivityResult();
	    	ActivityInfo ai=new ActivityInfo();
	    	ai.setActivity_id(activity_id);
	    	ActivityInfo mm = activityInfoService.queryActivityInfo(ai);
	    	
	    	data.setActivity_id(mm.getActivity_id());
	    	data.setActivity_name(mm.getActivity_name());
	    	data.setActivity_address(mm.getActivity_address());
	    	data.setLongitude_latitude(mm.getLongitude_latitude());
	    	data.setActivity_navigation(mm.getActivity_navigation());
	    	data.setActivity_file(mm.getActivity_file());
	    	data.setActivity_image(mm.getImgObj());
	    	data.setActivity_content(mm.getActivity_content());
	    	data.setIs_sign(mm.getIs_sign());
	    	data.setActivity_start_time(mm.getActivity_start_time());
	    	data.setActivity_link_man(mm.getActivity_link_man());
	    	data.setActivity_link_phone(mm.getActivity_link_phone());
	    	data.setActivity_end_time(mm.getActivity_end_time());
	    	data.setActivity_entry_end_time(mm.getActivity_entry_end_time());
	        return data;
	    }
	   
	   @ApiOperation(value = "进入新增/修改/查看活动页面", notes = "进入新增/修改/查看活动页面")
		@RequestMapping(value = "/toseeActivityPage", method = RequestMethod.GET)
		public String toseeActivityPage(
				@RequestParam( value = "activity_id", required = true ) String activity_id,
				@RequestParam( value = "state", required = true ) String state,
				Model model) {
	    	
		   model.addAttribute("activity_id", activity_id);
		   model.addAttribute("state", state);
			return "/web/html/activity/activityDetail";
		}
	   
	   
	   @ApiOperation( value = "添加活动", notes = "添加活动" )
	    @RequestMapping( value = "saveActivityInfo", method = RequestMethod.POST )
	    @ResponseBody
	    public String saveActivityInfo( HttpServletRequest request, String json,String passive_json,String qbhy ) throws ParseException {
	        JSONObject jsonObject = JSON.parseObject( json );
	        //活动名称去重
	        
	        ActivityInfo am = new ActivityInfo();
	        String str = jsonObject.get( "activity_start_time" ).toString().substring(0, jsonObject.get( "activity_start_time" ).toString().length() - 3);
	        String st=jsonObject.get( "activity_end_time" ).toString().substring(0, jsonObject.get( "activity_end_time" ).toString().length() - 3);
	        //活动报名截止时间
	        String hzsj1=jsonObject.get( "activity_entry_end_time" ).toString();
	        if(hzsj1!=null && !hzsj1.equals("")){
	        	String hzsj2 = jsonObject.get( "activity_entry_end_time" ).toString().substring(0, jsonObject.get( "activity_entry_end_time" ).toString().length() - 3);
	        	 am.setActivity_entry_end_time(hzsj2.replace("T", " "));
	        }else{
	        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        long dif =  sdf.parse(str.replace("T", " ")).getTime()-86400*1000;//减一天
		        Date date=new Date(); 
		        date.setTime(dif);
		        am.setActivity_entry_end_time(sdf.format(date));
	        }
	        String uuid=UUIDUtil.getUUID();
	        am.setActivity_id(uuid);
	        am.setActivity_name(jsonObject.get( "activity_name" ).toString());
	        am.setActivity_start_time(str.replace("T", " "));
	        am.setActivity_end_time(st.replace("T", " "));
	        am.setActivity_address(jsonObject.get( "activity_address" ).toString());
	        am.setActivity_navigation(jsonObject.get( "activity_navigation" ).toString());
	        am.setLongitude_latitude(jsonObject.get( "longitude_latitude" ).toString());
	        am.setActivity_file(jsonObject.get( "activity_file" ).toString());
	        am.setIs_sign(jsonObject.get( "is_sign" ).toString());
	        am.setActivity_content(jsonObject.get( "activity_content" ).toString());
	        am.setSign_qrcode(jsonObject.get( "activity_name" ).toString()+".jpg");
	        am.setActivity_link_phone(jsonObject.get( "activity_link_phone" ).toString());
	        am.setActivity_link_man(jsonObject.get( "activity_link_man" ).toString());
	        am.setActivity_image(jsonObject.get( "activity_image" ).toString());
	        //增加活动
	        Integer bools = activityInfoService.addActivityInfo(am);
	        
	        //判断是否加入全部会员信息
	        if(qbhy.equals("")){
	        	JSONArray parseArray = JSON.parseArray(passive_json);
				   List<AttendeesResult> list = parseArray.toJavaList(AttendeesResult.class);
				   List<ActivityPerson> apList=new ArrayList<>();
				   for (AttendeesResult attendeesResult : list) {
					   ActivityPerson a=new ActivityPerson();
					   String uid=UUIDUtil.getUUID();
					   a.setActivity_id(uuid);
					   a.setActivity_person_id(uid);
					   a.setSh_name(attendeesResult.getJoinObjName());
					   a.setMember_name(attendeesResult.getUser_name());
					   a.setMember_job(attendeesResult.getPosition());
					   a.setMember_linkman_name(attendeesResult.getContact());
					   a.setMember_linkman_phone(attendeesResult.getUser_phone());
					   a.setMember_id(attendeesResult.getUser_id());
					   apList.add(a);
	        }
				 //增加参会人员
				   activityPersonService.insertActivityPerson(apList);
	        
			}else{
				 ParamModel param=new ParamModel();
			        PageInfo<Map<String,Object>> mjm=appMemberJoinManagerService.queryMemberStaffList(param);
			        List<Map<String,Object>> sdf=mjm.getList();
			        List<ActivityPerson> apList=new ArrayList<>();
			        for (Map<String, Object> m : sdf) {
			        	  ActivityPerson a=new ActivityPerson();
			        	  String uid=UUIDUtil.getUUID();
						   a.setActivity_id(uuid);
						   a.setActivity_person_id(uid);
						   a.setSh_name(m.get("joinObjName").toString());
						   a.setMember_name(m.get("user_name").toString());
						   a.setMember_job(m.get("position").toString());
						   a.setMember_linkman_name(m.get("contact").toString());
						   a.setMember_linkman_phone(m.get("user_phone").toString());
						   a.setMember_id(m.get("user_id").toString());
						apList.add(a);
					}
				//增加参会人员
				   activityPersonService.insertActivityPerson(apList);
			}
			
				  //生成二维码
				   try {
					ZxingUtil.encode(uuid,"F:\\test/test.jpg",System.getProperty("user.dir")+"/target/classes/resources/images",true,jsonObject.get( "activity_name" ).toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        return "1";
	        
	    }
	   
	   
	   @ApiOperation( value = "修改活动", notes = "修改活动" )
	    @RequestMapping( value = "updateActivityInfo", method = RequestMethod.POST )
	    @ResponseBody
	    public String updateActivityInfo( HttpServletRequest request, String json,String passive_json,String qbhy	 ) throws ParseException {
	        JSONObject jsonObject = JSON.parseObject( json );
	        //活动名称去重
	        
	        ActivityInfo am = new ActivityInfo();
	        String str = jsonObject.get( "activity_start_time" ).toString().substring(0, jsonObject.get( "activity_start_time" ).toString().length() - 3);
	        String st=jsonObject.get( "activity_end_time" ).toString().substring(0, jsonObject.get( "activity_end_time" ).toString().length() - 3);
	        //活动报名截止时间
	        String hzsj1=jsonObject.get( "activity_entry_end_time" ).toString();
	        if(hzsj1!=null && !hzsj1.equals("")){
	        	String hzsj2 = jsonObject.get( "activity_entry_end_time" ).toString().substring(0, jsonObject.get( "activity_entry_end_time" ).toString().length() - 3);
	        	 am.setActivity_entry_end_time(hzsj2.replace("T", " "));
	        }else{
	        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        long dif =  sdf.parse(str.replace("T", " ")).getTime()-86400*1000;//减一天
		        Date date=new Date(); 
		        date.setTime(dif);
		        am.setActivity_entry_end_time(sdf.format(date));
	        }
	        am.setActivity_id(jsonObject.get( "activity_id" ).toString());
	        am.setActivity_name(jsonObject.get( "activity_name" ).toString());
	        am.setActivity_start_time(str.replace("T", " "));
	        am.setActivity_end_time(st.replace("T", " "));
	        am.setActivity_address(jsonObject.get( "activity_address" ).toString());
	        am.setActivity_navigation(jsonObject.get( "activity_navigation" ).toString());
	        am.setLongitude_latitude(jsonObject.get( "longitude_latitude" ).toString());
	        am.setActivity_file(jsonObject.get( "activity_file" ).toString());
	        am.setIs_sign(jsonObject.get( "is_sign" ).toString());
	        am.setActivity_content(jsonObject.get( "activity_content" ).toString());
	        am.setSign_qrcode(jsonObject.get( "activity_name" ).toString()+".jpg");
	        am.setActivity_link_phone(jsonObject.get( "activity_link_phone" ).toString());
	        am.setActivity_link_man(jsonObject.get( "activity_link_man" ).toString());
	        am.setActivity_image(jsonObject.get( "activity_image" ).toString());
	        //修改活动
	        Integer bools = activityInfoService.updateActivityInfo(am);
	        
	        //删除原有参与活动人员
			   ActivityPerson ap=new ActivityPerson();
			   ap.setActivity_id(jsonObject.get( "activity_id" ).toString());
			   activityPersonService.deleteActivityPerson(ap);
	      //判断是否加入全部会员信息
	        if(qbhy.equals("")){
	        	JSONArray parseArray = JSON.parseArray(passive_json);
				   List<AttendeesResult> list = parseArray.toJavaList(AttendeesResult.class);
				   List<ActivityPerson> apList=new ArrayList<>();
				   for (AttendeesResult attendeesResult : list) {
					   ActivityPerson a=new ActivityPerson();
					   String uid=UUIDUtil.getUUID();
					   a.setActivity_id(jsonObject.get( "activity_id" ).toString());
					   a.setActivity_person_id(uid);
					   a.setSh_name(attendeesResult.getJoinObjName());
					   a.setMember_name(attendeesResult.getUser_name());
					   a.setMember_job(attendeesResult.getPosition());
					   a.setMember_linkman_name(attendeesResult.getContact());
					   a.setMember_linkman_phone(attendeesResult.getUser_phone());
					   a.setMember_id(attendeesResult.getUser_id());
					   apList.add(a);
	        }
				 //增加参会人员
				   activityPersonService.insertActivityPerson(apList);
	        
			}else{
				 ParamModel param=new ParamModel();
			        PageInfo<Map<String,Object>> mjm=appMemberJoinManagerService.queryMemberStaffList(param);
			        List<Map<String,Object>> sdf=mjm.getList();
			        List<ActivityPerson> apList=new ArrayList<>();
			        for (Map<String, Object> m : sdf) {
			        	  ActivityPerson a=new ActivityPerson();
			        	  String uid=UUIDUtil.getUUID();
						   a.setActivity_id(jsonObject.get( "activity_id" ).toString());
						   a.setActivity_person_id(uid);
						   a.setSh_name(m.get("joinObjName").toString());
						   a.setMember_name(m.get("user_name").toString());
						   a.setMember_job(m.get("position").toString());
						   a.setMember_linkman_name(m.get("contact").toString());
						   a.setMember_linkman_phone(m.get("user_phone").toString());
						   a.setMember_id(m.get("user_id").toString());
						apList.add(a);
					}
				//增加参会人员
				   activityPersonService.insertActivityPerson(apList);
			}
			   
				  //生成二维码
				   try {
					ZxingUtil.encode("activity_id:"+jsonObject.get( "activity_id" ).toString(),"F:\\test/test.jpg",System.getProperty("user.dir")+"/target/classes/resources/images",true,jsonObject.get( "activity_name" ).toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        return "1";
	        
	    }
	   
	   
	   @ApiOperation(value = "删除活动", notes = "删除活动")
	   @RequestMapping( value = "deleteActivity", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult deleteActivity(
	            @RequestParam( value = "activity_id", required = true ) String activity_id) {
		   
		   ActivityInfo ai=new ActivityInfo();
		   ai.setActivity_id(activity_id);
		   ai.setIs_delete("1");
	        return new JsonResult(activityInfoService.updateActivityInfo(ai));
	    }
	   
	   
	   @ApiOperation(value = "进入查看签到二维码页面", notes = "进入查看签到二维码页面")
		@RequestMapping(value = "/toseeOrCodePage", method = RequestMethod.GET)
		public String toseeOrCodePage(
				@RequestParam( value = "activity_id", required = true ) String activity_id,
				Model model) {
		   ActivityInfo ai=new ActivityInfo();
		   ai.setActivity_id(activity_id);
		   ActivityInfo a= activityInfoService.queryActivityInfo(ai);
		   model.addAttribute("a", a);
			return "/web/html/activity/orCode";
		}
	   
	   
	   @ApiOperation(value = "进入查看签到页面", notes = "进入查看签到页面")
		@RequestMapping(value = "/toseeSignPage", method = RequestMethod.GET)
		public String toseeSignPage(
				@RequestParam( value = "activity_id", required = true ) String activity_id,
				Model model) {
		   ActivityInfo ai=new ActivityInfo();
		   ai.setActivity_id(activity_id);
		   ActivityInfo a= activityInfoService.queryActivityInfo(ai);
	    	Integer num=activitySignFlowService.queryPersonNum(activity_id);
		   model.addAttribute("a", a);
		   model.addAttribute("num", num);
			return "/web/html/activity/seeSign";
		}
	   
	   
	   @ApiOperation(value = "查询活动签到人员列表数据", notes = "查询活动签到人员列表数据")
	    @RequestMapping(value = "getActivitySignPersonList", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String, Object> getActivitySignPersonList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "activity_id", required = false ) String activity_id) {
	        Map<String, Object> map = new HashMap<>();
	        
	        List<ActivitySignFlow> asfList=activitySignFlowService.getActivitySignLists(activity_id, pageIndex, pageSize);
	        for (ActivitySignFlow a : asfList) {
				System.out.println("测试："+a.getSign_time());
			}
	        Integer asfTotal=activitySignFlowService.queryActivitySignTotal(activity_id, pageIndex, pageSize);
	            map.put( "data", asfList );
	            map.put( "total", asfTotal);
	            return map;
	    }
	   
	   
	   @ApiOperation(value = "进入查看报名页面", notes = "进入查看报名页面")
		@RequestMapping(value = "/toseeEntryPage", method = RequestMethod.GET)
		public String toseeEntryPage(
				@RequestParam( value = "activity_id", required = true ) String activity_id,
				@RequestParam( value = "activity_name", required = true ) String activity_name,Model model) {
		   Integer total=activityEntryFlowService.queryActivityEntryFlowTotal(activity_id, null, null);
		   model.addAttribute("total", total);
		   model.addAttribute("activity_id", activity_id);
		   model.addAttribute("activity_name", activity_name);
			return "/web/html/activity/seeReceipt";
		}
	   
	   
	   @ApiOperation(value = "查询查看报名列表数据", notes = "查询查看报名列表数据")
	    @RequestMapping(value = "getActivityEntryList", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String, Object> getActivityEntryList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "activity_id", required = false ) String activity_id) {
	        Map<String, Object> map = new HashMap<>();
	        	List<ActivityEntryFlow> ActivityEntryFlowList=activityEntryFlowService.getActivityEntryFlowLists(activity_id, pageIndex, pageSize);
		        Integer ActivityEntryFlowTotal=activityEntryFlowService.queryActivityEntryFlowTotal(activity_id, pageIndex, pageSize);
		            map.put( "data", ActivityEntryFlowList );
		            map.put( "total", ActivityEntryFlowTotal );
	        
	            return map;
	    }
	   
	   
	   /**
	     * 导出活动报名人员信息
	     * @return
	     */
	    @RequestMapping( value = "exportActivityEntryResult", method = RequestMethod.POST )
	    @ResponseBody
	    public Map<String, Object> exportActivityEntryResult(HttpServletResponse response, String activity_id) {
	        Map<String, Object> map = new HashMap<>();
	        String exportResult = activityEntryFlowService.exportActivityEntryFlowResult(response, activity_id);
	        if(!"".equals( exportResult )){
	            map.put( "code", 200 );
	            map.put( "data", exportResult );
	        }
	        else {
	            map.put( "code", 401 );
	            map.put( "msg", "导出有误，请重新尝试" );
	        }
	        return map;
	    }
}
