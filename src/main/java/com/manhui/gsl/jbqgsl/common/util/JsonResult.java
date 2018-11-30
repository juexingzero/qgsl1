package com.manhui.gsl.jbqgsl.common.util;

/**
 * 通过此对象封装控制层返回的JSON结果 便于对控制层返回数据进行统一格式化, 友好性管理
 */
public class JsonResult {
    public static final int SUCCESS = 1;
    public static final int ERROR   = 0;
    private int             state;      //服务端的响应状态
    private String          message;    //信息(给用户的提示)
    private Object          data;       //具体业务数据

    public JsonResult() {
        this.state = SUCCESS;
        this.message = "ok";
    }

    public JsonResult( Object data ) {
        this();
        this.data = data;
    }

    public JsonResult( Throwable e ) {
        this.state = ERROR;
        this.message = e.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public int getState() {
        return state;
    }
}
