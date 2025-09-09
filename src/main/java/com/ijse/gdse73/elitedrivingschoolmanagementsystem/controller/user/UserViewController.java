package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.user;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);

    public AnchorPane ancUserView;
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
        if (UserDetailsController.addUser){
            UserDTO user = new UserDTO(inputUserId.getText(),inputFullName.getText(),inputUserName.getText(),inputPassword.getText(),inputEmail.getText(),radioYes.isSelected());
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
            boolean isDeleted = userBO.deleteUser(inputUserId.getText());

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
