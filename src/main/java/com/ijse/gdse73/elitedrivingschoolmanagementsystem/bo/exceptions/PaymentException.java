package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions;

public class PaymentException extends DrivingSchoolException {
    public PaymentException(String message) {
        super("Payment Error: "+message);
    }
}
