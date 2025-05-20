package com.example.Api.pattern.command;
import com.example.Api.service.ServiceCart;

public class AddToCartCommand implements ICommand {
    private int idCustomer;
    private int idRestaurant;
    private int idDish;
    private int quantity;
    private ServiceCart serviceCart;

    public AddToCartCommand(ServiceCart serviceCart, int idCustomer, int idRestaurant, int idDish, int quantity) {
        this.serviceCart = serviceCart;
        this.idCustomer = idCustomer;
        this.idRestaurant = idRestaurant;
        this.idDish = idDish;
        this.quantity = quantity;
    }

    @Override
    public int execute() {
        return serviceCart.addToCart(idCustomer, idRestaurant, idDish, quantity);
    }
}

