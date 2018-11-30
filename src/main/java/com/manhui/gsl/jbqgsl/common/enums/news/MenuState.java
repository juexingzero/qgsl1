package com.manhui.gsl.jbqgsl.common.enums.news;

import java.util.HashMap;
import java.util.Map;

public enum MenuState {
    UNUSED( 0, "未使用" ), USING( 1, "使用中" ), DEL( 2, "已删除" );
    private Integer id;
    private String  name;

    MenuState( Integer id, String name ) {
        this.id = id;
        this.name = name;
    }

    public static Map<Integer, String> codeMap = new HashMap<Integer, String>();
    static {
        for ( int i = 0; i < values().length; i++ ) {
            codeMap.put( values()[i].id, values()[i].name );
        }
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
