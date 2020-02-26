package com.personal.pojo.web;

/**简历概要
 * @author 李箎
 */
public class ResumeOutline {
    //这个投简历的人的用户id
    private long userId;
    //姓名
    private String name;
    //年龄
    private int age;
    //邮箱
    private String email;
    //所在地
    private String location;
    //求职意向
    private String jobIntent;
    //个人评价
    private String selfeval;
    //学校
    private String school;
    //专业
    private String major;
    //学历
    private String eduBg;
    //工作经历-公司名称
    private String companyName;
    //工作经历-任职岗位
    private String position;
    //在该公司工作了多久（单位：月）
    private int workLength;

    //投递的职位的id
    private long positionId;
    //投递的职位的名称
    private String positionName;

    //临时 用户生日
    private String birthday;
    //临时-工作入职时间
    private String begindate;
    //临时-工作离职时间
    private String enddate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobIntent() {
        return jobIntent;
    }

    public void setJobIntent(String jobIntent) {
        this.jobIntent = jobIntent;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEduBg() {
        return eduBg;
    }

    public void setEduBg(String eduBg) {
        this.eduBg = eduBg;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getWorkLength() {
        return workLength;
    }

    public void setWorkLength(int workLength) {
        this.workLength = workLength;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSelfeval() {
        return selfeval;
    }

    public void setSelfeval(String selfeval) {
        this.selfeval = selfeval;
    }

    /**
     * 工作经历-入职时间
     * @return
     */
    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    /**
     * 工作经历-离职时间
     * @return
     */
    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "ResumeOutline{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", jobIntent='" + jobIntent + '\'' +
                ", selfeval='" + selfeval + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", eduBg='" + eduBg + '\'' +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", workLength=" + workLength +
                ", positionId=" + positionId +
                ", positionName='" + positionName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", begindate='" + begindate + '\'' +
                ", enddate='" + enddate + '\'' +
                '}';
    }
}
