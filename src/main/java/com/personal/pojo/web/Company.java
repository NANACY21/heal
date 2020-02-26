package com.personal.pojo.web;

/**公司概要
 * @author 李箎
 */
public class Company {
    private Long id;

    private String name;
    private String introduction;
    //该公司发布的职位数
    private int positionNum;
    //该公司logo url
    private String logoUrl;

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
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getPositionNum() {
        return positionNum;
    }

    public void setPositionNum(int positionNum) {
        this.positionNum = positionNum;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", positionNum=" + positionNum +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
