package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> allStudents(Optional<Integer> queryParam) {
        List<Student> students = studentRepository.findAll();
        if (queryParam.isEmpty()) {
            return students;
        }
        students.stream().iterator().forEachRemaining(student -> student.setAge(Period.between(student.getDob(), LocalDate.now()).getYears()));
        students.removeIf(student -> !queryParam.get().equals(student.getAge()));
        return students;
    }

    public Response addNewStudent(Student student) {
        Optional<Student> studentsByEmail = studentRepository.findStudentsByEmail(student.getEmail());
        if (studentsByEmail.isPresent()) {
            return Response.status(400).build();
        }
        studentRepository.save(student);
        return Response.ok(student).status(201).contentLocation(URI.create("http://localhost:8080/api/student/" + student.getId())).build();
    }

    public Response deleteStudent(Integer studentId) {
        try {
            Optional<Student> student = studentRepository.findStudentsById(studentId);
            studentRepository.deleteById(studentId);
            return Response.noContent().status(204).build();
        } catch (Exception exception) {
            return Response.noContent().status(404).language(String.valueOf(exception)).build();
        }
    }

    public Response updateStudent(Integer studentId, Student student) {
        if (studentRepository.findStudentsById(studentId).isPresent()) {
            Student student1 = studentRepository.findStudentsById(studentId).get();
            student1.setName(student.getName());
            student1.setDob(student.getDob());
            student1.setEmail(student.getEmail());
            studentRepository.save(student1);
            return Response.ok(student1).status(204).build();
        }
        return Response.notModified().status(404).build();
    }

    public Response findById(Integer studentId) {
        Optional<Student> optionalStudent = studentRepository.findStudentsById(studentId);
        if (optionalStudent.isPresent()) {
            return Response.ok().entity(optionalStudent.get()).build();
        }
        return Response.status(404).build();
    }
}
