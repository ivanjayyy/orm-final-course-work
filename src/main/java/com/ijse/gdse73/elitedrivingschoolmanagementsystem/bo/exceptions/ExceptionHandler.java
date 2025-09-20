package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions;

public class ExceptionHandler {
    public static void handleException(DrivingSchoolException ex) {
        System.out.println("❌ " + ex.getMessage());
    }
}
