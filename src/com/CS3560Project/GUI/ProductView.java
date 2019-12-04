package com.CS3560Project.GUI;

import com.CS3560Project.structures.products.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ProductView
{
    protected ImageView imageView = new ImageView();
    protected Button add = new Button("+");
    protected VBox vbox = new VBox(10, imageView, add);

    public ProductView(Image image)
    {
        imageView = new ImageView(image);
    }

    public VBox show()
    {
        return vbox;
    }
}
