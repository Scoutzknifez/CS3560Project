package com.CS3560Project;

import com.CS3560Project.structures.products.Product;
import com.CS3560Project.utility.Constants;

import com.CS3560Project.utility.Global;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

/**
 * Main running thread which hosts the GUI
 */
public class GUIMain extends Application {
    protected int totalCount = 0, count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Global.guiMainReference = this;
//Temp variables in place for quantity for now


        ArrayList<Product> products = new ArrayList<>(); // TODO FetchController

        //Declarations/Initialization of all GUI components
        BorderPane borderpane = new BorderPane();
        GridPane shoppingList = new GridPane();

        Button searchButton = new Button("Search");
        TextField search = new TextField();
        HBox hboxSearch = new HBox(search, searchButton);

        Button add1 = new Button("+");
        Button add2 = new Button("+");
        Button add3 = new Button("+");
        Button add4 = new Button("+");
        Button add5 = new Button("+");
        Button add6 = new Button("+");

        Button sub1 = new Button("-");
        Button sub2 = new Button("-");
        Button sub3 = new Button("-");
        Button sub4 = new Button("-");
        Button sub5 = new Button("-");
        Button sub6 = new Button("-");

        Label quantity1 = new Label("0");
        Label quantity2 = new Label("0");
        Label quantity3 = new Label("0");
        Label quantity4 = new Label("0");
        Label quantity5 = new Label("0");
        Label quantity6 = new Label("0");

        //Hboxes and Vboxes to create shopping menu
        HBox hbox1 = new HBox(10, add1, quantity1, sub1);
        HBox hbox2 = new HBox(10, add2, quantity2, sub2);
        HBox hbox3 = new HBox(10, add3, quantity3, sub3);
        HBox hbox4 = new HBox(10, add4, quantity4, sub4);
        HBox hbox5 = new HBox(10, add5, quantity5, sub5);
        HBox hbox6 = new HBox(10, add6, quantity6, sub6);

        hbox1.setPadding(new Insets(10));
        hbox2.setPadding(new Insets(10));
        hbox3.setPadding(new Insets(10));
        hbox4.setPadding(new Insets(10));
        hbox5.setPadding(new Insets(10));
        hbox6.setPadding(new Insets(10));

        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox3.setAlignment(Pos.CENTER);
        hbox4.setAlignment(Pos.CENTER);
        hbox5.setAlignment(Pos.CENTER);
        hbox6.setAlignment(Pos.CENTER);

        VBox vbox1 = new VBox(/*item1.getImageView(),*/ hbox1);
        VBox vbox2 = new VBox(/*item2.getImageView(),*/ hbox2);
        VBox vbox3 = new VBox(/*item3.getImageView(),*/ hbox3);
        VBox vbox4 = new VBox(/*item4.getImageView(),*/ hbox4);
        VBox vbox5 = new VBox(/*item5.getImageView(),*/ hbox5);
        VBox vbox6 = new VBox(/*item6.getImageView(),*/ hbox6);

        vbox1.setAlignment(Pos.CENTER);
        vbox2.setAlignment(Pos.CENTER);
        vbox3.setAlignment(Pos.CENTER);
        vbox4.setAlignment(Pos.CENTER);
        vbox5.setAlignment(Pos.CENTER);
        vbox6.setAlignment(Pos.CENTER);

        shoppingList.add(vbox1, 0, 0);
        shoppingList.add(vbox2, 1, 0);
        shoppingList.add(vbox3, 2, 0);
        shoppingList.add(vbox4, 0, 1);
        shoppingList.add(vbox5, 1, 1);
        shoppingList.add(vbox6, 2, 1);

        shoppingList.setAlignment(Pos.CENTER);
        shoppingList.setPadding(new Insets(10,10,10,10));

        Label cart = new Label("Shopping Cart (0)");

        HBox hbox = new HBox(50, hboxSearch, cart);
        hbox.setAlignment(Pos.CENTER);

        borderpane.setTop(hbox);
        borderpane.setCenter(shoppingList);
        borderpane.setPadding(new Insets(10,10,10,10));

        //Action Events
        //Adding to shopping cart; updates quantity under item
        //May be able to bind data to labels later, if time permits. Will work with repeated overwrite for now
        add1.setOnAction(event -> {
            count1++;
            totalCount++;
            quantity1.setText("" + count1);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        add2.setOnAction(event -> {
            count2++;
            totalCount++;
            quantity2.setText("" + count2);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        add3.setOnAction(event -> {
            count3++;
            totalCount++;
            quantity3.setText("" + count3);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        add4.setOnAction(event -> {
            count4++;
            totalCount++;
            quantity4.setText("" + count4);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        add5.setOnAction(event -> {
            count5++;
            totalCount++;
            quantity5.setText("" + count5);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        add6.setOnAction(event -> {
            count6++;
            totalCount++;
            quantity6.setText("" + count6);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        //Removes from shopping cart; updates quanity under item
        sub1.setOnAction(event -> {
            count1--;
            totalCount--;
            quantity1.setText("" + count1);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        sub2.setOnAction(event -> {
            count2--;
            totalCount--;
            quantity2.setText("" + count2);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });
        sub3.setOnAction(event -> {
            count3--;
            totalCount--;
            quantity3.setText("" + count3);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        sub4.setOnAction(event -> {
            count4--;
            totalCount--;
            quantity4.setText("" + count4);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        sub5.setOnAction(event -> {
            count5--;
            totalCount--;
            quantity5.setText("" + count5);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        sub6.setOnAction(event -> {
            count6--;
            totalCount--;
            quantity6.setText("" + count6);
            cart.setText("Shopping Cart (" + totalCount + ")");
        });

        //Read text field for given search term to decide on chosen items
        search.setOnKeyPressed(event ->{
            if(event.getCode().equals(KeyCode.ENTER))
            {
                searchResults(search.getText());
            }
        });

        searchButton.setOnAction(event ->{
            if(!search.getText().equals(""))
            {
                searchResults(search.getText());
            }
        });







        Scene scene = new Scene(borderpane,400,400);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Online Shopping Network");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Searches through inventory to display products to screen after search bar is activated
    protected void searchResults(String input)
    {
        return;
    }
    }
}
