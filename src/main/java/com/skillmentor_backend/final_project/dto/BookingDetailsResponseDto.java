package com.skillmentor_backend.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsResponseDto {
    private Long bookingId;
    private String className;
    private String studentName; // Comes from the session entity
    private String mentorName;
    private String sessionDate;
    private String status;
}
