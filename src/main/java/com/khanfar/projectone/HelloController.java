package com.khanfar.projectone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField coins;

    @FXML
    private AnchorPane splashPage;

  public static int[] coinsArray ;
    @FXML
    void sartOnAction(ActionEvent event) throws IOException {
        String arr[] = coins.getText().trim().split(",") ;
        coinsArray = new int [arr.length] ;
        for (int i = 0 ; i < arr.length ; i++) {
            coinsArray[i] = Integer.parseInt(arr[i]);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPage.fxml"));
        String  style= HelloApplication.class.getResource("Css/style.css").toExternalForm();
        HelloApplication.stage.getScene().setRoot(fxmlLoader.load());
        HelloApplication.stage.getScene().getStylesheets().add(style);
        HelloApplication.stage.setMaximized(true);

    }

}
