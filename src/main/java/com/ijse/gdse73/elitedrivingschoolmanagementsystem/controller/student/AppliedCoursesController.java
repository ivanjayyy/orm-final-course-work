package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.InstructorDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.AssignedCoursesTM;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppliedCoursesController implements Initializable {
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
    InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);

    ObservableList<AssignedCoursesTM> assignedCoursesTMS = FXCollections.observableArrayList();

    public TableView<AssignedCoursesTM> tblAppliedCourses;
    public TableColumn<?, ?> colBtnRemove;
    public TableColumn<AssignedCoursesTM,String> colCourseName;
    public TableColumn<AssignedCoursesTM,String> colCourseId;
    public JFXComboBox<String> comboCourses;

    public HBox btnAdd;
    public TextField lblWeeksLeft;
    public TextField lblWeeksDone;
    public HBox btnSave;

    public static ArrayList<String> addCourseIds = new ArrayList<>();

    public void btnSaveOnAction(MouseEvent mouseEvent) {
        if(StudentDetailsController.selectedStudentId != null){
            new Alert(Alert.AlertType.ERROR, "Can't Edit Course Details").show();
            return;
        }

        if(tblAppliedCourses.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Add A Course").show();
            return;
        }

        for (AssignedCoursesTM assignedCoursesTM : assignedCoursesTMS) {
            addCourseIds.add(assignedCoursesTM.getCourseId());
        }

        new Alert(Alert.AlertType.INFORMATION, "Applied Courses Saved").show();
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if(comboCourses.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Select A Course").show();
            return;
        }

        String courseId = courseBO.searchCourse(comboCourses.getSelectionModel().getSelectedItem()).getFirst().getCourseId();
        String courseName = comboCourses.getSelectionModel().getSelectedItem();
        JFXButton btnRemove = new JFXButton("REMOVE");

        AssignedCoursesTM assignedCoursesTM = new AssignedCoursesTM(courseId,courseName,btnRemove);
        assignedCoursesTMS.add(assignedCoursesTM);

        btnRemove.setOnAction(event -> {
            assignedCoursesTMS.remove(assignedCoursesTM);
            tblAppliedCourses.refresh();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableData();
        addCourseIds.clear();

        ButtonScale.hboxScaling(btnSave);
        ButtonScale.hboxScaling(btnAdd);

        if(StudentDetailsController.addStudent){
            addCourseNamesToComboBox();
        }
    }

    private void loadTableData() {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colBtnRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

        if(StudentDetailsController.selectedStudentId != null) {
            List<StudentDTO> searchStudent = studentBO.searchStudent(StudentDetailsController.selectedStudentId);
            List<String> courseIds = searchStudent.getFirst().getCourseIds();

            for (String courseId : courseIds) {
                List<CourseDTO> searchCourse = courseBO.searchCourse(courseId);
                String courseName;

                if(!searchCourse.isEmpty()) {
                    courseName = searchCourse.getFirst().getName();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Course Not Found").show();
                    return;
                }

                JFXButton btnRemove = new JFXButton("");

                AssignedCoursesTM assignedCoursesTM = new AssignedCoursesTM(courseId,courseName,btnRemove);
                assignedCoursesTMS.add(assignedCoursesTM);
            }
        }

        tblAppliedCourses.setItems(assignedCoursesTMS);
    }

    private void addCourseNamesToComboBox() {
        List<CourseDTO> courseDTOS = courseBO.getAllCourses();
        ObservableList<String> courseNames = FXCollections.observableArrayList();

        for (CourseDTO courseDTO : courseDTOS) {
            List<InstructorDTO> instructors = instructorBO.searchInstructor(courseDTO.getCourseId());

            if(!instructors.isEmpty()){
                courseNames.add(courseDTO.getName());
            }
        }

        comboCourses.setItems(courseNames);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        if(StudentDetailsController.selectedStudentId != null){
            AssignedCoursesTM selectedCourse = tblAppliedCourses.getSelectionModel().getSelectedItem();

            if(selectedCourse != null){
                String courseId = selectedCourse.getCourseId();

                int courseDuration = courseBO.searchCourse(courseId).getFirst().getDuration();
                String admissionDate = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst().getDate();

                int daysUntilEndDay = courseDuration * 7;
                long daysDone = ChronoUnit.DAYS.between(LocalDate.parse(admissionDate), LocalDate.now());
                int weeksDone = (int) (daysDone / 7);
                int weeksLeft = courseDuration - weeksDone;

                lblWeeksLeft.setText(String.valueOf(weeksLeft));
                lblWeeksDone.setText(String.valueOf(weeksDone));
            }
        }
    }
}
