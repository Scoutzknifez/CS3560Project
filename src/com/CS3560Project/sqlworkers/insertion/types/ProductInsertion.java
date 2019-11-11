package com.CS3560Project.sqlworkers.insertion.types;

import com.CS3560Project.structures.products.Product;

public class ProductInsertion extends BaseInsertion {

    public ProductInsertion(Product product) {
        super(product.fieldsToArray());
    }
}
