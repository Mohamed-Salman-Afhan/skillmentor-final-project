package com.skillmentor_backend.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomResponseDto {
    private Long id;
    private String name;
    private String imageUrl;
    private List<MentorSummaryDto> mentors; // Use a nested DTO for mentors
}