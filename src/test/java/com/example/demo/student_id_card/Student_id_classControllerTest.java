package com.example.demo.student_id_card;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


}
