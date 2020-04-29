package com.example.attendance.model;

import java.io.Serializable;

public class FacultyItem implements Serializable {
    private String facultyId;
    private String facultyName;
    private String facultyEmail;
    private String facultyPhone;
    private String facultyPassword;
    private String facultyUserID;


    public FacultyItem() {

    }


    public FacultyItem(String facultyId, String facultyName, String facultyEmail, String facultyPhone, String facultyPassword, String facultyUserID) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyPhone = facultyPhone;
        this.facultyPassword = facultyPassword;
        this.facultyUserID = facultyUserID;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyPhone() {
        return facultyPhone;
    }

    public void setFacultyPhone(String facultyPhone) {
        this.facultyPhone = facultyPhone;
    }

    public String getFacultyPassword() {
        return facultyPassword;
    }

    public void setFacultyPassword(String facultyPassword) {
        this.facultyPassword = facultyPassword;
    }

    public String getFacultyUserID() {
        return facultyUserID;
    }

    public void setFacultyUserID(String facultyUserID) {
        this.facultyUserID = facultyUserID;
    }
}
