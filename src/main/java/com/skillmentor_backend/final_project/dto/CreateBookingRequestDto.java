package com.skillmentor_backend.final_project.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingRequestDto {
    @NotNull(message = "Classroom ID is required")
    private Long classroomId;

    @NotNull(message = "Mentor ID is required")
    private Long mentorId;

    @NotNull(message = "Session date and time are required")
    @Future(message = "Session must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sessionDateTime;

    private Integer duration;

    @NotNull(message = "Bank slip URL is required")
    @URL(message = "Bank slip URL must be a valid URL")
    private String bankSlipUrl;
}
