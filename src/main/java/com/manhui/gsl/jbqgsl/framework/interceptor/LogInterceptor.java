package com.manhui.gsl.jbqgsl.framework.interceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.manhui.gsl.jbqgsl.common.util.AppUtil;

public class LogInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		//boolean flag = false;
		boolean flag = true;
		String path = request.getRequestURI();
		if (path.indexOf("web") != -1) {
			if (AppUtil.getCookie(request, "user_id") != null) {
				flag = true;
			} else {
				response.sendRedirect("/login/login");
				//flag = false;
				flag= true;
			}
		}
		// app
		else if (path.indexOf("app") != -1) {
			flag = true;
		}

        //暂时对manager/toIndex做处理，后续将优化
        if (path.indexOf("manager/toIndex") != -1) {
            if (AppUtil.getCookie(request, "user_id") != null) {
                flag = true;
            } else {
                response.sendRedirect("/login/login");
                flag = false;
            }
        }
        
		return flag;
	}

	/**
	 * @方法名称 getURL
	 * @功能描述
	 * 
	 *       <pre>
	 *       获取请求的URL
	 *       </pre>
	 * 
	 * @作者 kevin
	 * @创建时间 2018年8月7日 上午午11:34:45
	 * @param request
	 * @return
	 */
	protected final String getURL(HttpServletRequest request) {
		StringBuilder url = new StringBuilder();
		try {
			url.append(request.getScheme());
			url.append("://");
			url.append(request.getServerName());
			url.append(":");
			url.append(request.getServerPort());
			url.append(request.getContextPath());
			url.append("/");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return url.toString();
	}

	/**
	 * 是否进行登陆过滤
	 * 
	 * @param path
	 * @param basePath
	 * @return
	 */
	private boolean doLoginInterceptor(String path, String basePath) {
		path = path.substring(basePath.length());
		Set<String> notLoginPaths = new HashSet<>();
		// 设置不进行登录拦截的路径：登录注册和验证码
		// notLoginPaths.add("/");
		notLoginPaths.add("/index");
		notLoginPaths.add("/signin");
		notLoginPaths.add("/login");
		notLoginPaths.add("/register");
		notLoginPaths.add("/kaptcha.jpg");
		notLoginPaths.add("/kaptcha");
		notLoginPaths.add("/user");
		// notLoginPaths.add("/sys/logout");
		// notLoginPaths.add("/loginTimeout");

		if (notLoginPaths.contains(path))
			return false;
		return true;
	}
}
