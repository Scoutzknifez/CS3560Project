package com.CS3560Project.structures;

import com.CS3560Project.structures.products.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Inventory {
    private Map<Product, Integer> inventoryList;

    public Inventory() {
        setInventoryList(new HashMap<>());
    }

    /**
     * Increments a product's quantity in this inventory by a given quantity
     * @param productID     the product id to increment
     * @param incrementBy   the value to increment by
     * @return              true if set contains product / false if not found
     */
    public boolean incrementByValue(String productID, int incrementBy) {
        Product product = search(productID);
        if (product == null)
            throw new NullPointerException(productID);

        int newQuantity = getInventoryList().get(product) + incrementBy;
        getInventoryList().put(product, newQuantity);
        return true;
    }

    /**
     * Increments a product's quantity in this inventory by 1
     * @param productID     the product id to decrement
     * @return              true if set contains product / false if not found
     */
    public boolean increment(String productID) {
        return incrementByValue(productID, 1);
    }

    /**
     * Decrements a product's quantity in this inventory by a given quantity
     * @param productID     the product id to decrement
     * @param decrementBy   the value to decrement by
     * @return              true if set contains product / false if not found
     */
    public boolean decrementByValue(String productID, int decrementBy) {
        return incrementByValue(productID, (-1 * decrementBy));
    }

    /**
     * Decrements a product's quantity in this inventory by 1
     * @param productID     the product id to decrement
     * @return              true if set contains product / false if not found
     */
    public boolean decrement(String productID) {
        return decrementByValue(productID, 1);
    }

    /**
     * Gets a product from the inventory using a productID
     * @param productID     the product id to search for
     * @return              true if set contains product / false if not found
     */
    public Product search(String productID) {
        for (Product product : getInventoryList().keySet()) {
            if (product.getId().equals(productID))
                return product;
        }

        return null;
    }
}