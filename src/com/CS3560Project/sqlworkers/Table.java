package com.CS3560Project.sqlworkers;

import com.CS3560Project.structures.User;
import com.CS3560Project.structures.products.Product;
import com.CS3560Project.structures.products.ProductReview;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Table {
    PRODUCT(Product.class),
    PRODUCT_REVIEWS(ProductReview.class),
    USERS(User.class);

    private Class constructorClass;
}
