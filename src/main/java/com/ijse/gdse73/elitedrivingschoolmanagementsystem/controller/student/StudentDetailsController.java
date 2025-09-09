package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentDetailsController implements Initializable {
    public AnchorPane ancStudentDetails;
    public TableView tblStudents;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colStudentIsRegistered;

    public void btnViewOnAction(MouseEvent mouseEvent) {
        try {
            ancStudentDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentView.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudentDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentDetails.heightProperty());

            ancStudentDetails.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    public void btnResetOnAction(MouseEvent mouseEvent) {
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
    }
}
