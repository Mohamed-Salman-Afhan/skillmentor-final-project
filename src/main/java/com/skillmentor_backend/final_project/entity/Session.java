package com.skillmentor_backend.final_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Directly store student info from Clerk JWT
    @Column(nullable = false)
    private String studentClerkId;
    private String studentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentor mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    private LocalDateTime sessionDateTime;
    private Integer duration; // In minutes
    @Column(length = 1024)
    private String bankSlipUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;
}