package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public void deleteStudent(Integer studentId) {
        boolean exist = studentRepository.existsById(studentId);
        if (!exist) {
            throw new IllegalStateException("no student exist by the given Id");
        } else {
            studentRepository.deleteById(studentId);
        }
    }

    @Transactional
    public void updateStudent(Integer studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("not Student found by the given id"));
        if (name != null) {
            student.setName(name);
        }
        if (email != null) {
            student.setEmail(email);
        }
    }

    public Response findById(Integer studentId) {
        Optional<Student> optionalStudent = studentRepository.findStudentsById(studentId);
        if (optionalStudent.isPresent()) {
            return Response.ok().entity(optionalStudent.get()).build();
        }
        return Response.status(404).build();
    }
}
