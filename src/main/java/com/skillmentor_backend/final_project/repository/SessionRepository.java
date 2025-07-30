package com.skillmentor_backend.final_project.repository;

import com.skillmentor_backend.final_project.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
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
}