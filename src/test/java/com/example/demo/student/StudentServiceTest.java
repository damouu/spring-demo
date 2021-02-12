
package com.example.demo.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

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
    void addNewStudent() {
        Student student = new Student(1, UUID.randomUUID(), "dede", LocalDate.of(2020, 12, 12),
                "dede@fdp.com");
        Assertions.assertEquals(student, student);
        Assertions.assertNotNull(student);
        Assertions.assertNotEquals(student,student);
    }

    @Test
    void deleteStudent() {
    }

}
