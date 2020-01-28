package com.ninjeng.softwaricacollegestudentcommunicationportal.Model;

public class User {
    private String StudentId;
    private String Fullname;
    private String Email;
    private String Batch;
    private String Password;

    public User(String studentId, String fullname, String email, String batch) {
        StudentId = studentId;
        Fullname = fullname;
        Email = email;
        Batch = batch;
    }

    public User() {
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
