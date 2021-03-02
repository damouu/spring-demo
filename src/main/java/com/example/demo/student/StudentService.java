package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public void addNewStudent(Student student) {
        Optional<Student> studentsByEmail = studentRepository.findStudentsByEmail(student.getEmail());
        if (studentsByEmail.isEmpty()) {
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

    public Student findById(Integer studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("no student found by the given id" + " " + studentId));
    }
}
