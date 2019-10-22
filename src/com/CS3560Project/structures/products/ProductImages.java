package com.CS3560Project.structures.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImages {
    private List<Image> imageList = new ArrayList<>();

    public void addImage(Image image) {
        imageList.add(image);
    }
}