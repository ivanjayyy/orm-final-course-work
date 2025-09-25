package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.user;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);

    public AnchorPane ancUserView;
    public HBox btnBack;
    public HBox btnUpdate;
    public HBox btnDelete;
    public TextField inputUserId;
    public TextField inputFullName;
    public TextField inputUserName;
    public TextField inputPassword;
    public TextField inputEmail;
    public JFXRadioButton radioYes;
    public JFXRadioButton radioNo;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    private final String userFullNameRegex = "^[A-Z][a-z]+(?: [A-Z][a-z]+)*$";
    private final String usernameRegex = "^[a-zA-Z0-9_-]+$";
    private final String userEmailRegex = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,}$";

    public void goToUserDetailsPage(){
        try {
            ancUserView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Users/UserDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancUserView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancUserView.heightProperty());

            ancUserView.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
        goToUserDetailsPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        radioYes.setToggleGroup(toggleGroup);
        radioNo.setToggleGroup(toggleGroup);
        radioNo.setSelected(true);

        ButtonScale.hboxScaling(btnBack);
        ButtonScale.hboxScaling(btnUpdate);
        ButtonScale.hboxScaling(btnDelete);

        if(UserDetailsController.addUser){
            inputUserId.setText(userBO.getNextUserId());

            lblUpdate.setText("ADD");
            imgUpdate.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/add.gif")));

            lblDelete.setText("RESET");
            imgDelete.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/reset.gif")));
        }

        if(UserDetailsController.selectedUserId!=null){
            UserDTO selectedUser = getSelectedUser();

            inputUserId.setText(selectedUser.getUserId());
            inputFullName.setText(selectedUser.getName());

            inputUserName.setText("Access Denied");
            inputUserName.setEditable(false);

            inputPassword.setText("Access Denied");
            inputPassword.setEditable(false);

            inputEmail.setText(selectedUser.getEmail());

            boolean isAdmin = selectedUser.isAdmin();
            if(isAdmin){
                radioYes.setSelected(true);
            }else{
                radioNo.setSelected(true);
            }
        }
    }

    public UserDTO getSelectedUser(){
        return userBO.searchUser(UserDetailsController.selectedUserId).getFirst();
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        int wrongRegex = 0;

        if(inputUserId.getText().equals("") || inputFullName.getText().equals("") || inputUserName.getText().equals("") || inputPassword.getText().equals("") || inputEmail.getText().equals("")){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields").show();
            return;
        }

        if(!inputUserName.getText().matches(usernameRegex)){
            inputUserName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputUserName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputFullName.getText().matches(userFullNameRegex)){
            inputFullName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputFullName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputEmail.getText().matches(userEmailRegex)){
            inputEmail.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputEmail.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(wrongRegex > 0){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields Correctly").show();
            return;
        }

        if (UserDetailsController.addUser){
            String password = BCrypt.hashpw(inputPassword.getText(), BCrypt.gensalt(12));
            UserDTO user = new UserDTO(inputUserId.getText(),inputFullName.getText(),inputUserName.getText(),password,inputEmail.getText(),radioYes.isSelected());
            boolean isAdded = userBO.saveUser(user);

            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"User Added Successfully").show();
                goToUserDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR,"User Add Failed").show();
            }

        } else {
            UserDTO updatedUser = getSelectedUser();
            updatedUser.setName(inputFullName.getText());
            updatedUser.setEmail(inputEmail.getText());
            updatedUser.setAdmin(radioYes.isSelected());

            boolean isUpdated = userBO.updateUser(updatedUser);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"User Updated Successfully").show();
                goToUserDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR,"User Update Failed").show();
            }
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblDelete.getText().equals("DELETE")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            boolean isDeleted;

            if (result.isPresent() && result.get() == ButtonType.YES) {
                isDeleted = userBO.deleteUser(inputUserId.getText());
            } else {
                return;
            }

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"User Deleted Successfully").show();
                goToUserDetailsPage();

            }else{
                new Alert(Alert.AlertType.ERROR,"User Delete Failed").show();
            }

        } else {
            inputFullName.setText("");
            inputUserName.setText("");
            inputPassword.setText("");
            inputEmail.setText("");
            radioNo.setSelected(true);

            new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
        }
    }
}
