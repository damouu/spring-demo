package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findStudentsByEmail(String email);

    Optional<Student> findStudentByUuid(UUID uuid);
}
