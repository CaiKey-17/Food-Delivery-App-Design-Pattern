package com.example.Api.model;

import com.example.Api.inheritance.IUser;
import com.example.Api.inheritance.IVisitor;

public class Restaurant extends IUser {
    private int id;
    private String name;
    private Double balance;
    private int working;
    private Double rating;
    private String image;
    private Double latitude;
    private Double longitude;
    private Double distance;

    public Restaurant() {
    }

    public Restaurant(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Restaurant(int id, String name, Double balance, int working, Double rating) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.working = working;
        this.rating = rating;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public int getWorking() {
        return working;
    }

    public void setWorking(int working) {
        this.working = working;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void accept(IVisitor visitor) {
        visitor.visitRestaurant();
    }
}
