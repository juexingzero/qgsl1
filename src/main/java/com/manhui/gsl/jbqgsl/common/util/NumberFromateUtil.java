package com.manhui.gsl.jbqgsl.common.util;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * 数字转换工具
 * 
 * 
* @ClassName: NumberFromateUtil
* @Description: TODO(这里用一句话描述这个类的作用)
* @author LiuBin
* @date 2018年11月27日
 */
public class NumberFromateUtil {
	
	 /**
	  * 小数转成百分数
	  */
	 @SuppressWarnings("unused")
	public static String encrypt( String str ) {
		 String format=null;
		 if(str.contains("0.")) {
			 NumberFormat percentInstance = NumberFormat.getPercentInstance();
			 percentInstance.setMaximumFractionDigits(0); // 保留小数0位
			 format=percentInstance.format(str); // 结果是81% ，最后一们四舍五入了
		 }else if(str == null ||"".equals(str)){
			 return "0.0";
		 }else if(str.contains("%")){
			 return str;
		 }
		 return String.valueOf(format);
	 }
	 /**
	  * 百分数转成小数
	  */
	 @SuppressWarnings("unused")
	 public static String encrypt2( String str ) {
		 Number nf = null;
		if(str.contains("%")) {
			try {
				nf=NumberFormat.getPercentInstance().parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(str == null || "".equals(str)) {
			 return "0.0";
		 }else if(str .contains("0.")) {
			 return str;
		 }
		return String.valueOf(nf);
	 }

}
