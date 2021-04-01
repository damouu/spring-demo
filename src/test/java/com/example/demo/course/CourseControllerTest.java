package com.example.demo.course;

import com.example.demo.student_id_card.StudentIdCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCourses() {
    }

    @Test
    void getCourse() throws Exception {
        Course course = new Course(UUID.randomUUID(), "course_test", "campus_test", "university_test");
        Mockito.when(courseService.getCourse(course.getUuid())).thenReturn(ResponseEntity.ok(course));
        mockMvc.perform(get("/api/course/" + course.getUuid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"uuid\":\"" + course.getUuid() + "\",\"campus\":\"" + course.getCampus() + "\",\"university\":\"" + course.getUniversity() + "\"}"));
    }

    @Test
    void postCourse() throws Exception {
        Course course = new Course(null, "course_test", "campus_test", "university_test");
        Mockito.when(courseService.postCourse(course)).thenReturn(ResponseEntity.status(201).body(course));
        mockMvc.perform(post("/api/course")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteCourse() throws Exception {
        Course course = new Course(UUID.randomUUID(), "course_test", "campus_test", "university_test");
        Mockito.when(courseService.deleteCourse(course.getUuid())).thenReturn(ResponseEntity.status(204).body("course successfully deleted"));
        mockMvc.perform(delete("/api/course/" + course.getUuid()))
                .andExpect(status().is(204))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("course successfully deleted"));
    }

    @Test
    void updateCourse() throws Exception {
        Course course = new Course(UUID.randomUUID(), "course_test", "campus_test", "university_test");
        Mockito.when(courseService.updateCourse(course.getUuid(), course)).thenReturn(ResponseEntity.status(204).body(course));
        mockMvc.perform(put("/api/course/" + course.getUuid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(course)))
                .andExpect(status().is(200));
    }

    @Test
    void postStudentCourse() {
    }

    @Test
    void deleteStudentCourse() {
    }

    @Test
    void getStudentsCourse() throws Exception {
        Course course = new Course(UUID.randomUUID(), "course_test", "campus_test", "university_test");
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        StudentIdCard studentIdCard1 = new StudentIdCard(UUID.randomUUID());
        List<StudentIdCard> studentIdCardCollection = Arrays.asList(studentIdCard, studentIdCard1);
        Mockito.when(courseService.getStudentsCourse(course.getUuid())).thenReturn(ResponseEntity.ok(studentIdCardCollection));
        mockMvc.perform(get("/api/course/" + course.getUuid() + "/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"uuid\":\"" + studentIdCard.getUuid() + "\"},{\"uuid\":\"" + studentIdCard1.getUuid() + "\"}]"));
    }

    @Test
    void getCourseSearchQueryParam() {
    }
}