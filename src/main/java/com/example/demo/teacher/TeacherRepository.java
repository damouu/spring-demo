package com.example.demo.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Validated
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findTeacherByEmail(@NotNull String Email);

    Optional<Teacher> findTeacherByUuid(@NotNull UUID uuid);
}