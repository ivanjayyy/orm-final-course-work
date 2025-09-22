package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.PaymentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions.DrivingSchoolException;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions.ExceptionHandler;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.PaymentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.Mail;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOTypes.PAYMENT);
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOTypes.LESSON);

    public AnchorPane ancStudentView;
    public TextField lblLessonsLeft;
    public TextField lblNextLesson;
    public TextField inputStudentId;
    public JFXRadioButton radioMale;
    public JFXRadioButton radioFemale;
    public TextField inputStudentName;
    public TextField inputAddress;
    public TextField inputNic;
    public TextField inputContact;
    public TextField inputEmail;
    public JFXRadioButton radioYes;
    public JFXRadioButton radioNo;
    public DatePicker inputRegisterDate;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    private final String studentNameRegex = "^[A-Z][a-z]+(?: [A-Z][a-z]+)*$";
    private final String studentAddressRegex = "^[A-Z][a-z]+(?: [A-Z][a-z]+)*$";
    private final String studentNicRegex = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$";
    private final String studentContactRegex = "^0\\d{9}$";
    private final String studentEmailRegex = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,}$";

    public void goToStudentDetailsPage(){
        try {
            ancStudentView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancStudentView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentView.heightProperty());

            ancStudentView.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
        goToStudentDetailsPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup genderGroup = new ToggleGroup();
        radioMale.setToggleGroup(genderGroup);
        radioFemale.setToggleGroup(genderGroup);
        radioMale.setSelected(true);

        ToggleGroup registerGroup = new ToggleGroup();
        radioYes.setToggleGroup(registerGroup);
        radioNo.setToggleGroup(registerGroup);
        radioNo.setSelected(true);

        if(StudentDetailsController.addStudent){
            inputStudentId.setText(studentBO.getNextStudentId());

            lblUpdate.setText("ADD");
            imgUpdate.setImage(new Image(getClass().getResourceAsStream("/images/add.gif")));

            lblDelete.setText("RESET");
            imgDelete.setImage(new Image(getClass().getResourceAsStream("/images/reset.gif")));
        }

        if(StudentDetailsController.selectedStudentId!=null){
            StudentDTO selectedStudent = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst();

            inputStudentId.setText(selectedStudent.getStudentId());

            String gender = selectedStudent.getGender();
            if(gender.equals("Male")){
                radioMale.setSelected(true);
            } else {
                radioFemale.setSelected(true);
            }

            inputStudentName.setText(selectedStudent.getName());
            inputAddress.setText(selectedStudent.getAddress());

            inputNic.setText(selectedStudent.getNic());
            inputNic.setEditable(false);

            inputContact.setText(selectedStudent.getContact());
            inputEmail.setText(selectedStudent.getEmail());

            inputRegisterDate.setValue(LocalDate.parse(selectedStudent.getDate()));
            inputRegisterDate.setEditable(false);

            boolean isRegistered = selectedStudent.isRegistered();
            if(isRegistered){
                radioYes.setSelected(true);
            } else {
                radioNo.setSelected(true);
            }

            List<LessonDTO> lessonDTOS = lessonBO.searchLesson(selectedStudent.getStudentId());
            int pendingLessons = 0;

            for(LessonDTO lessonDTO : lessonDTOS){
                if(lessonDTO.getStatus().equals("Pending")){
                    ++pendingLessons;
                }
            }

            lblLessonsLeft.setText(String.valueOf(pendingLessons));

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

    public void btnSeeCoursesOnAction(MouseEvent mouseEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Students/AppliedCourses.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Applied Courses");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error In Opening Applied Courses Window").show();
        }
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        int wrongRegex = 0;

        if(inputStudentName.getText().equals("") || inputAddress.getText().equals("") || inputNic.getText().equals("") || inputContact.getText().equals("") || inputEmail.getText().equals("") || inputRegisterDate.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields").show();
            return;
        }

        if(AppliedCoursesController.addCourseIds.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Select A Course").show();
            return;
        }

        if(!inputStudentName.getText().matches(studentNameRegex)){
            inputStudentName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputStudentName.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputAddress.getText().matches(studentAddressRegex)){
            inputAddress.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputAddress.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputNic.getText().matches(studentNicRegex)){
            inputNic.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputNic.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputContact.getText().matches(studentContactRegex)){
            inputContact.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputContact.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(!inputEmail.getText().matches(studentEmailRegex)){
            inputEmail.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            wrongRegex++;
        } else {
            inputEmail.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

        if(wrongRegex > 0){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields Correctly").show();
            return;
        }

        if(StudentDetailsController.addStudent){
            ArrayList<String> courseIds = new ArrayList<>(AppliedCoursesController.addCourseIds);
            String gender;

            if (radioMale.isSelected()) {
                gender = "Male";
            } else {
                gender = "Female";
            }

            boolean isAdded;
            StudentDTO student = new StudentDTO(inputStudentId.getText(),gender,inputStudentName.getText(),inputAddress.getText(),inputNic.getText(),inputContact.getText(),inputEmail.getText(),radioYes.isSelected(),inputRegisterDate.getValue().toString(),courseIds);

            try {
                isAdded = studentBO.saveStudent(student);
            } catch (DrivingSchoolException ex) {
                ExceptionHandler.handleException(ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                return;
            }

            if(student.isRegistered()){
                for (String courseId : courseIds) {
                    BigDecimal courseFee = courseBO.searchCourse(courseId).getFirst().getFee();
                    int admissionFee = courseFee.intValue() / 10;

                    PaymentDTO paymentDTO = new PaymentDTO(paymentBO.getNextPaymentId(), inputRegisterDate.getValue().toString(), new BigDecimal(admissionFee), student.getStudentId(), courseId);
                    paymentBO.savePayment(paymentDTO);
                }
            }

            if(isAdded){
                String courseIdsString = "";

                for (String courseId : courseIds) {
                    courseIdsString = courseIdsString  + "\n" + courseBO.searchCourse(courseId).getFirst().getName();
                }

                String to = inputEmail.getText();
                String subject = "Welcome To Elite Driving School";
                String body = "Dear student,\nYour assigned courses are: \n" + courseIdsString + "\n\nThank you for your registration.";

                Runnable task = () -> {
                    Mail.sendMail(to, subject, body);
                };
                Thread thread = new Thread(task);
                thread.start();

                new Alert(Alert.AlertType.INFORMATION,"Student Added Successfully").show();
                goToStudentDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR,"Student Add Failed").show();
            }

        } else {
            StudentDTO updatedStudent = getSelectedStudent();
            updatedStudent.setGender(radioMale.isSelected()?"Male":"Female");
            updatedStudent.setName(inputStudentName.getText());
            updatedStudent.setAddress(inputAddress.getText());
            updatedStudent.setContact(inputContact.getText());
            updatedStudent.setEmail(inputEmail.getText());
            updatedStudent.setRegistered(radioYes.isSelected());

            boolean isUpdated;

            try {
                isUpdated = studentBO.updateStudent(updatedStudent);
            } catch (DrivingSchoolException ex) {
                ExceptionHandler.handleException(ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                return;
            }

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Student Updated Successfully").show();
                goToStudentDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR,"Student Update Failed").show();
            }
        }
    }

    public StudentDTO getSelectedStudent(){
        return studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst();
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblDelete.getText().equals("DELETE")){
            List<LessonDTO> lessonDTOS = lessonBO.getAllLessons();
            for (LessonDTO lessonDTO : lessonDTOS) {
                if(lessonDTO.getStudentId().equals(inputStudentId.getText())){
                    lessonBO.deleteLesson(lessonDTO.getLessonId());
                }
            }

            boolean isDeleted = studentBO.deleteStudent(inputStudentId.getText());

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Student Deleted Successfully").show();
                goToStudentDetailsPage();
            } else {
                new Alert(Alert.AlertType.ERROR,"Student Delete Failed").show();
            }

        } else {
            inputStudentName.setText("");
            inputAddress.setText("");
            inputNic.setText("");
            inputContact.setText("");
            inputEmail.setText("");
            radioMale.setSelected(true);
            radioNo.setSelected(true);
            inputRegisterDate.setValue(null);

            new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
        }
    }
}
