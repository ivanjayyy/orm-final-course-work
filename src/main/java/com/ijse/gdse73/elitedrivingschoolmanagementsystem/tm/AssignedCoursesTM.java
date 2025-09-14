package com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AssignedCoursesTM {

    private String courseId;
    private String courseName;
    private JFXButton btnRemove;

}
