package com.example.demo.student;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {
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
