/**
 * @Title: DateUtil.java
 * @Package com.manhui.gsl.jbqgsl.common.util
 * @Description: TODO(日期时间工具类)
 * @author LiuBin
 * @date 2018年8月8日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
package com.manhui.gsl.jbqgsl.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * @方法名称 getDateTime
     * @功能描述 按传入格式获取当前日期时间
     * @作者 kevin
     * @创建时间 2018年8月13日 下午7:47:57
     * @return
     */
    public static String getDateTime( String format ) {
        return new SimpleDateFormat( format ).format( new Date() );
    }

    /**
     * @方法名称 strToDate
     * @功能描述 字符串转Date
     * @作者 kevin
     * @创建时间 2018年8月17日 下午3:05:57
     * @param str
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date strToDate( String str, String format ) {
        Date date = null;
        try {
            date = new SimpleDateFormat( format ).parse( str );
        }
        catch ( ParseException e ) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        return df.format( new Date() );
    }
    /* 
     * 将时间转换为时间戳
     */    
    public static long dateToStamp(String s) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }
    /* 
     * 将时间转换为时间戳
     */    
    public static long dateToStamp2(String s) throws ParseException{
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	Date date = simpleDateFormat.parse(s);
    	long ts = date.getTime();
    	return ts;
    }
    /*
    public static String getConversionTime( String strTime, SimpleDateFormat sdf ) throws ParseException {
        Date sysDate = new Date();
        Date appDate = sdf.parse( strTime );
        //获得相差时间秒
        long longTime = ( sysDate.getTime() - appDate.getTime() ) / 1000;
        String returnStr = "";
        if ( ( longTime / 60 ) > 0 && ( longTime / 60 ) < 1 ) {
            returnStr = 1 + "分钟前";
        }
        else if ( ( longTime / 60 ) < 60 && ( longTime / 60 ) >= 1 ) {
            returnStr = ( longTime / 60 ) + "分钟前";
        }
        else if ( ( longTime / 60 / 60 ) < 24 && ( longTime / 60 / 60 ) >= 1 ) {
            returnStr = ( longTime / 60 / 60 ) + "小时前";
        }
        else if ( ( longTime / 60 / 60 / 24 ) < 30 && ( longTime / 60 / 60 / 24 ) >= 1 ) {
            returnStr = ( longTime / 60 / 60 / 24 ) + "天前";
        }
        else if ( ( longTime / 60 / 60 / 24 / 30 ) < 12 && ( longTime / 60 / 60 / 24 / 30 ) >= 1 ) {
            returnStr = ( longTime / 60 / 60 / 24 / 30 ) + "月前";
        }
        else {
            returnStr = ( longTime / 60 / 60 / 24 / 365 ) + "年前";
        }
        return returnStr;
    }
    */
    /******************************************** 时间换算-start ***********************************************/
    /**
     * @方法名称 getConversionTime
     * @功能描述 传入时间与当前时间比较，得到几秒前，几分钟前，几小时前，几天前，几月前，几年前
     * @作者 kevin
     * @创建时间 2018年9月1日 下午1:35:07
     * @param strTime
     * @param sdf
     * @return
     */
    public static String getConversionTime( String strTime, SimpleDateFormat sdf ) {
        String returnStr = "";
        try {
            Date date = sdf.parse( strTime );
            long delta = new Date().getTime() - date.getTime();
            /*
            if ( delta < 1L * 60000L ) {
                long seconds = toSeconds( delta );
                returnStr = ( seconds <= 0 ? 1 : seconds ) + "秒前";
            }
            else 
            */    
            if ( delta < 45L * 60000L ) {
                long minutes = toMinutes( delta );
                returnStr = ( minutes <= 0 ? 1 : minutes ) + "分钟前";
            }
            else if ( delta < 24L * 3600000L ) {
                long hours = toHours( delta );
                returnStr = ( hours <= 0 ? 1 : hours ) + "小时前";
            }
            else {
                returnStr = strTime.substring( 0, strTime.length()-9 );
            }
            
            /*
            else if ( delta < 30L * 86400000L ) {
                long days = toDays( delta );
                returnStr = ( days <= 0 ? 1 : days ) + "天前";
            }
            else if ( delta < 12L * 4L * 604800000L ) {
                long months = toMonths( delta );
                returnStr = ( months <= 0 ? 1 : months ) + "月前";
            }
            else {
                long years = toYears( delta );
                returnStr = ( years <= 0 ? 1 : years ) + "年前";
            }
            */
        }
        catch ( ParseException e ) {
            e.printStackTrace();
        }
        return returnStr;
    }

    private static long toSeconds( long date ) {
        return date / 1000L;
    }

    private static long toMinutes( long date ) {
        return toSeconds( date ) / 60L;
    }

    private static long toHours( long date ) {
        return toMinutes( date ) / 60L;
    }

    private static long toDays( long date ) {
        return toHours( date ) / 24L;
    }

    private static long toMonths( long date ) {
        return toDays( date ) / 30L;
    }

    private static long toYears( long date ) {
        return toMonths( date ) / 365L;
    }

    /******************************************** 时间换算-end ***********************************************/
    public static void main( String[] args ) throws ParseException {
        String strTime = "2018-09-13 12:19:08";
        System.out.println( getConversionTime( strTime, sdf ) );
    }
}
