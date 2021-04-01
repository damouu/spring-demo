package com.example.demo.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

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
    void getStudentsCourse() {
    }

    @Test
    void getCourseSearchQueryParam() {
    }
}