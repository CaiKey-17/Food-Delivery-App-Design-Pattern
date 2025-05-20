package com.example.Api.pattern.command;

import com.example.Api.service.ServiceCart;

public class MinusToCartCommand implements ICommand {
    private String idOrder;
    private int idDish;
    private ServiceCart serviceCart;

    public MinusToCartCommand(ServiceCart serviceCart, String idOrder, int idDish) {
        this.serviceCart = serviceCart;
        this.idOrder = idOrder;
        this.idDish = idDish;
    }

    @Override
    public int execute() {
        return serviceCart.MinusToCart(idOrder, idDish);
    }
}

