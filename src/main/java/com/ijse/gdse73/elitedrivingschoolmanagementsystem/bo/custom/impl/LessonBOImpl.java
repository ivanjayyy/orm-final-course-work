package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.LessonBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.exceptions.SchedulingException;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor.InstructorDetailsController;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.CourseDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.InstructorDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.LessonDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.StudentDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Course;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Instructor;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Lesson;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Student;

import java.util.ArrayList;

public class LessonBOImpl implements LessonBO {

    LessonDAO lessonDAO = (LessonDAO) DAOFactory.getInstance().getDAO(DAOTypes.LESSON);
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);
    InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTOR);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);

    @Override
    public boolean saveLesson(LessonDTO lessonDTO) throws SchedulingException {
        String studentName = studentDAO.search(lessonDTO.getStudentId()).getFirst().getName();
        ArrayList<Lesson> lessons = lessonDAO.search(studentName);

        for (Lesson lesson : lessons) {
            String date = lessonDTO.getDate();

            if(date.equals(lesson.getDate())){
                throw new SchedulingException("Student " + studentName + " already has a lesson on " + lesson.getDate());
            }
        }

        ArrayList<Lesson> lessons2 = lessonDAO.search(InstructorDetailsController.selectedInstructorId);
        String instructorName = instructorDAO.search(InstructorDetailsController.selectedInstructorId).getFirst().getName();

        for (Lesson lesson : lessons2) {
            String date = lessonDTO.getDate();

            if(date.equals(lesson.getDate())){
                throw new SchedulingException("Instructor " + instructorName + " already has a lesson on " + lesson.getDate());
            }
        }

        Course course = courseDAO.search(lessonDTO.getCourseId()).getFirst();
        Instructor instructor = instructorDAO.search(lessonDTO.getInstructorId()).getFirst();
        Student student = studentDAO.search(lessonDTO.getStudentId()).getFirst();

        return lessonDAO.save(new Lesson(lessonDTO.getLessonId(),lessonDTO.getDate(),lessonDTO.getStatus(),course,instructor,student));
    }

    @Override
    public boolean updateLesson(LessonDTO lessonDTO) throws SchedulingException {
        String studentName = studentDAO.search(lessonDTO.getStudentId()).getFirst().getName();
        ArrayList<Lesson> lessons = lessonDAO.search(studentName);

        for (Lesson lesson : lessons) {
            String date = lessonDTO.getDate();

            if(date.equals(lesson.getDate())){
                throw new SchedulingException("Student " + studentName + " already has a lesson on " + lesson.getDate());
            }
        }

        ArrayList<Lesson> lessons2 = lessonDAO.search(InstructorDetailsController.selectedInstructorId);
        String instructorName = instructorDAO.search(InstructorDetailsController.selectedInstructorId).getFirst().getName();

        for (Lesson lesson : lessons2) {
            String date = lessonDTO.getDate();

            if(date.equals(lesson.getDate())){
                throw new SchedulingException("Instructor " + instructorName + " already has a lesson on " + lesson.getDate());
            }
        }

        Course course = courseDAO.search(lessonDTO.getCourseId()).getFirst();
        Instructor instructor = instructorDAO.search(lessonDTO.getInstructorId()).getFirst();
        Student student = studentDAO.search(lessonDTO.getStudentId()).getFirst();

        return lessonDAO.update(new Lesson(lessonDTO.getLessonId(),lessonDTO.getDate(),lessonDTO.getStatus(),course,instructor,student));
    }

    @Override
    public boolean updateLessonStatus(LessonDTO lessonDTO) {
        Course course = courseDAO.search(lessonDTO.getCourseId()).getFirst();
        Instructor instructor = instructorDAO.search(lessonDTO.getInstructorId()).getFirst();
        Student student = studentDAO.search(lessonDTO.getStudentId()).getFirst();

        return lessonDAO.update(new Lesson(lessonDTO.getLessonId(),lessonDTO.getDate(),lessonDTO.getStatus(),course,instructor,student));
    }

    @Override
    public boolean deleteLesson(String id) {
        return lessonDAO.delete(id);
    }

    @Override
    public ArrayList<LessonDTO> searchLesson(String id) {
        ArrayList<Lesson> lessons = lessonDAO.search(id);
        ArrayList<LessonDTO> lessonDTOS = new ArrayList<>();

        for (Lesson lesson : lessons) {
            lessonDTOS.add(new LessonDTO(lesson.getId(),lesson.getDate(),lesson.getStatus(),lesson.getCourse().getId(),lesson.getInstructor().getId(),lesson.getStudent().getId()));
        }
        return lessonDTOS;
    }

    @Override
    public ArrayList<LessonDTO> getAllLessons() {
        ArrayList<Lesson> lessons = lessonDAO.getAll();
        ArrayList<LessonDTO> lessonDTOS = new ArrayList<>();

        for (Lesson lesson : lessons) {
            lessonDTOS.add(new LessonDTO(lesson.getId(),lesson.getDate(),lesson.getStatus(),lesson.getCourse().getId(),lesson.getInstructor().getId(),lesson.getStudent().getId()));
        }
        return lessonDTOS;
    }

    @Override
    public String getNextLessonId() {
        return lessonDAO.getNextId();
    }
}
