package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Validated
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    Optional<Student> findStudentsByEmail(@NotNull String email);

    Optional<Student> findStudentByUuid(@NotNull UUID uuid);
}
