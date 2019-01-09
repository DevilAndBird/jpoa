//package com.fh.filter;
//
//import java.io.IOException;
//import java.net.InetAddress;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.SecurityUtils;
//import org.springframework.stereotype.Component;
//
//import com.fh.entity.system.User;
//import com.fh.util.Const;
//import com.fh.util.Tools;
///**
// * session失效自动跳回首页
// * @author tangqm
// *
// */
//@Component
//public class SessionFilter implements Filter{
//
//	@Override
//	public void destroy() {
//		
//	}
//  
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response,
//			FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest   req =  (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		User user = (User) SecurityUtils.getSubject().getSession().getAttribute( Const.SESSION_USER );
//		String requestURI = req.getRequestURI();
//		String contextPath= req.getContextPath()+"/";//获取项目根目录
//		String remoteAddr = request.getRemoteAddr();
//		if(requestURI.equals(contextPath) || requestURI.contains(contextPath+"static") || requestURI.contains(contextPath+"code")||requestURI.contains(contextPath+"login_login")){
//			//项目返回首页以及加载样式通过
//			chain.doFilter(request, response);
//			return;
//		}
//	    if(!"127.0.0.1".equals(remoteAddr)){
//			//非本机则默认通过，因为过滤器只过滤本地sesson失效情况，测试的时候需要放开
//	    	chain.doFilter(request, response);
//			return;
//		}
//		if(user == null) {
//			//session为空返回首页
//			res.sendRedirect(contextPath);
//			// session 超时或服务器重启 -- 跳转登陆页面地址
//		}else{			
//			chain.doFilter(request, response);
//		}
//	}
//
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//		
//	}
//
//}
