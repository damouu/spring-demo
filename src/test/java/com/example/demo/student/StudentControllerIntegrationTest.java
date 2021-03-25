package com.example.demo.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;


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

    @Test
    void registrationWorksThroughAllLayers() throws Exception {
        Student student = new Student(UUID.randomUUID(), "tidus", LocalDate.of(2000, Month.APRIL, 21), "tidus@hotmail.com");
        mockMvc.perform(post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated());

        Optional<Student> student1 = studentRepository.findStudentsByEmail(student.getEmail());
        assertThat(student1.get().getEmail()).isEqualTo("tidus@hotmail.com");
        studentRepository.deleteByUuid(student.getUuid());
    }
}