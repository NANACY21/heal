package com.personal.pojo;

import java.util.Date;

public class Resume {
    private Long id;

    private Long userId;

    private Long idnum;

    private String name;

    private String sex;

    private Long telephone;

    private String email;

    private String qq;

    private String birthday;

    private String location;

    private String address;

    private String jobIntent;

    private String skill;
    private String selfeval;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIdnum() {
        return idnum;
    }

    public void setIdnum(Long idnum) {
        this.idnum = idnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getJobIntent() {
        return jobIntent;
    }

    public void setJobIntent(String jobIntent) {
        this.jobIntent = jobIntent == null ? null : jobIntent.trim();
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill == null ? null : skill.trim();
    }

    public String getSelfeval() {
        return selfeval;
    }

    public void setSelfeval(String selfeval) {
        this.selfeval = selfeval;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", userId=" + userId +
                ", idnum=" + idnum +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", telephone=" + telephone +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", birthday='" + birthday + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", jobIntent='" + jobIntent + '\'' +
                ", skill='" + skill + '\'' +
                ", selfeval='" + selfeval + '\'' +
                '}';
    }
}