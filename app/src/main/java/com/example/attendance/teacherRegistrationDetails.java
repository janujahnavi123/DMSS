package com.example.attendance;

public class teacherRegistrationDetails {
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String subject;
    private String branch;

    public teacherRegistrationDetails() {

    }

    public teacherRegistrationDetails(String username, String password, String email, String mobile,
                                      String subject, String branch) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.subject = subject;
        this.branch = branch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
