package com.zzj.waimai.filter;
import com.alibaba.fastjson.JSON;
import com.zzj.waimai.common.R;
import com.zzj.waimai.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否登陆，登陆了才给访问
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //调用Spring核心包的字符串匹配类
    //路径匹配器 支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //强转一下,向下转型
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //获取url

        String requestUrl = httpServletRequest.getRequestURI();
        //定义可以放行的请求url
        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        //判断这个路径是否直接放行
        Boolean check = checkUrl(urls, requestUrl);
        //不需要处理直接放行
        if (check){
            log.info("匹配到了{}",requestUrl);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            //放行完了直接结束就行
            return;
        }
        //判断用户已经登陆可以放行
        if(httpServletRequest.getSession().getAttribute("employee")!=null){

            Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截，交由前端跳转");
        return;


    }
    public boolean checkUrl(String[] urls,String requestUrl){
        Boolean matchUrlResult = true;
        //遍历的同时调用PATH_MATCHER来对路径进行匹配
        for (String currUrl : urls) {
            matchUrlResult=PATH_MATCHER.match(currUrl, requestUrl);
            if (matchUrlResult){
                //匹配到了可以放行的路径，直接放行
                return true;
            }
        }
        //否则就是没有匹配到，不予放行
        return false;
    }

}