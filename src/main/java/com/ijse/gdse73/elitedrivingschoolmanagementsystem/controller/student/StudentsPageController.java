package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
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
import java.util.List;
import java.util.ResourceBundle;

public class StudentsPageController implements Initializable {
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    public AnchorPane ancStudents;
    public AnchorPane ancStudentsPage;
    public TextField lblCourses;
    public TextField lblNoOfStudents;
    public TextField lblLessonsDone;
    public HBox btnPayments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int size = studentBO.getAllStudents().size();
        ButtonScale.hboxScaling(btnPayments);

        lblLessonsDone.clear();
        lblNoOfStudents.setText(size+"");

        try {
            ancStudents.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudents.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudents.heightProperty());

            ancStudents.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnPaymentsOnAction(MouseEvent mouseEvent) {
        if(StudentDetailsController.selectedStudentId==null){
            new Alert(Alert.AlertType.ERROR, "Please Select A Student").show();
            return;
        }

        try {
            ancStudentsPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Payments/PaymentsPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudentsPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentsPage.heightProperty());

            ancStudentsPage.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void loadTableDate() {
        if(StudentDetailsController.selectedStudentId!=null){
            List<LessonDTO> lessonDTOS = lessonBO.searchLesson(StudentDetailsController.selectedStudentId);
            int completedLessons = 0;

            for(LessonDTO lessonDTO : lessonDTOS){
                if(lessonDTO.getStatus().equals("Completed")){
                    ++completedLessons;
                }
            }

            lblLessonsDone.setText(String.valueOf(completedLessons));
            int coursesAmount = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst().getCourseIds().size();
            lblCourses.setText(coursesAmount+"");
            lblNoOfStudents.setText(String.valueOf(studentBO.getAllStudents().size()));
        } else {
            lblLessonsDone.clear();
            lblCourses.clear();
        }
    }

    public void resetPageOnAction(MouseEvent mouseEvent) {
        loadTableDate();
    }
}
