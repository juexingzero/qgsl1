


package com.manhui.gsl.jbqgsl.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @Title: RemoveUeditorStyle.java
* @Package com.manhui.gsl.jbqgsl.common.util
* @Description: TODO(去掉富文本自带的标签,显示文本内容)
* @author LiuBin
* @date 2018年9月25日
* @version V1.0
* @modify By:
* @Copyright: 版权由满惠科技拥有
*/

public class RemoveUeditorStyleUtil {
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
    private static final String regEx_nbsp = "&nbsp;";//定义空格回车换行符
    /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签
        
 
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签
 
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
 
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        
        Pattern p_nbsp = Pattern.compile(regEx_nbsp, Pattern.CASE_INSENSITIVE);
        Matcher m_nbsp = p_nbsp.matcher(htmlStr);
        htmlStr = m_nbsp.replaceAll(""); // 过滤&nbsp;
        return htmlStr.trim(); // 返回文本字符串
    }
    
    public static String getTextFromHtml(String htmlStr){
    	htmlStr = delHTMLTag(htmlStr);
    	htmlStr = htmlStr.replaceAll(" ", "");
    	return htmlStr;
    }

}
