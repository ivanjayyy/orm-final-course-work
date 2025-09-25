package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.payment;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.PaymentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student.StudentDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.PaymentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentsPageController implements Initializable {
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOTypes.PAYMENT);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
    CourseBO  courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    public AnchorPane ancPaymentPage;
    public AnchorPane ancPayments;
    public HBox btnHome;
    public Label lblStudentName;
    public Label lblIsRegistered;
    public TextField lblFullPayment;
    public TextField lblPaidAmount;

    public void btnHomeOnAction(MouseEvent mouseEvent) {
        try {
            ancPaymentPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Students/StudentsPage.fxml"));

            anchorPane.prefWidthProperty().bind(ancPaymentPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPaymentPage.heightProperty());

            ancPaymentPage.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Exception("Page Not Found").printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.hboxScaling(btnHome);
        loadLabelData();

        try {
            ancPayments.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Payments/PaymentDetails.fxml"));

            anchorPane.prefWidthProperty().bind(ancPayments.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPayments.heightProperty());

            ancPayments.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Exception("Page Not Found").printStackTrace();
        }
    }

    private void loadLabelData() {
        String studentName = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst().getName();
        lblStudentName.setText(studentName);

        boolean isRegistered = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst().isRegistered();

        if (isRegistered) {
            lblIsRegistered.setText("Registered");
        } else  {
            lblIsRegistered.setText("Not Registered");
        }

        double paidAmount = 0;
        List<PaymentDTO> payments = paymentBO.searchPayment(StudentDetailsController.selectedStudentId);

        for (PaymentDTO payment : payments) {
            paidAmount = paidAmount + payment.getPaidAmount().doubleValue();
        }

        lblPaidAmount.setText(paidAmount + "");

        double fullPayment = 0;
        List<String> courses = studentBO.searchStudent(StudentDetailsController.selectedStudentId).getFirst().getCourseIds();

        for (String course : courses) {
            fullPayment = fullPayment  + courseBO.searchCourse(course).getFirst().getFee().doubleValue();
        }

        lblFullPayment.setText(fullPayment + "");
    }

    public void resetPageOnAction(MouseEvent mouseEvent) {
        loadLabelData();
    }
}
