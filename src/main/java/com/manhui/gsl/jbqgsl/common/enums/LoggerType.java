package com.manhui.gsl.jbqgsl.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志类型
 */
public enum LoggerType {
    login("1","登录日志");
    private String code;
    private String name;

    LoggerType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static Map<String, String> codeMap = new HashMap<String, String>();
    static {
        for ( int i = 0; i < values().length; i++ ) {
            codeMap.put( values()[i].code, values()[i].name );
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
