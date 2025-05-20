package com.example.Api.builder;

import com.example.Api.model.GroupDish;

public interface GroupDishBuilder {
    GroupDishBuilder setName(String name);

    GroupDishBuilder setImage(String image);

    GroupDish build();
}
