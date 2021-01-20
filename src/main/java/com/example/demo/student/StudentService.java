package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public void addNewStudent(Student student) {
        Optional<Student> studentsByEmail = studentRepository.findStudentsByEmail(student.getEmail());
        if (studentsByEmail.isPresent()) {
            throw new IllegalStateException("email already taken");
        }
        studentRepository.save(student);
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

    public ResponseEntity<?> findById(Integer studentId) {
        Optional<Student> optionalStudent = studentRepository.findStudentsById(studentId);
        if (optionalStudent.isPresent()) {
            return ResponseEntity.ok(optionalStudent);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not user found by the given ID");
    }
}
