package com.skillmentor_backend.final_project.service;

import com.skillmentor_backend.final_project.dto.CreateBookingRequest;
import com.skillmentor_backend.final_project.dto.StudentDashboardResponse;
import com.skillmentor_backend.final_project.entity.*;
import com.skillmentor_backend.final_project.repository.ClassroomRepository;
import com.skillmentor_backend.final_project.repository.MentorRepository;
import com.skillmentor_backend.final_project.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final SessionRepository sessionRepository;
    private final ClassroomRepository classroomRepository;
    private final MentorRepository mentorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Classroom> getAllClasses() {
        return classroomRepository.findAll();
    }

    @Override
    @Transactional
    public Session createBooking(CreateBookingRequest request, Jwt jwt) {
        // Find the related entities
        Classroom classroom = classroomRepository.findById(request.getClassroomId())
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found"));
        Mentor mentor = mentorRepository.findById(request.getMentorId())
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found"));

        // Extract student info from the validated JWT
        String studentClerkId = jwt.getSubject();
        String studentName = jwt.hasClaim("name") ? jwt.getClaimAsString("name") : "Student User";

        // Create and populate the new session
        Session session = new Session();
        session.setStudentClerkId(studentClerkId);
        session.setStudentName(studentName);
        session.setClassroom(classroom);
        session.setMentor(mentor);
        session.setSessionDateTime(request.getSessionDateTime());
        session.setDuration(request.getDuration());
        session.setBankSlipUrl(request.getBankSlipUrl());
        session.setStatus(SessionStatus.PENDING); // Initial status

        return sessionRepository.save(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDashboardResponse> getStudentDashboard(Jwt jwt) {
        String studentClerkId = jwt.getSubject();
        List<Session> sessions = sessionRepository.findByStudentClerkIdOrderBySessionDateTimeDesc(studentClerkId);

        return sessions.stream()
                .map(this::mapSessionToStudentDashboardResponse)
                .collect(Collectors.toList());
    }

    // Helper method to map a Session entity to the dashboard response DTO
    private StudentDashboardResponse mapSessionToStudentDashboardResponse(Session session) {
        StudentDashboardResponse dto = new StudentDashboardResponse();
        dto.setClassName(session.getClassroom().getName());
        dto.setMentorName(session.getMentor().getFirstName() + " " + session.getMentor().getLastName());
        dto.setSessionDate(session.getSessionDateTime());
        dto.setStatus(session.getStatus().name());
        return dto;
    }
}