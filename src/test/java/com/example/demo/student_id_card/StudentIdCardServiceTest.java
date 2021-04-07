package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class StudentIdCardServiceTest {

    @Mock
    private StudentIdCardRepository studentIdCardRepository;

    @InjectMocks
    private StudentIdCardService studentIdCardService;


    @Test
    void getStudentIdCards() {
        List<StudentIdCard> studentIdCards = Arrays.asList(new StudentIdCard(UUID.randomUUID()), new StudentIdCard(UUID.randomUUID()));
        Page page = Mockito.mock(Page.class);
        Mockito.when(this.studentIdCardRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(page);
        ResponseEntity<List<StudentIdCard>> responseEntity = studentIdCardService.getStudentIdCards(0, 5);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
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
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        Mockito.when(studentIdCardRepository.findStudentIdCardByUuid(studentIdCard.getUuid())).thenReturn(java.util.Optional.of(studentIdCard));
        ResponseEntity responseEntity = studentIdCardService.deleteStudentIdCard(studentIdCard.getUuid());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Mockito.verify(studentIdCardRepository, Mockito.times(1)).delete(studentIdCard);
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