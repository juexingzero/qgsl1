package com.manhui.gsl.jbqgsl.common.enums.news;

import java.util.HashMap;
import java.util.Map;

public enum NewsType {
    img_news("t","轮播新闻"),teadline_news("c","工商联头条");
    private String code;
    private String name;

    NewsType(String code, String name) {
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
