package com.CS3560Project;

import com.CS3560Project.structures.Cart;
import com.CS3560Project.structures.User;
import com.CS3560Project.structures.products.Product;
import com.CS3560Project.structures.inventory.Inventory;

import com.CS3560Project.utility.Global;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main running thread which hosts the GUI
 */
public class GUIMain extends Application {
    // TODO Temp variables in place for quantity for now
    protected int totalCount = 0, count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0;

    protected Inventory inv = new Inventory(); // TODO This is old / outdated - Cody (Needs to not really have a reference to an inventory anymore but rather Global.inventoryList)
    protected List<Product> searchResults; // TODO Needs to be fetched from active inventories - Cody (This is something I will take part in on Monday)
    protected ArrayList<ImageView> images = new ArrayList<>(); // TODO This aint right - Cody (Products own images themselves)
    protected static User user = new User(null,null,null,null,null,null,null);
    protected static Cart cart = new Cart(user);

    @Override
    public void start(Stage stage) {
        Global.guiMainReference = this;
        checkOutWin();
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
        cart.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            try
            {
                shoppingCart();
            }
            catch(Exception e)
            {
                Alert notFound = new Alert(Alert.AlertType.ERROR, "There was an error finding the next page.");
                notFound.show();
            }

        });

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

        //Removes from shopping cart; updates quantity under item
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
            if(event.getCode() == KeyCode.ENTER)
            {
                searchResults(search.getText(), search);
            }
        });

        searchButton.setOnAction(event ->{
            if(!search.getText().equals(""))
            {
                searchResults(search.getText(), search);
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
    private void searchResults(String input, TextField searchBar)
    {
        List<Product> foundItems = inv.search(searchBar.getText().toString().split(" "));
    }

    //Kristine's Code
    //this is for the shopping cart windowpane
    private static GridPane makeItem(Product product) throws FileNotFoundException {
        System.out.println(product.toString());
        Label itemCount = new Label("");

        AtomicInteger count = new AtomicInteger();
        for(Product item: cart.getCartItems()) {
            if (item.equals(product))
                count.getAndIncrement();
        }

        Button plus = new Button("+");
        plus.autosize();
        plus.setOnAction(event -> {
            //adds more of item
            cart.addProduct(product);
            count.getAndIncrement();

            //increment and change the label
            itemCount.setText(count + "");
        });

        Button minus = new Button("-");
        minus.setMinSize(plus.getHeight(), plus.getWidth());
        minus.setOnAction(event -> {

            //remove Item
            cart.getCartItems().remove(product);
            count.getAndDecrement();

            //increment and change the label

            itemCount.setText(count + "");
        });


        Label itemName = new Label("item");

        //TODO figure out how to pull image from database
        // Images are already pulled from database and given to a product on creation (if the product has images) - Cody
        Image itemImage = new Image(new FileInputStream("C:\\Users\\Kristine\\Desktop\\purikura fun times!.JPG"));
        ImageView itemImageSet = new ImageView(itemImage);
        itemImageSet.setPreserveRatio(true);
        itemImageSet.setFitHeight(50);


        GridPane temp = new GridPane();
        temp.add(itemImageSet, 0, 0);
        temp.add(itemName, 1, 0);
        temp.add(minus, 2, 0);
        temp.add(itemCount, 3, 0);
        temp.add(plus, 4, 0);

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        ColumnConstraints col5 = new ColumnConstraints();

        col1.setPercentWidth(25);
        col2.setPercentWidth(50);
        col3.setPercentWidth(10);
        col4.setPercentWidth(15);
        col5.setPercentWidth(10);

        temp.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        temp.setPadding(new Insets(20));
        temp.setHalignment(itemImageSet, HPos.CENTER);
        temp.setHalignment(itemName, HPos.LEFT);
        temp.setHalignment(minus, HPos.CENTER);
        temp.setHalignment(itemCount, HPos.CENTER);
        temp.setHalignment(plus, HPos.CENTER);
        return temp;
    }

    private  void shoppingCart() throws FileNotFoundException {

        Stage primaryStage = new Stage();


        primaryStage.setTitle("Shopping Cart");
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(400);


        Button checkOut = new Button("Checkout");
        checkOut.setOnAction(event -> {
            checkOutWin();
        });

        Button goBack = new Button("Go Back");
        checkOut.setOnAction(event -> {
            //TODO figure how to go to previous page
        });

        VBox list = new VBox();
        list.setPadding(new Insets(10));

        //makes the rows for items
        ArrayList<Product> addedItems = new ArrayList<Product>();
        for(Product product: cart.getCartItems()) {
            if(!addedItems.contains(product)) {
                addedItems.add(product);
                list.getChildren().add(makeItem(product));
            }
        }
        list.setAlignment(Pos.BASELINE_CENTER);


        HBox navi = new HBox(goBack, checkOut);
        navi.setAlignment(Pos.CENTER);
        navi.setSpacing(10);


        BorderPane ex = new BorderPane();
        ex.setCenter(list);
        ex.setBottom(navi);
        BorderPane.setMargin(navi, new Insets(10));


        Scene demo = new Scene(ex);
        primaryStage.setScene(demo);
        primaryStage.show();
    }

    //TODO make a form
    private void newUser(){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Make a New Account");
        //Scene demo = new Scene();
    }

    private void login (){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Login to MarketPlace");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setMaxHeight(400);
        primaryStage.setMaxWidth(400);


        Label prompt = new Label("Please Sign In");
        Label invalid = new Label("");

        Button guestUser = new Button("Login as Guest");
        guestUser.setOnAction(event -> {
            //TODO link this to the shopping page
            // The user should be initialized as empty
        });


        //Text and Password Fields
        Label uID = new Label("Username:");
        TextField userID = new TextField();
        userID.setPadding(new Insets(10));

        Label pw = new Label("Password:");
        PasswordField password = new PasswordField();
        password.setPadding(new Insets(10));


        Button submit = new Button("Submit");
        BooleanBinding b = new BooleanBinding() {

            {
                super.bind(userID.textProperty(), password.textProperty());
            }

            @Override
            protected boolean computeValue()
            {
                return (userID.getText().isEmpty() || password.getText().isEmpty());
            }
        };
        submit.disableProperty().bind(b);
        submit.setOnAction(event -> {
            //TODO look through database for authentication
            // if fails, change the invalid label to notify the user that it's wrong
            // else make sure the User information is filled using database info
        });

        VBox ex = new VBox(10, prompt, uID, userID, pw, password, submit, guestUser, invalid);
        ex.setPadding(new Insets(10));
        ex.setSpacing(20);
        ex.setAlignment(Pos.CENTER);

        //Scene
        Scene demo = new Scene(ex);
        primaryStage.setScene(demo);
        primaryStage.show();
    }

    private void checkOutWin() {
        Stage primaryStage = new Stage();
        Separator separator = new Separator();
        Separator separator0 = new Separator();
        Separator separator1 = new Separator();
        Separator separator2 = new Separator();

        //TODO Make sure this only pops up if not logged in
        Label ReturningUser = new Label("Returning User? ");
        Label login = new Label("Login");
        HBox loginBar = new HBox();
        loginBar.getChildren().addAll(ReturningUser, login);
        loginBar.setPadding(new Insets(10));
        loginBar.setSpacing(10);
        loginBar.setAlignment(Pos.CENTER);


        //form if the user is a guest
        //shipping info
        final Label SHIPPING = new Label("Shipping address");
        final Label ADDRESS_LINE1 = new Label("Address Line 1");
        final Label ADDRESS_LINE2 = new Label("Address Line 2");
        final Label CITY = new Label("City");
        final Label STATE = new Label("State");
        final Label COUNTRY = new Label("Country");
        final Label ZIPCODE = new Label("Zip Code");

        TextField aL1 = new TextField();
        TextField aL2 = new TextField();
        TextField city = new TextField();
        TextField state = new TextField();
        TextField country = new TextField();
        TextField zip = new TextField();

        GridPane shippingForm = new GridPane();
        shippingForm.add(ADDRESS_LINE1, 0, 1);
        shippingForm.add(ADDRESS_LINE2, 0, 2);
        shippingForm.add(CITY, 0, 3);
        shippingForm.add(STATE, 0, 4);
        shippingForm.add(COUNTRY, 0, 5);
        shippingForm.add(ZIPCODE, 0 , 6);
        shippingForm.add(aL1, 1, 1);
        shippingForm.add(aL2, 1, 2);
        shippingForm.add(city, 1, 3);
        shippingForm.add(state, 1, 4);
        shippingForm.add(country, 1, 5);
        shippingForm.add(zip, 1 , 6);
        shippingForm.setMinSize(30, 50);
        shippingForm.setHgap(10);


        //payment info
        final Label PAYMENT_METHOD = new Label("Payment Method");
        final ToggleGroup paymentChoices = new ToggleGroup();
        RadioButton paypal = new RadioButton("Paypal");
        RadioButton creditCard = new RadioButton("Credit Card");
        RadioButton debitCard = new RadioButton("Debit Card");
        paypal.setToggleGroup(paymentChoices);
        creditCard.setToggleGroup(paymentChoices);
        debitCard.setToggleGroup(paymentChoices);


        HBox toggle = new HBox();
        toggle.getChildren().addAll(paypal, creditCard, debitCard);
        toggle.setAlignment(Pos.CENTER);
        toggle.setSpacing(10);
        toggle.setPadding(new Insets(10));


        VBox s = new VBox();
        s.getChildren().addAll(SHIPPING, separator0, shippingForm, separator1, PAYMENT_METHOD, separator2, toggle);
        s.setPadding(new Insets(10));
        s.setSpacing(10);
        s.setAlignment(Pos.CENTER_LEFT);

        //AMOUNT DUE
        final Label TOTAL_ITEMS_PURCHASED = new Label("Total Items Purchased: " + cart.getCartSize());
        final Label AMOUNT_DUE = new Label("Amount Due: " + cart.getTotalCost());
        Button purchase = new Button("Purchase");
        purchase.setOnAction(actionEvent -> {
            //TODO make a reciept page
        });

        Button back = new Button("Go Back");
        back.setOnAction(actionEvent -> {
            primaryStage.close();
            try {
                shoppingCart();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        VBox summary = new VBox();
        summary.getChildren().addAll(TOTAL_ITEMS_PURCHASED, AMOUNT_DUE);
        summary.setSpacing(10);
        summary.setPadding(new Insets(10));
        summary.setAlignment(Pos.CENTER_LEFT);


        HBox userInfo = new HBox();
        userInfo.getChildren().addAll(s, summary);
        userInfo.setSpacing(10);
        userInfo.setPadding(new Insets(10));
        userInfo.setAlignment(Pos.CENTER);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(back, purchase);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));
        buttons.setAlignment(Pos.CENTER);

        VBox all = new VBox();
        all.getChildren().addAll(loginBar,separator, userInfo, buttons);

        Scene ex = new Scene(all);
        primaryStage.setScene(ex);
        primaryStage.show();

    }

    protected void receipt(){

    }
}