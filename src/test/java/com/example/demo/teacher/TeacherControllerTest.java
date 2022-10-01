package com.example.demo.teacher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// this, tells to the spring core that this class, is a test class for the controller class. an MvcTest.
@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

    @Autowired
    // the autowire annotations, tells to the spring framework, that an existing bean of the instance of this class already exists
    // and automatically wire it to this object.
    private MockMvc mockMvc;

    //those annotations need to be implemented because, when we are running unit tests, the whole programme is not starting,
    // just a fraction of the program is only booting up. so, all the objects, resources needed are loaded.
    @MockBean
    private TeacherService teacherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTeacherUuid() throws Exception {
        UUID uuid = UUID.randomUUID();
        Teacher teacher = new Teacher(uuid, "teacher_name", LocalDate.parse("2022-10-01"), "male", "teacher@email.com");
        Mockito.when(teacherService.getTeacherUuid(teacher.getUuid())).thenReturn(teacher);
        mockMvc.perform(get("/api/teacher/" + teacher.getUuid())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json("{\"uuid\":\"" + uuid + "\",\"name\":\"" + "teacher_name" + "\", \"dob\":\"" + "2022-10-01" + "\", \"gender\": \"" + "male" + "\" ,\"email\":\"" + "teacher@email.com" + "\"}"));
        Mockito.verify(teacherService, Mockito.times(1)).getTeacherUuid(teacher.getUuid());
    }

    @Test
    void getTeacherEmail() throws Exception {
        UUID uuid = UUID.randomUUID();
        Teacher teacher = new Teacher(uuid, "teacher_name", LocalDate.parse("2022-10-01"), "male", "teacher@email.com");
        Mockito.when(teacherService.getTeacherEmail(teacher.getEmail())).thenReturn(Optional.of(teacher));
        mockMvc.perform(get("/api/teacher/email/teacher@email.com")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json("{\"uuid\":\"" + uuid + "\",\"name\":\"" + "teacher_name" + "\", \"dob\":\"" + "2022-10-01" + "\", \"gender\": \"" + "male" + "\" ,\"email\":\"" + "teacher@email.com" + "\"}"));
        Mockito.verify(teacherService, Mockito.times(1)).getTeacherEmail(teacher.getEmail());
    }
}
