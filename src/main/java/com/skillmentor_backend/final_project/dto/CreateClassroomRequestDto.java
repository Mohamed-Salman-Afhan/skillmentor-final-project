package com.skillmentor_backend.final_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassroomRequestDto {

    @NotBlank(message = "Class name cannot be empty")
    private String name;

    @URL(message = "Must be a valid URL")
    private String imageUrl;
}