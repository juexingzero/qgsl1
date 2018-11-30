package com.manhui.gsl.jbqgsl.common.util;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
@Component
public class ZxingUtil {
    private static String charset="utf-8";//定义字符编码
    private static String QrName="jpg";//定义类型
    private static int QrSize = 300;//定义size
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    
 
    /**
     * 生成二维码
     * @param content
     * @param imgPath
     * @param needCompress
     */
    public static BufferedImage createQrimage(String content,String imgPath,boolean needCompress) throws Exception{ 
        Hashtable<EncodeHintType,Object> hash = new Hashtable<EncodeHintType,Object>();
        hash.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//越高存储越小,容错率越高
        hash.put(EncodeHintType.CHARACTER_SET,charset);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, QrSize, QrSize, hash);//设置二维码参数
        int width = bitMatrix.getWidth();//获取比特矩阵的宽度
        int height = bitMatrix.getHeight();//获取比特矩阵的高度
        BufferedImage Qrimage = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        //开始画二维码
        for(int x = 0;x < width; x++){
            for(int y = 0;y < height; y++){
                Qrimage.setRGB(x, y, bitMatrix.get(x, y)? 0xFF000000:0xFFFFFFFF);
            }
        }
        if(imgPath == null || "".equals(imgPath)){//如果要插入的图片路径为空，直接返回图片
            return Qrimage;
        }

        ZxingUtil.insertImage(Qrimage,imgPath,needCompress);//调用insertImage函数插入图片
        return Qrimage;
    }


    /**
     * 插入内嵌图片
     * @param source
     * @param imgPath 要插入图片路径
     * @param needCompress 要插入图片的像素是否大于60
     * @throws Exception 
     */
    private static void insertImage(BufferedImage source, String imgPath,
            boolean needCompress) throws Exception {
    	System.out.println("图片保存路径："+imgPath);
        File file = new File(imgPath);
        if(!file.exists()){
           throw new RuntimeException(""+imgPath+"路径不存在！");
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);//获得原宽度
        int height = src.getHeight(null);//获得源高度
        if(needCompress){//比较要插入的图片的宽度是否大于设定的WIDTH=60像素宽
            if(width>WIDTH){
                width = WIDTH;
            }
            if(height>HEIGHT){//比较要插入的图片的高度是否大于设定的HEIGTH=60像素宽
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, //把image对象的getScaledInstance方法把图片缩小heightXwidth像素大小
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width,height,///创建一个透明色的BufferedImage对象
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();//获得画笔
            g.drawImage(image, 0, 0, null);//绘制指定图像中当前可用的image图像，图像的左上角位于该图形上下文坐标（0，0）的 (x, y)
        }
        //开始画内嵌图片
        Graphics2D graph = source.createGraphics();
        //计算绘画坐标
        int x = (QrSize - width)/2;
        int y = (QrSize - height)/2;
        graph.drawImage(src, x, y, width, height, null);//内嵌坐标为（x,y)的地方
        Shape shape = new RoundRectangle2D.Float(x,y,width,width,6,6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }


    /**
     * 对二维码图像进行编码
     * @param content 要封装的内容
     * @param imgPath 要插入图片路径
     * @param destPath 存储路径
     * @param needCompress 要插入图片的像素是否大于60
     * @param qrName 图片名字
     * @throws Exception
     */
    public static void encode(String content,String imgPath,String destPath,
        boolean needCompress,String qrName) throws Exception{
        mkdirs(destPath);// 检查是否有该路径，没有则创建
        BufferedImage image = ZxingUtil.createQrimage(content, imgPath, 
                needCompress);
        String file = qrName+".jpg";
        File qrPathFile = new File(destPath+"/"+file);
//        ImageIO.write(image, QrName, qrPathFile);
        ImageIO.write(image, QrName, new File(destPath+"/"+file));
       
    }


    public static void mkdirs(String destPath){
        File file = new File(destPath);
        if(!file.exists() && !file.isDirectory()){
            file.mkdirs();
        }
    }


    public static void main(String[] args) {
        try {
            ZxingUtil.encode("activity_id:10c95ef4a3274149812b94774f60b102","F:\\test/test.jpg","F:\\test",true,"z");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}