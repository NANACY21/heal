package com.personal.pojo.msg;

import com.personal.util.Util;
import lombok.Data;

/**
 * 消息
 * 消息通知 发的信息
 *
 * @author 李箎
 */
@Data
//@ApiModel(description = "websocket消息内容")
public class Message {
    //发送者用户名
    private String from;
    //接收者用户名
    private String to;
    //发送时间
    private String time;
    //发送的内容
    private Object msgContent;
    //1-已读 0-未读
    private int alreadyRead;

    //在线人数
    private int onlineCount;

    //时间戳 消息队列入队时产生 唯一索引 有时间戳的消息才能删除
    private long timestamp;

    public Message(String from, String to, Object msgContent) {
        this.from = from;
        this.to = to;
        this.time = Util.getTime();
        this.msgContent = msgContent;
        this.alreadyRead = 0;
    }

    public Message(String from, String to, String time, Object msgContent) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.msgContent = msgContent;
    }

    public Message() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(Object msgContent) {
        this.msgContent = msgContent;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(int alreadyRead) {
        this.alreadyRead = alreadyRead;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", time='" + time + '\'' +
                ", msgContent=" + msgContent +
                ", alreadyRead=" + alreadyRead +
                ", onlineCount=" + onlineCount +
                ", timestamp=" + timestamp +
                '}';
    }
}
