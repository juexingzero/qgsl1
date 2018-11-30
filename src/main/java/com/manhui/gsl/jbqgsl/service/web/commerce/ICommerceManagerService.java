package com.manhui.gsl.jbqgsl.service.web.commerce;

import com.manhui.gsl.jbqgsl.model.commerce.CommerceCategory;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;

import java.util.List;

/**
 * @类名称 ICommerceManagerService.java
 * @类描述 商会管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月25日 上午10:41:31
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年10月25日                创建
 *     ----------------------------------------------
 *       </pre>
 */
public interface ICommerceManagerService {
    List<MemberJgShxx> queryCommerceList();
    List<MemberJgShxx> queryCommerceData(String LYZJ);
    MemberJgShxx queryCommerceDetail(String ID);
    Integer saveCommerceCategory( MemberJgShxx category );
    Integer editCommerceCategory( MemberJgShxx category );
    Integer deleteCommerce(String ID);
    Integer saveCommerce( MemberJgShxx category );
    Integer editCommerce( MemberJgShxx category );
    List<MemberJgShxx> checkCommerceName(String SHMC);
}
