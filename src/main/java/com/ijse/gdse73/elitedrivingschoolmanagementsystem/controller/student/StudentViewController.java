package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    public AnchorPane ancStudentView;
    public TextField inputStudentId;
    public JFXRadioButton radioMale;
    public JFXRadioButton radioFemale;
    public TextField inputStudentName;
    public TextField inputAddress;
    public TextField inputNic;
    public TextField inputContact;
    public TextField inputEmail;
    public JFXRadioButton radioYes;
    public JFXRadioButton radioNo;
    public TextField inputRegisterDate;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    public void goToStudentDetailsPage(){
        try {
            ancStudentView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudentView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentView.heightProperty());

            ancStudentView.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
        goToStudentDetailsPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup genderGroup = new ToggleGroup();
        radioMale.setToggleGroup(genderGroup);
        radioFemale.setToggleGroup(genderGroup);
        radioMale.setSelected(true);

        ToggleGroup registerGroup = new ToggleGroup();
        radioYes.setToggleGroup(registerGroup);
        radioNo.setToggleGroup(registerGroup);
        radioNo.setSelected(true);

        if(StudentDetailsController.addStudent){
            inputStudentId.setText(studentBO.getNextStudentId());

            lblUpdate.setText("ADD");
            imgUpdate.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/add.gif")));

            lblDelete.setText("RESET");
            imgDelete.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/reset.gif")));
        }

        if(StudentDetailsController.selectedStudentId!=null){
            StudentDTO selectedStudent = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst();

            inputStudentId.setText(selectedStudent.getStudentId());

            String gender = selectedStudent.getGender();
            if(gender.equals("Male")){
                radioMale.setSelected(true);
            } else {
                radioFemale.setSelected(true);
            }

            inputStudentName.setText(selectedStudent.getName());
            inputAddress.setText(selectedStudent.getAddress());

            inputNic.setText(selectedStudent.getNic());
            inputNic.setEditable(false);

            inputContact.setText(selectedStudent.getContact());
            inputEmail.setText(selectedStudent.getEmail());

            inputRegisterDate.setText(selectedStudent.getDate());
            inputRegisterDate.setEditable(false);

            boolean isRegistered = selectedStudent.isRegistered();
            if(isRegistered){
                radioYes.setSelected(true);
            } else {
                radioNo.setSelected(true);
            }
        }
    }

    public void btnSeeCoursesOnAction(MouseEvent mouseEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Students/AppliedCourses.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Applied Courses");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error In Opening Applied Courses Window").show();
        }
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        if(AppliedCoursesController.addCourseIds == null){
            new Alert(Alert.AlertType.ERROR, "Please Select A Course").show();
            return;
        }

        if(StudentDetailsController.addStudent){
            ArrayList<String> courseIds = new ArrayList<>(AppliedCoursesController.addCourseIds);
            String gender;

            if (radioMale.isSelected()) {
                gender = "Male";
            } else {
                gender = "Female";
            }

            StudentDTO student = new StudentDTO(inputStudentId.getText(),gender,inputStudentName.getText(),inputAddress.getText(),inputNic.getText(),inputContact.getText(),inputEmail.getText(),radioYes.isSelected(),inputRegisterDate.getText(),courseIds);
            boolean isAdded = studentBO.saveStudent(student);

            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Student Added Successfully").show();
                goToStudentDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR,"Student Add Failed").show();
            }

        } else {
            StudentDTO updatedStudent = getSelectedStudent();
            updatedStudent.setGender(radioMale.isSelected()?"Male":"Female");
            updatedStudent.setName(inputStudentName.getText());
            updatedStudent.setAddress(inputAddress.getText());
            updatedStudent.setContact(inputContact.getText());
            updatedStudent.setEmail(inputEmail.getText());
            updatedStudent.setRegistered(radioYes.isSelected());

            boolean isUpdated = studentBO.updateStudent(updatedStudent);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Student Updated Successfully").show();
                goToStudentDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR,"Student Update Failed").show();
            }
        }
    }

    public StudentDTO getSelectedStudent(){
        return studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst();
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblDelete.getText().equals("DELETE")){
            boolean isDeleted = studentBO.deleteStudent(inputStudentId.getText());

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Student Deleted Successfully").show();
                goToStudentDetailsPage();
            } else {
                new Alert(Alert.AlertType.ERROR,"Student Delete Failed").show();
            }

        } else {
            inputStudentName.setText("");
            inputAddress.setText("");
            inputNic.setText("");
            inputContact.setText("");
            inputEmail.setText("");
            radioMale.setSelected(true);
            radioNo.setSelected(true);
            inputRegisterDate.setText("");

            new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
        }
    }
}
