package com.example.demo.student_id_card;

import com.example.demo.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StudentIdCardServiceTest {

    @Mock
    private StudentIdCardRepository studentIdCardRepository;

    private StudentIdCardService studentIdCardService;

    private StudentRepository studentRepository;

    @Captor
    private ArgumentCaptor<StudentIdCard> argumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        studentIdCardService = new StudentIdCardService(studentIdCardRepository, studentRepository);
    }

    @Test
    void getStudentIdCards() {

    }

    @Test
    void getStudentIdCard() {
    }

    @Test
    void createStudentIdCard() {
    }
}