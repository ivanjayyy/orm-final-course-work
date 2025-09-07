package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.user;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersPageController implements Initializable {
    public AnchorPane ancUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ancUsers.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Users/UserDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancUsers.widthProperty());
            anchorPane.prefHeightProperty().bind(ancUsers.heightProperty());

            ancUsers.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }
}
