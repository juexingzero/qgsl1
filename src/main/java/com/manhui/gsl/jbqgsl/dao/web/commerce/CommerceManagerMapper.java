package com.manhui.gsl.jbqgsl.dao.web.commerce;

import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShxx;
import org.apache.ibatis.annotations.Mapper;
import com.manhui.gsl.jbqgsl.model.commerce.CommerceCategory;

import java.util.List;

/**
 * @类名称 CommerceManagerMapper.java
 * @类描述 商会管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月25日 上午10:45:28
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年10月25日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Mapper
public interface CommerceManagerMapper {
    /**
     * 获取商会列表
     * @return
     */
    List<MemberJgShxx> queryCommerceList();

    /**
     * 获取有下属商会数据
     * @param LYZJ
     * @return
     */
    List<MemberJgShxx> queryCommerceData(String LYZJ);
    /**
     * 获取商会类详细
     * @param ID
     * @return
     */
    MemberJgShxx queryCommerceDetail(String ID);
    /**
     * 插入商会类别
     * 
     * @param category
     * @return
     */
    Integer insertCommerceCategory( MemberJgShxx category );

    /**
     * 修改商会类别
     * @param category
     * @return
     */
    Integer updateCommerceCategory( MemberJgShxx category );

    /**
     * 删除商会
     * @param ID
     * @return
     */
    Integer deleteCommerce(String ID);

    /**
     * 插入商会
     * @param category
     * @return
     */
    Integer saveCommerce( MemberJgShxx category );
    /**
     * 修改商会
     * @param category
     * @return
     */
    Integer updateCommerce( MemberJgShxx category );

    /**
     * 坚持商会名称是否相同
     * @param SHMC
     * @return
     */
    List<MemberJgShxx> checkCommerceName(String SHMC);
}
