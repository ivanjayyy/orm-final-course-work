package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.course;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.StudentsTM;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentsOfCourseController implements Initializable {
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    public TableView<StudentsTM> tblStudentsOfCourse;
    public TableColumn<StudentsTM,String> colStudentId;
    public TableColumn<StudentsTM,String> colStudentName;
    public TableColumn<StudentsTM,String> colIsRegistered;

    public HBox btnBack;

    public void btnBackOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableData();
        ButtonScale.hboxScaling(btnBack);
    }

    private void loadTableData() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colIsRegistered.setCellValueFactory(new PropertyValueFactory<>("isRegistered"));

        List<StudentDTO> students = studentBO.getAllStudents();
        ObservableList<StudentsTM> studentsTMS = FXCollections.observableArrayList();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for(StudentDTO studentDto : students){
            List<String> courseIds = studentDto.getCourseIds();

            if(courseIds.contains(CourseDetailsController.selectedCourseId)){
                studentDTOS.add(studentDto);
            }
        }

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

        tblStudentsOfCourse.setItems(studentsTMS);
    }
}
