


package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.PageObject;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.app.AppUserSuggestMapper;
import com.manhui.gsl.jbqgsl.model.UserSuggest;

/**
* @Title: AppUserSuggestServiceImpl.java
* @Package com.manhui.gsl.jbqgsl.service.impl
* @Description: TODO(用户反馈service实现类)
* @author LiuBin
* @date 2018年8月22日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Service
public class AppUserSuggestServiceImpl implements IAppUserSuggestService {
	@Resource
	private AppUserSuggestMapper mapper;

    /**
     * 保存意见对象
     * @param model 意见对象
     * @return
     */
    @Override
    public int save(UserSuggest model) {
    	model.setId(UUIDUtil.getUUID());
    	model.setCreate_time(DateUtil.getTime());
    	model.setIs_answer( "0" );
    	return mapper.save(model);
    }

    /**
     * 读取意见列表
     * @param model 读取条件
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> list(UserSuggest model) {
        return mapper.list(model);
    }
    /**
     * 根据userSuggest_id获取到具体的意见详情
     */
	@Override
	public UserSuggest getUserSuggestDetail(Map<String, Object> conditionMap) {
		return mapper.getUserSuggestDetail(conditionMap);
	}
}
