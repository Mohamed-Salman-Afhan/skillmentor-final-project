package com.skillmentor_backend.final_project.entity;

import jakarta.persistence.*; // Correct import for JPA
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "classrooms") // Correct import for JPA
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {
    @Id // Correct import for JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String imageUrl;

    @OneToMany(mappedBy = "classroom")
    private List<Session> sessions;
}