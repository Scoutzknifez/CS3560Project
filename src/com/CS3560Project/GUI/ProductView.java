package com.CS3560Project.GUI;

import com.CS3560Project.GUIMain;
import com.CS3560Project.structures.products.*;
import com.CS3560Project.utility.Global;
import com.CS3560Project.utility.Utils;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

//How to display images
//Image image = Utils.bufferImageToFXImage(Utils.base64ToBufferImage(product.getProductImage().get(0).getBase64()));

public class ProductView
{
    protected ImageView imageView;
    protected Label addToCart = new Label("Add to Cart");
    protected Label remove = new Label("Remove from Cart");
    protected Label name = new Label();
    protected Label rating = new Label();
    protected VBox vbox;

    public static int count;

    public ProductView(Product product)
    {
        imageView = new ImageView(Utils.bufferedImageToFXImage(Utils.base64ToBufferedImage(product.getProductImages().get(0).getBase64().split(",")[1])));
        imageView.setFitHeight(120);
        imageView.setFitWidth(100);

        remove.setVisible(false);

        name.setText(Utils.capitalize(product.getProductName()));
        remove.setTextFill(Color.RED);

        rating.setText("Average Rating: " + Utils.formatDouble(product.getAverageRating(), 1));
        if(product.getAverageRating() >= 4)
            rating.setTextFill(Color.GREEN);
        else if(product.getAverageRating() >= 3 && product.getAverageRating() < 4)
            rating.setTextFill(Color.DARKGOLDENROD);
        else
            rating.setTextFill(Color.RED);

        vbox = new VBox(10, name, imageView, rating, addToCart, remove);
        vbox.setAlignment(Pos.CENTER);

        addToCart.setOnMouseClicked(e -> {
            Global.guiMainReference.changeCartLabel(1);
            GUIMain.cart.addProduct(product, 1);
            remove.setVisible(true);
            addToCart.setVisible(false);
        });

        remove.setOnMouseClicked(e -> {
            Global.guiMainReference.changeCartLabel(-1);
            GUIMain.cart.getInventory().remove(product);
            remove.setVisible(false);
            addToCart.setVisible(true);
        });

        addToCart.setOnMouseEntered(e -> addToCart.setStyle("-fx-underline: true"));
        addToCart.setOnMouseExited(e -> addToCart.setStyle("-fx-underline: false"));

        remove.setOnMouseEntered(e -> remove.setStyle("-fx-underline: true"));
        remove.setOnMouseExited(e -> remove.setStyle("-fx-underline: false"));
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
