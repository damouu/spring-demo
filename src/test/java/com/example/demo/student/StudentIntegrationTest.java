package com.example.demo.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentIntegrationTest {

    @LocalServerPort
    private int port;

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
        ResponseEntity<List> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/student", List.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType()).includes(MediaType.APPLICATION_JSON));
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
    }

    @Test
    void getStudent() {
        Student student = new Student(UUID.randomUUID(), "test",
                LocalDate.of(2000, Month.JANUARY, 21), "test.finalfantasy@hotmail.com");
        studentRepository.save(student);
        Optional<Student> student1 = studentRepository.findStudentsByEmail(student.getEmail());
        ResponseEntity<Student> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/student/" + student1.get().getUuid(), Student.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType()).includes(MediaType.APPLICATION_JSON));
        Assertions.assertEquals(student1.get().getUuid(), Objects.requireNonNull(responseEntity.getBody()).getUuid());
        Assertions.assertEquals(student1.get().getName(), Objects.requireNonNull(responseEntity.getBody()).getName());
        Assertions.assertEquals(student1.get().getDob(), Objects.requireNonNull(responseEntity.getBody()).getDob());
        Assertions.assertEquals(student1.get().getEmail(), Objects.requireNonNull(responseEntity.getBody()).getEmail());
    }

    @Test
    void inertNewStudent() throws Exception {
        Student student = new Student(UUID.randomUUID(), "test", LocalDate.of(2000, Month.APRIL, 21), "test@hotmail.com");
        mockMvc.perform(post("/api/student/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"uuid\":\"" + student.getUuid() + "\",\"name\":\"" + student.getName() + "\",\"dob\":\"" + student.getDob() + "\",\"email\":\"" + student.getEmail() + "\"}"));
    }

    @Test
    void deleteStudent() throws Exception {
        Student student = new Student(UUID.randomUUID(), "tidus", LocalDate.of(2000, Month.APRIL, 21), "tidus@hotmail.com");
        studentRepository.save(student);
        Optional<Student> student1 = studentRepository.findStudentByUuid(student.getUuid());
        mockMvc.perform(delete("/api/student/" + student1.get().getUuid())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("student successfully deleted"));
    }

    @Test
    void updateStudent() throws Exception {
        var uuid = UUID.randomUUID();
        var studentRetrieved = new Student(uuid, "first_iteration", LocalDate.now(), "first_iteration@hotmail.com");
        studentRepository.save(studentRetrieved);
        var studentUpdates = new Student(uuid, "second_iteration", LocalDate.now(), "second_iteration@hotmail.com");
        mockMvc.perform(put("/api/student/" + studentRetrieved.getUuid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentUpdates)))
                .andExpect(status().is(204))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"uuid\":\"" + studentUpdates.getUuid() + "\",\"name\":\"" + studentUpdates.getName() + "\",\"dob\":\"" + studentUpdates.getDob() + "\",\"email\":\"" + studentUpdates.getEmail() + "\"}"));
    }
}