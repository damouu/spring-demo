package com.example.demo.student;

import com.example.demo.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(UUID studentUuid) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUuid(studentUuid);
        return optionalStudent.orElse(null);
    }

    public Response addNewStudent(Student student) {
        Optional<Student> studentsByEmail = studentRepository.findStudentsByEmail(student.getEmail());
        if (studentsByEmail.isPresent()) {
            return Response.status(400).build();
        }
        studentRepository.save(student);
        return Response.ok(student).status(201).contentLocation(URI.create("http://localhost:8080/api/student/" + student.getId())).build();
    }

    public Response deleteStudent(UUID studentUuid) {
        try {
            Optional<Student> student = studentRepository.findStudentByUuid(studentUuid);
            studentRepository.delete(student.get());
            return Response.noContent().status(204).build();
        } catch (Exception exception) {
            return Response.noContent().status(404).language(String.valueOf(exception)).build();
        }
    }

    public Response updateStudent(UUID studentUuid, Student student) {
        if (studentRepository.findStudentByUuid(studentUuid).isPresent()) {
            Student student1 = studentRepository.findStudentByUuid(studentUuid).get();
            student1.setName(student.getName());
            student1.setDob(student.getDob());
            student1.setEmail(student.getEmail());
            studentRepository.save(student1);
            return Response.ok(student1).status(204).build();
        }
        return Response.notModified().status(404).build();
    }
}
