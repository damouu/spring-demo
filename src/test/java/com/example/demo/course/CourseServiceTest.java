package com.example.demo.course;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void getCourse() {
        Course course = new Course(UUID.randomUUID(), "course_test", "campus_test", "university_test");
        Mockito.when(courseRepository.findByUuid(course.getUuid())).thenReturn(java.util.Optional.of(course));
        ResponseEntity<Course> responseEntity = courseService.getCourse(course.getUuid());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(responseEntity.getBody(), course);
        Mockito.verify(courseRepository, Mockito.times(1)).findByUuid(course.getUuid());
    }

    @Test
    void getCourses() {
    }

    @Test
    void postCourse() {
        Course course = new Course(null, "course_test", "campus_test", "university_test");
        var responseEntity = courseService.postCourse(course);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertTrue(responseEntity.getHeaders().containsKey("Location"));
        Assertions.assertEquals(responseEntity.getHeaders().get("Location"), List.of("http://localhost:8083/api/course/" + course.getUuid()));
        Assertions.assertEquals(course, responseEntity.getBody());
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
    }

    @Test
    void deleteCourse() {
        Course course = new Course(UUID.randomUUID(), "course_test", "campus_test", "university_test");
        Mockito.when(courseRepository.findByUuid(course.getUuid())).thenReturn(java.util.Optional.of(course));
        var responseEntity = this.courseService.deleteCourse(course.getUuid());
        Assertions.assertEquals(responseEntity.getStatusCode().value(), 204);
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).contains("course successfully deleted"));
        Mockito.verify(courseRepository, Mockito.times(1)).delete(course);
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
}