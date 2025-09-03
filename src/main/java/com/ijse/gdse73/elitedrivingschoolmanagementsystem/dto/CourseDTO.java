package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CourseDTO {

    private String courseId;
    private String name;
    private String description;
    private int duration;
    private BigDecimal fee;

}
