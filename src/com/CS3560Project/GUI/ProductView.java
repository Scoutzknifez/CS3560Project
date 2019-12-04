package com.CS3560Project.GUI;

import com.CS3560Project.structures.products.*;
import com.CS3560Project.utility.Global;
import com.CS3560Project.utility.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;

//How to display images
//Image image = Utils.bufferImageToFXImage(Utils.base64ToBufferImage(product.getProductImage().get(0).getBase64()));

public class ProductView
{
    protected ImageView imageView;
    protected Button add = new Button("+");
    protected VBox vbox;

    public ProductView(Product product)
    {
        imageView = new ImageView(Utils.bufferedImageToFXImage(Utils.base64ToBufferedImage(product.getProductImages().get(0).getBase64())));
        imageView.setFitHeight(90);
        imageView.setFitWidth(80);
        vbox = new VBox(10, imageView, add);
        vbox.setAlignment(Pos.CENTER);

        add.setOnMouseClicked(e -> {
            Global.guiMainReference.shoppingCartLabel.setText("Hey im here");
        });
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
