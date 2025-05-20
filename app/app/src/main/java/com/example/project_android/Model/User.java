package com.example.project_android.Model;

import java.util.Date;

public class User {
    private int id;
    private String fullName;
    private  String gender;
    private Date birthday;
    private String address;
    private  String phoneNumber;
    private  String email;
    private  String username;
    private  String password;
    private String otp;
    private int active;
    private int id_fk_role;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User() {
    }

    public User(String fullName, String phoneNumber, String username, String password, int id_fk_role) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.id_fk_role = id_fk_role;
    }

    public User(String fullName, String phoneNumber, String username, String password, int id_fk_role, String otp) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.id_fk_role = id_fk_role;
        this.otp = otp;
    }
    public User(int id, String fullName, String gender, Date birthday, String address, String phoneNumber, String email, String username, String password, String otp, int active, int id_fk_role) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.otp = otp;
        this.active = active;
        this.id_fk_role = id_fk_role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getId_fk_role() {
        return id_fk_role;
    }

    public void setId_fk_role(int id_fk_role) {
        this.id_fk_role = id_fk_role;
    }
}

