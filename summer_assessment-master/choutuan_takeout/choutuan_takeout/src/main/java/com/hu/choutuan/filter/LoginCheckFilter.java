package com.hu.choutuan.filter;

import com.hu.choutuan.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//检查用户是否登录，如果没登录就不可以进入商品页面----拦截器
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")//拦截器名称-LoginCheckFilter，拦截路径-所有请求
@Slf4j
public class LoginCheckFilter implements Filter {
    //匹配路径是否一致的工具
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;//类型转换
        HttpServletResponse response = (HttpServletResponse) servletResponse; //类型转换

        //获取本次请求
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}",requestURI);
        //不需要拦截的请求---还需要添加前端的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/user/sendMsg",
                "/user/login",
                "/user/logout"
        };
        //判断本次请求时用户是否登录
        boolean check = check(urls, requestURI);
        //不需要拦截
        if(check){
            log.info("该请求无需拦截");
            filterChain.doFilter(request,response);
            return;
        }
        //拦截--已登录
        if(request.getSession().getAttribute("employee") != null ){
            log.info("用户已登录");
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("user") != null ){
            log.info("用户已登录");
            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        return;
    }

    //判断是否登录
    public boolean check(String[] urls, String requestURI){
        for(String url : urls){
            boolean match  = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }

}
