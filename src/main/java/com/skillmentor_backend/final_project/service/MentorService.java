package com.skillmentor_backend.final_project.service;

import com.skillmentor_backend.final_project.dto.MentorProfileResponseDto;
import com.skillmentor_backend.final_project.entity.Mentor;

import java.util.List;

public interface MentorService {

    /**
     * Retrieves a list of all available mentors.
     * @return A list of Mentor entities.
     */
    List<Mentor> getAllMentors();

    /**
     * Retrieves the detailed profile for a specific mentor, including classes taught
     * and the number of students booked for each class.
     * @param id The ID of the mentor.
     * @return A DTO containing the mentor's detailed profile.
     */
    MentorProfileResponseDto getMentorProfile(Long id);
}