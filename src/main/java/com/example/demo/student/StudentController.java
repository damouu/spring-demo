package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> allStudents() {
        return studentService.allStudents();
    }

    @GetMapping(path = "{studentId}")
    public ResponseEntity<?> findById(@PathVariable("studentId") Integer studentId) {
        return studentService.findById(studentId);
    }

    @PostMapping
    public void inertNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Integer studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }
}
