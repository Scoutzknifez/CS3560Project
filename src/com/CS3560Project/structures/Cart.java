package com.CS3560Project.structures;

import com.CS3560Project.structures.inventory.Inventory;
import com.CS3560Project.structures.products.Product;
import com.CS3560Project.utility.Constants;
import com.CS3560Project.utility.Global;
import com.CS3560Project.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class Cart extends Inventory {

    public Cart(User owner) {
        super(owner);
    }

    /**
     * Allows adding a product with desired amount to a shopping cart
     * @param product   The product the customer wants
     * @param quantity  The amount the customer wants
     */
    public void addProduct(Product product, int quantity) {
        if (!getInventory().containsKey(product)) {
            getInventory().put(product, quantity);
        } else {
            int old = getInventory().get(product);
            getInventory().remove(product);
            getInventory().put(product, old + quantity);
        }
    }

    /**
     * Updates the data stored in database after checkout process
     */
    public void finishCheckout() {
        // Iterate through all inventories pulled from database
        for (Inventory inventory : Global.inventoryList) {
            // Iterate through all products inside of an inventory
            for (Product product : inventory.getInventory().keySet()) {
                // The current cart has the same product as the inventory found
                if (getInventory().keySet().contains(product)) {
                    int newStockCount = inventory.getInventory().get(product) - getInventory().get(product);
                    inventory.updateStockOnProduct(product, newStockCount);
                }
            }
        }

        getInventory().clear();
    }

    /**
     * Removes all {@link Product} out of the shopping cart.
     */
    public void empty() {
        getInventory().clear();
    }

    /**
     * Return the total price of items in the cart
     * @return  total price
     */
    public double getTotalCost() {
        double totalPrice = 0;

        List<Product> products = getInventory().keySet().stream().collect(Collectors.toList());
        List<Integer> stock = getInventory().values().stream().collect(Collectors.toList());

        for (int i = 0; i < products.size(); i++) {
            totalPrice += (products.get(i).getPrice() * stock.get(i));
        }

        return totalPrice + (totalPrice * Constants.TAX_RATE);
    }

    /**
     * Returns the carts total in the format #.##
     * @return  formatted total price
     */
    public String getTotal() {
        return Utils.formatDouble(getTotalCost(), 2);
    }

    /**
     * Returns the count of items in the shopping cart
     * @return  shopping cart item count
     */
    public int getCartSize() {
        int size = 0;

        List<Integer> stock = getInventory().values().stream().collect(Collectors.toList());

        for (int i = 0; i < stock.size(); i++) {
            size += stock.get(i);
        }

        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Cart Contents:{\n");

        for(Product product : getInventory().keySet())
            sb.append("\t" + product + "\n");

        sb.append("}");

        return sb.toString();
    }
}