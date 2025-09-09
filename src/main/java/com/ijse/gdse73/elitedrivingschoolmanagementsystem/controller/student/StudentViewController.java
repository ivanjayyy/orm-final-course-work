package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student;

import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {
    public AnchorPane ancStudentView;
    public TextField inputStudentId;
    public JFXRadioButton radioMale;
    public JFXRadioButton radioFemale;
    public TextField inputStudentName;
    public TextField inputAddress;
    public TextField inputNic;
    public TextField inputContact;
    public TextField inputEmail;
    public JFXRadioButton radioYes;
    public JFXRadioButton radioNo;
    public TextField inputRegisterDate;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    public void btnBackOnAction(MouseEvent mouseEvent) {
        try {
            ancStudentView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudentView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentView.heightProperty());

            ancStudentView.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnSeeCoursesOnAction(MouseEvent mouseEvent) {
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
    }
}
