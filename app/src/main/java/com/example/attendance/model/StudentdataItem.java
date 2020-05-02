package com.example.attendance.model;

public class StudentdataItem {

    private String id;
    private String date;
    private String namesListPresent;
    private String namesListAbsent;
    private String numListPresent;
    private String numListAbsent;
    private String namesListPresentCount;
    private String namesListAbsentCount;
    private String facultyEmail;
    private String facultyRandomId;
    private String randomId;


    public StudentdataItem(String id, String date, String namesListPresent, String namesListAbsent, String numListPresent, String numListAbsent, String namesListPresentCount, String namesListAbsentCount, String facultyEmail, String facultyRandomId, String randomId) {
        this.id = id;
        this.date = date;
        this.namesListPresent = namesListPresent;
        this.namesListAbsent = namesListAbsent;
        this.numListPresent = numListPresent;
        this.numListAbsent = numListAbsent;
        this.namesListPresentCount = namesListPresentCount;
        this.namesListAbsentCount = namesListAbsentCount;
        this.facultyEmail = facultyEmail;
        this.facultyRandomId = facultyRandomId;
        this.randomId = randomId;
    }

    public String getNumListPresent() {
        return numListPresent;
    }

    public void setNumListPresent(String numListPresent) {
        this.numListPresent = numListPresent;
    }

    public String getNumListAbsent() {
        return numListAbsent;
    }

    public void setNumListAbsent(String numListAbsent) {
        this.numListAbsent = numListAbsent;
    }

    public StudentdataItem() {
    }

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyRandomId() {
        return facultyRandomId;
    }

    public void setFacultyRandomId(String facultyRandomId) {
        this.facultyRandomId = facultyRandomId;
    }

    public String getNamesListPresentCount() {
        return namesListPresentCount;
    }

    public void setNamesListPresentCount(String namesListPresentCount) {
        this.namesListPresentCount = namesListPresentCount;
    }

    public String getNamesListAbsentCount() {
        return namesListAbsentCount;
    }

    public void setNamesListAbsentCount(String namesListAbsentCount) {
        this.namesListAbsentCount = namesListAbsentCount;
    }

    public String getNamesListPresent() {
        return namesListPresent;
    }

    public void setNamesListPresent(String namesListPresent) {
        this.namesListPresent = namesListPresent;
    }

    public String getNamesListAbsent() {
        return namesListAbsent;
    }

    public void setNamesListAbsent(String namesListAbsent) {
        this.namesListAbsent = namesListAbsent;
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


}
