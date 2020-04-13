package com.personal.pojo;

/**
 * @author 李箎
 */
public class Users {
    private Long id;

    private Long companyId;

    private String username;

    private String password;

    private Integer userType;

    private String userId;

    private String email;
    /**
     * 用户身份认证信息
     */
    private String auth;

    /**
     * 验证码存进Redis
     */
    private String code;
    //前端 登录方式 1-密码 2-邮箱 默认1
    private String loginMode;

    //前端 新密码
    private String newPassword;
    //前端 新密码确认
    private String newPasswordConfirm;

    public String getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(String loginMode) {
        this.loginMode = loginMode;
    }

    /**
     * 重写构造器
     * @param username
     * @param password
     * @param code
     */
    public Users(String username, String password, String code) {
        if (username != null) {
            this.username = username;
        }
        if (password != null) {
            this.password = password;
        }
        if (code != null) {
            this.code = code;
        }
    }

    /**需要写
     * 默认构造器
     */
    public Users() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email == null ? null : email.trim();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", auth='" + auth + '\'' +
                ", code='" + code + '\'' +
                ", loginMode='" + loginMode + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newPasswordConfirm='" + newPasswordConfirm + '\'' +
                '}';
    }
}