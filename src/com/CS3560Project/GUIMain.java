package com.CS3560Project;

import com.CS3560Project.sqlworkers.SQLHelper;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.structures.*;
import com.CS3560Project.structures.products.Product;
import com.CS3560Project.structures.inventory.Inventory;
import com.CS3560Project.GUI.ProductView;

import com.CS3560Project.utility.Constants;
import com.CS3560Project.utility.Global;
import com.CS3560Project.utility.Utils;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.Month;
import java.util.stream.Collectors;

/**
 * Main running thread which hosts the GUI
 */
public class GUIMain extends Application {
    protected List<Product> searchResults;
    public static Cart cart = null;
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
        shoppingList.setHgap(20);
        shoppingList.setVgap(20);

        populate(ProductView.createProductViews(Global.productList), Global.productList);

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

        cart.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cart.setStyle("-fx-underline: true");
            }
        });
        cart.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cart.setStyle("-fx-underline: false");
            }
        });

        Label loginLabel = new Label("Login");
        Label logout = new Label ("Logout");
        if(Global.loggedInUser == Global.GUEST)
        {
            logout.setVisible(false);
        }
        else
        {
            loginLabel.setVisible(false);
        }

        logout.setOnMouseClicked(event ->{
           Alert sure = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?");
           Optional<ButtonType> result = sure.showAndWait();
           if(result.isPresent() && result.get() == ButtonType.OK)
           {
               login();
           }
        });
        logout.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logout.setStyle("-fx-underline: true");
            }
        });
        logout.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logout.setStyle("-fx-underline: false");
            }
        });

        loginLabel.setOnMouseClicked(event ->{
           login();
        });
        loginLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginLabel.setStyle("-fx-underline: true");
            }
        });
        loginLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginLabel.setStyle("-fx-underline: false");
            }
        });

        HBox hbox = new HBox(75, hboxSearch, cart, loginLabel, logout);
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
        populate(ProductView.createProductViews(searchResults), searchResults);
    }

    private void populate(List<ProductView> views, List<Product> products)
    {
        shoppingList.getChildren().clear();
        int itemIndex = 0;
        for(int i = 0; i < products.size(); i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(itemIndex < products.size())
                {
                    shoppingList.add(views.get(itemIndex).show(), j, i);
                    itemIndex++;
                }
            }
        }
    }

    /*private void populateMain(List<ProductView> views)
    {
        int itemIndex = 0;
        for(int i = 0; i < Global.productList.size(); i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(itemIndex < Global.productList.size())
                {
                    shoppingList.add(views.get(itemIndex).show(), j, i);
                    itemIndex++;
                }
            }
        }
    }*/

    //Kristine's Code
    //this is for the shopping cart windowpane
    protected GridPane makeItem(Product product) throws FileNotFoundException {
        System.out.println(product.toString());


        AtomicInteger count = new AtomicInteger();
        for(Product item: cart.getInventory().keySet().stream().collect(Collectors.toList())) {
            if (item.equals(product))
                count.getAndIncrement();
        }

        Label itemCount = new Label("" + count);
        Button plus = new Button("+");
        plus.autosize();
        plus.setOnAction(event -> {
            //adds more of item
            cart.addProduct(product, 1);
            count.getAndIncrement();

            //increment and change the label
            itemCount.setText(count + "");

            //Alters shopping cart label if they go back to shopping page
            changeCartLabel(1);
        });

        Button minus = new Button("-");
        minus.setMinSize(plus.getHeight(), plus.getWidth());
        minus.setOnAction(event -> {
            //remove Item
            cart.getInventory().remove(product);
            count.getAndDecrement();

            //decrement and change the label
            itemCount.setText(count + "");

            //Alters shopping cart label
            changeCartLabel(-1);
        });

        Label itemName = new Label(Utils.capitalize(product.getProductName()));

        ImageView itemImageSet = new ImageView(Utils.bufferedImageToFXImage(Utils.base64ToBufferedImage(product.getProductImages().get(0).getBase64().split(",")[1])));
        itemImageSet.setPreserveRatio(true);
        itemImageSet.setFitHeight(50);


        GridPane temp = new GridPane();
        Label x = new Label("x");
        x.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                x.setStyle("-fx-underline: true");
            }
        });
        x.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                x.setStyle("-fx-underline: false");
            }
        });
        x.addEventHandler(MouseEvent.MOUSE_CLICKED, eventDispatchChain -> {
            cart.getInventory().remove(product);
            temp.getChildren().clear();

        });
        temp.add(itemImageSet, 0, 0);
        temp.add(itemName, 1, 0);
        temp.add(minus, 2, 0);
        temp.add(itemCount, 3, 0);
        temp.add(plus, 4, 0);
        temp.add(x,5,0);
        temp.setHgap(10);
        temp.setVgap(10);
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


        VBox list = new VBox();
        Label emptyCart = new Label("Your cart is currently empty");
        emptyCart.setVisible(false);

        Button checkOut = new Button("Checkout");
        checkOut.setOnAction(event -> {
            checkOutWin();
        });
        if(cart.getCartSize() == 0){
            checkOut.setDisable(true);
        }
        else{
            checkOut.setDisable(false);
        }

        Button goBack = new Button("Go Back");
        goBack.setOnAction(event -> {
            shoppingPage();
        });

        if(cart.getCartSize() == 0)
            emptyCart.setVisible(true);

        Button clearCart = new Button("Clear Cart");
        clearCart.setOnAction(actionEvent -> {
            cart.empty();
            list.setVisible(false);
            emptyCart.setVisible(true);
            ProductView.count = 0;
            shoppingCartLabel.setText("Shopping Cart(" + ProductView.count + ")");
            checkOut.setDisable(true);
        });

        VBox vbox = new VBox(emptyCart, list);
        vbox.setAlignment(Pos.CENTER);
        list.setSpacing(10);
        list.setPadding(new Insets(10));

        //makes the rows for items
        ArrayList<Product> addedItems = new ArrayList<>();
        for(Product product: cart.getInventory().keySet().stream().collect(Collectors.toList())) {
            if(!addedItems.contains(product)) {
                addedItems.add(product);
                list.getChildren().add(makeItem(product));
            }
        }
        list.setAlignment(Pos.BASELINE_CENTER);


        HBox navi = new HBox(goBack, checkOut);
        navi.setAlignment(Pos.CENTER);
        navi.setSpacing(10);

        VBox buttons = new VBox();
        buttons.getChildren().addAll(navi, clearCart);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));


        BorderPane ex = new BorderPane();
        ex.setCenter(vbox);
        ex.setBottom(buttons);
        BorderPane.setMargin(navi, new Insets(10));


        Scene demo = new Scene(ex);
        primaryStage.setScene(demo);
        primaryStage.show();
    }

    private void newUser(){
        primaryStage.close();
        primaryStage = new Stage();
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(250);
        primaryStage.setTitle("Make a New Account");
        Separator s0 = new Separator();
        Separator s1 = new Separator();


        Label title = new Label("Creating New Account");
        Label emailInUse = new Label("");
        Label passwordsDontMatch = new Label("");


        final Label EMAIL = new Label("Email");
        final Label USERNAME = new Label("Username");
        final Label PASSWORD = new Label("Password");
        final Label RENTER_PASSWORD = new Label("Re-enter your password");
        final Label FIRST_NAME = new Label("First Name");
        final Label LAST_NAME = new Label("Last Name");
        final Label ADDRESS = new Label("Address Information");
        final Label ADD_LINE_1 = new Label("Address Line 1");
        final Label ADD_LINE_2 = new Label("Address Line 2");
        final Label CITY = new Label("City");
        final Label STATE = new Label("State");
        final Label ZIPCODE = new Label("Zip Code");
        final Label PHONE_NUMBER = new Label("Phone Number");


        TextField em = new TextField();
        TextField un = new TextField();
        PasswordField pw = new PasswordField();
        PasswordField rpw = new PasswordField();
        TextField fn = new TextField();
        TextField ln = new TextField();
        TextField al1 = new TextField();
        TextField al2 = new TextField();
        TextField city = new TextField();
        ComboBox state = new ComboBox();
        TextField zip = new TextField();
        TextField num = new TextField("(xxx)xxx-xxxx");

        List<String> abbreviations = new ArrayList<>();
        for (State stateObj : State.values())
            abbreviations.add(stateObj.getAbbreviation());
        state.getItems().addAll(abbreviations);

        Button cancel = new Button("Cancel");
        cancel.setOnAction(actionEvent -> {
            primaryStage.close();
            login();
        });

        Button createAccount = new Button("Create Account");
        BooleanBinding b = new BooleanBinding() {

            {
                super.bind(un.textProperty(), pw.textProperty(), rpw.textProperty(), fn.textProperty(), ln.textProperty(), al1.textProperty(), num.textProperty(), em.textProperty(),
                           city.textProperty(), state.valueProperty(), zip.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return un.getText().isEmpty() || pw.getText().isEmpty() || fn.getText().isEmpty() || ln.getText().isEmpty() || al1.getText().isEmpty() ||
                       num.getText().isEmpty() || em.getText().isEmpty() || city.getText().isEmpty() || zip.getText().isEmpty() ||
                       state.getValue() == null || zip.getText().isEmpty() || rpw.getText().isEmpty();
            }
        };
        createAccount.disableProperty().bind(b);
        Alert error = new Alert(Alert.AlertType.ERROR);
        createAccount.setOnAction(actionEvent -> {
            //TODO add all of the verifications that have to go here and alerts depending on the error
            for(User user : Global.userList){
              if(em.getText() == user.getEmail()){
                  error.setHeaderText("Email in use");
                  error.setTitle("ERROR");
                  error.setContentText("This email is currently in use by another account.");
                  error.show();
                  break;
              }
            }
            if(!pw.getText().equals(rpw.getText())){
                error.setHeaderText("Passwords do not match");
                error.setTitle("ERROR");
                error.setContentText("Please maker sure your password and re-entered password matches.");
                error.show();
            }
            else{
                try {
                    Address ad = Address.stringToAddress(al1.getText() + "," + city.getText() + "," + State.getStateFromAbbreviation((String) state.getValue()) + "," + zip.getText());
                    PhoneNumber pn = PhoneNumber.stringToPhoneNumber(num.getText());
                    User newUser = new User(un.getText(), fn.getText(), ln.getText(), pn , ad, em.getText(), pw.getText(), AccountRank.BASIC);
                    SQLHelper.insertIntoTable(Table.USERS, newUser);

                    Alert accMade = new Alert(Alert.AlertType.INFORMATION);
                    accMade.setTitle("Success");
                    accMade.setHeaderText("Account made successfully");
                    accMade.setContentText("You will be directed to the login page.");
                    accMade.showAndWait();
                    primaryStage.close();
                    login();
                }catch(Exception e){
                    error.setHeaderText("Unable to make an Account.");
                    error.setContentText("Something has gone wrong, we are working on it.");
                    //send an error report to us
                    error.setTitle("ERROR");
                    error.showAndWait();
                }
            }
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(createAccount, cancel);
        buttons.setPadding(new Insets(10));
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        GridPane form = new GridPane();
        form.add(EMAIL, 0, 0);
        form.add(FIRST_NAME, 0 , 1);
        form.add(LAST_NAME, 0 , 2);
        form.add(USERNAME, 0, 3);
        form.add(PASSWORD, 0, 4);
        form.add(RENTER_PASSWORD, 0 , 5);
        form.add(ADD_LINE_1, 0, 6);
        form.add(ADD_LINE_2, 0, 7);
        form.add(CITY, 0, 8);
        form.add(STATE, 0, 9);
        form.add(ZIPCODE, 0 , 10);
        form.add(PHONE_NUMBER, 0, 11);
        form.add(em, 1,0);
        form.add(fn,1,1);
        form.add(ln,1,2);
        form.add(un,1,3);
        form.add(pw,1,4);
        form.add(rpw,1,5);
        form.add(al1,1,6);
        form.add(al2,1,7);
        form.add(city,1,8);
        form.add(state,1,9);
        form.add(zip,1,10);
        form.add(num,1,11);
        form.setVgap(10);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPadding(new Insets(10));



        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, s0 ,form, s1, buttons);

        ScrollPane all = new ScrollPane(layout);
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
        prompt.setStyle("-fx-font-weight: bold");

        Label newUser = new Label("New User?");
        newUser.setTextFill(Color.BLUE);
        newUser.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newUser.setStyle("-fx-underline: true");
            }
        });
        newUser.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newUser.setStyle("-fx-underline: false");
            }
        });
        newUser.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            newUser();
        });

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

        VBox ex = new VBox(10, prompt, uID, userID, pw, password, submit, guestUser, newUser,invalid);
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
        primaryStage.setMinHeight(400);
        primaryStage.setMaxWidth(400);
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

        HBox loginBar = new HBox();
        if(Global.loggedInUser == Global.GUEST) {
            Label ReturningUser = new Label("Returning User? ");
            Label login = new Label("Login");
            loginBar.getChildren().addAll(ReturningUser, login);
            loginBar.setPadding(new Insets(10));
            loginBar.setSpacing(10);
            loginBar.setAlignment(Pos.CENTER);
            login.setTextFill(Color.BLUE);
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
        final Label EMAIL = new Label("Email");

        //TODO parse the address string, the user can still change it as needed
        TextField aL1 = new TextField("");
        TextField aL2 = new TextField("");
        TextField city = new TextField("");
        TextField zip = new TextField("");
        TextField email = new TextField("");
        ComboBox state = new ComboBox();

        List<String> abbreviations = new ArrayList<>();
        for (State stateObj : State.values())
            abbreviations.add(stateObj.getAbbreviation());
        state.getItems().addAll(abbreviations);

        if(Global.loggedInUser != Global.GUEST){
            aL1.setText(Global.loggedInUser.getAddress().getHouseNumber());
            city.setText(Global.loggedInUser.getAddress().getCity());
            zip.setText("" + Global.loggedInUser.getAddress().getZip());
            state.setValue(Utils.capitalize(Global.loggedInUser.getAddress().getState().name()));
            email.setText(Global.loggedInUser.getEmail());
        }

        GridPane shippingForm = new GridPane();

        shippingForm.add(ADDRESS_LINE1, 0, 1);
        shippingForm.add(ADDRESS_LINE2, 0, 2);
        shippingForm.add(CITY, 0, 3);
        shippingForm.add(STATE, 0, 4);
        shippingForm.add(ZIPCODE, 0, 5);
        shippingForm.add(EMAIL, 0, 6);
        shippingForm.add(aL1, 1, 1);
        shippingForm.add(aL2, 1, 2);
        shippingForm.add(city, 1, 3);
        shippingForm.add(state, 1, 4);
        shippingForm.add(zip, 1, 5);
        shippingForm.add(email, 1, 6);
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

        Label name = new Label("Name on card");
        Label cardNumber = new Label("Card Number (no spaces or dashes)");
        Label expiration = new Label("Card Expiration Date");
        Label securityCode = new Label("Security Code");

        TextField n = new TextField();
        TextField cn = new TextField();
        TextField sc = new TextField();
        ComboBox month = new ComboBox();
        List<String> months = new ArrayList<>();
        for (Month monthObj : Month.values()) {
            months.add(Utils.capitalize(monthObj.name()));
        }
        month.getItems().addAll(months);

        ComboBox year = new ComboBox();
        for(int i = 2019; i<= 2030; i++){
            year.getItems().add(i);
        }

        //paypal
        Label ppemail = new Label("Email");
        Label pw = new Label("PayPal Password");
        TextField e = new TextField("");
        PasswordField p = new PasswordField();
        Button connect = new Button("Connect");


        GridPane pm = new GridPane();
        pm.setHgap(10);
        pm.setPadding(new Insets(10));
        pm.setVgap(10);
        pm.setHgap(10);
        pm.setAlignment(Pos.CENTER_LEFT);
        paymentChoices.selectedToggleProperty().addListener(listener -> {
            if(paymentChoices.getSelectedToggle() == paypal){
                pm.getChildren().clear();
                pm.add(ppemail, 0,0);
                pm.add(pw, 0, 1);
                pm.add(e, 1,0);
                pm.add(p, 1, 1);
                pm.add(connect, 1, 2);
            }


            if(paymentChoices.getSelectedToggle() == creditCard || paymentChoices.getSelectedToggle() == debitCard){
                pm.getChildren().clear();
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

        //AMOUNT DUE and BUTTONS
        final Label TOTAL_ITEMS_PURCHASED = new Label("Total Items Purchased: " + cart.getCartSize());
        final Label AMOUNT_DUE = new Label("Amount Due: " + cart.getTotalCost());
        Button purchase = new Button("Purchase");
        BooleanBinding b = new BooleanBinding() {
            {
                super.bind(aL1.textProperty(), city.textProperty(), zip.textProperty(),email.textProperty(), cn.textProperty(),
                           n.textProperty(), sc.textProperty(), state.valueProperty(), month.valueProperty(), year.valueProperty(),
                           e.textProperty(), p.textProperty());
            }

            @Override
            protected boolean computeValue()
            {
                boolean temp = true;
                if(!(aL1.getText().isEmpty() || city.getText().isEmpty() || zip.getText().isEmpty()  || email.getText().isEmpty() || state.getValue() == null)){
                    if(cn.getText().isEmpty()  &&  n.getText().isEmpty()    &&   sc.getText().isEmpty() && month.getValue() == null  && year.getValue() == null){
                        if(e.getText().isEmpty() || p.getText().isEmpty()){
                        }
                        if(!e.getText().isEmpty() && !p.getText().isEmpty()) {
                            temp = false;
                        }
                    }
                    if(!(cn.getText().isEmpty()  || n.getText().isEmpty()    ||   sc.getText().isEmpty() || month.getValue() == null  || year.getValue() == null)) {
                        temp = false;
                    }
                }
                return temp;
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
            } catch (FileNotFoundException execpt) {
                execpt.printStackTrace();
            }
        });

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
        ex = new Scene(root, 400, 400);
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
        Label action = new Label("A confirmation and receipt have been sent to your email.");
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

    public void changeCartLabel(int num)
    {
        if(num > 0)
        {
            ProductView.count++;
            shoppingCartLabel.setText("Shopping Cart(" + ProductView.count + ")");
        }
        else
        {
            ProductView.count--;
            shoppingCartLabel.setText("Shopping Cart(" + ProductView.count + ")");
        }
    }
}