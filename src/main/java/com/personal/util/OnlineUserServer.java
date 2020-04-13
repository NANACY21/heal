package com.personal.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在线的用户连到该服务器
 *
 * @author 李箎
 */
@Slf4j
@ServerEndpoint(value = "/onlineUser/{username}")
@Component
public class OnlineUserServer {
    private String username;

    @OnOpen
    @ResponseBody
    public void onOpen(Session session, @PathParam("username") String username) {
        if (username == null || username.length() == 0) {
            String key = Util.getTimeStamp(10) + "";
            while (ConstPool.onlineCount.containsKey(key)) {
                key = Util.getTimeStamp(10) + "";
            }
            ConstPool.onlineCount.put(key, "");
            return;
        }
        this.username = username;
        ConstPool.onlineCount.put(username, "");
        System.out.println("[有人上线]在线人数：" + onlineUserCount());
    }
    @OnClose
    @ResponseBody
    public void onClose() {
        ConstPool.onlineCount.remove(username);
        System.out.println("[有人离线]在线人数：" + onlineUserCount());
    }
    @OnError
    @ResponseBody
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public static int onlineUserCount() {
        return ConstPool.onlineCount.size();
    }
}
