package com.example.project_android.Model;

import java.io.Serializable;

public class Driver implements Serializable {
    private String name;
    private String phone;
    private String idCard;
    private String licensePlate;
    private String profileImageUrl;
    private String idCardImageUrl;
    private String drivingLicenseImageUrl;
    private String status;

    public Driver(String name, String phone, String idCard, String licensePlate, String profileImageUrl, String idCardImageUrl, String drivingLicenseImageUrl, String status) {
        this.name = name;
        this.phone = phone;
        this.idCard = idCard;
        this.licensePlate = licensePlate;
        this.profileImageUrl = profileImageUrl;
        this.idCardImageUrl = idCardImageUrl;
        this.drivingLicenseImageUrl = drivingLicenseImageUrl;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getIdCardImageUrl() {
        return idCardImageUrl;
    }

    public void setIdCardImageUrl(String idCardImageUrl) {
        this.idCardImageUrl = idCardImageUrl;
    }

    public String getDrivingLicenseImageUrl() {
        return drivingLicenseImageUrl;
    }

    public void setDrivingLicenseImageUrl(String drivingLicenseImageUrl) {
        this.drivingLicenseImageUrl = drivingLicenseImageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}