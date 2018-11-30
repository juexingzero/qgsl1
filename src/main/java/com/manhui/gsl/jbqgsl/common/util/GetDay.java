package com.manhui.gsl.jbqgsl.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetDay {
    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set( Calendar.DATE, 1 );
        a.roll( Calendar.DATE, -1 );
        int maxDate = a.get( Calendar.DATE );
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth( int year, int month ) {
        Calendar a = Calendar.getInstance();
        a.set( Calendar.YEAR, year );
        a.set( Calendar.MONTH, month - 1 );
        a.set( Calendar.DATE, 1 );
        a.roll( Calendar.DATE, -1 );
        int maxDate = a.get( Calendar.DATE );
        return maxDate;
    }

    /**
     * 获取某月的最后一天 @Title:getLastDayOfMonth @Description: @param:@param year @param:@param
     * month @param:@return @return:String @throws
     */
    public static String getLastDayOfMonth( int year, int month ) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set( Calendar.YEAR, year );
        //设置月份
        cal.set( Calendar.MONTH, month - 1 );
        //获取某月最大天数
        int lastDay = cal.getActualMaximum( Calendar.DAY_OF_MONTH );
        //设置日历中月份的最大天数
        cal.set( Calendar.DAY_OF_MONTH, lastDay );
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String lastDayOfMonth = sdf.format( cal.getTime() );
        return lastDayOfMonth;
    }

    /**
     * 获得该月第一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth( int year, int month ) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set( Calendar.YEAR, year );
        //设置月份
        cal.set( Calendar.MONTH, month - 1 );
        //获取某月最小天数
        int firstDay = cal.getActualMinimum( Calendar.DAY_OF_MONTH );
        //设置日历中月份的最小天数
        cal.set( Calendar.DAY_OF_MONTH, firstDay );
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String firstDayOfMonth = sdf.format( cal.getTime() );
        return firstDayOfMonth;
    }
}
