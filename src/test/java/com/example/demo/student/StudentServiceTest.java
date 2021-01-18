package com.example.demo.student;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        studentService = new StudentService(studentRepository);
    }

    @Test
    void allStudents() {
        Faker faker = new Faker();
        ArrayList<Student> studentArrayList2 = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Student student = new Student(
                    i,
                    faker.name().name(),
                    LocalDate.of(faker.number().numberBetween(1900, 2021), Month.NOVEMBER, faker.number().numberBetween(1, 31)),
                    faker.internet().emailAddress()
            );
            studentArrayList2.add(student);
        }
        when(studentRepository.findAll()).thenReturn(studentArrayList2);
        ArrayList<Student> allStudents = (ArrayList<Student>) studentRepository.findAll();
        Assertions.assertFalse(allStudents.isEmpty());
    }

    @Test
    void addNewStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void updateStudent() {
    }

    @Test
    void findById() {
    }
}