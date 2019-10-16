package com.CS3560Project.structures;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Cart {
    private User owner;
    private List<Product> cartItems;

    public Cart(User owner) {
        setOwner(owner);
        setCartItems(new ArrayList<>());
    }

    public boolean addProduct(Product product) {
        return getCartItems().add(product);
    }

    public double getTotalCost() {
        double totalPrice = 0;

        for (Product product : cartItems)
            totalPrice += product.getPrice();

        return totalPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Cart Contents:{\n");

        for(Product product : getCartItems())
            sb.append("\t" + product + "\n");

        sb.append("}");

        return sb.toString();
    }
}