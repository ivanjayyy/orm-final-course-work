package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InstructorsPageController implements Initializable {
    public AnchorPane ancInstructors;
    public AnchorPane ancInstructorPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ancInstructors.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Instructors/InstructorDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancInstructors.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructors.heightProperty());

            ancInstructors.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnLessonsOnAction(MouseEvent mouseEvent) {
        if(InstructorDetailsController.selectedInstructorId == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select An Instructor").show();
            return;
        }

        try {
            ancInstructorPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Lessons/LessonsPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancInstructorPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructorPage.heightProperty());

            ancInstructorPage.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }
}
