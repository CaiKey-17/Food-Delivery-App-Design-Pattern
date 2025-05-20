package com.example.project_android.Model;

import java.time.LocalDateTime;

public class VoucherSystem {
    private int id;
    private String name;
    private  int quantity;

    private  Double price;
    private String expiry;
    private int id_system;

    public VoucherSystem(int id, String name, int quantity, Double price, String expiry, int id_system) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiry = expiry;
        this.id_system = id_system;
    }

    public VoucherSystem() {
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

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public int getId_system() {
        return id_system;
    }

    public void setId_system(int id_system) {
        this.id_system = id_system;
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
}
