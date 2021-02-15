package com.example.demo.course;

import com.example.demo.student.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        /*UUID uuid = UUID.randomUUID();
        Course course = new Course(1, uuid, "dede", "campus", "dede_univ");
        Mockito.when(courseService.getCourse(course.getUuid())).thenReturn(Optional.of(course).get());
        Course course1 = courseService.getCourse(uuid);
        Assertions.assertEquals(course1, course);*/
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