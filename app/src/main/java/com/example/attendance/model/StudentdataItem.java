package com.example.attendance.model;

public class StudentdataItem {

    String rollno;
    String name;
    String currentDate;
    String attendence;

    public StudentdataItem(String rollno, String name, String currentDate, String attendence) {
        this.rollno = rollno;
        this.name = name;
        this.currentDate = currentDate;
        this.attendence = attendence;
    }

    public StudentdataItem() {
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getAttendence() {
        return attendence;
    }

    public void setAttendence(String attendence) {
        this.attendence = attendence;
    }
}
