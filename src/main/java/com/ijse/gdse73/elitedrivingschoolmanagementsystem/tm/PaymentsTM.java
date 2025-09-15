package com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentsTM {

    private String paymentId;
    private String courseName;
    private String paymentDate;
    private String paidAmount;

}
