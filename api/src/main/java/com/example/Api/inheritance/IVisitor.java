package com.example.Api.inheritance;

import com.example.Api.model.Restaurant;
import com.example.Api.model.Shipper;
import com.example.Api.model.User;

import java.util.List;

public interface IVisitor {
    public List<Restaurant> visitRestaurant();
    public List<User> visitUser();
    public List<Shipper> visitShipper();
}
