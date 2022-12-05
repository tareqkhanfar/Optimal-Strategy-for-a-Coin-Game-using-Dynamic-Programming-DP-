package com.khanfar.projectone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPage.fxml"));
        String  style= HelloApplication.class.getResource("Css/style.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(style);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}