package com.skillmentor_backend.final_project.service;

import com.skillmentor_backend.final_project.dto.BookingDetailsResponse;
import com.skillmentor_backend.final_project.dto.CreateClassroomRequest;
import com.skillmentor_backend.final_project.dto.CreateMentorRequest;
import com.skillmentor_backend.final_project.entity.Classroom;
import com.skillmentor_backend.final_project.entity.Mentor;

import java.util.List;

public interface AdminService {
    /**
     * Creates a new classroom.
     * @param dto The request object containing classroom details.
     * @return The created Classroom entity.
     */
    Classroom createClassroom(CreateClassroomRequest dto);

    /**
     * Creates a new mentor and assigns them to specified classrooms.
     * @param dto The request object containing mentor details and classroom IDs.
     * @return The created Mentor entity.
     */
    Mentor createMentor(CreateMentorRequest dto);

    /**
     * Retrieves a list of all bookings for the admin dashboard.
     * @return A list of DTOs formatted for the admin view.
     */
    List<BookingDetailsResponse> getAllBookings();

    /**
     * Approves a pending booking.
     * @param id The ID of the booking session.
     * @return A DTO of the updated booking.
     */
    BookingDetailsResponse approveBooking(Long id);

    /**
     * Marks an approved booking as completed.
     * @param id The ID of the booking session.
     * @return A DTO of the updated booking.
     */
    BookingDetailsResponse completeBooking(Long id);
}