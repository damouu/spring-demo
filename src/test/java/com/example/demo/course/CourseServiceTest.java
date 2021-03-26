package com.example.demo.course;

import com.example.demo.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {


    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private StudentRepository studentRepository;


    @Test
    void getCourse() {
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