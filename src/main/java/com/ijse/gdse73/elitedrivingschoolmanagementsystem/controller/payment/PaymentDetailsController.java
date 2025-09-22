package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.payment;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.PaymentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student.StudentDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.PaymentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm.PaymentsTM;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.util.ButtonScale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentDetailsController implements Initializable {
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOTypes.PAYMENT);
    CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

    public AnchorPane ancPaymentDetails;
    public TextField inputSearch;
    public HBox btnSearch;
    public HBox btnReset;
    public HBox btnAdd;
    public HBox btnView;
    public DatePicker btnDatePicker;
    public TableView<PaymentsTM> tblPayments;
    public TableColumn<PaymentsTM, String> colPaymentId;
    public TableColumn<PaymentsTM, String> colCourseName;
    public TableColumn<PaymentsTM, String> colPaymentDate;
    public TableColumn<PaymentsTM, String> colPaidAmount;

    public static String selectedPaymentId;
    public static boolean addPayment;

    public void onClickTable(MouseEvent mouseEvent) {
        PaymentsTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();

        if(selectedPayment != null){
            selectedPaymentId = selectedPayment.getPaymentId();
        }
    }

    public void btnResetOnAction(MouseEvent mouseEvent) {
        resetPage();
        new Alert(Alert.AlertType.INFORMATION, "Page Reset").show();
    }

    private void resetPage() {
        inputSearch.clear();
        loadTableData();
        selectedPaymentId = null;
        addPayment = false;
    }

    private void loadTableData() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));

        List<PaymentDTO> paymentDTOS;

        if(inputSearch.getText().isEmpty()){
            paymentDTOS = paymentBO.getAllPayments();
        } else {
            paymentDTOS = paymentBO.searchPayment(inputSearch.getText());
        }

        ObservableList<PaymentsTM> paymentsTMS = FXCollections.observableArrayList();

        for(PaymentDTO paymentDTO : paymentDTOS){
            String studentId = paymentDTO.getStudentId();

            if (studentId.equals(StudentDetailsController.selectedStudentId)) {
                String courseName = courseBO.searchCourse(paymentDTO.getCourseId()).getFirst().getName();

                PaymentsTM paymentsTM = new PaymentsTM(paymentDTO.getPaymentId(),courseName,paymentDTO.getDate(),paymentDTO.getPaidAmount().toString());
                paymentsTMS.add(paymentsTM);
            }
        }
        tblPayments.setItems(paymentsTMS);
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if(selectedPaymentId != null){
            new Alert(Alert.AlertType.ERROR, "Please Unselect The Payment").show();
            return;
        }

        try {
            addPayment = true;

            ancPaymentDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Payments/PaymentView.fxml"));

            anchorPane.prefWidthProperty().bind(ancPaymentDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPaymentDetails.heightProperty());

            ancPaymentDetails.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnViewOnAction(MouseEvent mouseEvent) {
        if(selectedPaymentId == null){
            new Alert(Alert.AlertType.ERROR, "Please Select A Payment").show();
            return;
        }

        try {
            ancPaymentDetails.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Payments/PaymentView.fxml"));

            anchorPane.prefWidthProperty().bind(ancPaymentDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPaymentDetails.heightProperty());

            ancPaymentDetails.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDatePicker.getEditor().setVisible(false);
        resetPage();

        ButtonScale.hboxScaling(btnSearch);
        ButtonScale.hboxScaling(btnReset);
        ButtonScale.hboxScaling(btnAdd);
        ButtonScale.hboxScaling(btnView);
    }

    public void btnSearchOnAction(MouseEvent mouseEvent) {
        loadTableData();
    }

    public void datePickerOnAction(ActionEvent actionEvent) {
        inputSearch.setText(btnDatePicker.getValue().toString());
    }
}
