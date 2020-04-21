package com.example.attendance.model;


public class AttendanceModel {
    private String id;
    private String date;
    private String name;
    private String number;
    private String status;

    public AttendanceModel() {
    }

    public AttendanceModel(String id, String date, String name, String number, String status) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.number = number;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
