package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.SuperBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;

import java.util.ArrayList;

public interface StudentBO extends SuperBO {

    boolean saveStudent(StudentDTO studentDTO);
    boolean updateStudent(StudentDTO studentDTO);
    boolean deleteStudent(String id);
    ArrayList<StudentDTO> searchStudent(String id);
    ArrayList<StudentDTO> getAllStudents();
    String getNextStudentId();

}
