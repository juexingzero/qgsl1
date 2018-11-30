


package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.app.linkman.TelephoneRecordResult;
import com.manhui.gsl.jbqgsl.dao.app.AppInstitutionMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppTelephoneRecordMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppUserMapper;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.topicevaluate.TelephoneRecord;
/**
* @Title: AppTelephoneRecordServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(AppTelephoneRecordService实现层)
* @author LiuBin
* @date 2018年9月27日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppTelephoneRecordServiceImpl implements IAppTelephoneRecordService {
	@Autowired
	private AppTelephoneRecordMapper appTelephoneRecordMapper;
	@Autowired
	private AppUserMapper appUserMapper;
	
	@Override
	public void save(TelephoneRecord tr) {
		
		//查找用户表以及机构表  补全呼叫方信息
		AppUser user = appUserMapper.queryUserAndInstitutionInfo(tr.getCaller_id());
		tr.setCaller_name(user.getUser_name());
		tr.setCaller_dept_id(user.getInstitution_id());
		tr.setCaller_phone(user.getUser_phone());
		if(user.getInstitution_name() !=null) {
			tr.setCaller_dept_name(user.getInstitution_name());
		}
		tr.setLink_time(DateUtil.getTime());
		//根据呼叫人id以及被呼叫人id查询是第一次还是第n次
		TelephoneRecord telephoneRecord = appTelephoneRecordMapper.query(tr);
		if(telephoneRecord==null) {
			 tr.setId(UUIDUtil.getUUID());
			 tr.setNum(1);
			 appTelephoneRecordMapper.save(tr);
		}else {
			int num = telephoneRecord.getNum();
			telephoneRecord.setNum(++num);
			telephoneRecord.setLink_time(DateUtil.getTime());
			appTelephoneRecordMapper.update(telephoneRecord);
		}
	}

	@Override
	public List<TelephoneRecordResult> queryLastTimeLinkManList(TelephoneRecord tr) {
		return appTelephoneRecordMapper.queryLastTimeLinkManList(tr);
	}


}
