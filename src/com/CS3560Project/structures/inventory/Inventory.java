package com.CS3560Project.structures.inventory;

import com.CS3560Project.sqlworkers.SQLHelper;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.sqlworkers.conditions.AndConditional;
import com.CS3560Project.sqlworkers.conditions.Conditional;
import com.CS3560Project.structures.User;
import com.CS3560Project.structures.products.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class Inventory {
    private String id;
    private Map<Product, Integer> inventory = new HashMap<>();

    public Inventory(User user) {
        setId(user.getID());
    }

    /**
     * Increments a product's quantity in this inventory by a given quantity
     * @param productID     the product id to increment
     * @param incrementBy   the value to increment by
     * @return              true if set contains product / false if not found
     */
    public boolean incrementByValue(String productID, int incrementBy) {
        Product product = search(productID);
        if (product == null) {
            // Returns false if product is not in list
            return false;
        }

        int newQuantity = getInventory().get(product) + incrementBy;
        if (newQuantity >= 1)
            getInventory().put(product, newQuantity);
        else
            getInventory().remove(product);

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
        for (Product product : getInventory().keySet()) {
            if (product.getId().equals(productID))
                return product;
        }

        return null;
    }

    /**
     * Searches this inventory for any products that match the given search terms
     * @param searchTerms   The list of words to search by
     * @return              A list of matching products
     */
    public List<Product> search(String... searchTerms) {
        List<Product> productsFound = new ArrayList<>();

        if (searchTerms.length == 1) {
            Product found = search(searchTerms[0]);
            if (found != null) {
                productsFound.add(found);
            }
        }

        for (Product product : getInventory().keySet()) {
            for (String searchTerm : searchTerms) {
                if (Pattern.compile(Pattern.quote(searchTerm), Pattern.CASE_INSENSITIVE).matcher(product.getProductName()).find() && !productsFound.contains(product)) {
                    productsFound.add(product);
                }
            }
        }

        return productsFound;
    }

    /**
     * Changes the databases' stock count of the given product
     * @param product   The product to change stock of
     * @param newStock  The new stock value
     */
    public void updateStockOnProduct(Product product, int newStock) {
        SQLHelper.updateTable(Table.INVENTORIES, "stock", newStock, new AndConditional(new Conditional("id", getId()), new Conditional("product_id", product.getId())));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\tid:\"");
        sb.append(getId());
        sb.append("\",\n\tinventory:[");

        List<Product> products = getInventory().keySet().stream().collect(Collectors.toList());
        List<Integer> stock = getInventory().values().stream().collect(Collectors.toList());

        String section;
        for (int i = 0; i < products.size(); i++) {
            section = products.get(i).toString() + "," + stock.get(i);
            sb.append(section);
        }

        return sb.toString();
    }
}
