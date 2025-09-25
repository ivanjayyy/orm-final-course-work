package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.userLogin;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);

    public AnchorPane ancChangePassword;
    public JFXButton btnSave;
    public TextField inputPassword;
    public TextField inputConfirmPassword;

    private final String pattern1WeakPassword = "^[A-Za-z]+$";
    private final String pattern2WeakPassword = "^[0-9]+$";
    private final String pattern3WeakPassword = "^[A-Za-z0-9]+$";
    private final String patternNormalPassword = "^[A-Za-z0-9]{6,}$";

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if(inputPassword.getText().equals(inputConfirmPassword.getText())){
            UserDTO userDTO = userBO.searchUser(ForgotPasswordController.userId).getFirst();
            String password = BCrypt.hashpw(inputPassword.getText(), BCrypt.gensalt(12));
            userDTO.setPassword(password);

            boolean isChanged = userBO.updateUser(userDTO);

            if(!isChanged){
                new Alert(Alert.AlertType.ERROR,"Password Change Failed").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION,"Password Changed Successfully").show();

                try {
                    ancChangePassword.getChildren().clear();
                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/UserLogin/LoginPage.fxml"));

                    anchorPane.prefWidthProperty().bind(ancChangePassword.widthProperty());
                    anchorPane.prefHeightProperty().bind(ancChangePassword.heightProperty());

                    ancChangePassword.getChildren().add(anchorPane);
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
                }
            }

        } else {
            new Alert(Alert.AlertType.ERROR,"Passwords Do Not Match").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnSave);

        inputPassword.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternNormalPassword)) {
                inputPassword.setStyle("-fx-background-color: white; -fx-border-color: orange; -fx-border-width: 0 0 3px 0;");

            } else if (newVal.matches(pattern1WeakPassword) || newVal.matches(pattern2WeakPassword) || newVal.matches(pattern3WeakPassword)) {
                inputPassword.setStyle("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");

            } else if (newVal.isEmpty()){
                inputPassword.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");

            } else {
                inputPassword.setStyle("-fx-background-color: white; -fx-border-color: green; -fx-border-width: 0 0 3px 0;");
            }
        });
    }
}
