package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.user;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsersPageController implements Initializable {
    public AnchorPane ancUsers;
    public TextField lblNoOfUsers;
    public TextField lblAdmins;
    public TextField lblNonAdmins;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLabelData();

        try {
            ancUsers.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Users/UserDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancUsers.widthProperty());
            anchorPane.prefHeightProperty().bind(ancUsers.heightProperty());

            ancUsers.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    private void loadLabelData() {
        List<UserDTO> userDTOS = userBO.getAllUsers();
        lblNoOfUsers.setText(userDTOS.size()+"");

        int adminCount = 0;
        int nonAdminCount = 0;

        for(UserDTO userDTO : userDTOS){
            if(userDTO.isAdmin()){
                ++adminCount;
            } else {
                ++nonAdminCount;
            }
        }

        lblAdmins.setText(adminCount+"");
        lblNonAdmins.setText(nonAdminCount+"");
    }

    public void resetPageOnAction(MouseEvent mouseEvent) {
        loadLabelData();
    }
}
