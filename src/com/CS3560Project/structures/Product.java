package com.CS3560Project.structures;

import com.CS3560Project.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Product {
    private String ID;
    private String productName;
    private List<String> searchTerms;

    public Product(String id, String productName) {
        this(id, productName, Arrays.asList(productName.split(Constants.SPACE_REGEX)));
    }

    @Override
    public String toString() {
        return "{ID:\"" + getID() + "\",productName:\"" + getProductName() + "\"}";
    }
}
