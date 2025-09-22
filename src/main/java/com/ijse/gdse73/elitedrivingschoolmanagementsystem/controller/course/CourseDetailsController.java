package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.course;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.userLogin.LoginPageController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.CoursesTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseDetailsController implements Initializable {
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    public AnchorPane ancCourseDetails;
    public TextField inputSearch;
    public TableView<CoursesTM> tblCourses;
    public TableColumn<CoursesTM, String> colCourseId;
    public TableColumn<CoursesTM, String> colCourseName;
    public TableColumn<CoursesTM, Integer> colDuration;

    public static String selectedCourseId;
    public static boolean addCourse;

    public void btnViewOnAction(MouseEvent actionEvent) {
        if(!LoginPageController.isAdmin){
            new Alert(Alert.AlertType.ERROR, "You Are Not Authorized To View This Page").show();
            return;
        }

        if (selectedCourseId == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select A Course").show();
            return;
        }

        try {
            ancCourseDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Courses/CourseView.fxml"));

            anchorPane.prefWidthProperty().bind(ancCourseDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCourseDetails.heightProperty());

            ancCourseDetails.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetPage();
    }

    public void resetPage(){
        inputSearch.clear();
        loadTableData();
        selectedCourseId = null;
        addCourse = false;
    }

    private void loadTableData() {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        List<CourseDTO> courseDTOS;

        if(inputSearch.getText().isEmpty()) {
            courseDTOS = courseBO.getAllCourses();
        } else {
            courseDTOS = courseBO.searchCourse(inputSearch.getText());
        }

        ObservableList<CoursesTM> coursesTMS = FXCollections.observableArrayList();

        for (CourseDTO courseDTO : courseDTOS) {
            CoursesTM coursesTM = new CoursesTM(
                    courseDTO.getCourseId(),
                    courseDTO.getName(),
                    courseDTO.getDuration()
            );
            coursesTMS.add(coursesTM);
        }
        tblCourses.setItems(coursesTMS);

    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if(!LoginPageController.isAdmin){
            new Alert(Alert.AlertType.ERROR, "You Are Not Authorized To View This Page").show();
            return;
        }

        if (selectedCourseId != null) {
            new Alert(Alert.AlertType.ERROR, "Please Unselect The Course").show();
            return;
        }

        try {
            addCourse = true;

            ancCourseDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Courses/CourseView.fxml"));

            anchorPane.prefWidthProperty().bind(ancCourseDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCourseDetails.heightProperty());

            ancCourseDetails.getChildren().add(anchorPane);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CoursesTM selectedCourse = tblCourses.getSelectionModel().getSelectedItem();

        if (selectedCourse != null) {
            selectedCourseId = selectedCourse.getCourseId();
        }
    }

    public void btnResetOnAction(MouseEvent mouseEvent) {
        resetPage();
        new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
    }

    public void btnSearchOnAction(MouseEvent mouseEvent) {
        loadTableData();
    }
}
