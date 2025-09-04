package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseDetailsController implements Initializable {
    public AnchorPane ancCourseDetails;

    public void btnViewOnAction(MouseEvent actionEvent) {
        try {
            ancCourseDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/CourseView.fxml"));

            anchorPane.prefWidthProperty().bind(ancCourseDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCourseDetails.heightProperty());

            ancCourseDetails.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
