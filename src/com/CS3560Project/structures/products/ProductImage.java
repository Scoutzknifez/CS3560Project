package com.CS3560Project.structures.products;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.utility.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.Image;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Image -> Base64
 * https://stackoverflow.com/questions/36492084/how-to-convert-an-image-to-base64-string-in-java
 * Base64 -> Image
 * https://stackoverflow.com/questions/23979842/convert-base64-string-to-image
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    private String id;
    private int priority; // TODO Change DB to have this, display images in priority the smaller the number, the sooner it is shown, 0 is before 1
    private String base64;

    public static ProductImage createInstance(ResultSet set) {
        try {
            // TODO Decode the base64 strings given back here and put into list
            return null;
        } catch (Exception e) {
            Utils.log("Could not parse returned list.");
            throw new ParseFailureException(set, ProductImage.class);
        }
    }
}