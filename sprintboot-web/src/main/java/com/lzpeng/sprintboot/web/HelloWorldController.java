package com.lzpeng.sprintboot.web;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
public class HelloWorldController {

    private Map<String, Object> data = new HashMap<>();

    @GetMapping("device")
    public JSONObject device(@RequestHeader("user-agent") String userAgent, @CookieValue(value = "username", required = false) String username){
        JSONObject json = JSONUtil.createObj();
        json.put("time", new Date());
        json.put("userAgent", userAgent);
        json.put("username", username);
        json.put("version", "SpringBoot " + SpringBootVersion.getVersion());
        json.put("mainClass", WebApplication.class.getName());
        return json;
    }

    @GetMapping
    public Map<String, Object> get(){
        String currentClass = JvmUtil.getCurrentClass();
        String currentMethod = JvmUtil.getCurrentMethod();
        log.info("{}.{}()", currentClass, currentMethod);
        return data;
    }

    @GetMapping("{key}")
    public String get(@PathVariable("key") String key){
        String currentClass = JvmUtil.getCurrentClass();
        String currentMethod = JvmUtil.getCurrentMethod();
        log.info("{}.{}({})", currentClass, currentMethod, key);
        return String.valueOf(data.get(key));
    }

    @PutMapping
    @PostMapping
    @PatchMapping
    public Map<String, Object> put(String key, String value){
        String currentClass = JvmUtil.getCurrentClass();
        String currentMethod = JvmUtil.getCurrentMethod();
        log.info("{}.{}({}, {})", currentClass, currentMethod, key, value);
        data.put(key, value) ;
        return data;
    }

    @PutMapping("{key}/{value}")
    @PostMapping("{key}/{value}")
    @PatchMapping("{key}/{value}")
    public Map<String, Object> putRest(@PathVariable String key, @PathVariable String value){
        String currentClass = JvmUtil.getCurrentClass();
        String currentMethod = JvmUtil.getCurrentMethod();
        log.info("{}.{}({}, {})", currentClass, currentMethod, key, value);
        data.put(key, value) ;
        return Collections.singletonMap(key, value);
    }


    @DeleteMapping
    public Map<String, Object> delete(String key){
        String currentClass = JvmUtil.getCurrentClass();
        String currentMethod = JvmUtil.getCurrentMethod();
        log.info("{}.{}({})", currentClass, currentMethod, key);
        data.remove(key);
        return data;
    }
    @DeleteMapping("{key}")
    public String deleteRest(@PathVariable String key){
        String currentClass = JvmUtil.getCurrentClass();
        String currentMethod = JvmUtil.getCurrentMethod();
        log.info("{}.{}({})", currentClass, currentMethod, key);
        return String.valueOf(data.remove(key));
    }
}
