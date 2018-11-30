
package com.manhui.gsl.jbqgsl.dao.app.sysuser;




import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.gsl.jbqgsl.model.AppUser;
/**
* @Title: CommerceMapper.java
* @Package com.manhui.gsl.jbqgsl.dao.app.commerce
* @Description: TODO(商会入会审批数据交互层)
* @author LiuBin
* @date 2018年11月1日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

@Mapper
public interface AppSysUserMapper {
	
	HashMap<String, Object> querySysUser(AppUser user);
	
	

}
