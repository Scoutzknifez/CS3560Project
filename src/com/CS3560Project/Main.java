package com.CS3560Project;

import com.CS3560Project.forms.Template;
import com.CS3560Project.structures.Cart;
import com.CS3560Project.structures.PhoneNumber;
import com.CS3560Project.structures.products.Product;
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
        PhoneNumber ph = PhoneNumber.stringToPhoneNumber("(951)123-4567");
        System.out.println(ph);
    }

    private static void testSet() {
        PhoneNumber phoneNumber = new PhoneNumber(951, 5063229);

        User user = new User("1234", "Test", "User", phoneNumber, "33292 Testville", "cpp@cpp.edu", "wowWhatAPASS");
        Product product = new Product("987", Utils.capitalize("Test_Item"), 10.98);
        System.out.println(user);

        Cart cart = new Cart(user);
        cart.addProduct(product);
        System.out.println(cart);

        Utils.printList(Utils.filterByTerms(cart.getCartItems(), "Test"));
        System.out.println(Utils.formatDouble(cart.getTotalCost(), 2));

        JFrame frame = new JFrame(Constants.APPLICATION_TITLE);         // Parent JFrame with title
        Template template = new Template();                             // Class representing GUI Form
        frame.setContentPane(template.getPanel1());                     // Content Pane is set to templates panel1
        frame.pack();
        frame.setVisible(true);
    }
}