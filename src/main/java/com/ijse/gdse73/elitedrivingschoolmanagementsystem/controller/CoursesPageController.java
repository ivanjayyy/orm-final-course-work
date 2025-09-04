package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CoursesPageController implements Initializable {
    public AnchorPane ancCourses;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ancCourses.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/CourseDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancCourses.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCourses.heightProperty());

            ancCourses.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }
}
