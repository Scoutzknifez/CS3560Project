package com.CS3560Project.structures.products;

import com.CS3560Project.utility.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class Product {
    private String ID;
    private String productName;
    private double price;

    private transient List<String> searchTerms;
    private transient ProductImages productImages;
    private transient ProductInfo productInfo;
    private transient List<ProductReview> productReviews;

    public Product(String id, String productName, double price) {
        setID(id);
        setProductName(productName);
        setPrice(price);
        setSearchTerms(Arrays.asList(productName.split(Constants.SPACE_REGEX)));
    }

    @Override
    public String toString() {
        return "{ID:\"" + getID() + "\",productName:\"" + getProductName() + "\",price:" + getPrice() + "}";
    }
}
