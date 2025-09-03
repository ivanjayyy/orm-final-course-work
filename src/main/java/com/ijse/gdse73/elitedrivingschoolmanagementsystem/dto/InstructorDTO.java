package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class InstructorDTO {

    private String instructorId;
    private String name;
    private String contact;
    private String email;
    private String courseId;

}
