package com.CS3560Project.structures.products;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductReview {
    private double rating;
    private String review;

    @Override
    public String toString() {
        return "{rating:" + getRating() + ",review:\"" + getReview() + "\"}";
    }
}
