package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.SuperBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.PaymentDTO;

import java.util.ArrayList;

public interface PaymentBO extends SuperBO {

    boolean savePayment(PaymentDTO paymentDTO);
    boolean updatePayment(PaymentDTO paymentDTO);
    boolean deletePayment(String id);
    ArrayList<PaymentDTO> searchPayment(String id);
    ArrayList<PaymentDTO> getAllPayments();
    String getNextPaymentId();

}
