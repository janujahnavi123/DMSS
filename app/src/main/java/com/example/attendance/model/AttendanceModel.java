package com.example.attendance.model;


public class AttendanceModel {

    private String studentId;
    private String studentName;
    private String studentRoll;
    private String studentDepartment;
    private String studentYear;
    private String studentSemester;
    private String studentSubject;
    private String studentRandomId;
    private String studentSubjectId;
    private boolean checkbox=false;

    public AttendanceModel() {
    }


    public AttendanceModel(String studentId, String studentName, String studentRoll, String studentDepartment, String studentYear, String studentSemester, String studentSubject, String studentRandomId, String studentSubjectId, boolean checkbox) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentRoll = studentRoll;
        this.studentDepartment = studentDepartment;
        this.studentYear = studentYear;
        this.studentSemester = studentSemester;
        this.studentSubject = studentSubject;
        this.studentRandomId = studentRandomId;
        this.studentSubjectId = studentSubjectId;
        this.checkbox = checkbox;
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

    public String getStudentSubjectId() {
        return studentSubjectId;
    }

    public void setStudentSubjectId(String studentSubjectId) {
        this.studentSubjectId = studentSubjectId;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}
