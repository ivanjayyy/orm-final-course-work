package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StudentDTO {

    private String studentId;
    private String gender;
    private String name;
    private String address;
    private String nic;
    private String contact;
    private String email;
    private boolean isRegistered;
    private String date;
    private List<String> courseids;

}
