package com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name="lesson_table")

public class Lesson {

    @Id
    @Column(name = "lesson_id")
    private String id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String status;



    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
