package com.personal.pojo;


import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class NUser {
    @Max(value = 2000)
    private long uID;

    //该校验只适用于（生效于）该分组

    @NotBlank(message = "昵称不允许为空")
    private String nickname;

    @NotBlank(message = "密码不允许为空")
    private String password;

    public NUser() {
    }

    public NUser(long uID, String nickname, String password) {
        this.uID = uID;
        this.nickname = nickname;
        this.password = password;
    }

    public long getuID() {
        return uID;
    }

    public void setuID(long uID) {
        this.uID = uID;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "NUser{" +
                "uID=" + uID +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
