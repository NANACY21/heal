package com.personal.pojo;

import org.apache.kafka.common.protocol.types.Field;

import java.util.Date;

public class Edu_exp {
    private Long id;

    private String school;

    private String major;

    private String begindate;

    private String enddate;

    private String ranking;

    private String eduBg;

    private String award;

    private Long resumeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking == null ? null : ranking.trim();
    }

    public String getEduBg() {
        return eduBg;
    }

    public void setEduBg(String eduBg) {
        this.eduBg = eduBg == null ? null : eduBg.trim();
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award == null ? null : award.trim();
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    @Override
    public String toString() {
        return "Edu_exp{" +
                "id=" + id +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", begindate=" + begindate +
                ", enddate=" + enddate +
                ", ranking='" + ranking + '\'' +
                ", eduBg='" + eduBg + '\'' +
                ", award='" + award + '\'' +
                ", resumeId=" + resumeId +
                '}';
    }
}