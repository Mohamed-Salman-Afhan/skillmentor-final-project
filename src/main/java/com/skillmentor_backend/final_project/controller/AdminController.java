package com.skillmentor_backend.final_project.controller;

import com.skillmentor_backend.final_project.dto.*;
import com.skillmentor_backend.final_project.entity.Classroom;
import com.skillmentor_backend.final_project.entity.Mentor;
import com.skillmentor_backend.final_project.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
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
    public ResponseEntity<Page<BookingDetailsResponseDto>> getAllBookings(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String searchTerm) { // <-- ADD THIS
        return ResponseEntity.ok(adminService.getAllBookings(pageable, searchTerm));
    }

    @PutMapping("/bookings/{id}/approve")
    public ResponseEntity<BookingDetailsResponseDto> approveBooking(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.approveBooking(id));
    }

    @PutMapping("/bookings/{id}/complete")
    public ResponseEntity<BookingDetailsResponseDto> completeBooking(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.completeBooking(id));
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<AdminDashboardStatsDto> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @GetMapping("/dashboard/daily-bookings")
    public ResponseEntity<List<DailyBookingsDto>> getDailyBookingsTrend() {
        return ResponseEntity.ok(adminService.getDailyBookingsTrend());
    }
}