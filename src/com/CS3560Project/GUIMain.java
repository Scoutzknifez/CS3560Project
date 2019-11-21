package com.CS3560Project;

import com.CS3560Project.utility.Constants;
import com.CS3560Project.utility.Global;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main running thread which hosts the GUI
 */
public class GUIMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Global.guiMainReference = this;

        //test

        Group root = new Group();
        Scene scene = new Scene(root, 1000, 800);

        stage.setTitle(Constants.APPLICATION_TITLE);
        stage.setScene(scene);
        stage.show();
    }
}
