
package com.manhui.gsl.jbqgsl.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.pagehelper.util.StringUtil;

/**
 * @Title: ParamCheckUtil.java
 * @Package com.manhui.gsl.jbqgsl.common.util
 * @Description: TODO(参数验证工具类)
 * @author LiuBin
 * @date 2018年10月23日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */

public class ParamCheckUtil {

	/**
	 * 验证身份证
	 */
	public static boolean isIdCardCheck(String id_card) {
		String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
		if (!Pattern.matches(REGEX_ID_CARD, id_card.trim())) {
			return false;
		}
		return true;

	}

	/**
	 * 验证手机号码
	 */
	public static boolean isTelePhone(String telephone) {
		String REGEX_TELEPHONE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		if ((telephone==null || "".equals(telephone))) {
			return false;
		}
		if (!Pattern.matches(REGEX_TELEPHONE, telephone.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * 统一社会信用认证
	 */
	@SuppressWarnings("unused")
	public static boolean socialCreditCkeck(String businessCode) {
		if (businessCode == null||"".equals(businessCode) || businessCode.length() != 18) {
			return false;
		}
		String baseCode = "0123456789ABCDEFGHJKLMNPQRTUWXY";
		char[] baseCodeArray = baseCode.toCharArray();
		Map<Character, Integer> codes = new HashMap<Character, Integer>();
		for (int i = 0; i < baseCode.length(); i++) {
			codes.put(baseCodeArray[i], i);
		}
		char[] businessCodeArray = businessCode.toCharArray();
		Character check = businessCodeArray[17];
		if (baseCode.indexOf(check) == -1) {
			return false;
		}
		int[] wi = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			Character key = businessCodeArray[i];
			if (baseCode.indexOf(key) == -1) {
				return false;
			}
			sum += (codes.get(key) * wi[i]);
		}
		int value = 31 - sum % 31;
		return value == codes.get(check);
	}

	/**
	 * 验证 String 类型的参数是否是数字类型
	 */
	public static boolean strIsNum(String[] param) {
		if(param.length==0) {
			return false;
		}
		try {
			for (int i = 0; i < param.length; i++) {
				Integer.parseInt(param[i].trim());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 验证字符串是不是网址
	 */
	public static boolean isUrl(String url) {
		if(!isEmpty(url)) {
			String regex = "(http://|ftp://|https://|www){0,1}[^\\u4e00-\\u9fa5\\\\s]*?\\\\.(com|net|cn|me|tw|fr)[^\\u4e00-\\u9fa5\\\\s]*";
			if (!Pattern.matches(regex, url.trim())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证字符串是不是邮箱
	 */
	public static boolean isEmail(String email) {
		if (email == null || "".equals(email))
			return false;
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p;
		Matcher m;
		p = Pattern.compile(regEx1);
		m = p.matcher(email);
		if (m.matches())
			return true;
		else
			return false;
	}

	/**
	 * 验证字符串是否为空
	 * 为空为 false 不为空为 true 不为空空false
	 */
	@SuppressWarnings("unused")
	public static boolean isEmpty(String str) {
		if (StringUtil.isNotEmpty(str) && !"".equals(str)) {
			return false;
		} 
		return true;
		
	}
}
