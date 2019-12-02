package com.CS3560Project.utility;

import com.CS3560Project.GUIMain;
import com.CS3560Project.sqlworkers.SQLHelper;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.structures.inventory.Inventory;
import com.CS3560Project.structures.products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Global {
    public static GUIMain guiMainReference = null;
    public static Random random = new Random();

    // List of active inventories
    public static List<Inventory> inventoryList = new ArrayList<>();

    // List of active products
    public static List<Product> productList = new ArrayList<>();



    public static void initialize() {
        System.out.println("[-----PRODUCTS-----]");
        List<?> list = SQLHelper.getFromTable(Table.PRODUCTS);
        Utils.printList(list);
        System.out.println("[-----INVENTORYSLOTS-----]");
        list = SQLHelper.getFromTable(Table.INVENTORIES);
        Utils.printList(list);
    }

    public static Inventory getInventory(String id) {
        for (Inventory inventory : inventoryList) {
            if (inventory.getId().equals(id)) {
                return inventory;
            }
        }

        Inventory newInv = new Inventory(id);
        inventoryList.add(newInv);
        return newInv;
    }

    public static Product getProduct(String id) {
        for (Product product : productList) {
            if (product.getId().equals(id))
                return product;
        }
        throw new NullPointerException("Could not find product (" + id + ")");
    }
}
