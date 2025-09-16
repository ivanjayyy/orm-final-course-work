package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.lesson;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor.InstructorDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LessonViewController implements Initializable {
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    public AnchorPane ancLessonView;
    public TextField inputLessonId;
    public JFXComboBox<String> inputStudentName;
    public DatePicker inputLessonDate;
    public TextField inputLessonStatus;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    String instructorCourse = instructorBO.searchInstructor(InstructorDetailsController.selectedInstructorId).getFirst().getCourseId();

    public void goToLessonDetailsPage(){
        try {
            ancLessonView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Lessons/LessonDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancLessonView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLessonView.heightProperty());

            ancLessonView.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
        goToLessonDetailsPage();
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        if(inputLessonId.getText().equals("") || inputLessonDate.getValue() == null || inputLessonStatus.getText().equals("") ){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields").show();
            return;
        }

        if(inputStudentName.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Select A Student").show();
            return;
        }

        ArrayList<LessonDTO> lessonDTOS = lessonBO.searchLesson(inputStudentName.getValue());
        for (LessonDTO lessonDTO : lessonDTOS) {
            String date = lessonDTO.getDate();

            if(date.equals(inputLessonDate.getValue().toString())){
                new Alert(Alert.AlertType.ERROR, "Student Already Has A Lesson On This Date").show();
                return;
            }
        }

        ArrayList<LessonDTO> lessonDTOS2 = lessonBO.searchLesson(InstructorDetailsController.selectedInstructorId);
        for (LessonDTO lessonDTO : lessonDTOS2) {
            String date = lessonDTO.getDate();

            if(date.equals(inputLessonDate.getValue().toString())){
                new Alert(Alert.AlertType.ERROR, "Instructor Already Has A Lesson On This Date").show();
                return;
            }
        }

        String studentId = studentBO.searchStudent(inputStudentName.getValue()).getFirst().getStudentId();
        String startDate = studentBO.searchStudent(studentId).getFirst().getDate();

        int courseDuration = courseBO.searchCourse(instructorCourse).getFirst().getDuration();
        int daysUntilEndDay = courseDuration * 7;
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(startDate), inputLessonDate.getValue());

        if(daysBetween > daysUntilEndDay){
            new Alert(Alert.AlertType.ERROR, "Student Has Ended His/Her Course").show();
            return;
        }

        if(LessonDetailsController.addLesson){
            boolean isRegistered = studentBO.searchStudent(studentId).getFirst().isRegistered();

            if(!isRegistered){
                new Alert(Alert.AlertType.ERROR, "Student Is Not Registered").show();
                return;
            }

            LessonDTO lesson = new LessonDTO(
                    inputLessonId.getText(),
                    inputLessonDate.getValue().toString(),
                    inputLessonStatus.getText(),
                    instructorCourse,
                    InstructorDetailsController.selectedInstructorId,
                    studentId
            );
            boolean isAdded = lessonBO.saveLesson(lesson);

            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Lesson Added Successfully").show();
                goToLessonDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Lesson Add Failed").show();
            }

        } else {
            LessonDTO updatedLesson = new LessonDTO(
                    inputLessonId.getText(),
                    inputLessonDate.getValue().toString(),
                    inputLessonStatus.getText(),
                    instructorCourse,
                    InstructorDetailsController.selectedInstructorId,
                    studentId
            );
            boolean isUpdated = lessonBO.updateLesson(updatedLesson);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Lesson Updated Successfully").show();
                goToLessonDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Lesson Update Failed").show();
            }
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblDelete.getText().equals("DELETE")){
            boolean isDeleted = lessonBO.deleteLesson(inputLessonId.getText());

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Lesson Deleted Successfully").show();
                goToLessonDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Lesson Delete Failed").show();
            }

        } else {
            inputStudentName.setValue(null);
            inputLessonDate.setValue(null);
            inputLessonStatus.setText("");

            new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStudentNamesToComboBox();
        inputLessonStatus.setText("Pending");

        if(LessonDetailsController.addLesson){
            inputLessonId.setText(lessonBO.getNextLessonId());
            inputLessonDate.setValue(LocalDate.now());

            lblUpdate.setText("ADD");
            imgUpdate.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/add.gif")));

            lblDelete.setText("RESET");
            imgDelete.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/reset.gif")));
        }

        if(LessonDetailsController.selectedLessonId!=null){
            LessonDTO selectedLesson = lessonBO.searchLesson(LessonDetailsController.selectedLessonId).getFirst();
            String studentName = studentBO.searchStudent(selectedLesson.getStudentId()).getFirst().getName();

            inputLessonId.setText(selectedLesson.getLessonId());
            inputStudentName.setValue(studentName);
            inputLessonDate.setValue(LocalDate.parse(selectedLesson.getDate()));
            inputLessonStatus.setText(selectedLesson.getStatus());
        }
    }

    private void addStudentNamesToComboBox() {
        List<StudentDTO> studentDTOS = studentBO.getAllStudents();
        ObservableList<String> studentNames = FXCollections.observableArrayList();

        for (StudentDTO studentDTO : studentDTOS) {
            if(studentDTO.isRegistered()){
                List<String> courseIds = studentBO.searchStudent(studentDTO.getStudentId()).getFirst().getCourseIds();

                for (String courseId : courseIds) {
                    if (courseId.equals(instructorCourse)) {
                        studentNames.add(studentDTO.getName());
                    }
                }
            }
        }
        inputStudentName.setItems(studentNames);
    }
}
