package com.CS3560Project.sqlworkers;

import com.CS3560Project.sqlworkers.insertion.Databasable;
import com.CS3560Project.structures.User;
import com.CS3560Project.structures.products.Product;
import com.CS3560Project.structures.products.ProductImage;
import com.CS3560Project.structures.products.ProductReview;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enumerators in this class file MUST be named the same as
 * the table names inside of the database.
 *
 * The {@link Class} parameter for each parameter must implement {@link Databasable}
 * and must have a static method called "createInstance" with a ResultSet as a parameter.
 */
@Getter
@AllArgsConstructor
public enum Table {
    PRODUCTS(Product.class),
    PRODUCT_IMAGES(ProductImage.class),
    PRODUCT_REVIEWS(ProductReview.class),
    USERS(User.class);

    private Class constructorClass;
}
