package com.manhui.gsl.jbqgsl.service.web.meetingmanager;

import java.util.List;

import com.manhui.gsl.jbqgsl.controller.web.meetingmanager.AttendeesResult;

public interface AttendeesResultService {

	List<AttendeesResult> getAttendeesResultList(String company_name,String concats_title,String approve_organization_name,Integer pageIndex, Integer pageSize);
	
	Integer queryAttendeesResultTotal(String company_name,String concats_title,String approve_organization_name,Integer pageIndex, Integer pageSize);
}
