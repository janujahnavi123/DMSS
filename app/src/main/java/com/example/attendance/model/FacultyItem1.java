package com.example.attendance.model;

import java.io.Serializable;

public class FacultyItem1 implements Serializable {
    private String facultyId;
    private String facultyName;
    private String facultyEmail;
    private String facultyPhone;
    private String facultyPassword;
    private String facultyDept;
    private String facultyYear;
    private String facultySem;
    private String facultySubject;
    private String facultyUserID;
    private String facultyRandomID;
    private String facultySubjectId;


    public FacultyItem1() {

    }
    public FacultyItem1(String facultyDept, String facultyYear, String facultySem, String facultySubject, String facultyRandomID,String facultySubjectId,String facultyEmail) {
        this.facultyDept = facultyDept;
        this.facultyYear = facultyYear;
        this.facultySem = facultySem;
        this.facultySubject = facultySubject;
        this.facultyRandomID = facultyRandomID;
        this.facultySubjectId = facultySubjectId;
        this.facultyEmail = facultyEmail;
    }

    public FacultyItem1(String facultyId, String facultyName, String facultyEmail, String facultyPhone, String facultyPassword, String facultyUserID) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyPhone = facultyPhone;
        this.facultyPassword = facultyPassword;
        this.facultyUserID = facultyUserID;
    }

    public String getFacultySubjectId() {
        return facultySubjectId;
    }

    public void setFacultySubjectId(String facultySubjectId) {
        this.facultySubjectId = facultySubjectId;
    }

    public String getFacultyPhone() {
        return facultyPhone;
    }

    public void setFacultyPhone(String facultyPhone) {
        this.facultyPhone = facultyPhone;
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
