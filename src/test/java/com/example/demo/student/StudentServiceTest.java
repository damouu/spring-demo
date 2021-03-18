
package com.example.demo.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
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
    void getStudent() {
        Student student = new Student(UUID.randomUUID(), "dede", LocalDate.of(2020, 12, 12),
                "dede@fdp.com");
        Mockito.when(studentRepository.findStudentByUuid(student.getUuid())).thenReturn(Optional.of(student));
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        Assertions.assertEquals(student, studentService.getStudent(student.getUuid()));
    }

    @Test
    void deleteStudent() {
        Student student = new Student(UUID.randomUUID(), "dede", LocalDate.of(2020, 12, 12),
                "dede@fdp.com");
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
    }

}
