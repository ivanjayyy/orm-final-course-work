package com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name="test")

public class Test {

    @Id
    @Column(name = "test_id")
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

}
