package com.example.attendance.model;

public class StudentItem {
    private String studentId;
    private String studentName;
    private String studentRoll;





    public StudentItem(String studentId, String studentName, String studentRoll) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentRoll = studentRoll;


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
}