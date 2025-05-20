package com.example.Api.model;

import java.time.LocalDateTime;

import com.example.Api.inheritance.IVoucher;

public class VoucherSystem extends IVoucher {
    private int id_system;

    public VoucherSystem(String name, int quantity, Double price, LocalDateTime expiry, int id_system) {
        super(name, quantity, price, expiry);
        this.id_system = id_system;
    }

    public VoucherSystem(int id, String name, int quantity, Double price, LocalDateTime expiry, int id_system) {
        super(id, name, quantity, price, expiry);
        this.id_system = id_system;
    }

    public VoucherSystem() {
    }

    public int getId_system() {
        return id_system;
    }

    public void setId_system(int id_system) {
        this.id_system = id_system;
    }
}
