package com.example.project_android.Model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Voucher_restaurant {
    private int id;
    private String name;
    private  int quantity;

    private  Double price;
    private String expiry;
    private int id_restaurant;
    private int active;

    public Voucher_restaurant() {
    }

    public Voucher_restaurant(String name, int quantity, Double price, String expiry, int id_restaurant, int active) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiry = expiry;
        this.id_restaurant = id_restaurant;
        this.active = active;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getExpiryAsLocalDateTime() {
        if (expiry == null) {
            return null; // Hoặc trả về giá trị mặc định
        }
        return LocalDateTime.parse(expiry); // Chuyển đổi chuỗi thành LocalDateTime
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry != null ? expiry.toString() : null; // Chuyển đổi LocalDateTime thành chuỗi
    }

    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
