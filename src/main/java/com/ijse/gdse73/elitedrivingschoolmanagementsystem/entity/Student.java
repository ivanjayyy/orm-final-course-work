package com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="student_table")

public class Student {

    @Id
    @Column(name = "student_id")
    private String id;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String nic;

    @Column(nullable = false, unique = true)
    private String contact;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isRegistered;

    @Column(nullable = false)
    private String date;



//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    private List<Payment> payment;
//
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    private List<Lesson> lesson;

    @ManyToMany
    @JoinTable(name = "student_detail_table", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> course;

}
