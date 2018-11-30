package com.manhui.gsl.jbqgsl.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.manhui.gsl.jbqgsl.framework.exception.JbqGslException;

/**
 * @类名称 RequestUtil.java
 * @类描述 请求调用工具类
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年8月17日 下午2:03:00
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年8月17日             
 *     ----------------------------------------------
 *       </pre>
 */
public class RequestUtil {
    private static Log logger = LogFactory.getLog( RequestUtil.class );

    /**
     * @方法名称 invoker
     * @功能描述 调用
     * @作者 kevin
     * @创建时间 2018年8月17日 下午2:09:09
     * @param method
     * @param url
     * @param clazz
     * @param json
     * @param isConvert
     * @return
     */
    public static Object invoker( String method, String url, Class<?> clazz, String json, boolean isConvert ) {
        Object resp = null;
        String resJson = "";
        try {
            if ( url.startsWith( "https" ) ) {
                resJson = httpsInvokerForJson( method, url, json );
            }
            else {
                resJson = httpInvokerForJson( method, url, json );
            }
            if(isConvert) {
                if ( !"".equals( resJson ) ) {
                    resp = JacksonUtil.toBean( resJson, clazz );
                }
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * @方法名称 invoker
     * @功能描述 HTTPS调用(忽略证书)
     * @作者 kevin
     * @创建时间 2018年8月17日 下午2:15:09
     * @param method
     * @param reqURL
     * @param json
     * @return
     */
    public static String httpsInvokerForJson( String method, String reqURL, String json ) {
        String result = "";
        try {
            HttpsURLConnection.setDefaultHostnameVerifier( new RequestUtil().new NullHostNameVerifier() );
            SSLContext sc = SSLContext.getInstance( "TLS" );
            sc.init( null, trustAllCerts, new SecureRandom() );
            HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory() );
            URL url = new URL( reqURL );
            HttpURLConnection conn = ( HttpURLConnection ) url.openConnection();
            conn.setDoOutput( true );
            conn.setDoInput( true );
            conn.setRequestMethod( method );
            conn.setRequestProperty( "Content-Type", "application/json;charset=utf-8" );
            conn.setConnectTimeout( 100000 );
            conn.setReadTimeout( 100000 );
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write( json.toString().getBytes() );
            out.flush();
            out.close();
            if ( conn.getResponseCode() == 200 ) {
                byte bytes[] = new byte[1024];
                InputStream inStream = conn.getInputStream();
                inStream.read( bytes, 0, inStream.available() );
                result = new String( bytes, "utf-8" );
//                logger.info( "-------------------响应报文明细-------------------\n" + result );
            }
            conn.disconnect();
        }
        catch ( Exception e ) {
            if ( e instanceof ConnectException ) {
                throw new JbqGslException( "------ 接口访问异常：" + reqURL + " ------\n", e );
            }
            else {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @方法名称 invoker
     * @功能描述 HTTP调用
     * @作者 kevin
     * @创建时间 2018年8月17日 下午2:15:09
     * @param method
     * @param reqURL
     * @param json
     * @return
     */
    public static String httpInvokerForJson( String method, String reqURL, String json ) {
        String result = "";
        try {
            URL url = new URL( reqURL );
            HttpURLConnection conn;
            StringBuffer sbuffer = null;
            conn = ( HttpURLConnection ) url.openConnection();
            conn.setDoOutput( true );
            conn.setDoInput( true );
            conn.setRequestMethod( method );
            conn.setRequestProperty( "Host", "*******" );
            conn.setRequestProperty( "Content-Type", " application/json" );
            conn.setRequestProperty( "Accept-Charset", "utf-8" );
            conn.setRequestProperty( "X-Auth-Token", "token" );
            conn.setRequestProperty( "Connection", "keep-alive" );
            conn.setRequestProperty( "Transfer-Encoding", "chunked" );
            conn.setRequestProperty( "Content-Length", json.toString().getBytes().length + "" );
            conn.setReadTimeout( 100000 );
            conn.setConnectTimeout( 100000 );
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write( json.toString().getBytes() );
            out.flush();
            out.close();
            if ( conn.getResponseCode() == 200 ) {
                InputStreamReader inputStream = new InputStreamReader( conn.getInputStream() );
                BufferedReader reader = new BufferedReader( inputStream );
                String lines;
                sbuffer = new StringBuffer( "" );
                while ( ( lines = reader.readLine() ) != null ) {
                    lines = new String( lines.getBytes(), "utf-8" );
                    sbuffer.append( lines );
                }
                result = sbuffer.toString();
//                logger.info( "-------------------响应报文明细-------------------\n" + result );
                reader.close();
            }
            conn.disconnect();
        }
        catch ( Exception e ) {
            if ( e instanceof ConnectException ) {
                throw new JbqGslException( "------ 接口访问异常：" + reqURL + " ------\n", e );
            }
            else {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted( X509Certificate[] chain, String authType )
                        throws CertificateException {}

                @Override
                public void checkServerTrusted( X509Certificate[] chain, String authType )
                        throws CertificateException {}

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
    };

    public class NullHostNameVerifier implements HostnameVerifier {
        @Override
        public boolean verify( String arg0, SSLSession arg1 ) {
            return true;
        }
    }

    public static void main( String[] args ) throws MalformedURLException {
        String method = "POST";
        String reqURL = "https://localhost:8081/web/v1/topicEvaluate/checkAndUpdateEvaluateState";
        String json = "{\"currentDate\":\"" + "" + "\"}";
        String result = httpsInvokerForJson( method, reqURL, json );
        System.out.println( "-----------------------------\n" + result + "\n" );
    }
}
