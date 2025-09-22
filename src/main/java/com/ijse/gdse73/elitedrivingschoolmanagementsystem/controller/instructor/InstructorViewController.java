package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.course.CourseDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.InstructorDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorViewController implements Initializable {
    InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);

    public AnchorPane ancInstructorView;
    public HBox btnUpdate;
    public HBox btnDelete;
    public HBox btnBack;
    public TextField lblLessonsLeft;
    public TextField lblNextLesson;
    public TextField inputInstructorId;
    public TextField inputInstructorName;
    public TextField inputContact;
    public TextField inputEmail;
    public JFXComboBox<String> inputAssignedCourse;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    private final String instructorNameRegex = "^[A-Z][a-z]+(?: [A-Z][a-z]+)*$";
    private final String instructorContactRegex = "^0\\d{9}$";
    private final String instructorEmailRegex = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,}$";

    public void goToInstructorDetailsPage(){
        try {
            ancInstructorView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Instructors/InstructorDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancInstructorView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructorView.heightProperty());

            ancInstructorView.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
        goToInstructorDetailsPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCourseNamesToComboBox();

        ButtonScale.hboxScaling(btnUpdate);
        ButtonScale.hboxScaling(btnDelete);
        ButtonScale.hboxScaling(btnBack);

        if(InstructorDetailsController.addInstructor){
            inputInstructorId.setText(instructorBO.getNextInstructorId());

            lblUpdate.setText("ADD");
            imgUpdate.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/add.gif")));

            lblDelete.setText("RESET");
            imgDelete.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/reset.gif")));
        }

        if(InstructorDetailsController.selectedInstructorId!=null){
            InstructorDTO selectedInstructor = instructorBO.searchInstructor(InstructorDetailsController.selectedInstructorId).getFirst();
            String courseName = courseBO.searchCourse(selectedInstructor.getCourseId()).getFirst().getName();

            inputInstructorId.setText(selectedInstructor.getInstructorId());
            inputInstructorName.setText(selectedInstructor.getName());
            inputContact.setText(selectedInstructor.getContact());
            inputEmail.setText(selectedInstructor.getEmail());
            inputAssignedCourse.setValue(courseName);

            List<LessonDTO> lessonDTOS = lessonBO.searchLesson(selectedInstructor.getInstructorId());
            int pendingLessons = 0;

            for(LessonDTO lessonDTO : lessonDTOS){
                if(lessonDTO.getStatus().equals("Pending")){
                    ++pendingLessons;
                }
            }
            lblLessonsLeft.setText(pendingLessons+"");

            if(pendingLessons > 0){
                LocalDate nextLesson = null;

                for(LessonDTO lessonDTO : lessonDTOS){
                    if(lessonDTO.getStatus().equals("Pending")){
                        nextLesson = LocalDate.parse(lessonDTO.getDate());
                        break;
                    }
                }

                for(LessonDTO lessonDTO : lessonDTOS){
                    if(lessonDTO.getStatus().equals("Pending")){
                        LocalDate date = LocalDate.parse(lessonDTO.getDate());

                        if(nextLesson.isAfter(date)){
                            nextLesson = date;
                        }
                    }
                }
                lblNextLesson.setText(nextLesson.toString());
            }
        }
    }

    private void addCourseNamesToComboBox() {
        List<CourseDTO> courseDTOS = courseBO.getAllCourses();
        ObservableList<String> courseNames = FXCollections.observableArrayList();

        for (CourseDTO courseDTO : courseDTOS) {
            courseNames.add(courseDTO.getName());
        }

        inputAssignedCourse.setItems(courseNames);
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        int wrongRegex = 0;

        if(inputInstructorId.getText().equals("") || inputInstructorName.getText().equals("") || inputContact.getText().equals("") || inputEmail.getText().equals("")){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields").show();
            return;
        }

        if(inputAssignedCourse.getValue()==null){
            new Alert(Alert.AlertType.ERROR, "Select A Course").show();
            return;
        }

        if(!inputContact.getText().matches(instructorContactRegex)){
            inputContact.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputContact.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputEmail.getText().matches(instructorEmailRegex)){
            inputEmail.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputEmail.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputInstructorName.getText().matches(instructorNameRegex)){
            inputInstructorName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputInstructorName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(wrongRegex>0){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields Correctly").show();
            return;
        }

        if (InstructorDetailsController.addInstructor) {
            String courseId = courseBO.searchCourse(inputAssignedCourse.getValue()).getFirst().getCourseId();

            InstructorDTO instructor = new InstructorDTO(
                    inputInstructorId.getText(),
                    inputInstructorName.getText(),
                    inputContact.getText(),
                    inputEmail.getText(),
                    courseId
            );
            boolean isAdded = instructorBO.saveInstructor(instructor);

            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Course Added Successfully").show();
                goToInstructorDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Course Not Added").show();
            }

        } else {
            String courseId = courseBO.searchCourse(inputAssignedCourse.getValue()).getFirst().getCourseId();

            InstructorDTO updatedInstructor = new InstructorDTO(
                    inputInstructorId.getText(),
                    inputInstructorName.getText(),
                    inputContact.getText(),
                    inputEmail.getText(),
                    courseId
            );

            boolean isUpdated = instructorBO.updateInstructor(updatedInstructor);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Course Updated Successfully").show();
                goToInstructorDetailsPage();

            } else  {
                new Alert(Alert.AlertType.ERROR, "Course Not Updated").show();
            }
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblDelete.getText().equals("DELETE")){
            List<LessonDTO> lessonDTOS = lessonBO.getAllLessons();
            for (LessonDTO lessonDTO : lessonDTOS) {
                if(lessonDTO.getInstructorId().equals(inputInstructorId.getText())){
                    lessonBO.deleteLesson(lessonDTO.getLessonId());
                }
            }

            boolean isDeleted = instructorBO.deleteInstructor(inputInstructorId.getText());

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Instructor Deleted Successfully").show();
                goToInstructorDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Instructor Delete Failed").show();
            }

        } else {
            inputInstructorName.setText("");
            inputContact.setText("");
            inputEmail.setText("");
            inputAssignedCourse.setValue(null);

            new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
        }
    }
}
