package com.personal.pojo.web;

/**前端json、MySQL查询结果集
 * @author 李箎
 */
public class ReleasePosition {

    /**
     * 职位信息
     */
    private Long id;

    private String name;

    private Long companyId;

    //发布该职位的用户id MySQL主键
    private Long userId;
    //发布该职位的用户名
    private String username;
    //发布该职位的用户号 19位随机数
    private String userLid;
    //发布该职位的用户认证信息
    private String auth;

    private String detail;
    private String city;
    private String needNum;

    private String workExp;

    private String eduBg;

    private Integer salary;

    private Integer salaryFloat;

    private String worktype;

    private String faceto;
    private int status;
    private String releaseTime;

    //内存数据
    private String del;

    //职位所属行业
    private String trade;
    //公司名称
    private String companyName;
    //公司信息
    private String companyInfo;

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public ReleasePosition() {
    }

    public Long getId() {
        return id;
    }

    public String getUserLid() {
        return userLid;
    }

    public void setUserLid(String userLid) {
        this.userLid = userLid;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "ReleasePosition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", userLid='" + userLid + '\'' +
                ", auth='" + auth + '\'' +
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
                ", trade='" + trade + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyInfo='" + companyInfo + '\'' +
                '}';
    }
}
