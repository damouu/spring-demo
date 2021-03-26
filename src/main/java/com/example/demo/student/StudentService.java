package com.example.demo.student;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

@Service
@Data
public class StudentService {

    private final StudentRepository studentRepository;

    public Collection<Student> allStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> pages = studentRepository.findAll(pageable);
        return pages.toList();
    }

    public Student getStudent(UUID studentUuid) {
        return studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student card does not exist"));
    }

    public ResponseEntity<Student> postStudent(Student student) {
        studentRepository.findStudentsByEmail(student.getEmail()).ifPresent(student1 -> {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid email address");
        });
        student.setUuid(UUID.randomUUID());
        studentRepository.save(student);
        return ResponseEntity.created(URI.create("http://localhost:8083/api/student/" + student.getUuid())).body(student);
    }

    public ResponseEntity<?> deleteStudent(UUID studentUuid) {
        Student student = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student does not exist"));
        studentRepository.delete(student);
        return ResponseEntity.status(204).build();
    }

    public Student updateStudent(UUID studentUuid, Student student) {
        Student student1 = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student does not exist"));
        student1.setName(student.getName());
        student1.setDob(student.getDob());
        student1.setEmail(student.getEmail());
        return studentRepository.save(student1);
    }
}
