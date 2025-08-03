package com.skillmentor_backend.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorSummaryDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private String imageUrl;
    private BigDecimal sessionFee;
}