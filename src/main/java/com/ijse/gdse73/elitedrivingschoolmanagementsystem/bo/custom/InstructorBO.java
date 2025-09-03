package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.SuperBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.InstructorDTO;

import java.util.ArrayList;

public interface InstructorBO extends SuperBO {

    boolean saveInstructor(InstructorDTO instructorDTO);
    boolean updateInstructor(InstructorDTO instructorDTO);
    boolean deleteInstructor(String id);
    ArrayList<InstructorDTO> searchInstructor(String id);
    ArrayList<InstructorDTO> getAllInstructors();
    String getNextInstructorId();

}
