package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// the SpringBootTest annotation is for Integration test purposes.
// Integration test will launch the whole program, and simulate HTTP request and assert the result.
// and because it will run the whole program we must define a random port to run on it.
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    // to make HTTP requests, like MockMVC for Unit tests
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void allStudents() {
        ResponseEntity<?> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/student", List.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findById() {
        ResponseEntity<?> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/student/1", List.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void inertNewStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void updateStudent() {
    }
}