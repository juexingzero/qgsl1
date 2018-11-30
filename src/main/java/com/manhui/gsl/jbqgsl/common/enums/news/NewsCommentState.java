package com.manhui.gsl.jbqgsl.common.enums.news;

import java.util.HashMap;
import java.util.Map;

/**
 * 新闻评论状态枚举
 */
public enum NewsCommentState {
    ONE( 1, "待审核" ), TWO( 2, "通过" ), THREE( 3, "不通过" );
    private Integer                    id;
    private String                     name;
    public static Map<Integer, String> codeMap = new HashMap<Integer, String>();
    static {
        for ( int i = 0; i < values().length; i++ ) {
            codeMap.put( values()[i].id, values()[i].name );
        }
    }

    NewsCommentState(Integer id, String name ) {
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
