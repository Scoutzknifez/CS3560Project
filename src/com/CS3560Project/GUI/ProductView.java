package com.CS3560Project.GUI;

import com.CS3560Project.structures.products.*;
import com.CS3560Project.utility.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

//How to display images
//Image image = Utils.bufferImageToFXImage(Utils.base64ToBufferImage(product.getProductImage().get(0).getBase64()));

public class ProductView
{
    protected ImageView imageView;
    protected Button add = new Button("+");
    protected VBox vbox;

    public ProductView(Product product)
    {
        //System.out.println(Utils.base64ToBufferedImage(product.getProductImages().get(0).getBase64()));
        imageView = new ImageView(Utils.bufferedImageToFXImage(Utils.base64ToBufferedImage(product.getProductImages().get(0).getBase64())));
        vbox = new VBox(10, imageView, add);
    }

    public static List<ProductView> createProductViews(List<Product> products)
    {
        List<ProductView> views = new ArrayList<>();
        for(int i = 0; i < products.size(); i++)
        {
            views.add(new ProductView(products.get(i)));
        }
        return views;
    }

    public VBox show()
    {
        return vbox;
    }

    public ImageView getImageView()
    {
        return imageView;
    }

}
