package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.CourseDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Course;

import java.util.ArrayList;

public class CourseBOImpl implements CourseBO {

    CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);

    @Override
    public boolean saveCourse(CourseDTO courseDTO) {
        return courseDAO.save(new Course(courseDTO.getCourseId(),courseDTO.getName(),courseDTO.getDescription(),courseDTO.getDuration(),courseDTO.getFee()));
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) {
        return courseDAO.update(new Course(courseDTO.getCourseId(),courseDTO.getName(),courseDTO.getDescription(),courseDTO.getDuration(),courseDTO.getFee()));
    }

    @Override
    public boolean deleteCourse(String id) {
        return courseDAO.delete(id);
    }

    @Override
    public ArrayList<CourseDTO> searchCourse(String id) {
        ArrayList<Course> courses = courseDAO.search(id);
        ArrayList<CourseDTO> courseDTOS = new ArrayList<>();

        for (Course course : courses) {
            courseDTOS.add(new CourseDTO(course.getId(),course.getName(),course.getDescription(),course.getDuration(),course.getFee()));
        }
        return courseDTOS;
    }

    @Override
    public ArrayList<CourseDTO> getAllCourses() {
        ArrayList<Course> courses = courseDAO.getAll();
        ArrayList<CourseDTO> courseDTOS = new ArrayList<>();

        for (Course course : courses) {
            courseDTOS.add(new CourseDTO(course.getId(),course.getName(),course.getDescription(),course.getDuration(),course.getFee()));
        }
        return courseDTOS;
    }

    @Override
    public String getNextCourseId() {
        return courseDAO.getNextId();
    }
}
