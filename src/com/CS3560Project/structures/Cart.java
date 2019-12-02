package com.CS3560Project.structures;

import com.CS3560Project.structures.products.Product;
import com.CS3560Project.utility.Constants;
import com.CS3560Project.utility.Utils;
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

    /**
     * Removes all {@link Product} out of the shopping cart.
     */
    public void empty() {
        cartItems.clear();
    }

    /**
     * Return the total price of items in the cart
     * @return  total price
     */
    public double getTotalCost() {
        double totalPrice = 0;

        for (Product product : cartItems)
            totalPrice += product.getPrice();

        return totalPrice + (totalPrice * Constants.TAX_RATE);
    }

    /**
     * Returns the carts total in the format #.##
     * @return  formatted total price
     */
    private String getTotal() {
        return Utils.formatDouble(getTotalCost(), 2);
    }

    /**
     * Returns the count of items in the shopping cart
     * @return  shopping cart item count
     */
    public int getCartSize() {
        return getCartItems().size();
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