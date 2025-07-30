package com.skillmentor_backend.final_project.service;

import com.skillmentor_backend.final_project.dto.CreateBookingRequest;
import com.skillmentor_backend.final_project.dto.StudentDashboardResponse;
import com.skillmentor_backend.final_project.entity.Classroom;
import com.skillmentor_backend.final_project.entity.Session;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface StudentService {

    /**
     * Retrieves all available classrooms for students to browse.
     * @return A list of Classroom entities.
     */
    List<Classroom> getAllClasses();

    /**
     * Creates a new booking session for the authenticated student.
     * @param request DTO containing the booking details.
     * @param jwt The JWT principal of the authenticated user.
     * @return The created Session entity.
     */
    Session createBooking(CreateBookingRequest request, Jwt jwt);

    /**
     * Retrieves the session history for the authenticated student's dashboard.
     * @param jwt The JWT principal of the authenticated user.
     * @return A list of DTOs formatted for the student dashboard.
     */
    List<StudentDashboardResponse> getStudentDashboard(Jwt jwt);
}