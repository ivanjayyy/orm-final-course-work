package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InstructorDetailsController implements Initializable {
    public AnchorPane ancInstructorDetails;

    public void btnViewOnAction(MouseEvent mouseEvent) {
        try {
            ancInstructorDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Instructors/InstructorView.fxml"));

            anchorPane.prefWidthProperty().bind(ancInstructorDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructorDetails.heightProperty());

            ancInstructorDetails.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
