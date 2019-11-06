package com.CS3560Project.structures.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.Image;
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
public class ProductImages {
    private List<Image> imageList = new ArrayList<>();

    public void addImage(Image image) {
        imageList.add(image);
    }
}