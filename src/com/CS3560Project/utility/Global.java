package com.CS3560Project.utility;

import com.CS3560Project.GUIMain;
import com.CS3560Project.sqlworkers.SQLHelper;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.structures.AccountRank;
import com.CS3560Project.structures.User;
import com.CS3560Project.structures.inventory.Inventory;
import com.CS3560Project.structures.inventory.InventorySlot;
import com.CS3560Project.structures.products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Global {
    public static GUIMain guiMainReference = null;
    public static Random random = new Random();
    public static User loggedInUser = null;
    public static final User GUEST = new User("Guest", "Guest", "", null, null, "", "", AccountRank.BASIC);

    // List of active inventories
    public static List<Inventory> inventoryList = new ArrayList<>();

    // List of active products
    public static List<Product> productList = new ArrayList<>();

    // List of active users
    public static List<User> userList = new ArrayList<>();

    public static void initialize() {
        //System.out.println("[-----PRODUCTS-----]");
        List<?> list = SQLHelper.getFromTable(Table.PRODUCTS);
        list.forEach(product -> productList.add((Product) product));
        //Utils.printList(list);

        //System.out.println("[-----USERS-----]");
        list = SQLHelper.getFromTable(Table.USERS);
        list.forEach(user -> userList.add((User) user));
        //tils.printList(list);

        //System.out.println("[-----INVENTORYSLOTS-----]");
        list = SQLHelper.getFromTable(Table.INVENTORIES);
        //Utils.printList(list);

        constructInventories((List<InventorySlot>) list);
    }

    private static void constructInventories(List<InventorySlot> slots) {
        for (InventorySlot slot : slots) {
            getInventory(slot.getInventory().getId()).getInventory().put(slot.getProduct(), slot.getStock());
        }
    }

    public static Inventory getInventory(String id) {
        for (Inventory inventory : inventoryList) {
            if (inventory.getId().equals(id)) {
                return inventory;
            }
        }

        User user = getUser(id);
        if (user != null) {
            Inventory newInv = new Inventory(user);
            inventoryList.add(newInv);
            return newInv;
        }

        return null;
    }

    public static Product getProduct(String id) {
        for (Product product : productList) {
            if (product.getId().equals(id))
                return product;
        }

        throw new NullPointerException("Could not find product (" + id + ")");
    }

    public static User getUser(String id) {
        for (User user : userList) {
            if (user.getID().equals(id))
                return user;
        }

        return null;
    }

    public static User getUserFromCredentials(String username, String password) {
        for (User user : userList)
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password))
                return user;

        return GUEST;
    }
}
