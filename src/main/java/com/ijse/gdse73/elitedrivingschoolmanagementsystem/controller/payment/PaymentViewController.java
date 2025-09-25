package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.payment;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.PaymentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions.DrivingSchoolException;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions.ExceptionHandler;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student.StudentDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.PaymentDTO;
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

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentViewController implements Initializable {
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOTypes.PAYMENT);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    public AnchorPane ancPaymentView;
    public TextField lblPaymentDone;
    public TextField lblPaymentLeft;
    public HBox btnBack;
    public HBox btnUpdate;
    public HBox btnDelete;
    public TextField inputPaymentId;
    public JFXComboBox<String> inputCourseName;
    public TextField inputPaidAmount;
    public DatePicker inputPaymentDate;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

    private final String paidAmountRegex = "^[0-9]+(\\.[0-9]{1,2})?$";

    public void goToPaymentDetailsPage(){
        try {
            ancPaymentView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Payments/PaymentDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancPaymentView.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPaymentView.heightProperty());

            ancPaymentView.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Exception("Page Not Found").printStackTrace();
        }
    }

    public void btnBackOnAction(MouseEvent mouseEvent) {
        goToPaymentDetailsPage();
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        if(inputPaymentId.getText().equals("") || inputPaymentDate.getValue() == null || inputPaidAmount.getText().equals("") ){
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields").show();
            return;
        }

        if(inputCourseName.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Select A Course").show();
            return;
        }

        if (!inputPaidAmount.getText().matches(paidAmountRegex)) {
            inputPaidAmount.styleProperty().setValue("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");
            new Alert(Alert.AlertType.ERROR, "Please Fill All Fields Correctly").show();
            return;
        } else {
            inputPaidAmount.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1px 0;");
        }

//        List<PaymentDTO> paymentDTOS = paymentBO.searchPayment(StudentDetailsController.selectedStudentId);
        String courseId = courseBO.searchCourse(inputCourseName.getValue()).getFirst().getCourseId();
//        BigDecimal courseFee = courseBO.searchCourse(courseId).getFirst().getFee();
//        double paymentCount = 0;
//
//        for (PaymentDTO paymentDTO : paymentDTOS) {
//            if(paymentDTO.getCourseId().equals(courseId)) {
//                paymentCount = paymentCount + Double.parseDouble(String.valueOf(paymentDTO.getPaidAmount()));
//            }
//        }
//
//        double fullPayment = paymentCount + Double.parseDouble(inputPaidAmount.getText());
//
//        if(fullPayment > courseFee.intValue()){
//            new Alert(Alert.AlertType.ERROR, "Payment Is Greater Than Course Fee").show();
//            return;
//        }

        if(PaymentDetailsController.addPayment){
            PaymentDTO payment = new PaymentDTO(inputPaymentId.getText(),inputPaymentDate.getValue().toString(),new BigDecimal(inputPaidAmount.getText()),StudentDetailsController.selectedStudentId,courseId);
            boolean isAdded;

            try {
                isAdded = paymentBO.savePayment(payment);
            } catch (DrivingSchoolException ex) {
                ExceptionHandler.handleException(ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                return;
            }

            if(isAdded){
                String to = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst().getEmail();
                String subject = "Payment Added To The Database";
                String body = "Dear Student,\nWe received your payment.\n\nPayment = Rs." + inputPaidAmount.getText() + "\nCourse = " + inputCourseName.getValue() + "\nDate = "+inputPaymentDate.getValue()+"\n\nThank you.";

                Runnable task = () -> {
                    Mail.sendMail(to, subject, body);
                };
                Thread thread = new Thread(task);
                thread.start();

                new Alert(Alert.AlertType.INFORMATION,"Payment Added Successfully").show();
                goToPaymentDetailsPage();

            }else {
                new Alert(Alert.AlertType.ERROR,"Payment Add Failed").show();
            }

        }else {

            PaymentDTO updatedPayment = new PaymentDTO(inputPaymentId.getText(),inputPaymentDate.getValue().toString(),new BigDecimal(inputPaidAmount.getText()),StudentDetailsController.selectedStudentId,courseId);
            boolean isUpdated;

            try {
                isUpdated = paymentBO.updatePayment(updatedPayment);
            } catch (DrivingSchoolException ex) {
                ExceptionHandler.handleException(ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                return;
            }

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Payment Updated Successfully").show();
                goToPaymentDetailsPage();

            } else {
                new Alert(Alert.AlertType.ERROR,"Payment Update Failed").show();
            }
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblDelete.getText().equals("DELETE")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            boolean isDeleted;

            if (result.isPresent() && result.get() == ButtonType.YES) {
                isDeleted = paymentBO.deletePayment(inputPaymentId.getText());
            } else {
                return;
            }

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Payment Deleted Successfully").show();
                goToPaymentDetailsPage();

            }else {
                new Alert(Alert.AlertType.ERROR,"Payment Delete Failed").show();
            }

        } else {
            inputCourseName.setValue(null);
            inputPaidAmount.setText("");
            inputPaymentDate.setValue(null);

            new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCourseNamesToComboBox();

        ButtonScale.hboxScaling(btnBack);
        ButtonScale.hboxScaling(btnUpdate);
        ButtonScale.hboxScaling(btnDelete);

        if(PaymentDetailsController.addPayment){
            inputPaymentId.setText(paymentBO.getNextPaymentId());

            lblUpdate.setText("ADD");
            imgUpdate.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/add.gif")));

            lblDelete.setText("RESET");
            imgDelete.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/reset.gif")));
        }

        if(PaymentDetailsController.selectedPaymentId!=null){
            PaymentDTO selectedPayment = paymentBO.searchPayment(PaymentDetailsController.selectedPaymentId).getFirst();
            String courseId = selectedPayment.getCourseId();
            String courseName = courseBO.searchCourse(courseId).getFirst().getName();
            String paidAmount = selectedPayment.getPaidAmount().toString();
            String paymentDate = selectedPayment.getDate();

            inputPaymentId.setText(PaymentDetailsController.selectedPaymentId);
            inputCourseName.setValue(courseName);
            inputPaidAmount.setText(paidAmount);
            inputPaymentDate.setValue(LocalDate.parse(paymentDate));

        }
    }

    private void addCourseNamesToComboBox() {
        List<String> courseIds = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst().getCourseIds();
        ObservableList<String> courseNames = FXCollections.observableArrayList();

        for (String courseId : courseIds) {
            String courseName = courseBO.searchCourse(courseId).getFirst().getName();
            courseNames.add(courseName);
        }

        inputCourseName.setItems(courseNames);
    }

    public void selectCourseOnAction(ActionEvent actionEvent) {
        String courseId = courseBO.searchCourse(inputCourseName.getValue()).getFirst().getCourseId();
        ArrayList<PaymentDTO> paymentDTOS = paymentBO.searchPayment(StudentDetailsController.selectedStudentId);
        double paidAmount = 0;

        for(PaymentDTO paymentDTO : paymentDTOS){
            if(paymentDTO.getCourseId().equals(courseId)){
                paidAmount = paidAmount + paymentDTO.getPaidAmount().doubleValue();
            }
        }

        lblPaymentDone.setText(paidAmount+"");

        double courseFee = courseBO.searchCourse(courseId).getFirst().getFee().doubleValue();
        lblPaymentLeft.setText((courseFee-paidAmount)+"");
    }
}
