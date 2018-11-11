package com.lzpeng.springboot.websocket.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
@CrossOrigin
@ServerEndpoint(value = "/socket/{userId}")
public class SocketServer {

    private Session session;
    private static Map<String, Session> sessionPool = new ConcurrentHashMap<>();
    private static Map<String, String> sessionIds = new ConcurrentHashMap<>();

    @OnOpen
    public void open(Session session, @PathParam(value = "userId") String userId) {
        this.session = session;
        sendAll(userId);
        sessionPool.put(userId, session);
        sessionIds.put(session.getId(), userId);
        String json = sessionIds.values().stream().collect(Collectors.joining("\",\"", "[\"", "\"]"));
        sendMessage(json, userId);
    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject obj = JSONUtil.parseObj(message);
        var to = obj.getInt("to");
        var userId = List.copyOf(sessionIds.values()).get(to);
        sendMessage(message, userId);
        System.out.println("当前发送人 userId 为: " + userId + ", 发送内容为: " + message);
    }

    @OnClose
    public void onClose() {
        int index = List.copyOf(sessionIds.keySet()).indexOf(session.getId());
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
        sendAll(String.valueOf(index));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public static void sendMessage(String message, String userId) {
        var s = sessionPool.get(userId);
        if (s != null) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getOnlineNum() {
        return sessionPool.size();
    }

    public static String getOnlineUsers() {
        return String.valueOf(sessionIds.values());
    }

    public static void sendAll(String msg) {
        for (var key : sessionIds.keySet()) {
            sendMessage(msg, sessionIds.get(key));
        }
    }
}
