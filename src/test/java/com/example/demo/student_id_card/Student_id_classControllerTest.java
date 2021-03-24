package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentIdCardController.class)
class Student_id_classControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentIdCardService studentIdCardService;

    @Test
    public void getStudentIdCards() throws Exception {
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        StudentIdCard studentIdCard1 = new StudentIdCard(UUID.randomUUID());
        StudentIdCard studentIdCard2 = new StudentIdCard(UUID.randomUUID());
        List<StudentIdCard> studentIdCardCollection = new ArrayList<>();
        studentIdCardCollection.add(studentIdCard);
        studentIdCardCollection.add(studentIdCard1);
        studentIdCardCollection.add(studentIdCard2);
        Mockito.when(studentIdCardService.getStudentIdCards(0, 5)).thenReturn(studentIdCardCollection);
        mockMvc.perform(get("/api/studentCard"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"uuid\":\"" + studentIdCard.getUuid() + "\"},{\"uuid\":\"" + studentIdCard1.getUuid() + "\"},{\"uuid\":\"" + studentIdCard2.getUuid() + "\"}]"));
        Mockito.verify(studentIdCardService, Mockito.times(1)).getStudentIdCards(0, 5);
    }

    @Test
    public void getStudentIdCard() throws Exception {
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        Mockito.when(studentIdCardService.getStudentIdCard(studentIdCard.getUuid())).thenReturn(studentIdCard);
        mockMvc.perform(get("/api/studentCard/" + studentIdCard.getUuid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"uuid\":\"" + studentIdCard.getUuid() + "\"}"));
        Mockito.verify(studentIdCardService, Mockito.times(1)).getStudentIdCard(studentIdCard.getUuid());
    }

    @Test
    public void deleteStudentIdCard() throws Exception {
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        ResponseEntity<String> responseEntity = new ResponseEntity<String>("student card" + studentIdCard.getUuid() + "deleted", HttpStatus.valueOf(204));
        Mockito.when(studentIdCardService.deleteStudentIdCard(studentIdCard.getUuid())).thenReturn(responseEntity);
        mockMvc.perform(delete("/api/studentCard/" + studentIdCard.getUuid()))
                .andExpect(status().is(204))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("student card" + studentIdCard.getUuid() + "deleted"));
        Mockito.verify(studentIdCardService, Mockito.times(1)).deleteStudentIdCard(studentIdCard.getUuid());
    }

    @Test
    public void postStudentIdCard() throws Exception {
        Student student = new Student(UUID.randomUUID(), "test", LocalDate.of(2000, Month.JANUARY, 1), "test@gmail.com");
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("http://localhost:8083/api/studentCard/" + studentIdCard.getUuid()));
        ResponseEntity<StudentIdCard> responseEntity = new ResponseEntity<StudentIdCard>(studentIdCard, responseHeaders, HttpStatus.CREATED);
        Mockito.when(studentIdCardService.postStudentIdCard(student.getUuid())).thenReturn(responseEntity);
        mockMvc.perform(post("/api/studentCard/student/" + student.getUuid()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", "http://localhost:8083/api/studentCard/" + studentIdCard.getUuid()))
                .andExpect(content().json("{\"uuid\":" + studentIdCard.getUuid() + "}"));
        Mockito.verify(studentIdCardService, Mockito.times(1)).postStudentIdCard(student.getUuid());
    }
}
