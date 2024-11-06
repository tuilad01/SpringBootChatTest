package com.example.testwebapp.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private final RequestHeaderObject requestHeaderObject;

    public RequestInterceptor(RequestHeaderObject requestHeaderObject) {
        this.requestHeaderObject = requestHeaderObject;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (requestHeaderObject != null) {
            requestHeaderObject.clientIPAddress = request.getRemoteAddr();
        }

        System.out.println("New request comming " + requestHeaderObject.clientIPAddress);

        return true;

    }
}
