package com.skillmentor_backend.final_project.service;

import com.skillmentor_backend.final_project.dto.MentorProfileResponse;
import com.skillmentor_backend.final_project.entity.Mentor;
import com.skillmentor_backend.final_project.repository.MentorRepository;
import com.skillmentor_backend.final_project.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public MentorProfileResponse getMentorProfile(Long id) {
        // 1. Find the mentor by ID or throw an exception
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found with id: " + id));

        // 2. For each class the mentor teaches, count the number of sessions they have
        List<MentorProfileResponse.MentorClassDto> classDtos = mentor.getClassrooms().stream()
                .map(classroom -> {
                    long studentCount = sessionRepository.countByClassroomIdAndMentorId(classroom.getId(), mentor.getId());
                    return new MentorProfileResponse.MentorClassDto(classroom.getName(), studentCount);
                })
                .collect(Collectors.toList());

        // 3. Assemble and return the final DTO
        return new MentorProfileResponse(mentor, classDtos);
    }
}