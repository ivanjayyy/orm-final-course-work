package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.InstructorDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.InstructorsTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorDetailsController implements Initializable {
    InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    public AnchorPane ancInstructorDetails;
    public TableView<InstructorsTM> tblInstructors;
    public TableColumn<InstructorsTM,String> colInstructorId;
    public TableColumn<InstructorsTM,String> colInstructorName;
    public TableColumn<InstructorsTM,String> colCourseName;

    public static String selectedInstructorId;
    public static boolean addInstructor;

    public void btnViewOnAction(MouseEvent mouseEvent) {
        if(selectedInstructorId == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select An Instructor").show();
            return;
        }

        try {
            ancInstructorDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Instructors/InstructorView.fxml"));

            anchorPane.prefWidthProperty().bind(ancInstructorDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructorDetails.heightProperty());

            ancInstructorDetails.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetPage();
    }

    private void resetPage() {
        loadTableDate();
        selectedInstructorId = null;
        addInstructor = false;
    }

    private void loadTableDate() {
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colInstructorName.setCellValueFactory(new PropertyValueFactory<>("instructorName"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("assignedCourse"));

        List<InstructorDTO> instructorDTOS = instructorBO.getAllInstructors();
        ObservableList<InstructorsTM> instructorsTMS = FXCollections.observableArrayList();

        for (InstructorDTO instructorDTO : instructorDTOS) {
            String courseName = courseBO.searchCourse(instructorDTO.getCourseId()).getFirst().getName();

            InstructorsTM instructorsTM = new InstructorsTM(
                    instructorDTO.getInstructorId(),
                    instructorDTO.getName(),
                    courseName
            );
            instructorsTMS.add(instructorsTM);
        }
        tblInstructors.setItems(instructorsTMS);

    }

    public void onClickTable(MouseEvent mouseEvent) {
        InstructorsTM selectedInstructor = tblInstructors.getSelectionModel().getSelectedItem();

        if(selectedInstructor != null) {
            selectedInstructorId = selectedInstructor.getInstructorId();
        }
    }

    public void btnResetOnAction(MouseEvent mouseEvent) {
        resetPage();
        new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if(selectedInstructorId != null) {
            new Alert(Alert.AlertType.ERROR, "Please Unselect The Instructor").show();
            return;
        }

        try {
            addInstructor = true;

            ancInstructorDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Instructors/InstructorView.fxml"));

            anchorPane.prefWidthProperty().bind(ancInstructorDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructorDetails.heightProperty());

            ancInstructorDetails.getChildren().add(anchorPane);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }
}
