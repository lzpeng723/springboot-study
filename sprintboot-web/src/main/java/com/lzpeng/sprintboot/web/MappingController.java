package com.lzpeng.sprintboot.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/mapping")
public class MappingController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping
    public List<Map<String, String>> allMapping() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : handlerMethods.entrySet()) {
            Map<String, String> map = new HashMap<>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            map.put("url", String.valueOf(p.getPatterns()));
            map.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            map.put("method", method.getMethod().getName()); // 方法名
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            map.put("type", String.valueOf(methodsCondition.getMethods().toString()));
            list.add(map);
        }
        return list;
    }
}
