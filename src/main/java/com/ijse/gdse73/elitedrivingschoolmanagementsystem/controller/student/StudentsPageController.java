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
    public AnchorPane ancStudentsPage;

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
        if(StudentDetailsController.selectedStudentId==null){
            new Alert(Alert.AlertType.ERROR, "Please Select A Student").show();
            return;
        }

        try {
            ancStudentsPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Payments/PaymentsPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudentsPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentsPage.heightProperty());

            ancStudentsPage.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }
}
