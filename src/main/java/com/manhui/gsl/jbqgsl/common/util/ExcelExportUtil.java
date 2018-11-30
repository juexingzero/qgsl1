package com.manhui.gsl.jbqgsl.common.util;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * @类名称 ExcelExportUtil.java
 * @类描述 导出Excel的工具类，所需依赖：jxls-core
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月31日 下午4:16:22
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月31日             创建
 *     ----------------------------------------------
 *       </pre>
 */
public class ExcelExportUtil {
    private static final Logger logger = LoggerFactory.getLogger( ExcelExportUtil.class );

    /**
     * @方法名称 ExportExcel
     * @功能描述 excel导出
     * @作者 kevin
     * @创建时间 2018年9月1日 下午4:18:32
     * @param templePath excel模板地址：E:\\AAA.xlsx
     * @param beanParams 数据，同一个map中的一对key-value对应一个sheet <br>
     *            key跟模板中用来接收数据的变量得一致 <br>
     *            value是个Object，最好是个List<Object> <br>
     * @param targetPath excel生成地址：E:\\AAA_data.xlsx
     * @return
     */
    public static int ExportExcel( String templePath, Map<String, Object> beanParams, String targetPath ) {
        if ( templePath.equals( null ) || beanParams == null || targetPath.equals( null ) ) {
            logger.info( "========================= excel导出-传入参数有误或有空值，中断执行 =========================" );
            return 1;
        }
        try {
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS( templePath, beanParams, targetPath );
        }
        catch ( Exception e ) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }
}
