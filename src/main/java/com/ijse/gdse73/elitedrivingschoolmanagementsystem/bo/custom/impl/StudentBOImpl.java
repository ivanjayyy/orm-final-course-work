package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.CourseDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.StudentDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Course;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);

    @Override
    public boolean saveStudent(StudentDTO studentDTO) {
        List<Course> courses = new ArrayList<>();

        for (String courseId : studentDTO.getCourseids()) {
            Course course = courseDAO.search(courseId).getFirst();
            courses.add(course);
        }

        return studentDAO.save(new Student(studentDTO.getStudentId(),studentDTO.getGender(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getNic(),studentDTO.getContact(),studentDTO.getEmail(),studentDTO.isRegistered(),studentDTO.getDate(),courses));
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) {
        List<Course> courses = new ArrayList<>();

        for (String courseId : studentDTO.getCourseids()) {
            Course course = courseDAO.search(courseId).getFirst();
            courses.add(course);
        }

        return studentDAO.update(new Student(studentDTO.getStudentId(),studentDTO.getGender(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getNic(),studentDTO.getContact(),studentDTO.getEmail(),studentDTO.isRegistered(),studentDTO.getDate(),courses));
    }

    @Override
    public boolean deleteStudent(String id) {
        return studentDAO.delete(id);
    }

    @Override
    public ArrayList<StudentDTO> searchStudent(String id) {
        ArrayList<Student> students = studentDAO.search(id);
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();

        ArrayList<String> courseIds = new ArrayList<>();
        for (Student student : students) {
            courseIds.add(student.getId());
        }

        for (Student student : students) {
            studentDTOS.add(new StudentDTO(student.getId(),student.getGender(),student.getName(),student.getAddress(),student.getNic(),student.getContact(),student.getEmail(),student.isRegistered(),student.getDate(),courseIds));
        }
        return studentDTOS;
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() {
        ArrayList<Student> students = studentDAO.getAll();
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();

        ArrayList<String> courseIds = new ArrayList<>();
        for (Student student : students) {
            courseIds.add(student.getId());
        }

        for (Student student : students) {
            studentDTOS.add(new StudentDTO(student.getId(),student.getGender(),student.getName(),student.getAddress(),student.getNic(),student.getContact(),student.getEmail(),student.isRegistered(),student.getDate(),courseIds));
        }
        return studentDTOS;
    }

    @Override
    public String getNextStudentId() {
        return studentDAO.getNextId();
    }
}
