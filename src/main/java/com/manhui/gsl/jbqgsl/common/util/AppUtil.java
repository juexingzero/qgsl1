package com.manhui.gsl.jbqgsl.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class AppUtil {
    /**
     * 获取两个时间的天数
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return +-(endTime - startTime)
     * @throws Exception
     */ 
    public static int getDay( String startTime, String endTime ) {
        SimpleDateFormat date = new SimpleDateFormat( "yyyy-MM-dd" );
        try {
            return ( int ) ( ( date.parse( endTime ).getTime() - date.parse( startTime ).getTime() ) /
                    ( 24 * 60 * 60 * 1000 ) );
        }
        catch ( ParseException e ) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置当前cookie 浏览器关闭消失
     *
     * @param response
     * @param name cookie名称
     * @param value cookie值
     * @author
     */
    public static void setUserCookie( HttpServletResponse response, String name, String value ) {
        Cookie cookie = new Cookie( name, value );
        cookie.setMaxAge( -1 );
        cookie.setPath( "/" );
        response.addCookie( cookie );
    }

    /**
     * 清除当前cookie
     *
     * @param response
     * @param name cookie名称
     * @author
     */
    public static void closeUserCookie( HttpServletResponse response, String name ) {
        Cookie cookie = new Cookie( name, null );
        cookie.setMaxAge( 0 );
        cookie.setPath( "/" );
        response.addCookie( cookie );
    }

    /**
     * 清除当前cookie
     *
     * @param response
     * @param name cookie名称
     * @author
     */
    public static void closeUserCookieManage( HttpServletResponse response, String name ) {
        Cookie cookie = new Cookie( name, null );
        cookie.setMaxAge( 0 );
        cookie.setPath( "/manager" );
        response.addCookie( cookie );
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name cookie名字
     * @return
     */
    public static String getCookieByName( HttpServletRequest request, String name ) {
        try {
            Cookie[] cookies = request.getCookies();
            //cookies不为空，则清除
            if ( cookies != null ) {
                for ( int i = 0; i < cookies.length; i++ ) {
                    Cookie cookie = cookies[i];
                    String user_name = URLDecoder.decode( cookie.getName(), "UTF-8" );
                    //查找用户名
                    if ( user_name.equals( name ) ) {
                        return URLDecoder.decode( cookie.getValue(), "UTF-8" );
                    }
                }
            }
        }
        catch ( UnsupportedEncodingException e ) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成随机字符串验证码
     *
     * @param charCount 字符串长度
     * @return
     */
    public static String getRandStr( int charCount ) {
        StringBuilder charValue = new StringBuilder();
        for ( int i = 0; i < charCount; i++ ) {
            charValue.append( String.valueOf( ( char ) ( randomInt( 0, 26 ) + 'a' ) ) );
        }
        return charValue.toString();
    }

    /**
     * 获取ip
     */
    public static String getIpAddr( HttpServletRequest request ) {
        String ip = request.getHeader( "x-forwarded-for" );
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
            ip = request.getHeader( "Proxy-Client-IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
            ip = request.getHeader( "WL-Proxy-Client-IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static int randomInt( int from, int to ) {
        Random r = new Random();
        return from + r.nextInt( to - from );
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param name 名称
     * @return
     */
    public static String getCookie( HttpServletRequest request, String name ) {
        try {
            Cookie[] cookies = request.getCookies();
            if ( cookies != null ) {
                for ( Cookie cookie : cookies ) {
                    String user_name = URLDecoder.decode( cookie.getName(), "UTF-8" );
                    //查找用户名
                    if ( user_name.equals( name ) ) {
                        return URLDecoder.decode( cookie.getValue(), "UTF-8" );
                    }
                }
            }
        }
        catch ( UnsupportedEncodingException e ) {
            e.printStackTrace();
        }
        return null;
    }
}
