package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.userLogin;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
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
    public TextField inputPassword;
    public TextField inputConfirmPassword;

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

    }
}
