package com.skillmentor_backend.final_project.controller;

import com.skillmentor_backend.final_project.dto.ClassroomResponseDto;
import com.skillmentor_backend.final_project.dto.CreateBookingRequestDto;
import com.skillmentor_backend.final_project.dto.StudentDashboardResponseDto;
import com.skillmentor_backend.final_project.entity.Session;
import com.skillmentor_backend.final_project.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomResponseDto>> getAllClasses() { // Update return type
        return ResponseEntity.ok(studentService.getAllClasses());
    }

    @PostMapping("/bookings")
    public ResponseEntity<Session> createBooking(@Valid @RequestBody CreateBookingRequestDto request, @AuthenticationPrincipal Jwt jwt) { // Add @Valid
        Session createdSession = studentService.createBooking(request, jwt);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<StudentDashboardResponseDto>> getDashboard(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(studentService.getStudentDashboard(jwt));
    }
}