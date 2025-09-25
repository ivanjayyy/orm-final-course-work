package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.lesson;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions.DrivingSchoolException;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions.ExceptionHandler;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor.InstructorDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.Mail;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LessonViewController implements Initializable {
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    public AnchorPane ancLessonView;
    public TextField lblStudentAvailable;
    public TextField lblInstructorAvailable;
    public HBox btnBack;
    public HBox btnUpdate;
    public HBox btnDelete;
    public TextField inputLessonId;
    public JFXComboBox<String> inputStudentName;
    public DatePicker inputLessonDate;
    public TextField inputLessonStatus;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    boolean studentSelected;
    boolean dateSelected;

    String instructorCourse = instructorBO.searchInstructor(InstructorDetailsController.selectedInstructorId).getFirst().getCourseId();
    String oldDate;

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
            boolean isAdded;

            try {
                isAdded = lessonBO.saveLesson(lesson);
            } catch (DrivingSchoolException ex) {
                ExceptionHandler.handleException(ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                return;
            }

            if(isAdded){
                String to = studentBO.searchStudent(studentId).getFirst().getEmail();
                String toInstructor = instructorBO.searchInstructor(InstructorDetailsController.selectedInstructorId).getFirst().getEmail();

                String subject = "New Lesson Added";

                String body = "Dear Student,\nYou have a New Lesson.\n\nCourse : "+courseBO.searchCourse(instructorCourse).getFirst().getName()+"\nDate : "+inputLessonDate.getValue()+"\n\nThank you.";
                String bodyInstructor = "Dear Instructor,\nYou have a New Lesson.\n\nStudent : "+inputStudentName.getValue()+"\nDate : "+inputLessonDate.getValue()+"\n\nThank you.";

                Runnable task = () -> {
                    Mail.sendMail(to, subject, body);
                    Mail.sendMail(toInstructor, subject, bodyInstructor);
                };
                Thread thread = new Thread(task);
                thread.start();

                new Alert(Alert.AlertType.INFORMATION,"Lesson Added Successfully").show();
                goToLessonDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Lesson Add Failed").show();
            }

        } else {
            if(inputLessonStatus.getText().equals("Completed")) {
                new Alert(Alert.AlertType.ERROR, "Can't Update Already Completed Lessons").show();
                return;
            }

            LessonDTO updatedLesson = new LessonDTO(
                    inputLessonId.getText(),
                    inputLessonDate.getValue().toString(),
                    inputLessonStatus.getText(),
                    instructorCourse,
                    InstructorDetailsController.selectedInstructorId,
                    studentId
            );
            boolean isUpdated;

            try {
                isUpdated = lessonBO.updateLesson(updatedLesson);
            } catch (DrivingSchoolException ex) {
                ExceptionHandler.handleException(ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                return;
            }

            if(isUpdated){
                String to = studentBO.searchStudent(studentId).getFirst().getEmail();
                String toInstructor = instructorBO.searchInstructor(InstructorDetailsController.selectedInstructorId).getFirst().getEmail();

                String subject = "Your Lesson Has Been Updated";

                String body = "Dear Student,\nYour " +courseBO.searchCourse(instructorCourse).getFirst().getName()+ " Lesson on "+oldDate+" has been Updated.\n\n   New Date : " +inputLessonDate.getValue()+ "\n\nThank you.";
                String bodyInstructor = "Dear Instructor,\nYour " +courseBO.searchCourse(instructorCourse).getFirst().getName()+ " Lesson on "+oldDate+" for " +inputStudentName.getValue()+ " has been Updated.\n\n   New Date : " +inputLessonDate.getValue()+ "\n\nThank you.";

                Runnable task = () -> {
                    Mail.sendMail(to, subject, body);
                    Mail.sendMail(toInstructor, subject, bodyInstructor);
                };
                Thread thread = new Thread(task);
                thread.start();

                new Alert(Alert.AlertType.INFORMATION,"Lesson Updated Successfully").show();
                goToLessonDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Lesson Update Failed").show();
            }
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblDelete.getText().equals("DELETE")){
            if(inputLessonStatus.getText().equals("Completed")) {
                new Alert(Alert.AlertType.ERROR, "Can't Delete Already Completed Lessons").show();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            boolean isDeleted;

            if (result.isPresent() && result.get() == ButtonType.YES) {
                isDeleted = lessonBO.deleteLesson(inputLessonId.getText());
            } else {
                return;
            }

            if(isDeleted){
                String to = studentBO.searchStudent(inputStudentName.getValue()).getFirst().getEmail();
                String toInstructor = instructorBO.searchInstructor(InstructorDetailsController.selectedInstructorId).getFirst().getEmail();

                String subject = "Your Lesson Has Been Canceled";

                String body = "Dear Student,\nYour " +courseBO.searchCourse(instructorCourse).getFirst().getName()+ " Lesson on "+oldDate+" has been Canceled.\n\nThank you.";
                String bodyInstructor = "Dear Instructor,\nYour " +courseBO.searchCourse(instructorCourse).getFirst().getName()+ " Lesson on "+oldDate+" for " +inputStudentName.getValue()+ " has been Canceled.\n\nThank you.";

                Runnable task = () -> {
                    Mail.sendMail(to, subject, body);
                    Mail.sendMail(toInstructor, subject, bodyInstructor);
                };
                Thread thread = new Thread(task);
                thread.start();

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
        studentSelected = false;
        dateSelected = false;
        addStudentNamesToComboBox();
        inputLessonStatus.setText("Pending");

        ButtonScale.hboxScaling(btnUpdate);
        ButtonScale.hboxScaling(btnDelete);
        ButtonScale.hboxScaling(btnBack);

        if(LessonDetailsController.addLesson){
            inputLessonId.setText(lessonBO.getNextLessonId());

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
            oldDate = inputLessonDate.getValue().toString();
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

    public void selectDateOnAction(ActionEvent actionEvent) {
        dateSelected = true;
        checkAvailability();
    }

    public void selectStudentOnAction(ActionEvent actionEvent) {
        studentSelected = true;
        checkAvailability();
    }

    private void checkAvailability(){
        if(studentSelected && dateSelected){
            lblStudentAvailable.setText("True");
            lblInstructorAvailable.setText("True");

            String studentName = inputStudentName.getValue();
            String lessonDate = inputLessonDate.getValue().toString();

            ArrayList<LessonDTO> lessons = lessonBO.searchLesson(studentName);

            for (LessonDTO lesson : lessons) {
                String date = lesson.getDate();

                if(date.equals(lessonDate)){
                    lblStudentAvailable.setText("False");
                }
            }

            ArrayList<LessonDTO> lessons2 = lessonBO.searchLesson(InstructorDetailsController.selectedInstructorId);

            for (LessonDTO lesson : lessons2) {
                String date = lesson.getDate();

                if(date.equals(lessonDate)){
                    lblInstructorAvailable.setText("False");
                }
            }
        }
    }
}
