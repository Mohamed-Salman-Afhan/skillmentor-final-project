package com.skillmentor_backend.final_project.dto;

import com.skillmentor_backend.final_project.entity.Mentor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorProfileResponse {
    private Mentor mentor; // Sending the whole entity for full details
    private List<MentorClassDto> classes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MentorClassDto {
        private String name;
        private long studentCount;
    }
}
