package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.course;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.CoursesTM;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseDetailsController implements Initializable {
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    public AnchorPane ancCourseDetails;
    public TableView<CoursesTM> tblCourses;
    public TableColumn<CoursesTM, String> colCourseId;
    public TableColumn<CoursesTM, String> colCourseName;
    public TableColumn<CoursesTM, Integer> colDuration;

    public void btnViewOnAction(MouseEvent actionEvent) {
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
        loadTableData();
    }

    private void loadTableData() {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        List<CourseDTO> courseDTOS = courseBO.getAllCourses();
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
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

}
