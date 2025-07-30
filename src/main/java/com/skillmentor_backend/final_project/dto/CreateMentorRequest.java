package com.skillmentor_backend.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMentorRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String title;
    private BigDecimal sessionFee;
    private String profession;
    private String bio;
    private String phoneNumber;
    private String qualification;
    private String imageUrl;
    // List of IDs for classes to assign this mentor to
    private List<Long> classroomIds;
}