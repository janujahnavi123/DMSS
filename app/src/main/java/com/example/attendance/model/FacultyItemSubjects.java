package com.example.attendance.model;

import java.io.Serializable;

public class FacultyItemSubjects implements Serializable {


    private String facultyEmail;
    private String facultyDept;
    private String facultyYear;
    private String facultySem;
    private String facultySubject;
    private String facultyRandomID;
    private String facultySubjectId;


    public FacultyItemSubjects() {

    }
    public FacultyItemSubjects(String facultyDept, String facultyYear, String facultySem, String facultySubject, String facultyRandomID, String facultySubjectId, String facultyEmail) {
        this.facultyDept = facultyDept;
        this.facultyYear = facultyYear;
        this.facultySem = facultySem;
        this.facultySubject = facultySubject;
        this.facultyRandomID = facultyRandomID;
        this.facultySubjectId = facultySubjectId;
        this.facultyEmail = facultyEmail;
    }


    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
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

    public String getFacultyRandomID() {
        return facultyRandomID;
    }

    public void setFacultyRandomID(String facultyRandomID) {
        this.facultyRandomID = facultyRandomID;
    }

    public String getFacultySubjectId() {
        return facultySubjectId;
    }

    public void setFacultySubjectId(String facultySubjectId) {
        this.facultySubjectId = facultySubjectId;
    }
}
