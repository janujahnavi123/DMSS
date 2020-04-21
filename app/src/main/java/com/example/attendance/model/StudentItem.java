package com.example.attendance.model;

public class StudentItem {
    private String studentId;
    private String studentName;
    private String studentRoll;
    private String studentDepartment;
    private String studentYear;
    private String studentSemester;
    private String studentSubject;
    private String studentRandomId;

    public StudentItem(String studentId, String studentName, String studentRoll, String studentDepartment, String studentYear, String studentSemester, String studentSubject, String studentRandomId) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentRoll = studentRoll;
        this.studentDepartment = studentDepartment;
        this.studentYear = studentYear;
        this.studentSemester = studentSemester;
        this.studentSubject = studentSubject;
        this.studentRandomId = studentRandomId;
    }

    public StudentItem() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(String studentRoll) {
        this.studentRoll = studentRoll;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(String studentDepartment) {
        this.studentDepartment = studentDepartment;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(String studentYear) {
        this.studentYear = studentYear;
    }

    public String getStudentSemester() {
        return studentSemester;
    }

    public void setStudentSemester(String studentSemester) {
        this.studentSemester = studentSemester;
    }

    public String getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(String studentSubject) {
        this.studentSubject = studentSubject;
    }

    public String getStudentRandomId() {
        return studentRandomId;
    }

    public void setStudentRandomId(String studentRandomId) {
        this.studentRandomId = studentRandomId;
    }
}