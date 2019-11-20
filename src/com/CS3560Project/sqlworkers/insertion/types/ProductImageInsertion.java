package com.CS3560Project.sqlworkers.insertion.types;

import com.CS3560Project.structures.products.ProductImage;

public class ProductImageInsertion extends BaseInsertion{
    public ProductImageInsertion(ProductImage productImage) {
        super(productImage.fieldsToArray());
    }
}