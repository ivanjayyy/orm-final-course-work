package com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class LessonsTM {

    private String lessonId;
    private String studentName;
    private String date;
    private String lessonStatus;

}
