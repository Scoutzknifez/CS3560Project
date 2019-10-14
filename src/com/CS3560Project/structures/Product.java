package com.CS3560Project.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private String ID;
    private String productName;

    @Override
    public String toString() {
        return "{ID:\"" + getID() + "\",productName:\"" + getProductName() + "\"}";
    }
}
