
/**
* @Title: MeetingRecept.java
* @Package com.manhui.gsl.jbqgsl.common.enums
* @Description: TODO(用一句话描述该文件做什么)
* @author LiuBin
* @date 2018年10月17日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

package com.manhui.gsl.jbqgsl.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum MeetingState {
	   UN_PUBLIC( "0", "未发布" ), UN_START( "1", "未开始" ), STARTING( "2", "会议中" ),COMPLETE( "3", "已结束" ),REVOKED( "4", "已撤销" );
    private String                    code;
    private String                     name;
    public static Map<String, String> codeMap = new HashMap<String, String>();
    static {
        for ( int i = 0; i < values().length; i++ ) {
            codeMap.put( values()[i].code, values()[i].name );
        }
    }

    MeetingState( String code, String name ) {
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
