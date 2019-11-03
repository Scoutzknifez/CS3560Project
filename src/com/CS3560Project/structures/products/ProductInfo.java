package com.CS3560Project.structures.products;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO
 * Think about moving this into the actual product, this is an unnecessary separate structure.
 */
@Getter
@AllArgsConstructor
public class ProductInfo {
    private String description;
    private String dimensions;
    private double weight;
    private int quantity;

    @Override
    public String toString() {
        return "{description:\"" + getDescription() +
                "\",dimensions:\"" + getDimensions() +
                "\",weight:" + getWeight() +
                ",quantity:" + getQuantity() +
                "}";
    }
}
