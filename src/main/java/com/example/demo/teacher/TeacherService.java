package com.example.demo.teacher;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.net.URI;
import java.time.LocalDate;
import java.util.Map;
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

    public ResponseEntity<String> deleteTeacher(UUID uuid) {
        Teacher teacher = teacherRepository.findTeacherByUuid(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "teacher not found"));
        teacherRepository.delete(teacher);
        return ResponseEntity.status(204).body("teacher successfully deleted");
    }

    public ResponseEntity<Teacher> postTeacher(Teacher teacher) {
        if ((LocalDate.now().getYear() - teacher.getDob().getYear() >= 18)) {
            try {
                teacher.setUuid(UUID.randomUUID());
                teacherRepository.save(teacher);
                return ResponseEntity.created(URI.create("http://localhost:8083/api/teacher/" + teacher.getUuid())).body(teacher);
            } catch (Exception exception) {
                throw new Error("this email can not be used.");
            }
        } else {
            return ResponseEntity.badRequest().body(teacher);
        }
    }

    public ResponseEntity<Optional<Teacher>> updateTeacher(UUID uuid, Map<String, String> teacherUpdates) {
        try {
            Optional<Teacher> teacher = teacherRepository.findTeacherByUuid(uuid);
            teacherUpdates.forEach((key, value) -> {
                try {
                    Field field = teacher.get().getClass().getDeclaredField(key); // Using reflections to set an object property
                    field.setAccessible(true);
                    field.set(teacher.get(), value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            teacherRepository.save(teacher.get());
            return ResponseEntity.status(204).body(teacher);
        } catch (Exception exception) {
            throw new EntityNotFoundException("this teacher does not exist");
        }
    }
}
