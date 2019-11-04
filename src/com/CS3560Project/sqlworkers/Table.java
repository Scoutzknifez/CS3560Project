package com.CS3560Project.sqlworkers;

import com.CS3560Project.structures.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Anything inside here must have a createInstance method with a ResultSet as a parameter.
 */
@Getter
@AllArgsConstructor
public enum Table {
    //PRODUCT(Product.class),
    //PRODUCT_IMAGES(ProductImages.class),
    //PRODUCT_REVIEWS(ProductReview.class),
    USERS(User.class);

    private Class constructorClass;
}
