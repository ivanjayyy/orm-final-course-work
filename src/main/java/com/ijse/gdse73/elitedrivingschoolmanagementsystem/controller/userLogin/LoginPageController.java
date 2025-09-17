package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.userLogin;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);

    public AnchorPane ancLoginPage;
    public JFXButton btnLogin;
    public TextField inputUsername;
    public PasswordField inputPassword;
    public ImageView imgEye;
    public TextField visiblePassword;

    public static Boolean isAdmin = false;
    private boolean isPasswordVisible = false;
    public static String currentUser;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String username = inputUsername.getText();
        String password = isPasswordVisible ? visiblePassword.getText() : inputPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Fill All Fields");
            alert.show();
            return;
        }

        ArrayList<UserDTO> userList = userBO.searchUser(username);

        if (!userList.isEmpty()) {
            UserDTO checkUser = userList.getFirst();

            if (checkUser!=null) {
                String hashedPassword = checkUser.getPassword();

                if (BCrypt.checkpw(password, hashedPassword)) {
                    isAdmin = checkUser.isAdmin();
                    currentUser = checkUser.getUserId();

                    Stage currentstage = (Stage) btnLogin.getScene().getWindow();
                    currentstage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashBoard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("ELITE Driving School");
                    stage.setResizable(false);
                    stage.show();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong Password");
                    alert.show();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong Username");
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong Username");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnShowPasswordOnAction(MouseEvent mouseEvent) {
        if (isPasswordVisible) {
            imgEye.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/eye-open.png")));
            visiblePassword.setVisible(false);
            visiblePassword.setManaged(false);
            inputPassword.setText(visiblePassword.getText());
            inputPassword.setVisible(true);
            inputPassword.setManaged(true);
            isPasswordVisible = false;

        } else {
            imgEye.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/eye-close.png")));
            visiblePassword.setVisible(true);
            visiblePassword.setManaged(true);
            inputPassword.setVisible(false);
            inputPassword.setManaged(false);
            visiblePassword.setText(inputPassword.getText());
            isPasswordVisible = true;
        }
    }

    public void btnForgotPasswordOnAction(MouseEvent mouseEvent) {
        try {
            ancLoginPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/UserLogin/ForgotPassword.fxml"));

            anchorPane.prefWidthProperty().bind(ancLoginPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLoginPage.heightProperty());

            ancLoginPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }
}
