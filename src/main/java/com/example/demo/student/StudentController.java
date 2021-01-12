package com.example.demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    @GetMapping
    public List<Student> allStudents() {
        return List.of(
                new Student(
                        2L,
                        "dede",
                        LocalDate.of(2000, Month.NOVEMBER, 5),
                        12,
                        "petiteTepueDu57@hotmail.fr")
        );
    }
}
