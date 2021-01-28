package com.example.demo.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course getCourse(Integer courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.orElseThrow();
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Response createCourse(Course course) {
        courseRepository.save(course);
        return Response.ok(course).status(201).location(URI.create("http://localhost:8080/api/course/" + course.getId())).build();
    }

    public Response removeCourse(Integer courseId) {
        if (courseRepository.findById(courseId).isPresent()) {
            Course course = courseRepository.findById(courseId).get();
            courseRepository.delete(course);
            return Response.ok(course).status(204).build();
        }
        return Response.noContent().status(404).build();
    }

    public Response updateCourse(Integer courseId, Course course) {
        if (courseRepository.findById(courseId).isPresent()) {
            Course optionalCourse = courseRepository.findById(courseId).get();
            optionalCourse.setName(course.getName());
            optionalCourse.setDepartment(course.getDepartment());
            courseRepository.save(optionalCourse);
            return Response.ok(course).status(204).location(URI.create("http://localhost:8080/api/course/" + course.getId())).build();
        }
        return Response.noContent().status(404).build();
    }
}
