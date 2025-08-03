package com.skillmentor_backend.final_project.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMentorRequestDto {

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 25, message = "First name must be between 3 and 25 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 25, message = "Last name must be between 3 and 25 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email format")
    private String email;

    private String address;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Session fee is required")
    @PositiveOrZero(message = "Session fee cannot be negative")
    private BigDecimal sessionFee;

    @NotBlank(message = "Profession is required")
    private String profession;

    private String bio;

    @Pattern(regexp = "^07\\d{8}$", message = "Phone number must be 10 digits and start with 07")
    private String phoneNumber;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;

    @NotEmpty(message = "At least one classroom must be assigned")
    private List<Long> classroomIds;
}