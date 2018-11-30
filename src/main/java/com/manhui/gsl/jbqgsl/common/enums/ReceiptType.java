package com.manhui.gsl.jbqgsl.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 回执类型
 */
public enum ReceiptType {
    one(1,"参加"),two(2,"请假"),three(3,"指派人参加");
    private Integer id;
    private String name;

    ReceiptType(Integer id, String name) {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
