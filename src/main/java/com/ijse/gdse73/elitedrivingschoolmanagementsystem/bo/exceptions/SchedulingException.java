package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions;

public class SchedulingException extends DrivingSchoolException {
    public SchedulingException(String message) {
        super("Scheduling Error: "+message);
    }
}
