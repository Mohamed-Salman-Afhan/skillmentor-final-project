package com.skillmentor_backend.final_project.service;

import com.skillmentor_backend.final_project.dto.ClassroomResponseDto;
import com.skillmentor_backend.final_project.dto.CreateBookingRequestDto;
import com.skillmentor_backend.final_project.dto.MentorSummaryDto;
import com.skillmentor_backend.final_project.dto.StudentDashboardResponseDto;
import com.skillmentor_backend.final_project.entity.Classroom;
import com.skillmentor_backend.final_project.entity.Mentor;
import com.skillmentor_backend.final_project.entity.Session;
import com.skillmentor_backend.final_project.entity.SessionStatus;
import com.skillmentor_backend.final_project.repository.ClassroomRepository;
import com.skillmentor_backend.final_project.repository.MentorRepository;
import com.skillmentor_backend.final_project.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final SessionRepository sessionRepository;
    private final ClassroomRepository classroomRepository;
    private final MentorRepository mentorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDto> getAllClasses() {
        // 1. Fetch the data using the existing query
        List<Classroom> classrooms = classroomRepository.findAllWithMentors();

        // 2. Map the entities to the new DTOs
        return classrooms.stream()
                .map(this::mapClassroomToDto)
                .toList();
    }

    @Override
    @Transactional
    public Session createBooking(CreateBookingRequestDto request, Jwt jwt) {
        // Find the related entities
        Classroom classroom = classroomRepository.findById(request.getClassroomId())
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + request.getClassroomId()));
        Mentor mentor = mentorRepository.findById(request.getMentorId())
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found with id: " + request.getMentorId()));

        // --- CORRECTED NAME EXTRACTION ---
        String studentClerkId = jwt.getSubject();
        String firstName = jwt.hasClaim("given_name") ? jwt.getClaimAsString("given_name") : "";
        String lastName = jwt.hasClaim("family_name") ? jwt.getClaimAsString("family_name") : "";
        String studentName = (firstName + " " + lastName).trim();
        if (studentName.isEmpty()) {
            studentName = "Student User"; // Fallback if no name claims are present
        }
        // --- END OF CORRECTION ---

        // Create and populate the new session
        Session session = new Session();
        session.setStudentClerkId(studentClerkId);
        session.setStudentName(studentName); // Use the correctly assembled name
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
    public List<StudentDashboardResponseDto> getStudentDashboard(Jwt jwt) {
        String studentClerkId = jwt.getSubject();
        List<Session> sessions = sessionRepository.findByStudentClerkIdOrderBySessionDateTimeDesc(studentClerkId);

        return sessions.stream()
                .map(this::mapSessionToStudentDashboardResponse)
                .toList();
    }

    // Helper method to map a Session entity to the dashboard response DTO
    private StudentDashboardResponseDto mapSessionToStudentDashboardResponse(Session session) {
        StudentDashboardResponseDto dto = new StudentDashboardResponseDto();
        dto.setClassName(session.getClassroom().getName());
        dto.setMentorName(session.getMentor().getFirstName() + " " + session.getMentor().getLastName());
        dto.setSessionDate(session.getSessionDateTime());
        dto.setStatus(session.getStatus().name());
        return dto;
    }

    // Helper method to perform the mapping
    private ClassroomResponseDto mapClassroomToDto(Classroom classroom) {
        ClassroomResponseDto classroomDto = new ClassroomResponseDto();
        classroomDto.setId(classroom.getId());
        classroomDto.setName(classroom.getName());
        classroomDto.setImageUrl(classroom.getImageUrl());

        List<MentorSummaryDto> mentorDtos = classroom.getMentors().stream()
                .map(this::mapMentorToSummaryDto)
                .toList();

        classroomDto.setMentors(mentorDtos);
        return classroomDto;
    }

    private MentorSummaryDto mapMentorToSummaryDto(Mentor mentor) {
        MentorSummaryDto mentorDto = new MentorSummaryDto();
        mentorDto.setId(mentor.getId());
        mentorDto.setFirstName(mentor.getFirstName());
        mentorDto.setLastName(mentor.getLastName());
        mentorDto.setTitle(mentor.getTitle());
        mentorDto.setImageUrl(mentor.getImageUrl());
        mentorDto.setSessionFee(mentor.getSessionFee());
        return mentorDto;
    }
}