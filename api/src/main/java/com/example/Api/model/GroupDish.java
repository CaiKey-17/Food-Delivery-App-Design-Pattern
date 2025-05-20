package com.example.Api.model;

public class GroupDish {
    private final String name;
    private final String image;

    public GroupDish(String name, String image) {
        this.name = name;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }



    @Override
    public String toString() {
        return "GroupDish{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
