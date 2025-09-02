package com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name="payment_table")

public class Payment {

    @Id
    @Column(name = "payment_id")
    private String id;

    @Column(nullable = false)
    private boolean status;



    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
