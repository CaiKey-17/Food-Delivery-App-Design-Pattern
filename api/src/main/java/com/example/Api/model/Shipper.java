package com.example.Api.model;

import com.example.Api.inheritance.IUser;
import com.example.Api.inheritance.IVisitor;

public class Shipper extends IUser {
    private double balance;
    private int working;
    private double rating;
    private String bienso;
    private int status_request;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getWorking() {
        return working;
    }

    public void setWorking(int working) {
        this.working = working;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBienso() {
        return bienso;
    }

    public void setBienso(String bienso) {
        this.bienso = bienso;
    }

    public int getStatus_request() {
        return status_request;
    }

    public void setStatus_request(int status_request) {
        this.status_request = status_request;
    }

    public void accept(IVisitor visitor) {
        visitor.visitShipper();
    }
}
