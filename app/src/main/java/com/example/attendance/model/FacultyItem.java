package com.example.attendance.model;

import java.io.Serializable;

public class FacultyItem implements Serializable {
    private String facultyId;
    private String facultyName;
    private String facultyEmail;
    private String facultyPassword;
    private String facultyDept;
    private String facultyYear;
    private String facultySem;
    private String facultySubject;
    private String facultyUserID;
    private String facultyRandomID;


    public FacultyItem() {

    }

    public FacultyItem(String facultyId, String facultyName, String facultyEmail, String facultyPassword, String facultyDept, String facultyYear, String facultySem, String facultySubject, String facultyUserID, String facultyRandomID) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyPassword = facultyPassword;
        this.facultyDept = facultyDept;
        this.facultyYear = facultyYear;
        this.facultySem = facultySem;
        this.facultySubject = facultySubject;
        this.facultyUserID = facultyUserID;
        this.facultyRandomID = facultyRandomID;
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

    public String getFacultyPassword() {
        return facultyPassword;
    }

    public void setFacultyPassword(String facultyPassword) {
        this.facultyPassword = facultyPassword;
    }

    public String getFacultyDept() {
        return facultyDept;
    }

    public void setFacultyDept(String facultyDept) {
        this.facultyDept = facultyDept;
    }

    public String getFacultyYear() {
        return facultyYear;
    }

    public void setFacultyYear(String facultyYear) {
        this.facultyYear = facultyYear;
    }

    public String getFacultySem() {
        return facultySem;
    }

    public void setFacultySem(String facultySem) {
        this.facultySem = facultySem;
    }

    public String getFacultySubject() {
        return facultySubject;
    }

    public void setFacultySubject(String facultySubject) {
        this.facultySubject = facultySubject;
    }

    public String getFacultyUserID() {
        return facultyUserID;
    }

    public void setFacultyUserID(String facultyUserID) {
        this.facultyUserID = facultyUserID;
    }

    public String getFacultyRandomID() {
        return facultyRandomID;
    }

    public void setFacultyRandomID(String facultyRandomID) {
        this.facultyRandomID = facultyRandomID;
    }
}
