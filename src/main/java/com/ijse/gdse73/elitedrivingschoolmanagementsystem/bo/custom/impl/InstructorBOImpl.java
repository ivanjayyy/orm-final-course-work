package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.CourseDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.InstructorDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.InstructorDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Course;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Instructor;

import java.util.ArrayList;

public class InstructorBOImpl implements InstructorBO {

    InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTOR);
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);

    @Override
    public boolean saveInstructor(InstructorDTO instructorDTO) {
        Course course = courseDAO.search(instructorDTO.getCourseId()).getFirst();

        return instructorDAO.save(new Instructor(instructorDTO.getInstructorId(),instructorDTO.getName(),instructorDTO.getContact(),instructorDTO.getEmail(),course));
    }

    @Override
    public boolean updateInstructor(InstructorDTO instructorDTO) {
        Course course = courseDAO.search(instructorDTO.getCourseId()).getFirst();

        return instructorDAO.update(new Instructor(instructorDTO.getInstructorId(),instructorDTO.getName(),instructorDTO.getContact(),instructorDTO.getEmail(),course));
    }

    @Override
    public boolean deleteInstructor(String id) {
        return instructorDAO.delete(id);
    }

    @Override
    public ArrayList<InstructorDTO> searchInstructor(String id) {
        ArrayList<Instructor> instructors = instructorDAO.search(id);
        ArrayList<InstructorDTO> instructorDTOS = new ArrayList<>();

        for (Instructor instructor : instructors) {
            instructorDTOS.add(new InstructorDTO(instructor.getId(),instructor.getName(),instructor.getContact(),instructor.getEmail(),instructor.getCourse().getId()));
        }
        return instructorDTOS;
    }

    @Override
    public ArrayList<InstructorDTO> getAllInstructors() {
        ArrayList<Instructor> instructors = instructorDAO.getAll();
        ArrayList<InstructorDTO> instructorDTOS = new ArrayList<>();

        for (Instructor instructor : instructors) {
            instructorDTOS.add(new InstructorDTO(instructor.getId(),instructor.getName(),instructor.getContact(),instructor.getEmail(),instructor.getCourse().getId()));
        }
        return instructorDTOS;
    }

    @Override
    public String getNextInstructorId() {
        return instructorDAO.getNextId();
    }

}
