package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.user;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.UsersTM;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);

    public AnchorPane ancUserDetails;
    public HBox btnSearch;
    public HBox btnReset;
    public HBox btnAdd;
    public HBox btnView;
    public TextField inputSearch;
    public TableView<UsersTM> tblUsers;
    public TableColumn<UsersTM, String> colUserId;
    public TableColumn<UsersTM, String> colIsAdmin;
    public TableColumn<UsersTM, String> colFullName;

    public static String selectedUserId;
    public static boolean addUser;

    public void btnViewOnAction(MouseEvent mouseEvent) {
        if (selectedUserId == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select A User").show();
            return;
        }

        try {
            ancUserDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Users/UserView.fxml"));

            anchorPane.prefWidthProperty().bind(ancUserDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancUserDetails.heightProperty());

            ancUserDetails.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetPage();

        ButtonScale.hboxScaling(btnSearch);
        ButtonScale.hboxScaling(btnReset);
        ButtonScale.hboxScaling(btnAdd);
        ButtonScale.hboxScaling(btnView);
    }

    private void resetPage() {
        inputSearch.clear();
        loadTableData();
        selectedUserId = null;
        addUser = false;
    }

    private void loadTableData() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colIsAdmin.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));

        List<UserDTO> userDTOS;

        if (inputSearch.getText().isEmpty()) {
            userDTOS = userBO.getAllUsers();
        } else {
            userDTOS = userBO.searchUser(inputSearch.getText());
        }

        ObservableList<UsersTM> usersTMS = FXCollections.observableArrayList();

        for (UserDTO userDTO : userDTOS) {
            boolean isAdmin = userDTO.isAdmin();
            String admin;

            if (isAdmin) {
                admin = "Yes";
            } else {
                admin = "No";
            }

            UsersTM usersTM = new UsersTM(
                    userDTO.getUserId(),
                    userDTO.getName(),
                    admin
            );
            usersTMS.add(usersTM);
        }
        tblUsers.setItems(usersTMS);

    }

    public void onClickTable(MouseEvent mouseEvent) {
        UsersTM selectedUser = tblUsers.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            selectedUserId = selectedUser.getUserId();
        }
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if (selectedUserId != null) {
            new Alert(Alert.AlertType.ERROR, "Please Unselect The User").show();
            return;
        }

        try {
            addUser = true;

            ancUserDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Users/UserView.fxml"));

            anchorPane.prefWidthProperty().bind(ancUserDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancUserDetails.heightProperty());

            ancUserDetails.getChildren().add(anchorPane);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnResetOnAction(MouseEvent mouseEvent) {
        resetPage();
        new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
    }

    public void btnSearchOnAction(MouseEvent mouseEvent) {
        loadTableData();
    }
}
