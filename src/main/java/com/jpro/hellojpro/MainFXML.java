package com.jpro.hellojpro;/*
  Sight Read Master
  @author Renan Siqueira
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.jpro.webapi.JProApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainFXML extends JProApplication {

    static final Player player = new Player();
    static final UserInputs user = new UserInputs();
    static final Keyboard piano = new Keyboard(88, 0,
            9, false);
    public static main_windowController controller;
    public static final ArrayList<Integer> ALLOWED_NOTE_VALUES = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/main_window.fxml"));
        Scene scene = null;

        AnchorPane root = loader.load();
        controller = loader.getController();
        controller.setMainApp(this);
        controller.setPrimaryStage(primaryStage);
        Scene main_scene = new Scene(root);
        controller.setScene(main_scene);


        //FXMLLoader loader = new FXMLLoader();
        //FileInputStream fxmlStream = new FileInputStream("/com/jpro/hellojpro/main_window.fxml");
        //AnchorPane root = loader.load(fxmlStream);
        primaryStage.setTitle("Sight Read Master");
        primaryStage.setScene(main_scene);
        // Give the controller access to the main app.


        primaryStage.show();
    }
}
