package com.skillmentor_backend.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingRequest {
    private Long classroomId;
    private Long mentorId;
    private LocalDateTime sessionDateTime;
    private Integer duration;
    private String bankSlipUrl; // This would be the URL after uploading the image
}
