package com.skillmentor_backend.final_project.controller;

import com.skillmentor_backend.final_project.dto.BookingDetailsResponseDto;
import com.skillmentor_backend.final_project.dto.CreateClassroomRequestDto;
import com.skillmentor_backend.final_project.dto.CreateMentorRequestDto;
import com.skillmentor_backend.final_project.entity.Classroom;
import com.skillmentor_backend.final_project.entity.Mentor;
import com.skillmentor_backend.final_project.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
// Use ROLE_admin as Spring Security prefixes authorities with ROLE_ by default
@PreAuthorize("hasAuthority('ROLE_admin')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/classrooms")
    public ResponseEntity<Classroom> createClassroom(@Valid @RequestBody CreateClassroomRequestDto dto) { // Add @Valid
        Classroom createdClassroom = adminService.createClassroom(dto);
        return new ResponseEntity<>(createdClassroom, HttpStatus.CREATED);
    }

    @PostMapping("/mentors")
    public ResponseEntity<Mentor> createMentor(@Valid @RequestBody CreateMentorRequestDto dto) { // Add @Valid
        Mentor createdMentor = adminService.createMentor(dto);
        return new ResponseEntity<>(createdMentor, HttpStatus.CREATED);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDetailsResponseDto>> getAllBookings() {
        return ResponseEntity.ok(adminService.getAllBookings());
    }

    @PutMapping("/bookings/{id}/approve")
    public ResponseEntity<BookingDetailsResponseDto> approveBooking(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.approveBooking(id));
    }

    @PutMapping("/bookings/{id}/complete")
    public ResponseEntity<BookingDetailsResponseDto> completeBooking(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.completeBooking(id));
    }
}