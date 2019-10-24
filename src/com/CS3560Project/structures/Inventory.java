package com.CS3560Project.structures;

import com.CS3560Project.structures.products.Product;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@AllArgsConstructor
public class Inventory {
    private List<Product> productList;

    public Inventory() {
        setProductList(new ArrayList<>());
    }

    public boolean decrement(String productID) {
        return false;
    }

    public boolean increment(String productID) {
        return false;
    }

    public boolean restock(String productID, int quantityChange) {
        return false;
    }

    public boolean search(String productID) {
        return false;
    }
}