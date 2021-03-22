package com.example.demo.course;

import com.example.demo.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

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
        Course course = new Course(uuid, "dede", "campus", "dede_univ");
     /*   Mockito.when(courseService.getCourse(course.getUuid())).thenReturn(course);
        Mockito.when(courseRepository.findByUuid(course.getUuid())).thenReturn(Optional.of(course));
        Mockito.verify(courseRepository).findByUuid(courseArgumentCaptor.capture().getUuid());
        Course course1 = courseService.getCourse(uuid);
        Assertions.assertEquals(course1, course);*/
    }


    @Test
    void createCourse() {
        Course course = new Course(UUID.randomUUID(), "dede", "campus", "dede_univ");
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        Mockito.when(courseService.postCourse(course)).thenReturn(ResponseEntity.ok().build());
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