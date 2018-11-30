package com.manhui.gsl.jbqgsl.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @类名称 ZipUtil.java
 * @类描述 zip工具类
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年9月1日 下午4:52:16
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年9月1日             
 *     ----------------------------------------------
 *       </pre>
 */
public class ZipUtil {
    private static final Logger logger = LoggerFactory.getLogger( ZipUtil.class );

    /**
     * @方法名称 compress
     * @功能描述 压缩
     * @作者 kevin
     * @创建时间 2018年9月1日 下午4:55:07
     * @param zipFile
     * @param fileList
     * @return
     */
    public static boolean compress( String zipFile, List<String> fileList ) {
        boolean flag = false;
        ZipOutputStream out = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream( new File( zipFile ) );
            CheckedOutputStream cos = new CheckedOutputStream( fileOutputStream, new CRC32() );
            out = new ZipOutputStream( cos );
            String basedir = "";
            for ( int i = 0; i < fileList.size(); i++ ) {
                compress( new File( fileList.get( i ) ), out, basedir );
            }
            out.close();
            flag = true;
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
        return flag;
    }

    //压缩
    private static void compress( File file, ZipOutputStream out, String basedir ) {
        if ( file.isDirectory() ) {
            compressDirectory( file, out, basedir );
        }
        else {
            logger.info( "-----------文件压缩-----------\n" + basedir + file.getName() );
            compressFile( file, out, basedir );
        }
    }

    //压缩一个目录
    private static void compressDirectory( File dir, ZipOutputStream out, String basedir ) {
        if ( !dir.exists() )
            return;
        File[] files = dir.listFiles();
        for ( int i = 0; i < files.length; i++ ) {
            compress( files[i], out, basedir + dir.getName() + "/" );
        }
    }

    //压缩一个文件
    private static void compressFile( File file, ZipOutputStream out, String basedir ) {
        if ( !file.exists() ) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
            ZipEntry entry = new ZipEntry( basedir + file.getName() );
            out.putNextEntry( entry );
            int count;
            byte data[] = new byte[8192];
            while ( ( count = bis.read( data, 0, 8192 ) ) != -1 ) {
                out.write( data, 0, count );
            }
            bis.close();
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }

    public static void main( String[] args ) {
        String zipFile = "D:\\jbqgsl\\file\\download\\excel\\result/11.zip";
        List<String> fileList = new ArrayList<>();
        fileList.add( "D:\\jbqgsl\\file\\download\\excel\\result/2018半年考核1-20180830231310.xlsx" );
        fileList.add( "D:\\jbqgsl\\file\\download\\excel\\result/2018半年考核1-20180830231528.xlsx" );
        compress( zipFile, fileList );
    }
}
