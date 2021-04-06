package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentIdCardIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentIdCardRepository studentIdCardRepository;

    @Test
    void getStudentStudentIdCard() {
        Student student = new Student(UUID.randomUUID(), "Tidus",
                LocalDate.of(2000, Month.JANUARY, 21), "tidus.finalfantasy@hotmail.com");
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        studentIdCard.setStudent(student);
        studentIdCardRepository.save(studentIdCard);
        ResponseEntity<Student> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/studentCard/" + studentIdCard.getUuid() + "/student", Student.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(responseEntity.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON));
        Assertions.assertEquals(responseEntity.getBody().getEmail(), "tidus.finalfantasy@hotmail.com");
        Assertions.assertEquals(responseEntity.getBody().getName(), "Tidus");
        Assertions.assertEquals(responseEntity.getBody().getDob(), LocalDate.of(2000, Month.JANUARY, 21));
    }

    @Test
    void deleteStudentIdCard() {
        Student student = new Student(UUID.randomUUID(), "Tidus",
                LocalDate.of(2000, Month.JANUARY, 21), "tidus.finalfantasy@hotmail.com");
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        studentIdCard.setStudent(student);
        studentIdCardRepository.save(studentIdCard);
        restTemplate.delete("http://localhost:" + port + "/api/studentCard/" + studentIdCard.getUuid());
        Assertions.assertTrue(studentIdCardRepository.findStudentIdCardByUuid(studentIdCard.getUuid()).isEmpty());
    }

}
