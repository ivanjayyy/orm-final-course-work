package com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="payment_table")

public class Payment {

    @Id
    @Column(name = "payment_id")
    private String id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private BigDecimal paidAmount;



    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
