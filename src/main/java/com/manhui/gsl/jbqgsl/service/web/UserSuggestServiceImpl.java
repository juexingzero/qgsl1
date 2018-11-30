
package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.Constant;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.dao.MessageMapper;
import com.manhui.gsl.jbqgsl.dao.UserSuggestMapper;
import com.manhui.gsl.jbqgsl.model.MessageFlowing;
import com.manhui.gsl.jbqgsl.model.MessageInfo;
import com.manhui.gsl.jbqgsl.model.UserSuggest;

/**
 * @Title: UserSuggestServiceImpl.java
 * @Package com.manhui.gsl.jbqgsl.service.impl
 * @Description: TODO(意见反馈service实现层)
 * @author LiuBin
 * @date 2018年9月5日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

@Service
public class UserSuggestServiceImpl implements IUserSuggestService {
	private static final Logger logger = LoggerFactory.getLogger(UserSuggestServiceImpl.class);
	@Resource
	private UserSuggestMapper userSuggestMapper;
	@Resource
    private MessageMapper  messageMapper; 

	@Override
	public PageInfo<UserSuggest> getUserSuggestList(Map<String, Object> conditionMap) {

		logger.info("----- 意见反馈-意见反馈列表 ==> start -----");
		PageInfo<UserSuggest> info;
		String index = conditionMap.get("pageIndex") + "";
		String size = conditionMap.get("pageSize") + "";
		if (StringUtils.isNotEmpty(index) && StringUtils.isNotEmpty(size)) {
			int pageIndex = Integer.valueOf(index);
			int pageSize = Integer.valueOf(size);
			PageHelper.startPage(pageIndex+1, pageSize);
		}
		List<UserSuggest> userSuggestList = userSuggestMapper.getUserSuggestList(conditionMap);
		if (userSuggestList != null && userSuggestList.size() > 0) {
			info = new PageInfo<UserSuggest>(userSuggestList);
		} else {
			info = new PageInfo<UserSuggest>();
		}
		logger.info("----- 意见反馈-意见反馈列表 ==> end -----");
		return info;
	}

	@Override
	public UserSuggest getUserSuggestDetail(String id) {
		logger.info("----- 意见反馈-意见反馈列表 ==> start -----");
		UserSuggest userSuggest = userSuggestMapper.getUserSuggestMapperDetail(id);
		if(userSuggest.getUser_phone()==null) {
			userSuggest.setUser_phone( "" );
		}
		if(userSuggest.getUser_name()==null) {
			userSuggest.setUser_name( "" );
		}
		logger.info("----- 意见反馈-意见反馈列表==> end -----");
		return userSuggest;
	}

	@Override
	public Integer saveUserSuggest(UserSuggest userSuggest) {
		Integer flag = 0;
		if (userSuggest.getId() != null && !"".equals(userSuggest.getId())) {
			logger.info("----- 意见反馈-更新意见反馈列表 ==> start -----");
			userSuggest.setAnswer_time(DateUtil.getTime());
			flag = userSuggestMapper.updateUserSuggest(userSuggest);
			logger.info("----- 意见反馈-更新意见反馈列表 ==> end -----");
		}
		if(flag ==1) {
			logger.info("----- 意见反馈- 消息管理注入数据==> start -----");
            //添加消息模板信息
            MessageInfo info = new MessageInfo();
            info.setMessage_id( UUIDUtil.getUUID() );
            info.setMessage_type( "3" );
            info.setMessage_mode( "1" );
            String answerContent = "您好：现针对您提的意见反馈\""+userSuggest.getContent()+"\"做以下回复:"+userSuggest.getAnswer_content();
            info.setMessage_content( answerContent );
            info.setCreator_id( userSuggest.getAnswer_man_id() );
            info.setCreator_name( userSuggest.getAnswer_man_name() );
            info.setCreate_time( DateUtil.getDateTime(Constant.DATETIME_PATTERN) );
			messageMapper.insertMessageInfo( info );
			
			logger.info("----- 意见反馈- 消息管理注入数据==> end -----");
			 //发送消息
			logger.info("----- 意见反馈- 发送消息==> start -----");
            MessageFlowing flowing = new MessageFlowing();
            flowing.setMessage_id( info.getMessage_id() );
            flowing.setFlowing_id( UUIDUtil.getUUID() );
            flowing.setSend_id( info.getCreator_id() );
            flowing.setSend_name( info.getCreator_name() );
            flowing.setReceive_id( userSuggest.getUser_id());
            flowing.setReceive_name( userSuggest.getUser_name() );
            flowing.setIs_read( "0" );
            flowing.setSend_time(DateUtil.getDateTime(Constant.DATETIME_PATTERN));
            flowing.setUserSuggest_id(userSuggest.getId());;
            messageMapper.insertMessageFlowing( flowing );
            
            logger.info( "--------------意见反馈： 开始，发送消息给评价方：" + flowing.getReceive_name() + "--------------\n" );
			
		}
		return flag;
	}

	@Override
	public Integer deleteUserSuggest(String id) {
		logger.info("----- 意见反馈-更新意见反馈列表 ==> start -----");
		Integer flag = userSuggestMapper.deleteUserSuggest(id);
		logger.info("----- 意见反馈-更新意见反馈列表 ==> start -----");
		return flag;
	}

}
