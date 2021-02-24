package com.example.demo.student_id_card;

import com.example.demo.student.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<StudentIdCard> studentIdCards = new ArrayList<>();
        StudentIdCard studentIdCard = new StudentIdCard(1, UUID.randomUUID());
        studentIdCards.add(studentIdCard);
        Mockito.when(studentIdCardService.getStudentIdCards()).thenReturn(studentIdCards);
        List<StudentIdCard> studentIdCards1 = studentIdCardService.getStudentIdCards();
        Assertions.assertNotNull(studentIdCards1);
    }

    @Test
    void getStudentIdCard() {
    }

    @Test
    void createStudentIdCard() {
    }
}