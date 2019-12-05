package com.CS3560Project.GUI;

import com.CS3560Project.GUIMain;
import com.CS3560Project.structures.products.*;
import com.CS3560Project.utility.Global;
import com.CS3560Project.utility.Utils;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;

//How to display images
//Image image = Utils.bufferImageToFXImage(Utils.base64ToBufferImage(product.getProductImage().get(0).getBase64()));

public class ProductView
{
    protected ImageView imageView;
    protected Label addToCart = new Label("Add to Cart");
    protected Label name = new Label();
    protected Label rating = new Label();
    protected VBox vbox;

    public static int count;

    public ProductView(Product product)
    {
        imageView = new ImageView(Utils.bufferedImageToFXImage(Utils.base64ToBufferedImage(product.getProductImages().get(0).getBase64().split(",")[1])));
        imageView.setFitHeight(120);
        imageView.setFitWidth(100);

        name.setText(Utils.capitalize(product.getProductName()));
        rating.setText("Average Rating: " + Utils.formatDouble(product.getAverageRating(), 1));

        vbox = new VBox(10, name, imageView, rating, addToCart);
        vbox.setAlignment(Pos.CENTER);

        addToCart.setOnMouseClicked(e -> {
            count++;
            Global.guiMainReference.shoppingCartLabel.setText("Shopping Cart (" + count + ")");
            GUIMain.cart.addProduct(product, 1);
        });
        addToCart.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addToCart.setStyle("-fx-underline: true");
            }
        });
        addToCart.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addToCart.setStyle("-fx-underline: false");
            }
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
