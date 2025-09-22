package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.SuperBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.LessonDTO;

import java.util.ArrayList;

public interface LessonBO extends SuperBO {

    boolean saveLesson(LessonDTO lessonDTO);
    boolean updateLesson(LessonDTO lessonDTO);
    boolean updateLessonStatus(LessonDTO lessonDTO);
    boolean deleteLesson(String id);
    ArrayList<LessonDTO> searchLesson(String id);
    ArrayList<LessonDTO> getAllLessons();
    String getNextLessonId();

}
