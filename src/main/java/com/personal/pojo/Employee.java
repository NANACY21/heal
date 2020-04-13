package com.personal.pojo;

/**
 * @author 李箎
 */
public class Employee {
    private Long id;

    private Long companyId;

    private String email;

    private String name;
    private String auth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }
}