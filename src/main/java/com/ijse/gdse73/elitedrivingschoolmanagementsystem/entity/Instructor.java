package com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name="instructor_table")

public class Instructor {

    @Id
    @Column(name = "instructor_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String contact;

    @Column(nullable = false, unique = true)
    private String email;



    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Lesson> lesson;

}
