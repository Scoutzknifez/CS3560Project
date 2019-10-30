package com.CS3560Project;

import com.CS3560Project.forms.Template;
import com.CS3560Project.sqlworkers.GetWorker;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.sqlworkers.insertion.InsertWorker;
import com.CS3560Project.sqlworkers.insertion.types.UserInsertion;
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
        doingGet();
    }

    private static void doingGet() {
        GetWorker worker = new GetWorker(Table.USERS);
        Thread thread = new Thread(worker);
        thread.start();

        try {
            thread.join();
            for (Object obj : worker.getItems()) {
                System.out.println(obj + " of type " + obj.getClass());
            }
        } catch (Exception e) {
            Utils.log("Here when thread fails.");
        }
    }

    private static void doingInsert() {
        PhoneNumber phoneNumber = new PhoneNumber(951, 5063229);
        User user = new User("1234", "Test", "User", phoneNumber, "33292 Testville", "cpp@cpp.edu", "wowWhatAPASS");

        InsertWorker worker = new InsertWorker(Table.USERS, new UserInsertion(user));
        Thread thread = new Thread(worker);
        thread.start();

        try {
            thread.join();
        } catch (Exception e) {
            Utils.log("Here when thread fails.");
        }
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