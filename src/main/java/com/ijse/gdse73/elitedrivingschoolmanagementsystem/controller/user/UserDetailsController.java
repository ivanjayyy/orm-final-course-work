package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.user;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {
    public AnchorPane ancUserDetails;

    public void btnViewOnAction(MouseEvent mouseEvent) {
        try {
            ancUserDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Users/UserView.fxml"));

            anchorPane.prefWidthProperty().bind(ancUserDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancUserDetails.heightProperty());

            ancUserDetails.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
