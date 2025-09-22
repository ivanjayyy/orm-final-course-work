package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.lesson;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor.InstructorDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LessonsPageController implements Initializable {
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);

    public AnchorPane ancLessons;
    public AnchorPane ancLessonPage;
    public TextField lblNoOfLessons;
    public TextField lblCompletedLessons;
    public TextField lblPendingLessons;

    public void btnHomeOnAction(MouseEvent mouseEvent) {
        try {
            ancLessonPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Instructors/InstructorsPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancLessonPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLessonPage.heightProperty());

            ancLessonPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLabelData();

        try {
            ancLessons.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Lessons/LessonDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancLessons.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLessons.heightProperty());

            ancLessons.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }

    private void loadLabelData() {
        List<LessonDTO> lessonDTOS = lessonBO.searchLesson(InstructorDetailsController.selectedInstructorId);

        int lessonCount = 0;
        int completedLessonCount = 0;
        int pendingLessonCount = 0;

        for(LessonDTO lessonDTO : lessonDTOS){
            if(lessonDTO.getStatus().equals("Completed")){
                ++completedLessonCount;
            }else{
                ++pendingLessonCount;
            }
        }

        lessonCount = lessonDTOS.size();

        lblNoOfLessons.setText(String.valueOf(lessonCount));
        lblCompletedLessons.setText(String.valueOf(completedLessonCount));
        lblPendingLessons.setText(String.valueOf(pendingLessonCount));
    }
}
