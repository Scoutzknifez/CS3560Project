package com.CS3560Project.structures.products;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.sqlworkers.insertion.Databasable;
import com.CS3560Project.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Product implements Databasable {
    private String id;
    private String productName;
    private double price;
    private String description;
    private String dimensions;
    private double weight;

    private transient List<ProductImage> productImages;
    private transient List<ProductReview> productReviews;

    public Product(String id, String productName, double price, String description, String dimensions, double weight) {
        setId(id);
        setProductName(productName);
        setPrice(price);
        setDescription(description);
        setDimensions(dimensions);
        setWeight(weight);
    }

    public Object[] fieldsToArray() {
        List<Object> fieldList = new ArrayList<>();

        fieldList.add(getId());
        fieldList.add(getProductName());
        fieldList.add(getPrice());
        fieldList.add(getDescription());
        fieldList.add(getDimensions());
        fieldList.add(getWeight());

        return fieldList.stream().toArray(Object[]::new);
    }

    @Override
    public String toString() {
        return "{ID:\"" + getId() +
                "\",productName:\"" + getProductName() +
                "\",price:" + getPrice() +
                ",description:\"" + getDescription() +
                "\",dimensions:\"" + getDimensions() +
                "\",weight:" + getWeight() +
                "}";
    }

    public static Product createInstance(ResultSet set) {
        try {
            String id = set.getString("id");
            String productName = set.getString("productName");
            double price = set.getDouble("price");
            String product_description = set.getString("product_description");
            String dimensions = set.getString("dimensions");
            double weight = set.getDouble("weight");

            return new Product(id, productName, price, product_description, dimensions, weight);
        } catch (Exception e) {
            Utils.log("Could not parse returned list.");
            throw new ParseFailureException(set, Product.class);
        }
    }
}
