


package com.manhui.gsl.jbqgsl.controller.app.commerce;

import java.util.List;

import lombok.Data;

/**
* @Title: Commerce.java
* @Package com.manhui.gsl.jbqgsl.controller.app.commerce
* @Description: TODO(商会)
* @author LiuBin
* @date 2018年11月2日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/
@Data
public class Commerce {
	private String ID; //ID
	private String SHBM;//商会编码（参考“组织机构编码”）
	private String SHMC;//商会名称
	private String ZZJGBM;//组织机构编码（参考“组织机构编码”）
	private String SHLX;//商会类型
	private String LYZJ;//领域 ID
	private String PX;//联系人 ID
	List<Commerce> zChildCommerce;
}
