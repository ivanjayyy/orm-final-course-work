package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.payment;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentsPageController implements Initializable {
    public AnchorPane ancPaymentPage;
    public AnchorPane ancPayments;
    public HBox btnHome;

    public void btnHomeOnAction(MouseEvent mouseEvent) {
        try {
            ancPaymentPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentsPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancPaymentPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPaymentPage.heightProperty());

            ancPaymentPage.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Exception("Page Not Found").printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.hboxScaling(btnHome);

        try {
            ancPayments.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Payments/PaymentDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancPayments.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPayments.heightProperty());

            ancPayments.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Exception("Page Not Found").printStackTrace();
        }
    }
}
