package com.example.attendance.model;

public class SubjectItem {
    private String subjectId;
    private String department;
    private String year;
    private String semester;
    private String subject;
    private String randomId;
    private String subjectRandomId;

    public SubjectItem(String subjectId, String department, String year, String semester, String subject, String randomId,String subjectRandomId) {
        this.subjectId = subjectId;
        this.department = department;
        this.year = year;
        this.semester = semester;
        this.subject = subject;
        this.randomId = randomId;
        this.subjectRandomId = subjectRandomId;
    }

    public SubjectItem() {
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectRandomId() {
        return subjectRandomId;
    }

    public void setSubjectRandomId(String subjectRandomId) {
        this.subjectRandomId = subjectRandomId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }
}
