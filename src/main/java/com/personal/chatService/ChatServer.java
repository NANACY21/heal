package com.personal.chatService;
import com.personal.kafkaService.producer.SystemMessageProducer;
import com.personal.pojo.msg.Message;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import com.personal.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**在线聊天 WebSocket+Redis
 * 聊天服务器 ChatServer
 * ws协议
 * https://blog.csdn.net/qq_38182820/article/details/83898433
 *
 * @author 李箎
 */
@Slf4j
//接口uri
@ServerEndpoint(value = "/chat/{username}")
@Component
public class ChatServer {
    private static SystemMessageProducer producer;
    private static UsersService service;
    //存储客户端的连接对象，每个客户端连接都会产生一个连接对象 当前在线的用户
    private static ConcurrentHashMap<String, ChatServer> map = new ConcurrentHashMap();
    //每个连接都会有自己的会话
    private Session session;
    //客户端用户名
    private String username;
    //此处定义静态变量，以在其他方法中获取到所有连接
    public static CopyOnWriteArraySet<ChatServer> wbSockets = new CopyOnWriteArraySet<>();

    @Autowired
    public void setSystemMessageProducer(SystemMessageProducer producer) {
        ChatServer.producer = producer;
    }

    @Autowired
    public void setUsersService(UsersService service) {
        ChatServer.service = service;
    }

    /**
     * 有客户端连接到服务器时调用
     *
     * @param session
     * @param username
     */
    @OnOpen
    @ResponseBody
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        map.put(this.username, this);
        //将此对象存入集合中以便在之后广播用，如果要实现一对一订阅，则类型对应为Map。由于这里广播就可以了随意用Set
        wbSockets.add(this);
        System.out.println(username + "已连接到ChatServer");
        System.out.println("当前客户端连接个数：" + getConnectionNum());
        System.out.println("New session insert,sessionId is " + this.session.getId());
    }

    @OnClose
    @ResponseBody
    public void onClose() {
        map.remove(username);
        System.out.println(username + "断开了服务器连接");
        //将socket对象从集合中移除，以便广播时不发送次连接。如果不移除会报错（需要测试）
        wbSockets.remove(this);
        System.out.println("A session remove,sessionId is " + this.session.getId());
    }

    @OnError
    @ResponseBody
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        System.out.println(this.username + "这个客户端出现了异常");
    }

    /**
     * 客户端向ChatServer发消息时调用
     *
     * @param message 客户端发送的内容
     * @param session 那个客户端会话
     * @throws IOException
     */
    @OnMessage
    @ResponseBody
    public void onMessage(String message, Session session) throws IOException {
        Message msg = (Message) Util.jsonToObject(new Message(), message);
        msg.setTime(Util.getTime());
        msg.setFromId(service.getUserIdByUsername(msg.getFrom()));
        msg.setToId(service.getUserIdByUsername(msg.getTo()));
        System.out.println("ChatServer收到来自[用户名：" + this.username + "，session id：" + session.getId() + "]客户端的消息：");
        System.out.println(msg.toString());
        System.out.println("当前客户端连接个数：" + getConnectionNum());
        //想消息队列生产消息
        producer.send(ConstPool.KAFKA_TOPIC1, com.personal.util.Util.objectToJson(msg));

        //实时向对方推送消息 这里没经过Kafka
        Set<Map.Entry<String, ChatServer>> entries = map.entrySet();
        for (Map.Entry<String, ChatServer> entry : entries) {
            //客户端标识
            String key = entry.getKey();
            //接收方如果在线 则将消息实时推送到接收方客户端！！！
            if (key.equals(msg.getTo())) {
                entry.getValue().send(message);
            }
            //发送方页面实时更新显示聊天内容
            if (key.equals(msg.getFrom())) {
                entry.getValue().send(message);
            }
        }
    }

    public void send(String message) throws IOException {
        if (this.session.isOpen()) {
            this.session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 客户端连接个数
     *
     * @return
     */
    public int getConnectionNum() {
        return map.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatServer that = (ChatServer) o;
        return session.equals(that.session) &&
                username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, username);
    }
}
