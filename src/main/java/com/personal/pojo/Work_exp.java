package com.personal.pojo;

public class Work_exp {
    private Long id;

    private String company;

    private String department;

    private String position;

    private String worktype;

    private String begindate;

    private String enddate;

    private Long resumeId;

    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype == null ? null : worktype.trim();
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
        return "Work_exp{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", worktype='" + worktype + '\'' +
                ", begindate=" + begindate +
                ", enddate=" + enddate +
                ", resumeId=" + resumeId +
                ", detail='" + detail + '\'' +
                '}';
    }
}