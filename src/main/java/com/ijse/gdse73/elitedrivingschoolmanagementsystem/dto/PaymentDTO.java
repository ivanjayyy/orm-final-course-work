package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentDTO {

    private String paymentId;
    private String date;
    private BigDecimal paidAmount;
    private String studentId;
    private String courseId;

}
