package com.skillmentor_backend.final_project.repository;

import com.skillmentor_backend.final_project.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    // This custom query uses a JOIN FETCH to solve the N+1 and lazy loading problem.
    // It tells JPA to fetch all classrooms and their associated mentors in a single query.
    @Query("SELECT c FROM Classroom c LEFT JOIN FETCH c.mentors")
    List<Classroom> findAllWithMentors();
}