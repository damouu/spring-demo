package com.example.demo.course;

import com.example.demo.student.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class CourseServiceTest {

    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        courseService = new CourseService(courseRepository, studentRepository);
    }

    @Test
    void getCourse() {
        UUID uuid = UUID.randomUUID();
        Course course = new Course(1, uuid, "dede", "campus", "dede_univ");
        Mockito.when(courseService.getCourse(course.getUuid())).thenReturn(course);
        /*Mockito.when(courseRepository.findByUuid(course.getUuid())).thenReturn(Optional.of(course));
        Mockito.verify(courseRepository).findByUuid(courseArgumentCaptor.capture().getUuid());*/
//        Course course1 = courseService.getCourse(uuid);
//        Assertions.assertEquals(course1, course);
    }

    @Test
    void getCourses() {
        List<Course> courses = new ArrayList<Course>();
        Course course = new Course(1, UUID.randomUUID(), "dede", "campus", "dede_univ");
        courses.add(course);
        Mockito.when(courseService.getCourses()).thenReturn(courses);
        List<Course> courses1 = courseService.getCourses();
        Assertions.assertFalse(courses1.isEmpty());
    }

    @Test
    void createCourse() {
        Course course = new Course(1, UUID.randomUUID(), "dede", "campus", "dede_univ");
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        Mockito.when(courseService.createCourse(course)).thenReturn(Response.accepted().build());
    }

    @Test
    void removeCourse() {
    }

    @Test
    void updateCourse() {
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
    void getCourseDepartment() {
    }
}