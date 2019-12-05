package com.CS3560Project;

import com.CS3560Project.structures.Cart;
import com.CS3560Project.structures.products.Product;
import com.CS3560Project.structures.inventory.Inventory;
import com.CS3560Project.GUI.ProductView;

import com.CS3560Project.utility.Constants;
import com.CS3560Project.utility.Global;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.event.EventHandler;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.Month;

/**
 * Main running thread which hosts the GUI
 */
public class GUIMain extends Application {
    protected List<Product> searchResults;
    protected static Cart cart = null;
    protected Stage primaryStage;
    protected GridPane shoppingList = new GridPane();
    public Label shoppingCartLabel;

    @Override
    public void start(Stage stage) {
        Global.guiMainReference = this;
        login();
    }

    private void shoppingPage()
    {
        primaryStage.close();
        //Declarations/Initialization of all GUI components
        BorderPane borderpane = new BorderPane();

        Button searchButton = new Button("Search");
        TextField search = new TextField();
        HBox hboxSearch = new HBox(search, searchButton);

        shoppingList.setAlignment(Pos.CENTER);
        shoppingList.setPadding(new Insets(10,10,10,10));

        Label cart = new Label("Shopping Cart (" + ProductView.count + ")");
        shoppingCartLabel = cart;
        cart.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            try {
                shoppingCart();
            } catch(Exception e) {
                Alert notFound = new Alert(Alert.AlertType.ERROR, "There was an error finding the next page.");
                notFound.show();
            }
        });

        Label logout = new Label ("Logout");
        logout.setOnMouseClicked(event ->{
           Alert sure = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?");
           Optional<ButtonType> result = sure.showAndWait();
           if(result.isPresent() && result.get() == ButtonType.OK)
           {
               login();
           }
        });

        HBox hbox = new HBox(75, hboxSearch, cart, logout);
        hbox.setAlignment(Pos.CENTER);

        borderpane.setTop(hbox);
        borderpane.setCenter(shoppingList);
        borderpane.setPadding(new Insets(10,10,10,10));

        //Read text field for given search term to decide on chosen items
        search.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER)
            {
                if(search != null && search.getText() != null && !search.getText().equals(""))
                {
                    searchProducts(search.getText());
                }
            }
        });

        searchButton.setOnAction(event ->{
            if(search != null && search.getText() != null && !search.getText().equals(""))
            {
                searchProducts(search.getText());
            }
        });

        Scene scene = new Scene(borderpane,1000,600);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage = new Stage();
        primaryStage.setTitle("Online Shopping Network");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Searches through inventory to display products to screen after search bar is activated
    private void searchProducts(String input)
    {
        searchResults = new ArrayList<>();
        for (Inventory inv : Global.inventoryList) {
            searchResults.addAll(inv.search(input.split(Constants.SPACE_REGEX)));
        }
        populate(ProductView.createProductViews(searchResults));
    }

    private void populate(List<ProductView> views)
    {
        int itemIndex = 0;
        for(int i = 0; i < searchResults.size(); i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(itemIndex < searchResults.size())
                {
                    shoppingList.add(views.get(itemIndex).show(), j, i);
                    itemIndex++;
                }
            }
        }
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
        primaryStage.close();
        primaryStage = new Stage();


        primaryStage.setTitle("Shopping Cart");
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(400);


        Button checkOut = new Button("Checkout");
        checkOut.setOnAction(event -> {
            checkOutWin();
        });

        Button goBack = new Button("Go Back");
        goBack.setOnAction(event -> {
            shoppingPage();
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

    private void newUser(){
        primaryStage.close();
        primaryStage = new Stage();
        primaryStage.setTitle("Make a New Account");
        VBox all = new VBox();
        //TODO make newUser page
        Scene ex = new Scene(all);
        primaryStage.setScene(ex);
        primaryStage.show();
    }

    private void login (){
        if (primaryStage != null)
            primaryStage.close();

        primaryStage = new Stage();
        primaryStage.setTitle("Login to MarketPlace");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setMaxHeight(400);
        primaryStage.setMaxWidth(400);

        Label prompt = new Label("Please Sign In");
        Label invalid = new Label("");

        Button guestUser = new Button("Login as Guest");
        guestUser.setOnAction(event -> {
            Global.loggedInUser = Global.GUEST;
            cart = new Cart(Global.loggedInUser);
            shoppingPage();
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
            Global.loggedInUser = Global.getUserFromCredentials(userID.getText(), password.getText());
            if (Global.loggedInUser == Global.GUEST) {
                invalid.setText("Username or Password invalid.");
            } else {
                cart = new Cart(Global.loggedInUser);
                shoppingPage();
            }
        });

        VBox ex = new VBox(10, prompt, uID, userID, pw, password, submit, guestUser, invalid);
        ex.setPadding(new Insets(10));
        ex.setSpacing(20);
        ex.setAlignment(Pos.CENTER);

        //Scene
        Scene demo = new Scene(ex, 400, 400);
        primaryStage.setScene(demo);
        primaryStage.show();
    }

    private void checkOutWin() {
        primaryStage.close();
        primaryStage = new Stage();
        primaryStage.setTitle("Checkout");
        Scene ex;
        Separator separator = new Separator();
        Separator separator0 = new Separator();
        Separator separator1 = new Separator();
        Separator separator2 = new Separator();
        Separator separator3 = new Separator();
        final Label SHIPPING = new Label("Shipping address");
        final Label PAYMENT_METHOD = new Label("Payment Method");
        SHIPPING.setStyle("-fx-font-weight: bold");
        PAYMENT_METHOD.setStyle("-fx-font-weight: bold");

        //AMOUNT DUE and BUTTONS
        final Label TOTAL_ITEMS_PURCHASED = new Label("Total Items Purchased: " + cart.getCartSize());
        final Label AMOUNT_DUE = new Label("Amount Due: " + cart.getTotalCost());
        Button purchase = new Button("Purchase");
        BooleanBinding b = new BooleanBinding() {
            @Override
            protected boolean computeValue()
            {
                return (cart.getCartSize() == 0);
            }
        };
        purchase.disableProperty().bind(b);

        purchase.setOnAction(actionEvent -> {
            receipt();
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

        HBox loginBar = new HBox();
        if(Global.loggedInUser == Global.GUEST) {
            Label ReturningUser = new Label("Returning User? ");
            Label login = new Label("Login");
            loginBar.getChildren().addAll(ReturningUser, login);
            loginBar.setPadding(new Insets(10));
            loginBar.setSpacing(10);
            loginBar.setAlignment(Pos.CENTER);
            login.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    login.setStyle("-fx-underline: true");
                }
            });
            login.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    login.setStyle("-fx-underline: false");
                }
            });
            login.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                login();
            });
        }


        //form if the user is a guest
        //shipping info
        final Label ADDRESS_LINE1 = new Label("Address Line 1");
        final Label ADDRESS_LINE2 = new Label("Address Line 2");
        final Label CITY = new Label("City");
        final Label STATE = new Label("State");
        final Label ZIPCODE = new Label("Zip Code");

        //TODO parse the address string, the user can still change it as needed
        TextField aL1 = new TextField("");
        TextField aL2 = new TextField("");
        TextField city = new TextField("");
        TextField zip = new TextField("");
        ComboBox state = new ComboBox();
        state.getItems().addAll("AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", "DC", "DE",
                "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY",
                "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC",
                "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR",
                "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI",
                "VT", "WA", "WI", "WV", "WY");
        if(Global.loggedInUser != Global.GUEST){
            aL1.setText(Global.loggedInUser.getAddress().getHouseNumber());
            city.setText(Global.loggedInUser.getAddress().getCity());
            zip.setText("" + Global.loggedInUser.getAddress().getZip());
            state.setValue(Global.loggedInUser.getAddress().getState());
        }


        GridPane shippingForm = new GridPane();


        shippingForm.add(ADDRESS_LINE1, 0, 1);
        shippingForm.add(ADDRESS_LINE2, 0, 2);
        shippingForm.add(CITY, 0, 3);
        shippingForm.add(STATE, 0, 4);
        shippingForm.add(ZIPCODE, 0, 5);
        shippingForm.add(aL1, 1, 1);
        shippingForm.add(aL2, 1, 2);
        shippingForm.add(city, 1, 3);
        shippingForm.add(state, 1, 4);
        shippingForm.add(zip, 1, 5);
        shippingForm.setMinSize(30, 50);


        shippingForm.setHgap(10);
        shippingForm.setVgap(10);


        //payment info
        final ToggleGroup paymentChoices = new ToggleGroup();
        RadioButton paypal = new RadioButton("Paypal");
        RadioButton creditCard = new RadioButton("Credit Card");
        RadioButton debitCard = new RadioButton("Debit Card");
        paypal.setToggleGroup(paymentChoices);
        creditCard.setToggleGroup(paymentChoices);
        debitCard.setToggleGroup(paymentChoices);
        GridPane pm = new GridPane();
        pm.setHgap(10);
        pm.setPadding(new Insets(10));
        pm.setVgap(10);
        pm.setHgap(10);
        pm.setAlignment(Pos.CENTER_LEFT);
        paymentChoices.selectedToggleProperty().addListener(listener -> {
            if(paymentChoices.getSelectedToggle() == paypal){
                pm.getChildren().clear();
                Label email = new Label("Email");
                Label pw = new Label("PayPal Password");
                TextField e = new TextField();
                PasswordField p = new PasswordField();
                Button connect = new Button("Connect");

                pm.add(email, 0,0);
                pm.add(pw, 0, 1);
                pm.add(e, 1,0);
                pm.add(p, 1, 1);
                pm.add(connect, 1, 2);
            }

            else if(paymentChoices.getSelectedToggle() == creditCard || paymentChoices.getSelectedToggle() == debitCard){
                pm.getChildren().clear();
                Label name = new Label("Name on card");
                Label cardNumber = new Label("Card Number (no spaces or dashes)");
                Label expiration = new Label("Card Expiration Date");
                Label securityCode = new Label("Security Code");

                TextField n = new TextField();
                TextField cn = new TextField();
                TextField sc = new TextField();
                ComboBox month = new ComboBox();
                month.getItems().addAll(Month.values());
                ComboBox year = new ComboBox();
                for(int i = 2019; i<= 2030; i++){
                    year.getItems().add(i);
                }

                HBox date = new HBox();
                date.getChildren().addAll(month, year);
                date.setSpacing(10);
                date.setAlignment(Pos.CENTER);
                date.setPadding(new Insets(10));

                pm.add(name, 0,0);
                pm.add(cardNumber, 0, 1);
                pm.add(securityCode, 0, 2);
                pm.add(expiration, 0, 3);
                pm.add(n, 1, 0);
                pm.add(cn, 1, 1);
                pm.add(sc, 1, 2);
                pm.add(date, 1, 3);

            }
        });

        HBox toggle = new HBox();
        toggle.getChildren().addAll(paypal, creditCard, debitCard);
        toggle.setAlignment(Pos.CENTER);
        toggle.setSpacing(10);
        toggle.setPadding(new Insets(10));

        VBox s = new VBox();
        s.getChildren().addAll(SHIPPING, separator0, shippingForm, separator1, PAYMENT_METHOD, separator2, toggle, pm);
        s.setPadding(new Insets(10));
        s.setSpacing(10);
        s.setAlignment(Pos.CENTER_LEFT);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(back, purchase);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));
        buttons.setAlignment(Pos.CENTER);

        VBox all = new VBox();
        all.getChildren().addAll(loginBar, separator, s, separator3, TOTAL_ITEMS_PURCHASED, AMOUNT_DUE, buttons);
        all.setSpacing(10);
        all.setPadding(new Insets(10));
        all.setAlignment(Pos.CENTER);

        ScrollPane root = new ScrollPane(all);
        root.setFitToWidth(true);
        ex = new Scene(root, 500, 500);
        primaryStage.setScene(ex);
        primaryStage.show();
    }

    protected void receipt(){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Purchase Complete");
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(250);

        Label thanks = new Label("Thank you for shopping with us!");
        thanks.setStyle("-fx-font-weight: bold");
        Label action = new Label("A confirmation and receipt have been sent to " + Global.loggedInUser.getEmail());
        Button back = new Button("Back to shopping");
        back.setOnAction(event -> {
            primaryStage.close();
            shoppingPage();
        });

        Button closeWin = new Button("Logout and close");
        closeWin.setOnAction(event -> {
            primaryStage.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Logout successful");
            alert.showAndWait();
        });

        Separator s = new Separator();

        VBox all = new VBox();
        all.getChildren().addAll(thanks,s, action, back, closeWin);
        all.setPadding(new Insets(10));
        all.setSpacing(10);
        all.setAlignment(Pos.CENTER);

        Scene ex = new Scene(all, 300, 250);
        primaryStage.setScene(ex);
        primaryStage.show();
    }
}