package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> allStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> pages = studentRepository.findAll(pageable);
        return pages.toList();
    }

    public Student getStudent(UUID studentUuid) {
        return studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new IllegalArgumentException("student does not exist") {
        });
    }

    public Student postStudent(Student student) {
        studentRepository.findStudentsByEmail(student.getEmail()).ifPresent(student1 -> {
            throw new EntityExistsException("invalid email address");
        });
        student.setUuid(UUID.randomUUID());
        return studentRepository.save(student);
    }

    public Response deleteStudent(UUID studentUuid) {
        Student student = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new EntityNotFoundException("student does not exist"));
        studentRepository.delete(student);
        return Response.accepted().status(204).build();
    }

    public Student updateStudent(UUID studentUuid, Student student) {
        Student student1 = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new EntityNotFoundException("invalid uuid"));
        student1.setName(student.getName());
        student1.setDob(student.getDob());
        student1.setEmail(student.getEmail());
        return studentRepository.save(student1);
    }

    public Response getCourseStudent(UUID studentUuid) {
        Student student = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new IllegalStateException("provided studentUuid does not exist"));
        return Response.ok(student.getCourses()).status(200).build();
    }
}
