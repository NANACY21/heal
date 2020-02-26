package com.personal.pojo;

public class Project_exp {
    private Long id;

    private Long resumeId;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    @Override
    public String toString() {
        return "Project_exp{" +
                "id=" + id +
                ", resumeId=" + resumeId +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}