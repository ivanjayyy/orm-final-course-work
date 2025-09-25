package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.course;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.InstructorDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CoursesPageController implements Initializable {
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);

    public AnchorPane ancCourses;
    public TextField lblNoOfCourses;
    public TextField lblMostPopularCourse;
    public TextField lblBestInstructor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLabelData();

        try {
            ancCourses.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Courses/CourseDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancCourses.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCourses.heightProperty());

            ancCourses.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void loadLabelData() {
        int courseCount = courseBO.getAllCourses().size();
        int maxStudentCount = 0;

        List<CourseDTO> courseDTOS = courseBO.getAllCourses();
        List<StudentDTO> studentDTOS = studentBO.getAllStudents();

        if(CourseDetailsController.selectedCourseId!=null){
            List<InstructorDTO> instructors = instructorBO.searchInstructor(CourseDetailsController.selectedCourseId);
            int maxLessons = 0;

            if(instructors.isEmpty()){
                lblBestInstructor.clear();
                return;
            }

            for(InstructorDTO instructorDTO : instructors){
                List<LessonDTO> lessons = lessonBO.searchLesson(instructorDTO.getInstructorId());
                int lessonCount = 0;

                for(LessonDTO lessonDTO : lessons){
                    ++lessonCount;
                }

                if(lessonCount > maxLessons){
                    maxLessons = lessonCount;
                    lblBestInstructor.setText(instructorDTO.getName());
                }

                if (lessonCount == 0) {
                    lblBestInstructor.clear();
                }
            }
        } else {
            lblBestInstructor.clear();
        }

        for(CourseDTO courseDTO : courseDTOS){
            int studentCount = 0;

            for(StudentDTO studentDTO : studentDTOS){
                if(studentDTO.getCourseIds().contains(courseDTO.getCourseId())) {
                    ++studentCount;
                }
            }

            if(studentCount > maxStudentCount){
                maxStudentCount = studentCount;
                lblMostPopularCourse.setText(courseDTO.getName());
            }
        }

        lblNoOfCourses.setText(String.valueOf(courseCount));
    }

    public void resetPageOnAction(MouseEvent mouseEvent) {
        loadLabelData();
    }
}
