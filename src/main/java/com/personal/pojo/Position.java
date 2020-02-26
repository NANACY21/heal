package com.personal.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 职位对象
 * 使用elasticsearch
 * 在SpringDataElasticSearch中，只需要操作对象，
 * 就可以操作elasticsearch中的数据
 *
 * @author 李箎
 */
@Document(indexName = "position", type = "docs", shards = 1, replicas = 1)
public class Position {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword)
    private Long companyId;

    @Field(type = FieldType.Keyword)
    private Long userId;

    private String detail;
    private String city;
    private String needNum;

    private String workExp;

    private String eduBg;

    @Field(type = FieldType.Integer)
    private Integer salary;

    private Integer salaryFloat;

    private String worktype;

    private String faceto;
    private int status;
    private String releaseTime;

    //内存数据
    private String del;

    public Position() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getNeedNum() {
        return needNum;
    }

    public void setNeedNum(String needNum) {
        this.needNum = needNum == null ? null : needNum.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getWorkExp() {
        return workExp;
    }

    public void setWorkExp(String workExp) {
        this.workExp = workExp == null ? null : workExp.trim();
    }

    public String getEduBg() {
        return eduBg;
    }

    public void setEduBg(String eduBg) {
        this.eduBg = eduBg == null ? null : eduBg.trim();
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getSalaryFloat() {
        return salaryFloat;
    }

    public void setSalaryFloat(Integer salaryFloat) {
        this.salaryFloat = salaryFloat;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype == null ? null : worktype.trim();
    }

    public String getFaceto() {
        return faceto;
    }

    public void setFaceto(String faceto) {
        this.faceto = faceto == null ? null : faceto.trim();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", userId=" + userId +
                ", detail='" + detail + '\'' +
                ", city='" + city + '\'' +
                ", needNum='" + needNum + '\'' +
                ", workExp='" + workExp + '\'' +
                ", eduBg='" + eduBg + '\'' +
                ", salary=" + salary +
                ", salaryFloat=" + salaryFloat +
                ", worktype='" + worktype + '\'' +
                ", faceto='" + faceto + '\'' +
                ", status=" + status +
                ", releaseTime='" + releaseTime + '\'' +
                ", del='" + del + '\'' +
                '}';
    }
}