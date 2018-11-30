package com.manhui.gsl.jbqgsl.common.enums.news;

import java.util.HashMap;
import java.util.Map;

/**
 * 新闻栏目模型
 */
public enum MenuModel {
    TEXT_VIDEO_ARTICLES( "articleType", "文章类型" ),
    FILE_DOWNLOAD( "listType", "列表类型" );
    private String code;
    private String name;

    MenuModel( String code, String name ) {
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

    public void setCode( String code ) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
}
