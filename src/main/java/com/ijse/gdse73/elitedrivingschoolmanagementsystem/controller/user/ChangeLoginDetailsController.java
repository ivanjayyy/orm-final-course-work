package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.user;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.LoginPageController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChangeLoginDetailsController implements Initializable {
    public TextField inputUsername;
    public TextField inputPassword;

    private final String usernameRegex = "^[a-zA-Z0-9_-]+$";

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);
        String password = BCrypt.hashpw(inputPassword.getText(), BCrypt.gensalt(12));

        if(!inputUsername.getText().matches(usernameRegex)){
            inputUsername.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields Correctly").show();
            return;
        } else {
            inputUsername.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        ArrayList<UserDTO> userList = userBO.searchUser(LoginPageController.currentUser);
        UserDTO checkUser = userList.getFirst();

        if (checkUser!=null) {
            checkUser.setUsername(inputUsername.getText());
            checkUser.setPassword(password);
            boolean isUpdated = userBO.updateUser(checkUser);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Login Details Updated Successfully").show();
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.close();

            } else {
                new Alert(Alert.AlertType.ERROR, "Login Details Update Failed").show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
