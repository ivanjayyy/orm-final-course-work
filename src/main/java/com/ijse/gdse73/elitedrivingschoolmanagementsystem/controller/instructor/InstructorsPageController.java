package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.InstructorDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorsPageController implements Initializable {
    public AnchorPane ancInstructors;
    public AnchorPane ancInstructorPage;
    public TextField lblNoOfInstructors;
    public TextField lblNoOfStudents;
    public TextField lblLessonsDone;
    public HBox btnLessons;

    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);
    InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.hboxScaling(btnLessons);

        List<InstructorDTO> instructorDTOS = instructorBO.getAllInstructors();
        int instructorCount = instructorDTOS.size();
        lblNoOfInstructors.setText(instructorCount+"");

        try {
            ancInstructors.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Instructors/InstructorDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancInstructors.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructors.heightProperty());

            ancInstructors.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    private void loadLabelData() {
        List<InstructorDTO> instructorDTOS = instructorBO.getAllInstructors();
        int instructorCount = instructorDTOS.size();
        lblNoOfInstructors.setText(String.valueOf(instructorCount));

        if (InstructorDetailsController.selectedInstructorId != null) {
            int studentCount = 0;
            int lessonsDone = 0;
            List<LessonDTO> lessonDTOS = lessonBO.searchLesson(InstructorDetailsController.selectedInstructorId);
            List<String> students = new ArrayList<>();

            List<String> newStudents = new ArrayList<>();
            for (LessonDTO lessonDTO : lessonDTOS) {
                if (lessonDTO.getStatus().equals("Completed")) {
                    ++lessonsDone;
                }
                String studentId = lessonDTO.getStudentId();
                if (!newStudents.contains(studentId)) {
                    newStudents.add(studentId);
                }
            }

            students.addAll(newStudents);
            studentCount = newStudents.size();

            lblNoOfStudents.setText(String.valueOf(studentCount));
            lblLessonsDone.setText(String.valueOf(lessonsDone));
        }
    }

    public void btnLessonsOnAction(MouseEvent mouseEvent) {
        if(InstructorDetailsController.selectedInstructorId == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select An Instructor").show();
            return;
        }

        ArrayList<LessonDTO> lessonDTOS = lessonBO.searchLesson(InstructorDetailsController.selectedInstructorId);
        for (LessonDTO lessonDTO : lessonDTOS) {
            LocalDate lessonDate = LocalDate.parse(lessonDTO.getDate());

            if(lessonDate.isBefore(LocalDate.now())){
                lessonDTO.setStatus("Completed");
                lessonBO.updateLessonStatus(lessonDTO);
            }
        }

        try {
            ancInstructorPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Lessons/LessonsPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancInstructorPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructorPage.heightProperty());

            ancInstructorPage.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void resetPageOnAction(MouseEvent mouseEvent) {
        loadLabelData();
    }
}
