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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentIdCardIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getStudentStudentIdCard() {
        ResponseEntity<Student> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/studentCard/" + "e59c3f4e-46a6-4cd3-8a82-7198e6d940a9" + "/student", Student.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(responseEntity.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON));
        Assertions.assertEquals(responseEntity.getBody().getEmail(), "hubert.schmeler@hotmail.com");
        Assertions.assertEquals(responseEntity.getBody().getName(), "Thomasina Stokes");
        Assertions.assertEquals(responseEntity.getBody().getDob(), LocalDate.of(1909, Month.NOVEMBER, 07));
    }

}
