package com.manhui.gsl.jbqgsl.common.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;

/**
 * 分页实体
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@Data
public class PageModel<T> implements Serializable {
    private List<T> list;                      //数据集
    private int     totalRecords;              //总记录数
    private String  txt;                       //筛选条件
    private Integer inte;                      //筛选条件
    private int     pageSize;                  //每页显示多少条
    private int     pageNo;                    //当前是第几页
    private T       t;                         //参数对象
    private int     totalPage = getTotalPage();//总共有多少页
    private String  mo_state;                  //服务状态
    private Integer sm_id;                     //服务类型
    private Date    dateStart;                 // 开始时间
    private Date    dateEnd;                   // 结束时间
    private String  sqlWhere;                  // 条件
    private String  sqlOrderBy;                // 排序
    private String  dateTitle;                 // 时间筛选标题
    private Double  sumMoney;                  //总金额

    /**
     * 总共有多少页
     * 
     * @return
     */
    public int getTotalPage() {
        return ( int ) Math.ceil( ( double ) totalRecords / pageSize );
    }

    /**
     * 首页
     *
     * @return
     */
    public int getFirst() {
        return 1;
    }

    /**
     * 尾页
     * 
     * @return
     */
    public int getLast() {
        return getTotalPage();
    }

    /**
     * 上页
     * 
     * @return
     */
    public int getPre() {
        if ( pageNo == getFirst() ) {
            return 1;
        }
        return pageNo - 1;
    }

    /**
     * 下页
     * 
     * @return
     */
    public int getNext() {
        if ( pageNo == getLast() ) {
            return getTotalPage();
        }
        return pageNo + 1;
    }

    /**
     * 数据转化为SQL 时间格式化
     * 
     * @return
     *
     * @throws ParseException
     */
    public PageModel<T> getWhereData( TableList tableList, String dateStr, HttpServletRequest request )
            throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat( dateStr );
        PageModel<T> pageModel1 = new PageModel<T>();
        if ( tableList.getDateStart() != null && !tableList.getDateStart().equals( "" ) ) {
            pageModel1.setDateStart( sf.parse( tableList.getDateStart() ) );
        }
        if ( tableList.getDateEnd() != null && !tableList.getDateEnd().equals( "" ) ) {
            pageModel1.setDateEnd( sf.parse( tableList.getDateEnd() ) );
        }
        pageModel1.setSqlWhere( tableList.getSqlWhere() );
        pageModel1.setDateTitle( tableList.getDateType() );
        if ( tableList.getOrderBy() != null && !tableList.getOrderBy().equals( "" ) ) {
            pageModel1.setSqlOrderBy( "order by " + tableList.getOrderBy() + " asc" );
        }
        else {
            pageModel1.setSqlOrderBy( "" );
        }
        int pageSize = Integer.parseInt( AppUtil.getCookie( request, "pageSize" ) );
        pageModel1.setPageNo( ( tableList.getPageNo() - 1 ) * pageSize );
        // 分页设置查询条数
        pageModel1.setPageSize( pageSize );
        return pageModel1;
    }
}
