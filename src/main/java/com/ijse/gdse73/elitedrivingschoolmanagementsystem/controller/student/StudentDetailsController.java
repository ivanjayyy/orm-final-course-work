package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.StudentsTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentDetailsController implements Initializable {
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    public AnchorPane ancStudentDetails;
    public TextField inputSearch;
    public DatePicker btnDatePicker;
    public TableView<StudentsTM> tblStudents;
    public TableColumn<StudentsTM,String> colStudentId;
    public TableColumn<StudentsTM,String> colStudentName;
    public TableColumn<StudentsTM,String> colStudentIsRegistered;

    public static String selectedStudentId;
    public static boolean addStudent;

    public void btnViewOnAction(MouseEvent mouseEvent) {
        if(selectedStudentId == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select A Student").show();
            return;
        }

        try {
            ancStudentDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentView.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudentDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentDetails.heightProperty());

            ancStudentDetails.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDatePicker.getEditor().setVisible(false);
        resetPage();
    }

    private void resetPage() {
        inputSearch.clear();
        loadTableData();
        selectedStudentId = null;
        addStudent = false;
    }

    private void loadTableData() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStudentIsRegistered.setCellValueFactory(new PropertyValueFactory<>("isRegistered"));

        List<StudentDTO> studentDTOS;

        if(inputSearch.getText().isEmpty()){
            studentDTOS = studentBO.getAllStudents();
        } else {
            studentDTOS = studentBO.searchStudent(inputSearch.getText());
        }

        ObservableList<StudentsTM> studentsTMS = FXCollections.observableArrayList();

        for (StudentDTO studentDTO : studentDTOS) {
            boolean isRegistered = studentDTO.isRegistered();
            String registered;

            if(isRegistered){
                registered = "Yes";
            } else {
                registered = "No";
            }

            StudentsTM studentsTM = new StudentsTM(
                    studentDTO.getStudentId(),
                    studentDTO.getName(),
                    registered
            );
            studentsTMS.add(studentsTM);
        }

        tblStudents.setItems(studentsTMS);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        StudentsTM selectedStudent = tblStudents.getSelectionModel().getSelectedItem();

        if(selectedStudent != null) {
            selectedStudentId = selectedStudent.getStudentId();
        }
    }

    public void btnResetOnAction(MouseEvent mouseEvent) {
        resetPage();
        new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if(selectedStudentId != null) {
            new Alert(Alert.AlertType.ERROR, "Please Unselect The Student").show();
            return;
        }

        try {
            addStudent = true;

            ancStudentDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentView.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudentDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentDetails.heightProperty());

            ancStudentDetails.getChildren().add(anchorPane);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void datePickerOnAction(ActionEvent actionEvent) {
        inputSearch.setText(btnDatePicker.getValue().toString());
    }

    public void btnSearchOnAction(MouseEvent mouseEvent) {
        loadTableData();
    }
}
