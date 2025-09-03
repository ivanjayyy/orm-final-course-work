package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.SuperBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;

import java.util.ArrayList;

public interface CourseBO extends SuperBO {

    boolean saveCourse(CourseDTO courseDTO);
    boolean updateCourse(CourseDTO courseDTO);
    boolean deleteCourse(String id);
    ArrayList<CourseDTO> searchCourse(String id);
    ArrayList<CourseDTO> getAllCourses();
    String getNextCourseId();

}
