package com.manhui.gsl.jbqgsl.controller.web.meetingmanager;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.annotation.Resources;
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
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.util.JsonResult;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.common.util.ZxingUtil;
import com.manhui.gsl.jbqgsl.common.util.positioningUtil;
import com.manhui.gsl.jbqgsl.controller.BaseController;
import com.manhui.gsl.jbqgsl.controller.app.commerce.Commerce;
import com.manhui.gsl.jbqgsl.model.EvaluateStandard;
import com.manhui.gsl.jbqgsl.model.MeetingManager;
import com.manhui.gsl.jbqgsl.model.MeetingPerson;
import com.manhui.gsl.jbqgsl.model.MeetingReceipt;
import com.manhui.gsl.jbqgsl.model.MeetingSignFlow;
import com.manhui.gsl.jbqgsl.model.MeetingVote;
import com.manhui.gsl.jbqgsl.model.MeetingVoteOptions;
import com.manhui.gsl.jbqgsl.service.app.commerce.IAppCommerceService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.AppMemberJoinManagerService;
import com.manhui.gsl.jbqgsl.service.app.membermanager.ParamModel;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.AttendeesResultService;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingManagerService;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingPersonService;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingReceiptService;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingSignFlowService;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingVoteOptionsService;
import com.manhui.gsl.jbqgsl.service.web.meetingmanager.MeetingVoteService;
import com.mysql.jdbc.Connection;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @类名称 MeetingManagerController.java
 * @类描述
 * @作者 WangSheng
 * @创建时间 2018年10月17日 
 * @版本 1.00
 *
 */

@Api(tags = "后台-会务管理")
@Controller
@RequestMapping(MeetingManagerController.ROOT_URL)
public class MeetingManagerController extends BaseController {
	
	public static final String ROOT_URL = PARENT_URL_WEB + "/meetingManager";
	
	@Resource
	private MeetingManagerService meetingManagerService;
	
	@Resource
	private MeetingReceiptService meetingReceiptService;
	
	@Resource
	private AttendeesResultService attendeesResultService;
	
	@Resource
	private MeetingPersonService meetingPersonService;
	
	@Resource
	private MeetingVoteService meetingVoteService;
	
	@Resource
	private MeetingVoteOptionsService meetingVoteOptionsService;
	
	@Resource
	private MeetingSignFlowService meetingSignFlowService;
	
	@Resource
	private AppMemberJoinManagerService appMemberJoinManagerService;
	
	@Resource
	private IAppCommerceService iAppCommerceService;
	
	
	@ApiOperation(value = "进入会务管理页面", notes = "进入会务管理页面")
	@RequestMapping(value = "/toMeetingManagerPage", method = RequestMethod.GET)
	public String toMeetingManagerPage(Model model,
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
		
		MeetingManager mm=new MeetingManager();
		mm.setMeeting_starttime(starttime);
		mm.setMeeting_endtime(endtime);
		Integer mmTotal=meetingManagerService.querymmTotal(mm);
		MeetingSignFlow msf=new MeetingSignFlow();
		msf.setSign_phone(starttime);
		msf.setSing_time(endtime);
		Integer pTotal=meetingSignFlowService.queryCompanyTotal(msf);
		MeetingSignFlow msft=new MeetingSignFlow();
		msft.setMeeting_id("sdfs");
		msft.setSign_phone(starttime);
		msft.setSing_time(endtime);
		Integer cTotal=meetingSignFlowService.queryCompanyTotal(msft);
		
		model.addAttribute("mmTotal", mmTotal);
		model.addAttribute("pTotal", pTotal);
		model.addAttribute("cTotal", cTotal);
		return "/web/html/meetingManager/meetingManagerList";
	}
	
	
	   @ApiOperation(value = "查询列表数据", notes = "查询列表数据")
	    @RequestMapping(value = "getMeetingManagerList", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String, Object>institutionList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "meeting_theme", required = false ) String meeting_theme,
	    		 @RequestParam( value = "meeting_state", required = false )String  meeting_state ) {
		   
	        Map<String, Object> map = new HashMap<>();
	        List<String> meeting_stateList=new ArrayList<>();
	         if(meeting_state.equals("1")){
	        	meeting_stateList.add("0");
	        }else if(meeting_state.equals("2")){
	        	meeting_stateList.add("1");
	        }else if(meeting_state.equals("3")){
	        	meeting_stateList.add("2");
	        }else if(meeting_state.equals("4")){
	        	meeting_stateList.add("3");
	        	meeting_stateList.add("4");
	        }
	        
	         if(meeting_theme!=null){
	        	 meeting_theme="%"+meeting_theme+"%";
	         }
	         
	        List<MeetingManager> MeetingManagerList = meetingManagerService.getMeetingManagerList(meeting_theme, meeting_stateList,pageIndex, pageSize);
	      
	        for (MeetingManager meetingManager : MeetingManagerList) {
				if(meetingManager.getMeeting_state().equals("0")){
					meetingManager.setMeeting_state("未发布");
				}else if(meetingManager.getMeeting_state().equals("1")){
					meetingManager.setMeeting_state("待开会");
				}else if(meetingManager.getMeeting_state().equals("2")){
					meetingManager.setMeeting_state("会议中");
				}else if(meetingManager.getMeeting_state().equals("3")){
					meetingManager.setMeeting_state("已结束");
				}else if(meetingManager.getMeeting_state().equals("4")){
					meetingManager.setMeeting_state("已撤销");
				}
			}

	        Integer MeetingManagerTotal = meetingManagerService.queryMeetingManagerTotal(meeting_theme, meeting_stateList,pageIndex, pageSize);
	        if ( !MeetingManagerList.isEmpty() ) { 
	            map.put( "data", MeetingManagerList );
	            map.put( "total", MeetingManagerTotal );
	        }
	        else {
	            map.put( "data", "" );
	        }
	        return map;
	    }
	   
	   
//	   @ApiOperation(value = "进入发起会议页面", notes = "进入发起会议页面")
//		@RequestMapping(value = "/toaddMeeting", method = RequestMethod.GET)
//		public String toaddMeeting() {
//			return "/web/html/meetingManager/meetingAdd";
//		}
	   
	   @ApiOperation(value = "进入查看会议详情页面", notes = "进入查看会议详情页面")
		@RequestMapping(value = "/toseeMeetingManagerPage", method = RequestMethod.GET)
		public String toseeMeetingManagerPage(
				@RequestParam( value = "meeting_id", required = true ) String meeting_id,
				@RequestParam( value = "state", required = true ) String state,
				Model model) {
	    	
		   model.addAttribute("meeting_id", meeting_id);
		   model.addAttribute("state", state);
			return "/web/html/meetingManager/meetingDetail";
		}
	   
	   
	   @ApiOperation(value = "删除会议", notes = "删除会议")
	   @RequestMapping( value = "deleteMeetingManager", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult deleteMeetingManager(
	            @RequestParam( value = "meeting_id", required = true ) String meeting_id) {
		   MeetingManager mm=new MeetingManager();
		   mm.setMeeting_id(meeting_id);
		   mm.setIs_delete("1");
	        return new JsonResult(meetingManagerService.updateMeetingManager(mm));
	    }
	   
//	   @ApiOperation(value = "添加会议", notes = "添加会议")
//	   @RequestMapping( value = "addMeetingManager", method = RequestMethod.POST )
//	    @ResponseBody
//	    public JsonResult addMeetingManager(MeetingManager mm) {
//		  
//	        return new JsonResult(meetingManagerService.insertMeetingManager(mm));
//	    }
//	   
	   @ApiOperation(value = "发布/撤销会议", notes = "发布/撤销会议")
	   @RequestMapping( value = "releaseMeetingManager", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult releaseMeetingManager(
	    		@RequestParam( value = "meeting_id", required = true ) String meeting_id,
	    		@RequestParam( value = "meeting_state", required = true ) String meeting_state) {
		   MeetingManager mm=new MeetingManager();
		   mm.setMeeting_id(meeting_id);
		   mm.setMeeting_state(meeting_state);
	        return new JsonResult(meetingManagerService.updateMeetingManager(mm));
	    }
	   
	   
	   @ApiOperation( value = "进入人员选择页面", notes = "进入人员选择页面" )
	    @RequestMapping( value = "/toPersonSelectPage", method = RequestMethod.GET )
	    public String toPersonSelectPage() {
	        return "/web/html/meetingManager/personSelect";
	    }
	   
	   @ApiOperation(value = "查询所有商会", notes = "查询所有商会")
	   @RequestMapping(value = "getshList" , method = RequestMethod.POST)
	   @ResponseBody
	    public Map<String, Object> getshList() {
	        Map<String, Object> map = new HashMap<>();
	        List<Commerce> commerce = iAppCommerceService.queryCommerceList();
	        Commerce c=new Commerce();
	        c.setID("0");
	        c.setSHMC("全部商会");
	        commerce.add(c);
	        Collections.reverse(commerce);
	        if ( !commerce.isEmpty() ) {
	            map.put( "data", commerce );
	        }
	        else {
	            map.put( "data", "" );
	        }
	        return map;
	    }
	   
	   
	   @ApiOperation( value = "获取总商会人员列表", notes = "获取总商会人员列表" )
	    @RequestMapping( value = "getAttendeesResultList", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult getAttendeesResultList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "position", required = false ) String position,
	    		 @RequestParam( value = "headChamberId", required = false )String  headChamberId,
	    		 @RequestParam( value = "joinObjId", required = false )String  joinObjId,
	    		 @RequestParam( value = "chamberType", required = false )String  chamberType,
	    		 @RequestParam( value = "contact", required = false )String  contact) {
	        Map<String, Object> map = new HashMap<>();
	        
	        ParamModel param=new ParamModel();
	        if(!position.equals("1")){
	        	param.setPosition(position);
		        param.setJoinObjId(joinObjId);
		        param.setContact(contact);
	        }
	        
	        PageInfo<Map<String,Object>> mjm=appMemberJoinManagerService.queryMemberStaffList(param);
	        List<Map<String,Object>> sdf=mjm.getList();
	        if(sdf!=null){
	        	List<AttendeesResult> arList=new ArrayList<AttendeesResult>();
		        for (Map<String, Object> m : sdf) {
		        	AttendeesResult a=new AttendeesResult();
					a.setCreateTime(m.get("createTime").toString());
					a.setJoinObjId(m.get("joinObjId").toString());
					a.setJoinObjName(m.get("joinObjName").toString());
					a.setContact(m.get("contact").toString());
					a.setPosition(m.get("position").toString());
					a.setUser_id(m.get("user_id").toString());
					a.setUser_name(m.get("user_name").toString());
					a.setUser_phone(m.get("user_phone").toString());
					arList.add(a);
				}
		        	map.put( "arList", arList );
		        	map.put( "len", sdf.size() );
	        }else{
	        	map.put( "arList", "" );
	        }
	        
	        return new JsonResult(map);
	    }
	   
	   
	   @ApiOperation( value = "获取全部商会人员列表", notes = "获取全部商会人员列表" )
	    @RequestMapping( value = "getAllAttendeesResultList", method = RequestMethod.POST )
	    @ResponseBody
	    public Map<String, Object> getAllAttendeesResultList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "position", required = false ) String position,
	    		 @RequestParam( value = "user_phone", required = false )String  user_phone,
	    		 @RequestParam( value = "joinObjId", required = false )String  joinObjId,
	    		 @RequestParam( value = "chamberType", required = false )String  chamberType,
	    		 @RequestParam( value = "contact", required = false )String  contact) {
	        Map<String, Object> map = new HashMap<>();
	        ParamModel param=new ParamModel();
	        if(joinObjId!=null){
	        if(joinObjId.equals("0")){
	        	joinObjId="";
	        }
	        }
	        param.setJoinObjId(joinObjId);
	        param.setPosition(position);
	        param.setContact(contact);
	        param.setPageNo(pageIndex+1);
	        param.setPageSize(pageSize);
	        param.setUser_phone(user_phone);
	        PageInfo<Map<String,Object>> mjm=appMemberJoinManagerService.queryMemberStaffList(param);
	        List<Map<String,Object>> sdf=mjm.getList();
	        if(sdf!=null){
	        	 List<AttendeesResult> arList=new ArrayList<AttendeesResult>();
	 	        for (Map<String, Object> m : sdf) {
	 	        	AttendeesResult a=new AttendeesResult();
	 				a.setCreateTime(m.get("createTime").toString());
	 				a.setJoinObjId(m.get("joinObjId").toString());
	 				a.setJoinObjName(m.get("joinObjName").toString());
	 				a.setContact(m.get("contact").toString());
	 				//a.setMemberType(m.get("memberType").toString());
	 				a.setPosition(m.get("position").toString());
	 				a.setUser_id(m.get("user_id").toString());
	 				a.setUser_name(m.get("user_name").toString());
	 				a.setUser_phone(m.get("user_phone").toString());
	 				arList.add(a);
	 			}
	 	        	map.put( "data", arList );
	 	        	map.put( "total", mjm.getTotal());
	        }else{
	        	map.put( "data", "" );
	        }
	       
	        return map;
	    }
	   
	   
	   
	   @ApiOperation(value = "进入查看回执页面", notes = "进入查看回执页面")
		@RequestMapping(value = "/toseeReceiptPage", method = RequestMethod.GET)
		public String toseeReceiptPage(
				@RequestParam( value = "meeting_id", required = true ) String meeting_id,
				@RequestParam( value = "meeting_theme", required = true ) String meeting_theme,Model model) {
		   Integer total=meetingReceiptService.queryMeetingReceiptTotal(meeting_id,"2", null, null);
		   model.addAttribute("total", total);
		   model.addAttribute("meeting_id", meeting_id);
		   model.addAttribute("meeting_theme", meeting_theme);
			return "/web/html/meetingManager/seeReceipt";
		}
		
	   
	   
	   @ApiOperation(value = "查询参会回执列表数据", notes = "查询参会回执列表数据")
	    @RequestMapping(value = "getMeetingReceiptList", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String, Object> getMeetingReceiptList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "meeting_id", required = false ) String meeting_id,
	    		 @RequestParam( value = "state", required = false ) String state) {
	        Map<String, Object> map = new HashMap<>();
	        if(state.equals("1")){
	        	List<MeetingReceipt> MeetingReceiptList=meetingReceiptService.getMeetingReceiptList(meeting_id,"2", pageIndex, pageSize);
		        Integer meetingReceiptTotal=meetingReceiptService.queryMeetingReceiptTotal(meeting_id,"2", pageIndex, pageSize);
		            map.put( "data", MeetingReceiptList );
		            map.put( "total", meetingReceiptTotal );	
	        }else{
	        	List<MeetingReceipt> MeetingReceiptList=meetingReceiptService.getMeetingReceiptList(meeting_id,"", pageIndex, pageSize);
		        Integer meetingReceiptTotal=meetingReceiptService.queryMeetingReceiptTotal(meeting_id,"", pageIndex, pageSize);
		            map.put( "data", MeetingReceiptList );
		            map.put( "total", meetingReceiptTotal );
	        }
	        
	            return map;
	    }
	   
	   
	   @ApiOperation(value = "定位", notes = "定位")
	   @RequestMapping( value = "positioning", method = RequestMethod.POST )
	   @ResponseBody
	   public JsonResult positioning(
			   @RequestParam( value = "address", required = false ) String address) {
	        Object[] o;
	        positioningUtil p=new positioningUtil();
	        try {
				o = p.getLatAndLngByBaidu( address );
		 	        String ll=o[0]+","+o[1];
		 	       return new JsonResult(ll);
			} catch (IOException e) {
				e.printStackTrace();
			}
				return new JsonResult("default");
	   }
	   
	   
	    @ApiOperation( value = "添加会议", notes = "添加会议" )
	    @RequestMapping( value = "saveMeetingManager", method = RequestMethod.POST )
	    @ResponseBody
	    public String saveMeetingManager( HttpServletRequest request, String json,String passive_json,String vote_options ) throws ParseException {
	        JSONObject jsonObject = JSON.parseObject( json );
	        Integer repeat=meetingManagerService.queryMeetingManagerTotal(jsonObject.get( "meeting_theme" ).toString(), null, 0, 10);
	        if(repeat>0){
	        	 return "0";
	        }
	        MeetingManager mm = new MeetingManager();
	        MeetingVote mv=new MeetingVote();
	        String str = jsonObject.get( "meeting_starttime" ).toString().substring(0, jsonObject.get( "meeting_starttime" ).toString().length() - 3);
	        String st=jsonObject.get( "meeting_endtime" ).toString().substring(0, jsonObject.get( "meeting_endtime" ).toString().length() - 3);
	        //回执时间
	        String hzsj1=jsonObject.get( "receipt_time" ).toString();
	        if(hzsj1!=null && !hzsj1.equals("")){
	        	String hzsj2 = jsonObject.get( "receipt_time" ).toString().substring(0, jsonObject.get( "receipt_time" ).toString().length() - 3);
	        	 mm.setReceipt_time(hzsj2.replace("T", " "));
	        }else{
	        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        long dif =  sdf.parse(str.replace("T", " ")).getTime()-86400*1000;//减一天
		        Date date=new Date(); 
		        date.setTime(dif);
		        mm.setReceipt_time(sdf.format(date));
	        }
	        String uuid=UUIDUtil.getUUID();
	        mm.setMeeting_id(uuid);
	        mm.setMeeting_theme(jsonObject.get( "meeting_theme" ).toString());
	        mm.setMeeting_starttime(str.replace("T", " "));
	        mm.setMeeting_endtime(st.replace("T", " "));
	        mm.setMeeting_address(jsonObject.get( "meeting_address" ).toString());
	        mm.setMeeting_navigation(jsonObject.get( "meeting_navigation" ).toString());
	        mm.setMeeting_ll(jsonObject.get( "meeting_ll" ).toString());
	        String wjmc=jsonObject.get( "meeting_data" ).toString();
	        wjmc=wjmc.replace("[", "");
	        wjmc=wjmc.replace("]", "");
	        wjmc=wjmc.replace("\"", "");
	        mm.setMeeting_data(wjmc);
	        mm.setIs_vote(jsonObject.get( "is_vote" ).toString());
	        mm.setMeeting_content(jsonObject.get( "meeting_content" ).toString());
	        mm.setOr_code(jsonObject.get( "meeting_theme" ).toString()+".jpg");
	        mm.setAllow_select(jsonObject.get( "allow_select" ).toString());
	        //增加会议
	        Integer bools = meetingManagerService.insertMeetingManager(mm);
	        JSONArray parseArray = JSON.parseArray(passive_json);
			   List<AttendeesResult> list = parseArray.toJavaList(AttendeesResult.class);
			   List<MeetingPerson> mpList=new ArrayList<>();
			   for (AttendeesResult attendeesResult : list) {
				   MeetingPerson m=new MeetingPerson();
				   m.setMeeting_id(uuid);
				   m.setGroup_name(attendeesResult.getUser_name());
				   m.setOffice(attendeesResult.getPosition());
				   m.setSh_name(attendeesResult.getJoinObjName());
				   m.setPerson_name(attendeesResult.getContact());
				   m.setPhone(attendeesResult.getUser_phone());
				   m.setMember_id(attendeesResult.getUser_id());
				   mpList.add(m);
			}
//			   
//			   //增加参会人员
			   meetingPersonService.insertMeetingPerson(mpList);
			   String voteId=UUIDUtil.getUUID();
			   mv.setMeeting_id(uuid);
			   mv.setVote_id(voteId);
			   mv.setMeeting_name(jsonObject.get( "meeting_theme" ).toString());
			   mv.setVote_starttime(str.replace("T", " "));
			   mv.setVote_endtime(st.replace("T", " "));
			   mv.setAllow_item(jsonObject.get( "allow_select" ).toString());
			   mv.setMeetings(jsonObject.get( "meetings" ).toString());
			
			   List<MeetingVoteOptions> mvoList=new ArrayList<>();
			  
				 JSONArray j = JSON.parseArray(vote_options);
				 for (int i = 0; i < j.size(); i++) {
					 MeetingVoteOptions mvo=new MeetingVoteOptions();
					 mvo.setOptions_id(UUIDUtil.getUUID());
					 mvo.setVote_id(voteId);
					 mvo.setSorting(String.valueOf(i));
					 mvo.setOptions(j.get(i).toString());
					 mvoList.add(mvo);
					}
				 
				   if(jsonObject.get( "is_vote" ).toString().equals("0")){
				 //增加投票主体
				   meetingVoteService.insertMeetingVote(mv);
				 
				 //增加投票选项信息
				  meetingVoteOptionsService.insertMeetingVoteOptions(mvoList);
				   }
				  //生成二维码
				   try {
					ZxingUtil.encode("meeting_id:"+mm.getMeeting_id(),"F:\\test/test.jpg",System.getProperty("user.dir")+"/target/classes/resources/images",true,mm.getMeeting_theme());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        return "1";
	        
	    }
	   
	    
	    @ApiOperation( value = "修改会议", notes = "修改会议" )
	    @RequestMapping( value = "updateMeetingManager", method = RequestMethod.POST )
	    @ResponseBody
	    public String updateMeetingManager( HttpServletRequest request, String json,String passive_json,String vote_options ) throws ParseException {
	        JSONObject jsonObject = JSON.parseObject( json );
	        Integer repeat=meetingManagerService.queryMeetingManagerTotal(jsonObject.get( "meeting_theme" ).toString(), null, 0, 10);
	        if(repeat>0){
	        	 MeetingManager nm=new MeetingManager();
	 	    	nm.setMeeting_id(jsonObject.get( "meeting_id" ).toString());
	 	    	nm.setMeeting_theme(jsonObject.get( "meeting_theme" ).toString());
	 	    	MeetingManager man=meetingManagerService.queryMeetingManager(nm);
	 	        if(man==null){
	 	        	 return "0";
	 	        }
	        }
	       
	        MeetingManager mm = new MeetingManager();
	        MeetingVote mv=new MeetingVote();
	        String str = jsonObject.get( "meeting_starttime" ).toString().substring(0, jsonObject.get( "meeting_starttime" ).toString().length() - 3);
	        String st=jsonObject.get( "meeting_endtime" ).toString().substring(0, jsonObject.get( "meeting_endtime" ).toString().length() - 3);
	        //回执时间
	        String hzsj1=jsonObject.get( "receipt_time" ).toString();
	        if(hzsj1!=null && !hzsj1.equals("")){
	        	String hzsj2 = jsonObject.get( "receipt_time" ).toString().substring(0, jsonObject.get( "receipt_time" ).toString().length() - 3);
	        	 mm.setReceipt_time(hzsj2.replace("T", " "));
	        }else{
	        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        long dif =  sdf.parse(str.replace("T", " ")).getTime()-86400*1000;//减一天
		        Date date=new Date(); 
		        date.setTime(dif);
		        mm.setReceipt_time(sdf.format(date));
	        }
	        mm.setMeeting_id(jsonObject.get( "meeting_id" ).toString());
	        mm.setMeeting_theme(jsonObject.get( "meeting_theme" ).toString());
	        mm.setMeeting_starttime(str.replace("T", " "));
	        mm.setMeeting_endtime(st.replace("T", " "));
	        mm.setMeeting_address(jsonObject.get( "meeting_address" ).toString());
	        mm.setMeeting_navigation(jsonObject.get( "meeting_navigation" ).toString());
	        mm.setMeeting_ll(jsonObject.get( "meeting_ll" ).toString());
	        String wjmc=jsonObject.get( "meeting_data" ).toString();
	        wjmc=wjmc.replace("[", "");
	        wjmc=wjmc.replace("]", "");
	        wjmc=wjmc.replace("\"", "");
	        mm.setMeeting_data(wjmc);
	        mm.setIs_vote(jsonObject.get( "is_vote" ).toString());
	        mm.setMeeting_content(jsonObject.get( "meeting_content" ).toString());
	        mm.setOr_code(jsonObject.get( "meeting_theme" ).toString()+".jpg");
	        mm.setAllow_select(jsonObject.get( "allow_select" ).toString());
	        //修改会议
	        Integer bools = meetingManagerService.updateMeetingManager(mm);
	        JSONArray parseArray = JSON.parseArray(passive_json);
			   List<AttendeesResult> list = parseArray.toJavaList(AttendeesResult.class);
			   List<MeetingPerson> mpList=new ArrayList<>();
			   for (AttendeesResult attendeesResult : list) {
				   MeetingPerson m=new MeetingPerson();
				   m.setMeeting_id(jsonObject.get( "meeting_id" ).toString());
				   m.setGroup_name(attendeesResult.getUser_name());
				   m.setOffice(attendeesResult.getPosition());
				   m.setSh_name(attendeesResult.getJoinObjName());
				   m.setPerson_name(attendeesResult.getContact());
				   m.setPhone(attendeesResult.getUser_phone());
				   m.setMember_id(attendeesResult.getUser_id());
				   mpList.add(m);
			}
			   
			   //删除原有参会人员
			   MeetingPerson mp=new MeetingPerson();
			   mp.setMeeting_id(jsonObject.get( "meeting_id" ).toString());
			   meetingPersonService.deleteMeetingPerson(mp);
			   
			   //增加参会人员
			   meetingPersonService.insertMeetingPerson(mpList);

			   
			   mv.setMeeting_id(jsonObject.get( "meeting_id" ).toString());
			   mv.setVote_id(jsonObject.get( "vote_id" ).toString());
			   mv.setMeeting_name(jsonObject.get( "meeting_theme" ).toString());
			   mv.setVote_starttime(str.replace("T", " "));
			   mv.setVote_endtime(st.replace("T", " "));
			   mv.setAllow_item(jsonObject.get( "allow_select" ).toString());
			   if(jsonObject.get( "is_vote" ).toString().equals("1")){
				   mv.setMeetings("");
			   }else{
			   mv.setMeetings(jsonObject.get( "meetings" ).toString());
			   }
			   
			   
			  
			   List<MeetingVoteOptions> mvoList=new ArrayList<>();
			  
				 JSONArray j = JSON.parseArray(vote_options);
				 for (int i = 0; i < j.size(); i++) {
					 MeetingVoteOptions mvo=new MeetingVoteOptions();
					 mvo.setVote_id(jsonObject.get( "vote_id" ).toString());
					 mvo.setOptions_id(UUIDUtil.getUUID());
					 mvo.setSorting(String.valueOf(i));
					 mvo.setOptions(j.get(i).toString());
					 mvoList.add(mvo);
					}
				 
				 //删除原有投票选项信息
				 meetingVoteOptionsService.deleteMeetingVoteOptions(jsonObject.get( "vote_id" ).toString());
				 
				 if(jsonObject.get( "is_vote" ).toString().equals("0")){
				 
				 //增加投票信息
				 meetingVoteOptionsService.insertMeetingVoteOptions(mvoList);
				 }
				 MeetingVote m=meetingVoteService.queryMeetingVote(jsonObject.get( "meeting_id" ).toString());
				 if(m!=null){
					//修改投票主体
					 meetingVoteService.updateMeetingVote(mv);
				 }else{
					//增加投票主体
					   meetingVoteService.insertMeetingVote(mv);
				 }
				 
				  //生成二维码
				   try {
					ZxingUtil.encode("meeting_id:"+mm.getMeeting_id(),"F:\\test/test.jpg",System.getProperty("user.dir")+"/target/classes/resources/images",true,mm.getMeeting_theme());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        return "1";
	        
	    }
	    
	    
	    @ApiOperation(value = "查看会议详情数据", notes = "查看会议详情数据")
	    @RequestMapping(value = "MeetingManagerDetail", method = RequestMethod.POST)
	    @ResponseBody
	    public meetingResult MeetingManagerDetail( String meeting_id ) {
	    	meetingResult data=new meetingResult();
	    	MeetingManager nm=new MeetingManager();
	    	nm.setMeeting_id(meeting_id);
	    	MeetingManager mm = meetingManagerService.queryMeetingManager(nm);
	    	MeetingVote mv= meetingVoteService.queryMeetingVote(meeting_id);
	    	List<String> optionsList=new ArrayList<>();
	    	if(mv!=null){
	    		List<MeetingVoteOptions> options=meetingVoteOptionsService.queryMeetingVoteOptionsList(mv.getVote_id());
		    	for (MeetingVoteOptions m : options) {
					optionsList.add(m.getOptions());
				}
		    	data.setMeetings(mv.getMeetings());
		    	data.setVote_id(mv.getVote_id());
	    	}
	    	
	    	data.setMeeting_id(meeting_id);
	    	data.setMeeting_theme(mm.getMeeting_theme());
	    	data.setMeeting_address(mm.getMeeting_address());
	    	data.setMeeting_ll(mm.getMeeting_ll());
	    	data.setMeeting_navigation(mm.getMeeting_navigation());
	    	int num =this.countStr(mm.getMeeting_data(), ",");
	    	List<String> dataList=new ArrayList<>();
	    	if(num>0){
	    		String[] strarray = mm.getMeeting_data().split(",");
	    		for (String string : strarray) {
	    			dataList.add(string);
	    		}
	    	}else{
	    		dataList.add(mm.getMeeting_data());
	    	}
	    	data.setWjlength(String.valueOf(num+1));
	    	data.setDataList(dataList);
	    	data.setMeeting_content(mm.getMeeting_content());
	    	data.setAllow_select(mm.getAllow_select());
	    	data.setIs_vote(mm.getIs_vote());
	    	data.setMeeting_starttime(mm.getMeeting_starttime());
	    	data.setMeeting_endtime(mm.getMeeting_endtime());
	    	data.setReceipt_time(mm.getReceipt_time());
	    	data.setXxlength(String.valueOf(optionsList.size()));
	    	
	    	data.setOptionsList(optionsList);
	        return data;
	    }
	    
	    
	    @ApiOperation( value = "获取参会人员详情数据", notes = "获取参会人员情数据" )
	    @RequestMapping( value = "getMeetingPersonDetailData", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult getMeetingPersonDetailData(
	            @RequestParam(value = "meeting_id", required = true)String meeting_id ) {
	        List<MeetingPerson> mpList= meetingPersonService.queryMeetingPersonList(meeting_id);
	        List<AttendeesResult> arList=new ArrayList<>();
	        for (MeetingPerson m : mpList) {
	        	AttendeesResult ar=new AttendeesResult();
	        	ar.setUser_id(m.getMember_id());
	        	ar.setContact(m.getPerson_name());
	        	ar.setUser_name(m.getGroup_name());
	        	ar.setUser_phone(m.getPhone());
	        	ar.setJoinObjName(m.getSh_name());
	        	ar.setPosition(m.getOffice());
				arList.add(ar);
			}
	        Map<String, Object> resultMap=new HashMap<>();
	        resultMap.put("arList", arList);
	        return new JsonResult(resultMap);
	    }
		
	    
	    @ApiOperation(value = "进入查看签到二维码页面", notes = "进入查看签到二维码页面")
		@RequestMapping(value = "/toseeOrCodePage", method = RequestMethod.GET)
		public String toseeOrCodePage(
				@RequestParam( value = "meeting_id", required = true ) String meeting_id,
				Model model) {
	    	MeetingManager nm=new MeetingManager();
	    	nm.setMeeting_id(meeting_id);
	    	MeetingManager mm = meetingManagerService.queryMeetingManager( nm);
		   model.addAttribute("mm", mm);
			return "/web/html/meetingManager/orCode";
		}
	    
	    
	    @ApiOperation(value = "进入查看签到页面", notes = "进入查看签到页面")
		@RequestMapping(value = "/toseeSignPage", method = RequestMethod.GET)
		public String toseeSignPage(
				@RequestParam( value = "meeting_id", required = true ) String meeting_id,
				Model model) {
	    	MeetingManager nm=new MeetingManager();
	    	nm.setMeeting_id(meeting_id);
	    	MeetingManager mm = meetingManagerService.queryMeetingManager( nm);
	    	Integer num=meetingSignFlowService.querypersonNum(meeting_id);
		   model.addAttribute("mm", mm);
		   model.addAttribute("num", num);
			return "/web/html/meetingManager/seeSign";
		}
	    
	    
	    @ApiOperation(value = "查询签到人员列表数据", notes = "查询签到人员列表数据")
	    @RequestMapping(value = "getMeetingSignPersonList", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String, Object> getMeetingSignPersonList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "meeting_id", required = false ) String meeting_id) {
	        Map<String, Object> map = new HashMap<>();
	        
	        List<MeetingSignFlow> msfList=meetingSignFlowService.getMeetingSignLists(meeting_id, pageIndex, pageSize);
	        for (MeetingSignFlow meetingSignFlow : msfList) {
				meetingSignFlow.setCompany_title(meetingSignFlow.getSign_name()+" "+meetingSignFlow.getCompany_name()+" "+meetingSignFlow.getSing_time());
			}
	        Integer msfTotal=meetingSignFlowService.queryMeetingSignTotal(meeting_id, pageIndex, pageSize);
	            map.put( "data", msfList );
	            map.put( "total", msfTotal);
	            return map;
	    }
	    
	    
	    
	    @ApiOperation(value = "进入查看投票页面", notes = "进入查看投票页面")
		@RequestMapping(value = "/toseeVotePage", method = RequestMethod.GET)
		public String toseeVotePage(
				@RequestParam( value = "meeting_id", required = true ) String meeting_id,
				Model model) {
	    	MeetingVote mv=meetingVoteService.queryMeetingVote(meeting_id);
		   model.addAttribute("mv", mv);
			return "/web/html/meetingManager/seeVote";
		}
	    
	    
	    @ApiOperation(value = "查看投票详情数据", notes = "查看投票详情数据")
	    @RequestMapping(value = "VoteDetail", method = RequestMethod.POST)
	    @ResponseBody
	    public voteResult VoteDetail( String meeting_id ) {
	    	voteResult data=new voteResult();
	    	List<String> optionsList=new ArrayList<>();
	    	List<Integer> optionsnumList=new ArrayList<>();
	    	List<String> optionsProportionList=new ArrayList<>();
	    	DecimalFormat df = new DecimalFormat("0.00");
	    	MeetingVote mv=meetingVoteService.queryMeetingVote(meeting_id);
	    	
	    	Integer personNum=meetingPersonService.queryMeetingPersonNum(meeting_id);
	    	List<MeetingVoteOptions> options=meetingVoteOptionsService.queryMeetingVoteOptionsList(mv.getVote_id());
	    	if(personNum!=0){
	    		for (MeetingVoteOptions m : options) {
					optionsList.add(m.getOptions());
					Integer num=meetingPersonService.querypersonNum("%"+m.getOptions()+"%");
					optionsnumList.add(num);
					
					double d= num/(double)personNum;
					String s = df.format(d);
					NumberFormat nt = NumberFormat.getPercentInstance();
					   //设置百分数精确度2即保留两位小数
					   nt.setMinimumFractionDigits(2);
					   //最后格式化并输出
					   optionsProportionList.add(nt.format(d));
				}
	    	}else{
	    		for (MeetingVoteOptions m : options) {
					optionsList.add(m.getOptions());
					Integer num=meetingPersonService.querypersonNum("%"+m.getOptions()+"%");
					optionsnumList.add(num);
					optionsProportionList.add("0.00%");
				}
	    	}
	    	
	    	
	    	data.setMeeting_id(meeting_id);
	    	data.setMeeting_theme(mv.getMeeting_name());
	    	data.setMeetings(mv.getMeetings());
	    	data.setOptionsList(optionsList);
	    	data.setVote_starttime(mv.getVote_starttime());
	    	data.setVote_endtime(mv.getVote_endtime());
	    	data.setVoteNum(optionsnumList);
	    	data.setVotePerson(personNum);
	    	data.setOptionsProportion(optionsProportionList);
	        return data;
	    }
	    
	    
	    @ApiOperation(value = "进入投票人数详情页面", notes = "进入投票人数详情页面")
		@RequestMapping(value = "/toVotePersonNumPage", method = RequestMethod.GET)
		public String toVotePersonNumPage(
				@RequestParam( value = "meeting_id", required = true ) String meeting_id,
				@RequestParam( value = "vote_options", required = true ) String vote_options,
				@RequestParam( value = "people", required = true ) String people,
				Model model) {	
	    	
		   model.addAttribute("meeting_id", meeting_id);
		   model.addAttribute("vote_options", vote_options);
		   model.addAttribute("people", people);
			return "/web/html/meetingManager/seeVoteDetail";
		}
	    
	    @ApiOperation(value = "查询投票人数详情列表数据", notes = "查询投票人数详情列表数据")
	    @RequestMapping(value = "getVotePersonNumList", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String, Object> getVotePersonNumList(HttpServletRequest request,Integer pageIndex, Integer pageSize,
	    		 @RequestParam( value = "meeting_id", required = false ) String meeting_id,
	    		 @RequestParam( value = "vote_options", required = false ) String vote_options) {
	        Map<String, Object> map = new HashMap<>();
	        List<MeetingPerson> mpList=meetingPersonService.queryvotePerson(meeting_id,vote_options,pageIndex,pageSize);
	        for (MeetingPerson m : mpList) {
				System.out.println("时间："+m.getUpdate_time());
			}
	        Integer mpTotal=meetingPersonService.queryvotePersonNum(meeting_id, vote_options,pageIndex,pageSize);
	            map.put( "data", mpList );
	            map.put( "total", mpTotal );
	            return map;
	    }
	    
	    
	    @ApiOperation(value = "进入会议纪要页面", notes = "进入会议纪要页面")
		@RequestMapping(value = "/toseeMeetingMinutesPage", method = RequestMethod.GET)
		public String toseeMeetingMinutesPage(
				@RequestParam( value = "meeting_id", required = true ) String meeting_id,
				Model model) {	
	    	MeetingManager nm=new MeetingManager();
	    	nm.setMeeting_id(meeting_id);
	    	MeetingManager mm=meetingManagerService.queryMeetingManager(nm);
		   model.addAttribute("mm", mm);
		   return "/web/html/meetingManager/meetingMinutes";
		}
	    
	    
	    @ApiOperation( value = "定时任务：检测并更新会议状态", notes = "定时任务：检测并更新会议状态" )
	    @ApiImplicitParams( {
	            @ApiImplicitParam( paramType = "query", required = false, name = "currentDate", value = "当前时间（格式：yyyy-MM-dd HH:mm）", dataType = "字符串" )
	    } )
	    @RequestMapping( value = "checkMeetingState", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult checkMeetingState() {
	    	SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm");
	    	MeetingManager mm=new MeetingManager();
	    	List<MeetingManager> mmList=meetingManagerService.querymeetingManagerList(mm);
	    	for (MeetingManager m : mmList) {
	    		if(m.getMeeting_starttime().equals(sdf.format(new Date()))){
	    			MeetingManager me=new MeetingManager();
	    			me.setMeeting_state("2");
	    			me.setMeeting_id(m.getMeeting_id());
	    			meetingManagerService.updateMeetingManager(me);
	    		}
	    		if(m.getMeeting_endtime().equals(sdf.format(new Date()))){
	    			MeetingManager mee=new MeetingManager();
	    			mee.setMeeting_state("3");
	    			mee.setMeeting_id(m.getMeeting_id());
	    			meetingManagerService.updateMeetingManager(mee);
	    		}
				
			}
	        return new JsonResult(1);
	    }
	    
	    /**
	     * 导出主题评价结果
	     * @return
	     */
	    @RequestMapping( value = "exportTopicEvaluateResult", method = RequestMethod.POST )
	    @ResponseBody
	    public Map<String, Object> exportTopicEvaluateResult(HttpServletResponse response, String meeting_id) {
	    	System.out.println("会议id："+meeting_id);
	        Map<String, Object> map = new HashMap<>();
	        String exportResult = meetingReceiptService.exportTopicEvaluateResult(response, meeting_id);
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
	    
	    
	    
	    @ApiOperation( value = "修改文件纪要", notes = "修改文件纪要" )
	    @RequestMapping( value = "updateMinutes", method = RequestMethod.POST )
	    @ResponseBody
	    public JsonResult insertMinutes(
	    		@RequestParam( value = "meeting_id", required = true ) String meeting_id,
	    		@RequestParam( value = "meeting_minutes", required = true ) String meeting_minutes) {
	    	
	    	MeetingManager mm=new MeetingManager();
	    	mm.setMeeting_id(meeting_id);
	    	mm.setMeeting_minutes(meeting_minutes);
	    	meetingManagerService.updateMeetingManager(mm);
	    	
	        return new JsonResult(1);
	    }
	    
	    
		public int countStr(String str,String sToFind) {
	        int num = 0;
	        while (str.contains(sToFind)) {
	            str = str.substring(str.indexOf(sToFind) + sToFind.length());
	            num ++;
	        }
	        return num;
	    }
}
