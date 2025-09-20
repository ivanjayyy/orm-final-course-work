package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions;

public class LoginException extends DrivingSchoolException {
    public LoginException(String message) {
        super("Login Error: "+message);
    }
}
