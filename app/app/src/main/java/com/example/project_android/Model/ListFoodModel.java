package com.example.project_android.Model;

public class ListFoodModel {
    int id;
    String name;
    String star;
    String location;
    int img;

    public ListFoodModel(){}

    public ListFoodModel(int id, String name, String star, String location, int img) {
        this.id = id;
        this.name = name;
        this.star = star;
        this.location = location;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
