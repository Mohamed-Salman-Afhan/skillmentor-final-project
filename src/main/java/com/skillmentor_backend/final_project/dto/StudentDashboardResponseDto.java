package com.skillmentor_backend.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDashboardResponseDto {
    private String className;
    private String mentorName;
    private String sessionDate;
    private String status;
}
