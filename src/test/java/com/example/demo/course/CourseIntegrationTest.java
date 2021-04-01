package com.example.demo.course;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CourseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void getCourse() {
        Course course = new Course(UUID.randomUUID(), "course_test", "campus_test", "university_test");
        courseRepository.save(course);
        ResponseEntity<Course> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/course/" + course.getUuid(), Course.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType()).includes(MediaType.APPLICATION_JSON));
        Assertions.assertEquals(course.getUuid(), Objects.requireNonNull(responseEntity.getBody()).getUuid());
        Assertions.assertEquals(course.getName(), Objects.requireNonNull(responseEntity.getBody()).getName());
        Assertions.assertEquals(course.getCampus(), Objects.requireNonNull(responseEntity.getBody()).getCampus());
        Assertions.assertEquals(course.getUniversity(), Objects.requireNonNull(responseEntity.getBody()).getUniversity());
    }

    @Test
    void deleteCourse() {
        Course course = new Course(UUID.randomUUID(), "course_test", "campus_test", "university_test");
        courseRepository.save(course);
        var dede = courseRepository.findByUuid(course.getUuid());
        Assertions.assertTrue(dede.isPresent());
        restTemplate.delete("http://localhost:" + port + "/api/course/" + course.getUuid());
        var o = courseRepository.findByUuid(course.getUuid());
        Assertions.assertTrue(o.isEmpty());
    }

    @Test
    void postCourse() {
        Course course = new Course(null, "course_test", "campus_test", "university_test");
        var responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/api/course", course, Course.class);
        var courseRetrieved = courseRepository.findByUuid(responseEntity.getBody().getUuid());
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertTrue(responseEntity.getHeaders().containsKey("Location"));
        Assertions.assertEquals(course.getName(), Objects.requireNonNull(responseEntity.getBody()).getName());
        Assertions.assertEquals(course.getCampus(), responseEntity.getBody().getCampus());
        Assertions.assertEquals(course.getUniversity(), responseEntity.getBody().getUniversity());
        Assertions.assertNotNull(courseRetrieved.get());
    }

}