package com.example.demo.teacher;

import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Data
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public Teacher getTeacherUuid(UUID uuid) {
        return teacherRepository.findTeacherByUuid(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "teacher not found"));
    }

    public Optional<Teacher> getTeacherEmail(String email) {
        return Optional.ofNullable(teacherRepository.findTeacherByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "teacher not found")));
    }

    public HttpEntity<String> deleteTeacher(UUID uuid) {
        Teacher teacher = teacherRepository.findTeacherByUuid(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "teacher not found"));
        teacherRepository.delete(teacher);
        return ResponseEntity.status(204).body("teacher successfully deleted");
    }
}
