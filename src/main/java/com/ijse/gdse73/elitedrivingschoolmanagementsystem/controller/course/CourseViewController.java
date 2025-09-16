package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.course;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseViewController implements Initializable {
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
//    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);

    public AnchorPane ancCourseView;
    public TextField inputCourseFee;
    public TextField inputDuration;
    public TextField inputDescription;
    public TextField inputCourseName;
    public TextField inputCourseId;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    private final String courseNameRegex = "^[A-Z][a-z]+(?: [A-Z][a-z]+)*$";
    private final String courseFeeRegex = "^[0-9]+(\\.[0-9]{1,2})?$";
    private final String courseDurationRegex = "^[0-9]+$";
    private final String courseDescriptionRegex = "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$";

    public void goToCourseDetailsPage(){
        try {
            ancCourseView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Courses/CourseDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancCourseView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCourseView.heightProperty());

            ancCourseView.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
        goToCourseDetailsPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(CourseDetailsController.addCourse){
            inputCourseId.setText(courseBO.getNextCourseId());

            lblUpdate.setText("ADD");
            imgUpdate.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/add.gif")));

            lblDelete.setText("RESET");
            imgDelete.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/reset.gif")));
        }

        if(CourseDetailsController.selectedCourseId!=null){
            CourseDTO selectedCourse = courseBO.searchCourse(CourseDetailsController.selectedCourseId).getFirst();

            inputCourseId.setText(selectedCourse.getCourseId());
            inputCourseName.setText(selectedCourse.getName());
            inputDescription.setText(selectedCourse.getDescription());
            inputDuration.setText(String.valueOf(selectedCourse.getDuration()));
            inputCourseFee.setText(String.valueOf(selectedCourse.getFee()));
        }

    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        int wrongRegex = 0;

        if(inputCourseId.getText().equals("") || inputCourseName.getText().equals("") || inputDescription.getText().equals("") || inputDuration.getText().equals("") || inputCourseFee.getText().equals("")){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields").show();
            return;
        }

        if(!inputCourseName.getText().matches(courseNameRegex)){
            inputCourseName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputCourseName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputDuration.getText().matches(courseDurationRegex)){
            inputDuration.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputDuration.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputCourseFee.getText().matches(courseFeeRegex)){
            inputCourseFee.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputCourseFee.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputDescription.getText().matches(courseDescriptionRegex)){
            inputDescription.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputDescription.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(wrongRegex>0){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields Correctly").show();
            return;
        }

        if (CourseDetailsController.addCourse){
            CourseDTO course = new CourseDTO(inputCourseId.getText(),inputCourseName.getText(),inputDescription.getText(),Integer.parseInt(inputDuration.getText()), new BigDecimal(inputCourseFee.getText()));
            boolean isAdded = courseBO.saveCourse(course);

            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Course Added Successfully").show();
                goToCourseDetailsPage();

            }else{
                new Alert(Alert.AlertType.ERROR,"Course Add Failed").show();
            }

        } else {
            CourseDTO updatedCourse = new CourseDTO(inputCourseId.getText(),inputCourseName.getText(),inputDescription.getText(),Integer.parseInt(inputDuration.getText()), new BigDecimal(inputCourseFee.getText()));
            boolean isUpdated = courseBO.updateCourse(updatedCourse);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Course Updated Successfully").show();
                goToCourseDetailsPage();

            }else{
                new Alert(Alert.AlertType.ERROR,"Course Update Failed").show();
            }
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblDelete.getText().equals("DELETE")){
//            List<LessonDTO> lessonDTOS = lessonBO.getAllLessons();
//
//            for (LessonDTO lessonDTO : lessonDTOS) {
//                if(lessonDTO.getCourseId().equals(inputCourseId.getText()) && lessonDTO.getStatus().equals("Pending")){
//                    new Alert(Alert.AlertType.ERROR,"There Are Lessons Still Available For This Course").show();
//                    return;
//                }
//            }

            boolean isDeleted = courseBO.deleteCourse(inputCourseId.getText());

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Course Deleted Successfully").show();
                goToCourseDetailsPage();

            }else{
                new Alert(Alert.AlertType.ERROR,"Course Delete Failed").show();
            }

        } else {
            inputCourseName.setText("");
            inputDescription.setText("");
            inputDuration.setText("");
            inputCourseFee.setText("");

            new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
        }
    }

    public void btnViewStudentsOfCourse(MouseEvent mouseEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Courses/StudentsOfCourse.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Students Of Course");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error In Opening Students Of Course Window").show();
        }
    }
}
