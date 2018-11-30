package com.manhui.gsl.jbqgsl.framework.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @类名称 JbqGslException.java
 * @类描述 关键业务点异常类
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月17日 下午2:13:00
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年8月17日             
 *     ----------------------------------------------
 *       </pre>
 */
public class JbqGslException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static Log        logger           = LogFactory.getLog( JbqGslException.class );

    public JbqGslException( String msg, Throwable e ) {
        super( msg, e );
        logger.error( msg, e );
    }
}
