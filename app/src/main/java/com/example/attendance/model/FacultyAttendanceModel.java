package com.example.attendance.model;

/**
 * Created by Aneel on 03-Aug-18.
 */

public class FacultyAttendanceModel {
    String id,name,dept,year,sem,rollno,status,date;

    public FacultyAttendanceModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FacultyAttendanceModel(String id, String name, String dept, String year, String sem, String rollno, String status, String date) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.year = year;
        this.sem = sem;
        this.rollno = rollno;
        this.status = status;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
