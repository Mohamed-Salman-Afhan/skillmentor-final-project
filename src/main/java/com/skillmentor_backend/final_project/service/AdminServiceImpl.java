package com.skillmentor_backend.final_project.service;

import com.skillmentor_backend.final_project.dto.BookingDetailsResponseDto;
import com.skillmentor_backend.final_project.dto.CreateClassroomRequestDto;
import com.skillmentor_backend.final_project.dto.CreateMentorRequestDto;
import com.skillmentor_backend.final_project.entity.Classroom;
import com.skillmentor_backend.final_project.entity.Mentor;
import com.skillmentor_backend.final_project.entity.Session;
import com.skillmentor_backend.final_project.entity.SessionStatus;
import com.skillmentor_backend.final_project.repository.ClassroomRepository;
import com.skillmentor_backend.final_project.repository.MentorRepository;
import com.skillmentor_backend.final_project.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ClassroomRepository classroomRepository;
    private final MentorRepository mentorRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public Classroom createClassroom(CreateClassroomRequestDto dto) {
        Classroom classroom = new Classroom();
        classroom.setName(dto.getName());
        classroom.setImageUrl(dto.getImageUrl());
        return classroomRepository.save(classroom);
    }

    @Override
    @Transactional
    public Mentor createMentor(CreateMentorRequestDto dto) {
        Mentor mentor = new Mentor();
        // Map all fields from DTO to entity
        mentor.setFirstName(dto.getFirstName());
        mentor.setLastName(dto.getLastName());
        mentor.setEmail(dto.getEmail());
        mentor.setAddress(dto.getAddress());
        mentor.setTitle(dto.getTitle());
        mentor.setSessionFee(dto.getSessionFee());
        mentor.setProfession(dto.getProfession());
        mentor.setBio(dto.getBio());
        mentor.setPhoneNumber(dto.getPhoneNumber());
        mentor.setQualification(dto.getQualification());
        mentor.setImageUrl(dto.getImageUrl());

        // Find and assign classrooms
        List<Classroom> classrooms = classroomRepository.findAllById(dto.getClassroomIds());
        mentor.setClassrooms(new HashSet<>(classrooms));

        return mentorRepository.save(mentor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDetailsResponseDto> getAllBookings() {
        return sessionRepository.findAll().stream()
                .map(this::mapSessionToBookingDetailsResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingDetailsResponseDto approveBooking(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        if (session.getStatus() != SessionStatus.PENDING) {
            throw new IllegalStateException("Cannot approve a booking that is not in PENDING state.");
        }

        session.setStatus(SessionStatus.ACCEPTED);
        Session updatedSession = sessionRepository.save(session);
        return mapSessionToBookingDetailsResponse(updatedSession);
    }

    @Override
    @Transactional
    public BookingDetailsResponseDto completeBooking(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        if (session.getStatus() != SessionStatus.ACCEPTED) {
            throw new IllegalStateException("Cannot complete a booking that is not in ACCEPTED state.");
        }

        session.setStatus(SessionStatus.COMPLETED);
        Session updatedSession = sessionRepository.save(session);
        return mapSessionToBookingDetailsResponse(updatedSession);
    }

    // Helper method to map a Session entity to the response DTO
    private BookingDetailsResponseDto mapSessionToBookingDetailsResponse(Session session) {
        BookingDetailsResponseDto dto = new BookingDetailsResponseDto();
        dto.setBookingId(session.getId());
        dto.setClassName(session.getClassroom().getName());
        dto.setStudentName(session.getStudentName());
        dto.setMentorName(session.getMentor().getFirstName() + " " + session.getMentor().getLastName());
        dto.setSessionDate(session.getSessionDateTime());
        dto.setStatus(session.getStatus().name());
        return dto;
    }
}