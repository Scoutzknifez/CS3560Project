package com.CS3560Project;

import com.CS3560Project.structures.Cart;
import com.CS3560Project.structures.Product;
import com.CS3560Project.structures.User;

public class Main {
    /**
     * Test main to show how to use lombok methods
     * @param args  program arguments
     */
    public static void main(String[] args) {
        User user = new User("1234", "Test", "User");
        Product product = new Product("987", "TestItem");
        System.out.println(user);
        System.out.println(product);

        Cart cart = new Cart(user);
        cart.addProduct(product);
        System.out.println(cart);
    }
}