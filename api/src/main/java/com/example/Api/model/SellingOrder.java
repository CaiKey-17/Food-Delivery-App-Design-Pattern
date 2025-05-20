package com.example.Api.model;

public class SellingOrder {
    private String id;
    private int quantity;
    private  double totalFish;
    private double delivery_fee;
    private double voucherS;
    private double voucherR;
    private double total;
    private String note;
    private String process;
    private String send_order;
    private String receive_order;
    private int id_fk_customer;
    private int id_fk_shiper;
    private int id_fk_restaurant;
    private int id_voucher_system;
    private int id_voucher_restaurant;
    private Double longitude;
    private Double latitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public SellingOrder() {
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalFish() {
        return totalFish;
    }

    public void setTotalFish(double totalFish) {
        this.totalFish = totalFish;
    }

    public double getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(double delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public double getVoucherS() {
        return voucherS;
    }

    public void setVoucherS(double voucherS) {
        this.voucherS = voucherS;
    }

    public double getVoucherR() {
        return voucherR;
    }

    public void setVoucherR(double voucherR) {
        this.voucherR = voucherR;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getSend_order() {
        return send_order;
    }

    public void setSend_order(String send_order) {
        this.send_order = send_order;
    }

    public String getReceive_order() {
        return receive_order;
    }

    public void setReceive_order(String receive_order) {
        this.receive_order = receive_order;
    }

    public int getId_fk_customer() {
        return id_fk_customer;
    }

    public void setId_fk_customer(int id_fk_customer) {
        this.id_fk_customer = id_fk_customer;
    }

    public int getId_fk_shiper() {
        return id_fk_shiper;
    }

    public void setId_fk_shiper(int id_fk_shiper) {
        this.id_fk_shiper = id_fk_shiper;
    }

    public int getId_fk_restaurant() {
        return id_fk_restaurant;
    }

    public void setId_fk_restaurant(int id_fk_restaurant) {
        this.id_fk_restaurant = id_fk_restaurant;
    }

    public int getId_voucher_system() {
        return id_voucher_system;
    }

    public void setId_voucher_system(int id_voucher_system) {
        this.id_voucher_system = id_voucher_system;
    }

    public int getId_voucher_restaurant() {
        return id_voucher_restaurant;
    }

    public void setId_voucher_restaurant(int id_voucher_restaurant) {
        this.id_voucher_restaurant = id_voucher_restaurant;
    }
}
