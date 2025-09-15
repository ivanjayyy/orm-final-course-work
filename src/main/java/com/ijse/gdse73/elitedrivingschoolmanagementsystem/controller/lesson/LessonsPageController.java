package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.lesson;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LessonsPageController implements Initializable {
    public AnchorPane ancLessons;
    public AnchorPane ancLessonPage;

    public void btnHomeOnAction(MouseEvent mouseEvent) {
        try {
            ancLessonPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Instructors/InstructorsPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancLessonPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLessonPage.heightProperty());

            ancLessonPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ancLessons.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Lessons/LessonDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancLessons.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLessons.heightProperty());

            ancLessons.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }
}
