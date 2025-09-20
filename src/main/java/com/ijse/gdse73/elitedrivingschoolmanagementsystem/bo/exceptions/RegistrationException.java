package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions;

public class RegistrationException extends DrivingSchoolException {
    public RegistrationException(String message) {
        super("Registration Error: "+message);
    }
}
