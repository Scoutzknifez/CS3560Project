package com.CS3560Project.structures.products;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.sqlworkers.insertion.Databasable;
import com.CS3560Project.structures.User;
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
public class ProductReview implements Databasable {
    private String id;      // Compound Primary Key
    private String userId;  // Compound Alternate Key
    private double rating;
    private String review;

    public ProductReview(String userId, double rating, String review) {
        this (Utils.generateID(Table.PRODUCT_REVIEWS), userId, rating, review);
    }

    public ProductReview(Product product, User user, double rating, String review) {
        this (product.getId(), user.getID(), rating, review);
    }

    public Object[] fieldsToArray() {
        List<Object> fieldList = new ArrayList<>();

        fieldList.add(getId());
        fieldList.add(getUserId());
        fieldList.add(getRating());
        fieldList.add(getReview());

        return fieldList.stream().toArray(Object[]::new);
    }

    @Override
    public String toString() {
        return "{\n\trating:" + getRating() +
                ",\n\treview:\"" + getReview() +
                "\"\n}";
    }

    public static ProductReview createInstance(ResultSet set) {
        try {
            String id = set.getString("id");
            String userId = set.getString("user_id");
            double rating = set.getDouble("rating");
            String review = set.getString("review");

            return new ProductReview(id, userId, rating, review);
        } catch (Exception e) {
            throw new ParseFailureException(set, ProductReview.class);
        }
    }


}
