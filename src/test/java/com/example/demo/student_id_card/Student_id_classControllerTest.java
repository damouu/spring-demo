package com.example.demo.student_id_card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentIdCardController.class)
class Student_id_classControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentIdCardService studentIdCardService;


}
