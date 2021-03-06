package com.example.demo.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<Course> findByUuid(@NotNull UUID uuid);

    // the "containing" keyword is used as the LIKE keyword in SQL syntax. // Containing: select ... like %:place%
    Optional<Collection<Course>> findByCampusContaining(String department);

    Optional<Collection<Course>> findByUniversityContaining(String department);

    Optional<Collection<Course>> findByNameContaining(String department);

    Optional<Collection<Course>> findByUniversityContainingAndCampusContaining(String university, String campus);

    Optional<Collection<Course>> findByUniversityContainingAndNameContaining(String university, String name);

    Optional<Collection<Course>> findByNameContainingAndCampusContaining(String university, String name);

}
