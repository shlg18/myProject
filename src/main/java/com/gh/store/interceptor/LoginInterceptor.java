package com.gh.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义处理器拦截器 */
public class LoginInterceptor implements HandlerInterceptor{
    /**
     * 检测全局session对象中是否有userid数据，如果有则放行，否则重定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url+Controller:映射）
     * @return 如果返回值为true表示放行当前的请求，否则拦截当前的请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;//请求放行
    }
}
