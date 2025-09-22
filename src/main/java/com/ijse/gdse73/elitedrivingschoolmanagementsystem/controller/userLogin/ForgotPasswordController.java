package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.userLogin;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.Mail;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);

    public AnchorPane ancForgotPassword;
    public HBox btnBack;
    public JFXButton btnVerify;
    public TextField inputEmail;
    public Label lblCountDown;
    public JFXButton btnResend;

    public TextField one;
    public TextField two;
    public TextField three;
    public TextField four;
    public TextField five;
    public TextField six;

//    int emailSentCount;
    private Timeline timeline;
    private int timeLeft;
    int code;
    public static String userId;

    public void btnBackOnAction(MouseEvent mouseEvent) {
        try {
            ancForgotPassword.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/UserLogin/LoginPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancForgotPassword.widthProperty());
            anchorPane.prefHeightProperty().bind(ancForgotPassword.heightProperty());

            ancForgotPassword.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }

    public void btnVerifyOnAction(ActionEvent actionEvent) {
        String inputCode = one.getText() + two.getText() + three.getText() + four.getText() + five.getText() + six.getText();
        if(inputCode.equals(String.valueOf(code))){
            try {
                ancForgotPassword.getChildren().clear();
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/UserLogin/ChangePassword.fxml"));

                anchorPane.prefWidthProperty().bind(ancForgotPassword.widthProperty());
                anchorPane.prefHeightProperty().bind(ancForgotPassword.heightProperty());

                ancForgotPassword.getChildren().add(anchorPane);

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Page not found").show();
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect Verification Code").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        emailSentCount = 0;
        limitTextField();

        ButtonScale.buttonScaling(btnVerify);
        ButtonScale.buttonScaling(btnResend);
        ButtonScale.hboxScaling(btnBack);
    }

    private void limitTextField() {
        limitToOneDigit(one);
        limitToOneDigit(two);
        limitToOneDigit(three);
        limitToOneDigit(four);
        limitToOneDigit(five);
        limitToOneDigit(six);

        setAutoMove(one, two, null);
        setAutoMove(two, three, one);
        setAutoMove(three, four, two);
        setAutoMove(four, five, three);
        setAutoMove(five, six, four);
        setAutoMove(six, null, five);
    }

    private void limitToOneDigit(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.matches("\\d?")) {
                return change;
            } else {
                return null;
            }
        }));
    }

    private void setAutoMove(TextField current, TextField next, TextField prev) {
        current.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.length() == 1 && next != null) {
                next.requestFocus();
            }
        });

        current.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case BACK_SPACE:
                    if (current.getText().isEmpty() && prev != null) {
                        prev.requestFocus();
                    }
                    break;
            }
        });
    }

    public void resendEmailOnAction(ActionEvent actionEvent) {
//        if(emailSentCount > 0){
//            btnResend.setText("RESEND");
//        }

        boolean isValid = false;
        String username = null;
        String subject;
        String body;

        Random random = new Random();
        code = 100000 + random.nextInt(900000);

        String email = inputEmail.getText();

        ArrayList<UserDTO> users = userBO.getAllUsers();
        for(UserDTO user:users){
            if(user.getEmail().equals(email)){
                isValid = true;
                username = user.getUsername();
                userId = user.getUserId();
            }
        }

        if(isValid) {
            subject = "Elite Driving School Management System - Forgot Password Verification";
            body = "Dear "+username+",\nYour Verification Code is:\n\n     "+code;

            btnResend.setText("RESEND");

            Runnable task = () -> {
                Mail.sendMail(email, subject, body);
            };
            Thread thread = new Thread(task);
            thread.start();

            setTimer();

        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
        }
    }

    public void setTimer(){
        timeLeft = 60;
        lblCountDown.setText(String.valueOf(timeLeft));
        btnResend.setDisable(true);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeLeft--;
                    lblCountDown.setText(String.valueOf(timeLeft));
                    if(timeLeft == 0){
                        timeline.stop();
                        btnResend.setDisable(false);
                        lblCountDown.setText("");
                    }
                })
        );
        timeline.setCycleCount(timeLeft);
        timeline.play();
    }
}
