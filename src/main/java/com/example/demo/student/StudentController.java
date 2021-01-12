package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
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

    @PostMapping
    public void inertNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

}
