package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void allStudents() throws Exception {
        List<Student> studentList = new ArrayList<>();
        Student student = new Student(UUID.randomUUID(), "student", LocalDate.of(2000, Month.NOVEMBER, 1), "student@email.com");
        Student student1 = new Student(UUID.randomUUID(), "student1", LocalDate.of(2000, Month.NOVEMBER, 1), "student1@email.com");
        studentList.add(student);
        studentList.add(student1);
        Mockito.when(studentService.allStudents(0, 2)).thenReturn(studentList);
        mockMvc.perform(get("/api/student"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"name\":\"student\",\"dob\":\"2000-11-01\",\"age\":20,\"email\":\"student@email.com\"},{\"id\":2,\"name\":\"student1\",\"dob\":\"2000-11-01\",\"age\":20,\"email\":\"student1@email.com\"}]"));
        Mockito.verify(studentService, Mockito.times(1)).allStudents(0, 2);
    }

    @Test
    void findById() throws Exception {
        UUID uuid = UUID.randomUUID();
        Student student = new Student(uuid, "student", LocalDate.of(2000, Month.NOVEMBER, 1), "student@email.com");
        Mockito.when(studentService.getStudent(uuid)).thenReturn(student);
        mockMvc.perform(get("/api/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"student\",\"dob\":\"2000-11-01\",\"age\":20,\"email\":\"student@email.com\"}"));
        Mockito.verify(studentService, Mockito.times(1)).getStudent(uuid);
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