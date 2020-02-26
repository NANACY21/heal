package com.personal.pojo.msg;

import com.personal.util.Util;

/**
 * 消息
 *
 * @author 李箎
 */
public class Message {
    //发送者用户名
    private String from;
    //接收者用户名
    private String to;
    //发送时间
    private String time;
    //发送的内容
    private Object data;
    //1-已读 0-未读
    private int alreadyRead;

    public Message(String from, String to, Object data) {
        this.from = from;
        this.to = to;
        this.time = Util.getTime();
        this.data = data;
        this.alreadyRead = 0;
    }

    public Message(String from, String to, String time, Object data) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(int alreadyRead) {
        this.alreadyRead = alreadyRead;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", time='" + time + '\'' +
                ", data=" + data +
                ", alreadyRead=" + alreadyRead +
                '}';
    }
}
