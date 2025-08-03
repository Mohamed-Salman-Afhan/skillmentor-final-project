package com.skillmentor_backend.final_project.controller;

import com.skillmentor_backend.final_project.dto.MentorProfileResponseDto;
import com.skillmentor_backend.final_project.entity.Mentor;
import com.skillmentor_backend.final_project.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mentors") // Public endpoint as per SecurityConfig
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    @GetMapping
    public ResponseEntity<List<Mentor>> getAllMentors() {
        return ResponseEntity.ok(mentorService.getAllMentors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorProfileResponseDto> getMentorProfile(@PathVariable Long id) {
        return ResponseEntity.ok(mentorService.getMentorProfile(id));
    }
}