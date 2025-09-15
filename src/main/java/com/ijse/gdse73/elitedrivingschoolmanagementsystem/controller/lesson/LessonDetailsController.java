package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.lesson;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor.InstructorDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.LessonsTM;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LessonDetailsController implements Initializable {
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    public AnchorPane ancLessonDetails;
    public TableView<LessonsTM> tblLessons;
    public TableColumn<LessonsTM, String> colDate;
    public TableColumn<LessonsTM, String> colStudentName;
    public TableColumn<LessonsTM, String> colLessonStatus;
    public TableColumn<LessonsTM, String> colLessonID;

    public static String selectedLessonId;
    public static boolean addLesson;

    public void onClickTable(MouseEvent mouseEvent) {
        LessonsTM selectedLesson = tblLessons.getSelectionModel().getSelectedItem();

        if(selectedLesson != null){
            selectedLessonId = selectedLesson.getLessonId();
        }
    }

    public void btnResetOnAction(MouseEvent mouseEvent) {
        resetPage();
        new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if(selectedLessonId != null){
            new Alert(Alert.AlertType.ERROR,"Please Unselect The Lesson").show();
            return;
        }

        try {
            addLesson = true;

            ancLessonDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Lessons/LessonView.fxml"));

            anchorPane.prefWidthProperty().bind(ancLessonDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLessonDetails.heightProperty());

            ancLessonDetails.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }

    public void btnViewOnAction(MouseEvent mouseEvent) {
        if(selectedLessonId == null){
            new Alert(Alert.AlertType.ERROR,"Please Select A Lesson").show();
            return;
        }

        try {
            ancLessonDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Lessons/LessonView.fxml"));

            anchorPane.prefWidthProperty().bind(ancLessonDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLessonDetails.heightProperty());

            ancLessonDetails.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetPage();
    }

    private void resetPage() {
        loadTableData();
        selectedLessonId = null;
        addLesson = false;
    }

    private void loadTableData() {
        colLessonID.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLessonStatus.setCellValueFactory(new PropertyValueFactory<>("lessonStatus"));

        List<LessonDTO> lessonDTOS = lessonBO.getAllLessons();
        ObservableList<LessonsTM> lessonsTMS = FXCollections.observableArrayList();

        for (LessonDTO lessonDTO : lessonDTOS) {
            String instructorId = lessonDTO.getInstructorId();

            if(instructorId.equals(InstructorDetailsController.selectedInstructorId)){
                String studentName = studentBO.searchStudent(lessonDTO.getStudentId()).getFirst().getName();

                LessonsTM lessonsTM = new LessonsTM(lessonDTO.getLessonId(),studentName,lessonDTO.getDate(),lessonDTO.getStatus());
                lessonsTMS.add(lessonsTM);
            }
        }

        tblLessons.setItems(lessonsTMS);
    }
}
