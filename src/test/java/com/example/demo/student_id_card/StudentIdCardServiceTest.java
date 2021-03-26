package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
// what does this annotation mean ?
class StudentIdCardServiceTest {

    @Mock
    // what does this annotation mean ?
    private StudentIdCardRepository studentIdCardRepository;

    @InjectMocks
    // what does this annotation mean ?
    private StudentIdCardService studentIdCardService;


    @Test
    void getStudentIdCards() {

    }

    @Test
    void getStudentIdCard() {
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        Mockito.when(studentIdCardRepository.findStudentIdCardByUuid(studentIdCard.getUuid())).thenReturn(java.util.Optional.of(studentIdCard));
        var studentIdCard1 = studentIdCardService.getStudentIdCard(studentIdCard.getUuid());
        Assertions.assertNotNull(studentIdCard1);
        Assertions.assertEquals(studentIdCard.getUuid(), studentIdCard1.getUuid());
        Mockito.verify(studentIdCardRepository, Mockito.times(1)).findStudentIdCardByUuid(studentIdCard.getUuid());
    }

    @Test
    void deleteStudentIdCard() {
    }

    @Test
    void postStudentIdCard() {
    }

    @Test
    void getStudentIdCardCourse() {
    }

    @Test
    void getStudentStudentIdCard() {
        Student student = new Student(UUID.randomUUID(), "dede", LocalDate.of(2000, Month.AUGUST, 11), "dedeUnit@msn.com");
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        studentIdCard.setStudent(student);
        Mockito.when(studentIdCardRepository.findStudentIdCardByUuid(studentIdCard.getUuid())).thenReturn(java.util.Optional.of(studentIdCard));
        var studentIdCard1 = studentIdCardRepository.findStudentIdCardByUuid(studentIdCard.getUuid());
        Assertions.assertNotNull(studentIdCard1);
        Assertions.assertEquals(student, studentIdCard1.get().getStudent());
        Assertions.assertNotNull(studentIdCard1.get().getStudent());
        Assertions.assertEquals(studentIdCard1.get().getStudent().getName(), "dede");
        Assertions.assertEquals(studentIdCard1.get().getStudent().getEmail(), "dedeUnit@msn.com");
    }


}