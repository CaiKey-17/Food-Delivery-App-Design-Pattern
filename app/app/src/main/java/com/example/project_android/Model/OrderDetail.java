package com.example.project_android.Model;

public class OrderDetail {
    private int id;
    private double price;
    private int quantity;
    private double total;
    private String image;
    private String hd;
    private String name;
    private String id_fk_donhang;
    private int id_fk_dish;

    // Getter và Setter cho các trường
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_fk_donhang() {
        return id_fk_donhang;
    }

    public void setId_fk_donhang(String id_fk_donhang) {
        this.id_fk_donhang = id_fk_donhang;
    }

    public int getId_fk_dish() {
        return id_fk_dish;
    }

    public void setId_fk_dish(int id_fk_dish) {
        this.id_fk_dish = id_fk_dish;
    }
}
