package com.example.demo.student;

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


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    public Response getStudent(UUID studentUuid) {
        Student student = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new IllegalStateException("student does not exist"));
        return Response.ok(student).status(200).build();
    }

    public Response addNewStudent(Student student) {
        Optional<Student> studentsByEmail = studentRepository.findStudentsByEmail(student.getEmail());
        if (studentsByEmail.isPresent()) {
            return Response.status(400).build();
        }
        studentRepository.save(student);
        return Response.ok(student).status(201).contentLocation(URI.create("http://localhost:8080/api/student/" + student.getUuid())).build();
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
            student1.setUuid(studentUuid);
            studentRepository.save(student1);
            return Response.ok(student).status(204).contentLocation(URI.create("http://localhost:8080/api/student/" + student1.getUuid())).build();
        }
        return Response.notModified().status(404).build();
    }

    public Response getCourseStudent(UUID studentUuid) {
        Student student = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new IllegalStateException("provided studentUuid does not exist"));
        return Response.ok(student.getCourses()).status(200).build();
    }
}
