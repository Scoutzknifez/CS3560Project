package com.CS3560Project.structures.products;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.sqlworkers.insertion.Databasable;
import com.CS3560Project.utility.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage implements Databasable {
    private String id;
    private int priority; // Displays images in priority the smaller the number, the sooner it is shown, 0 is before 1
    private String base64;

    public Object[] fieldsToArray() {
        List<Object> fieldList = new ArrayList<>();

        fieldList.add(getId());
        fieldList.add(getPriority());
        fieldList.add(getBase64());

        return fieldList.stream().toArray(Object[]::new);
    }

    @Override
    public String toString() {
        return "{ID:\"" + getId() +
                "\",priority:" + getPriority() +
                ",base64:\"" + getBase64() +
                "\"}";
    }

    public static ProductImage createInstance(ResultSet set) {
        try {
            String id = set.getString("id");
            int priority = set.getInt("priority");
            String base64 = set.getString("base64image");

            return new ProductImage(id, priority, base64);
        } catch (Exception e) {
            Utils.log("Could not parse returned list.");
            throw new ParseFailureException(set, ProductImage.class);
        }
    }
}