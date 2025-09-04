package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.course;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseViewController implements Initializable {
    public AnchorPane ancCourseView;

    public void btnBackOnAction(MouseEvent mouseEvent) {
        try {
            ancCourseView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Courses/CourseDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancCourseView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCourseView.heightProperty());

            ancCourseView.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
