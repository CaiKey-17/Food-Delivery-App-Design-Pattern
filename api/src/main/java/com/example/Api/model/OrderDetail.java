package com.example.Api.model;

public class OrderDetail {
    private int id;
    private Double price;
    private int quantity;
    private Double total;
    private String Id_fk_donhang;
    private int Id_fk_dish;
    private String image;
    private String hd;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public OrderDetail() {
    }

    public OrderDetail(Double price, int quantity, Double total, String id_fk_donhang, int id_fk_dish) {
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        Id_fk_donhang = id_fk_donhang;
        Id_fk_dish = id_fk_dish;
    }

    public OrderDetail(int id, Double price, int quantity, Double total, String id_fk_donhang, int id_fk_dish) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        Id_fk_donhang = id_fk_donhang;
        Id_fk_dish = id_fk_dish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getId_fk_donhang() {
        return Id_fk_donhang;
    }

    public void setId_fk_donhang(String id_fk_donhang) {
        Id_fk_donhang = id_fk_donhang;
    }

    public int getId_fk_dish() {
        return Id_fk_dish;
    }

    public void setId_fk_dish(int id_fk_dish) {
        Id_fk_dish = id_fk_dish;
    }
}
