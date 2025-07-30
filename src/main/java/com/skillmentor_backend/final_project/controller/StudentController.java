package com.skillmentor_backend.final_project.controller;

import com.skillmentor_backend.final_project.dto.CreateBookingRequest;
import com.skillmentor_backend.final_project.dto.StudentDashboardResponse;
import com.skillmentor_backend.final_project.entity.Classroom;
import com.skillmentor_backend.final_project.entity.Session;
import com.skillmentor_backend.final_project.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/classrooms")
    public ResponseEntity<List<Classroom>> getAllClasses() {
        return ResponseEntity.ok(studentService.getAllClasses());
    }

    @PostMapping("/bookings")
    public ResponseEntity<Session> createBooking(@RequestBody CreateBookingRequest request, @AuthenticationPrincipal Jwt jwt) {
        Session createdSession = studentService.createBooking(request, jwt);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<StudentDashboardResponse>> getDashboard(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(studentService.getStudentDashboard(jwt));
    }
}