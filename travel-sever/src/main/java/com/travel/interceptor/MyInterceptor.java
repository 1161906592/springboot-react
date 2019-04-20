package com.travel.interceptor;

import com.travel.common.SessionEntity;
import com.travel.exception.NotLoginException;
import com.travel.utils.CacheUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor {
    // 所有handle之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域设置
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With,x-xsrf-token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (request.getMethod().toLowerCase().equals("options")) {
            return true;
        }
        String cacheId = request.getHeader("token");
        // 未登录
        SessionEntity sessionEntity = (SessionEntity) CacheUtils.getCache(cacheId);
        if (sessionEntity == null) {
            throw new NotLoginException();
        }
        // 更新当前用户的登录过期时间
        CacheUtils.updateCacheTime(cacheId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
