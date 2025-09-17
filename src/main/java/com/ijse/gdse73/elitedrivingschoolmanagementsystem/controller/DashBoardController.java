package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.userLogin.LoginPageController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
        if(!LoginPageController.isAdmin){
            new Alert(Alert.AlertType.ERROR, "You Are Not Authorized To View This Page").show();
            return;
        }

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Exit?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            System.exit(0);
        }
    }

    public void btnEditAccountOnAction(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want To Change The Password?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/view/Users/ChangeLoginDetails.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Change Login Details");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error In Opening Change Login Details Window").show();
            }
        }
    }
}
