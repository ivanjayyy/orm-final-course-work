package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentsPageController implements Initializable {
    public AnchorPane ancStudents;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ancStudents.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudents.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudents.heightProperty());

            ancStudents.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnPaymentsOnAction(MouseEvent mouseEvent) {

    }
}
