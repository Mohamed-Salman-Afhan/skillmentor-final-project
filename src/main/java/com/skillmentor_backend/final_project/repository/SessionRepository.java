package com.skillmentor_backend.final_project.repository;

import com.skillmentor_backend.final_project.dto.EnrollmentTrendDto;
import com.skillmentor_backend.final_project.entity.Session;
import com.skillmentor_backend.final_project.entity.SessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    /**
     * Counts the number of sessions for a specific mentor in a specific class.
     * This is required for the Mentor Profile feature.
     */
    long countByClassroomIdAndMentorId(Long classroomId, Long mentorId);

    /**
     * Finds all sessions for a given student, ordered by the most recent session date.
     * This is required for the Student Dashboard feature.
     */
    List<Session> findByStudentClerkIdOrderBySessionDateTimeDesc(String studentClerkId);

    long countByStatus(SessionStatus status);

    // New method to count distinct students who have booked sessions
    @Query("SELECT COUNT(DISTINCT s.studentClerkId) FROM Session s")
    long countDistinctStudents();

    @Query(value = "SELECT DATE(session_date_time) as date, COUNT(DISTINCT student_clerk_id) as studentCount " +
            "FROM sessions " +
            "GROUP BY DATE(session_date_time) " +
            "ORDER BY date ASC", nativeQuery = true)
    List<EnrollmentTrendDto> findEnrollmentTrend();

    @Query("SELECT s FROM Session s WHERE " +
            "LOWER(s.studentName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.classroom.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.mentor.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.mentor.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Session> findAllWithSearch(@Param("searchTerm") String searchTerm, Pageable pageable);
}