
package com.manhui.gsl.jbqgsl.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum MeetingRecept {
	   IS_RECEPT( "1", "已回执" ), NO_RECEPT( "0", "未回执" );
    private String                    code;
    private String                     name;
    public static Map<String, String> codeMap = new HashMap<String, String>();
    static {
        for ( int i = 0; i < values().length; i++ ) {
            codeMap.put( values()[i].code, values()[i].name );
        }
    }

    MeetingRecept( String code, String name ) {
        this.code = code;
        this.name = name;
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
