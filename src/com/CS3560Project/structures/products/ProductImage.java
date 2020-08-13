package com.CS3560Project.structures.products;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.sqlworkers.insertion.Databasable;
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
public class ProductImage implements Databasable {
    private String id;      // Compound Primary Key
    // Displays images in priority the smaller the number, the sooner it is shown, 0 is before 1
    private int priority;   // Compound Alternate Key
    private String base64;

    public ProductImage(Product product, int priority, String base64) {
        this (product.getId(), priority, base64);
    }

    public Object[] fieldsToArray() {
        List<Object> fieldList = new ArrayList<>();

        fieldList.add(getId());
        fieldList.add(getPriority());
        fieldList.add(getBase64());

        return fieldList.stream().toArray(Object[]::new);
    }

    @Override
    public String toString() {
        return "{\n\tID:\"" + getId() +
                "\",\n\tpriority:" + getPriority() +
                ",\n\tbase64:\"" + getBase64() +
                "\"\n}";
    }

    public static ProductImage createInstance(ResultSet set) {
        try {
            String id = set.getString("id");
            int priority = set.getInt("priority");
            String base64 = set.getString("base64image");

            return new ProductImage(id, priority, base64);
        } catch (Exception e) {
            throw new ParseFailureException(set, ProductImage.class);
        }
    }
}
