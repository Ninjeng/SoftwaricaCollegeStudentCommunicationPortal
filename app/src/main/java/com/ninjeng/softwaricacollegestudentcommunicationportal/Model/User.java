package com.ninjeng.softwaricacollegestudentcommunicationportal.Model;

public class User {
    private String id;
    private String StudentId;
    private String Fullname;
    private String Email;
    private String Batch;
    private String Password;
    private String profileImage;
    private String status;

    public User(String id,String studentId, String fullname, String email, String batch, String profileImage,String status) {
        this.id=id;
        StudentId = studentId;
        Fullname = fullname;
        Email = email;
        Batch = batch;
        this.profileImage = profileImage;
        this.status = status;
    }

    public User(String studentId, String fullname, String profileImage) {
        StudentId = studentId;
        Fullname = fullname;
        this.profileImage = profileImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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
