package com.CS3560Project.structures.products;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.structures.User;
import com.CS3560Project.utility.Constants;
import com.CS3560Project.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class Product {
    private String ID;
    private String productName;
    private double price;
    private String description;
    private String dimensions;
    private double weight;

    private transient List<String> searchTerms;
    private transient ProductImages productImages;
    private transient List<ProductReview> productReviews;

    public Product(String id, String productName, double price, String description, String dimensions, double weight) {
        setID(id);
        setProductName(productName);
        setPrice(price);
        setDescription(description);
        setDimensions(dimensions);
        setWeight(weight);

        setSearchTerms(Arrays.asList(getProductName().split(Constants.SPACE_REGEX)));
    }

    @Override
    public String toString() {
        return "{ID:\"" + getID() +
                "\",productName:\"" + getProductName() +
                "\",price:" + getPrice() +
                ",description:\"" + getDescription() +
                "\",dimensions:\"" + getDimensions() +
                "\",weight:" + getWeight() +
                "}";
    }

    public static Product createInstance(ResultSet set) {
        try {
            // TODO Look at user for how to fill this in.
            // set field names are in discord
            return null;
        } catch (Exception e) {
            Utils.log("Could not parse returned list.");
            throw new ParseFailureException(set, Product.class);
        }
    }
}
