package com.example.Api.inheritance;

import java.time.LocalDateTime;

public class IVoucher {
    private int id;
    private String name;
    private  int quantity;

    private  Double price;
    private LocalDateTime expiry;

    public IVoucher() {
    }

    public IVoucher(String name, int quantity, Double price, LocalDateTime expiry) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiry = expiry;
    }

    public IVoucher(int id, String name, int quantity, Double price, LocalDateTime expiry) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiry = expiry;
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

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
}

