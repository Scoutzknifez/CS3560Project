package com.CS3560Project.sqlworkers.insertion.types;

import com.CS3560Project.structures.products.ProductReview;

public class ProductReviewInsertion extends BaseInsertion {
    public ProductReviewInsertion(ProductReview productReview) {
        super(productReview.fieldsToArray());
    }
}
