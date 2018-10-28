package com.lzpeng.sprintboot.mobile;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Enumeration;

@Slf4j
public class DeviceInterceptor extends HandlerInterceptorAdapter {

    private long startTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime = System.currentTimeMillis();
        log.info("开始访问 {} {} {}", request.getMethod(), request.getRequestURL(), LocalDateTime.now());
        Device device = DeviceUtils.getCurrentDevice(request);
        if (device.isMobile()) {
            log.info("{} {}手机", ServletUtil.getClientIP(request), device.getDevicePlatform().name());
        } else if (device.isTablet()) {
            log.info("{} {}平板", ServletUtil.getClientIP(request), device.getDevicePlatform().name());
        } else if (device.isNormal()) {
            log.info("{} 电脑", ServletUtil.getClientIP(request));
        } else {
            log.info("{} 未知设备", ServletUtil.getClientIP(request));
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("Header {} {}", headerName, request.getHeader(headerName));
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("Cookie {} {}", cookie.getName(), cookie.getValue());
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long endTime = System.currentTimeMillis();
        log.info("结束访问 {} {} 共用时 {}s", request.getMethod(), request.getRequestURL(), (endTime - startTime) / 1000.0);
    }
}
