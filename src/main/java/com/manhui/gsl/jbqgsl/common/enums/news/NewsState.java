package com.manhui.gsl.jbqgsl.common.enums.news;

import java.util.HashMap;
import java.util.Map;

/**
 * 新闻状态枚举
 */
public enum NewsState {
    ADD( 1, "新增" ), RELEASE( 2, "发布" ),OBTAINED(3,"下架"), DEL( 5, "删除" );
    private Integer                    id;
    private String                     name;
    public static Map<Integer, String> codeMap = new HashMap<Integer, String>();
    static {
        for ( int i = 0; i < values().length; i++ ) {
            codeMap.put( values()[i].id, values()[i].name );
        }
    }

    NewsState( Integer id, String name ) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
}
