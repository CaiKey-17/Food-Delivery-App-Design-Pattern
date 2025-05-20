package com.example.Api.concretebuilder;

import com.example.Api.builder.GroupDishBuilder;
import com.example.Api.model.GroupDish;

public class ConcreteGroupDishBuilder implements GroupDishBuilder {
    private String name;
    private String image;

    @Override
    public GroupDishBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GroupDishBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    @Override
    public GroupDish build() {
        return new GroupDish(name, image);
    }
}
