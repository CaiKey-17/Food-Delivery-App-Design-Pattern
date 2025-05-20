package com.example.project_android.Model;

public class Dish {
    private int id;
    private String name;
    private Double price;
    private int quantity;
    private String describe_dish;
    private String image;
    private  Double rating;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private int id_fk_restaurant;
    private String id_fk_group_dish;

    public Dish() {
    }

    public Dish(String name, Double price, int quantity, String describe_dish, int id_fk_restaurant, String id_fk_group_dish) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.describe_dish = describe_dish;
        this.id_fk_restaurant = id_fk_restaurant;
        this.id_fk_group_dish = id_fk_group_dish;
    }

    public Dish(int id, String name, Double price, int quantity, String describe_dish, int id_fk_restaurant, String id_fk_group_dish) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.describe_dish = describe_dish;
        this.id_fk_restaurant = id_fk_restaurant;
        this.id_fk_group_dish = id_fk_group_dish;
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

    public String getDescribe_dish() {
        return describe_dish;
    }

    public void setDescribe_dish(String describe_dish) {
        this.describe_dish = describe_dish;
    }

    public int getId_fk_restaurant() {
        return id_fk_restaurant;
    }

    public void setId_fk_restaurant(int id_fk_restaurant) {
        this.id_fk_restaurant = id_fk_restaurant;
    }

    public String getId_fk_group_dish() {
        return id_fk_group_dish;
    }

    public void setId_fk_group_dish(String id_fk_group_dish) {
        this.id_fk_group_dish = id_fk_group_dish;
    }
}
