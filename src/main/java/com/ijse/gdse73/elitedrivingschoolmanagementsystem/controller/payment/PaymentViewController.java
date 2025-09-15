package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.payment;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.PaymentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student.StudentDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.PaymentDTO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentViewController implements Initializable {
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOTypes.PAYMENT);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);

    public AnchorPane ancPaymentView;
    public TextField inputPaymentId;
    public JFXComboBox<String> inputCourseName;
    public TextField inputPaidAmount;
    public TextField inputPaymentDate;
    public Label lblUpdate;
    public ImageView imgUpdate;
    public Label lblDelete;
    public ImageView imgDelete;

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
        if(inputCourseName.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Select A Course").show();
            return;
        }

        if(PaymentDetailsController.addPayment){
            String courseId = courseBO.searchCourse(inputCourseName.getValue()).getFirst().getCourseId();

            PaymentDTO payment = new PaymentDTO(inputPaymentId.getText(),inputPaymentDate.getText(),new BigDecimal(inputPaidAmount.getText()),StudentDetailsController.selectedStudentId,courseId);
            boolean isAdded = paymentBO.savePayment(payment);

            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Payment Added Successfully").show();
                goToPaymentDetailsPage();

            }else {
                new Alert(Alert.AlertType.ERROR,"Payment Add Failed").show();
            }

        }else {
            String courseId = courseBO.searchCourse(inputCourseName.getValue()).getFirst().getCourseId();

            PaymentDTO updatedPayment = new PaymentDTO(inputPaymentId.getText(),inputPaymentDate.getText(),new BigDecimal(inputPaidAmount.getText()),StudentDetailsController.selectedStudentId,courseId);
            boolean isUpdated = paymentBO.updatePayment(updatedPayment);

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
            boolean isDeleted = paymentBO.deletePayment(inputPaymentId.getText());

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Payment Deleted Successfully").show();
                goToPaymentDetailsPage();

            }else {
                new Alert(Alert.AlertType.ERROR,"Payment Delete Failed").show();
            }

        } else {
            inputCourseName.setValue(null);
            inputPaidAmount.setText("");
            inputPaymentDate.setText("");

            new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCourseNamesToComboBox();

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
            inputPaymentDate.setText(paymentDate);

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
}
