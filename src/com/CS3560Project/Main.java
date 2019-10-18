package com.CS3560Project;

import com.CS3560Project.forms.Template;
import com.CS3560Project.structures.Cart;
import com.CS3560Project.structures.Product;
import com.CS3560Project.structures.User;
import com.CS3560Project.utility.Constants;
import com.CS3560Project.utility.Utils;

import javax.swing.*;

public class Main {
    /**
     * Test main to show how to use lombok methods
     * @param args  program arguments
     */
    public static void main(String[] args) {
        User user = new User("1234", "Test", "User");
        Product product = new Product("987", Utils.capitalize("Test_Item"), 10.98);

        Cart cart = new Cart(user);
        cart.addProduct(product);
        System.out.println(cart);

        Utils.printList(Utils.filterByTerms(cart.getCartItems(), "Test"));
        System.out.println(Utils.formatDouble(cart.getTotalCost(), 2));

        JFrame frame = new JFrame(Constants.APPLICATION_TITLE);
        Template template = new Template();
        frame.setContentPane(template.getPanel1());
        frame.pack();
        frame.setVisible(true);
    }
}