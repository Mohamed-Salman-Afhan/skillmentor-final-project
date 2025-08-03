package com.skillmentor_backend.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsResponseDto {
    private Long bookingId;
    private String className;
    private String studentName; // Comes from the session entity
    private String mentorName;
    private LocalDateTime sessionDate;
    private String status;
}
