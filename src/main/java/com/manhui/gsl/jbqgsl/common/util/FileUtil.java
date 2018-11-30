package com.manhui.gsl.jbqgsl.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @类名称 FileUtil.java
 * @类描述 文件工具类
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月1日 下午4:22:04
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本        修改人        修改日期         修改内容描述
 *     ----------------------------------------------
 *     1.00     kevin    2018年9月1日             创建
 *     ----------------------------------------------
 *       </pre>
 */
public class FileUtil {
    /**
     * @方法名称 download
     * @功能描述 下载
     * @作者 kevin
     * @创建时间 2018年9月1日 下午4:27:00
     * @param response
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean download( HttpServletResponse response, String filePath, String fileName ) {
        boolean flag = false;
        String fileUrl = filePath + fileName;
        if ( fileUrl != null ) {
            File file = new File( fileUrl );
            if ( file.exists() ) {
                response.setContentType( "application/force-download" );// 设置强制下载不打开
                response.addHeader( "Content-Disposition", "attachment;fileName=" + fileName );// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream( file );
                    bis = new BufferedInputStream( fis );
                    OutputStream os = response.getOutputStream();
                    int i = bis.read( buffer );
                    while ( i != -1 ) {
                        os.write( buffer, 0, i );
                        i = bis.read( buffer );
                    }
                    flag = true;
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        if ( bis != null ) {
                            bis.close();
                        }
                        if ( fis != null ) {
                            fis.close();
                        }
                    }
                    catch ( IOException e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return flag;
    }

    /**
     * @方法名称 delete
     * @功能描述 删除
     * @作者    kevin
     * @创建时间 2018年9月3日 上午8:52:11
     * @param fileName
     * @return
     */
    public static boolean delete( String fileName ) {
        boolean flag = false;
        File file = new File( fileName );
        if ( file.isDirectory() ) {
            String[] filelist = file.list();
            for ( int i = 0; i < filelist.length; i++ ) {
                File delfile = new File( filelist[i] );
                if ( !delfile.isDirectory() ) {
                    delfile.delete();
                }
            }
        }
        else {
            if ( file.exists() ) {
                file.delete();
                flag = true;
            }
        }
        return flag;
    }
}
