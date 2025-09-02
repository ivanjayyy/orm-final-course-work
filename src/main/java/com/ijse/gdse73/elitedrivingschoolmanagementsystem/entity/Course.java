package com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name="course_table")

public class Course {

    @Id
    @Column(name = "course_id")
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;



    @OneToMany(mappedBy = "course")
    private List<Instructor> instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lesson;

    @ManyToMany(mappedBy = "course")
    private List<Student> student;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private Payment payment;

}
