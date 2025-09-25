package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.SuperDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Student;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<Student> getStudentsRegisteredForAllCourses();
}
