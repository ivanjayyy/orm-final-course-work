package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    public AnchorPane ancDashBoard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/Courses/CoursesPage.fxml");
    }

    public void navigateTo(String path) {
        try {
            ancDashBoard.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashBoard.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashBoard.heightProperty());

            ancDashBoard.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnUsersOnAction(MouseEvent mouseEvent) {
        navigateTo("/view/Users/UsersPage.fxml");
    }

    public void btnStudentsOnAction(MouseEvent mouseEvent) {
        navigateTo("/view/Students/StudentsPage.fxml");
    }

    public void btnInstructorsOnAction(MouseEvent mouseEvent) {
        navigateTo("/view/Instructors/InstructorsPage.fxml");
    }

    public void btnCoursesOnAction(MouseEvent mouseEvent) {
        navigateTo("/view/Courses/CoursesPage.fxml");
    }

    public void btnQuitOnAction(MouseEvent mouseEvent) {
    }

    public void btnEditAccountOnAction(MouseEvent mouseEvent) {

    }
}
