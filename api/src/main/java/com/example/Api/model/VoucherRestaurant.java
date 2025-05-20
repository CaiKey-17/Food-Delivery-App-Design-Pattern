package com.example.Api.model;

import java.time.LocalDateTime;
import com.example.Api.inheritance.IVoucher;

public class VoucherRestaurant extends IVoucher {
    private int id_restaurant;
    private int active;

    public VoucherRestaurant() {
    }

    public VoucherRestaurant(String name, int quantity, Double price, LocalDateTime expiry, int id_restaurant, int active) {
        super(name, quantity, price, expiry);
        this.id_restaurant = id_restaurant;
        this.active = active;
    }

    public VoucherRestaurant(int id, String name, int quantity, Double price, LocalDateTime expiry, int id_restaurant, int active) {
        super(id, name, quantity, price, expiry);
        this.id_restaurant = id_restaurant;
        this.active = active;
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
