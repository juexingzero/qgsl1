package com.manhui.gsl.jbqgsl.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final Logger   logger        = LoggerFactory.getLogger( MD5Util.class );
    // 全局数组
    private final static String[] strDigits     = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    };
    private static MessageDigest  messagedigest = null;
    static {
        try {
            messagedigest = MessageDigest.getInstance( "MD5" );
        }
        catch ( NoSuchAlgorithmException e ) {
            logger.error( "MD5Util messagedigest初始化失败", e );
        }
    }

    public static String encrypt( String str ) {
        return byteToString( messagedigest.digest( str.getBytes() ) ); //md.digest() 该函数返回值为存放哈希值结果的byte数组
    }

    // 转换字节数组为16进制字串
    private static String byteToString( byte[] bByte ) {
        StringBuilder sBuffer = new StringBuilder();
        for ( byte aBByte : bByte ) {
            sBuffer.append( byteToArrayString( aBByte ) );
        }
        return sBuffer.toString();
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString( byte bByte ) {
        int iRet = bByte;
        if ( iRet < 0 ) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    public static void main( String[] args ) {
        System.out.println( MD5Util.encrypt( "123456" ) );
    }

	    /**
     * md5加密
     *
     * @param plainText 文本内容
     * @return 返回加密后的字符串
     */
    @Deprecated
    public static String parse(String plainText) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i = 0;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
