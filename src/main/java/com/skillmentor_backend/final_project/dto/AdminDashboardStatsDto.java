package com.skillmentor_backend.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardStatsDto {
    private long totalMentors;
    private long totalStudents;
    private long totalClassrooms;
    private long pendingSessions;
    private long acceptedSessions;
    private long completedSessions;
}
