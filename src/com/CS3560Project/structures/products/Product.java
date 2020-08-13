package com.CS3560Project.structures.products;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.sqlworkers.SQLHelper;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.sqlworkers.conditions.Conditional;
import com.CS3560Project.sqlworkers.insertion.Databasable;
import com.CS3560Project.utility.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
// DTO
public class Product implements Databasable {
    private String id;
    private String productName;
    private double price;
    private String description;
    private String dimensions;
    private double weight;

    // Fields that are not REQUIRED to be filled in when object is created, also not stored with product
    private transient List<ProductImage> productImages;
    private transient List<ProductReview> productReviews;

    public Product(String productName, double price, String description, String dimensions, double weight) {
        setId(Utils.generateID(Table.PRODUCTS));
        setProductName(productName);
        setPrice(price);
        setDescription(description);
        setDimensions(dimensions);
        setWeight(weight);
    }

    public Object[] fieldsToArray() {
        List<Object> fieldList = new ArrayList<>();

        fieldList.add(getId());
        fieldList.add(Utils.capitalize(getProductName()));
        fieldList.add(getPrice());
        fieldList.add(getDescription());
        fieldList.add(getDimensions());
        fieldList.add(getWeight());

        return fieldList.stream().toArray(Object[]::new);
    }

    /**
     * Gets the average rating of this product based off of current reviews
     * @return  The average rating
     */
    public double getAverageRating() {
        double avg = 0;
        for (ProductReview review : getProductReviews())
            avg += review.getRating();

        // Keeps the range from [0 - 5]
        return Math.max(0, Math.min(5, (avg / getProductReviews().size())));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String beforeConcatenation = "{\n\tID:\"" + getId() +
                "\",\n\tproductName:\"" + getProductName() +
                "\",\n\tprice:" + getPrice() +
                ",\n\tdescription:\"" + getDescription() +
                "\",\n\tdimensions:\"" + getDimensions() +
                "\",\n\tweight:" + getWeight();
        sb.append(beforeConcatenation);

        sb.append(",\n\tproductImages:[\n");
        String section;
        for (int i = 0; i < getProductImages().size(); i++) {
            //ProductImage pi = getProductImages()
            section = "\t\t" + getProductImages().get(i) + ",\n";
            sb.append(section);
        }

        sb.append("\t],\n\tproductReviews:[\n");
        for (int i = 0; i < getProductReviews().size(); i++) {
            section = "\t\t" + getProductReviews().get(i) + ",\n";
            sb.append(section);
        }
        sb.append("\t]\n}");
        return sb.toString();
    }

    public static Product createInstance(ResultSet set) {
        try {
            String id = set.getString("id");
            String productName = set.getString("productName");
            double price = set.getDouble("price");
            String product_description = set.getString("product_description");
            String dimensions = set.getString("dimensions");
            double weight = set.getDouble("weight");

            // Fields that are fetched and set from different databases
            Conditional idCondition = new Conditional("id", id);
            List<ProductImage> images = (List<ProductImage>)SQLHelper.getFromTableWithConditions(Table.PRODUCT_IMAGES, idCondition);
            List<ProductReview> reviews = (List<ProductReview>) SQLHelper.getFromTableWithConditions(Table.PRODUCT_REVIEWS, idCondition);

            return new Product(id, productName, price, product_description, dimensions, weight, images, reviews);
        } catch (Exception e) {
            throw new ParseFailureException(set, Product.class);
        }
    }
}
