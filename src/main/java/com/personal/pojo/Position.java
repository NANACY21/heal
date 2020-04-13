package com.personal.pojo;

import com.personal.util.ConstPool;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 职位
 * 使用ES
 * 在SpringDataElasticSearch中，只需要操作对象，
 * 就可以操作ES中的数据
 *
 * @author 李箎
 */
@Data
@Document(indexName = ConstPool.INDEX_NAME, type = "position", shards = 3, replicas = 2)
public class Position {
    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword)
    private Long companyId;

    public Position(Long id, String name, Long companyId, Long userId, String detail, String workExp, String eduBg) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.userId = userId;
        this.detail = detail;
        this.workExp = workExp;
        this.eduBg = eduBg;
    }

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
    /**
     * 对于招聘者来说的
     */
    private int status;
    private String releaseTime;

    //内存数据
    private String del;

    /**
     * 投递状态 对于求职者来说的
     */
    private int postStatus;

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

    public int getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(int postStatus) {
        this.postStatus = postStatus;
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
                ", postStatus=" + postStatus +
                '}';
    }
}