package com.example.demo.course;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public Course getCourse(UUID courseUuid) {
        return courseRepository.findByUuid(courseUuid).orElseThrow(() -> new IllegalStateException("course does not exist" + courseUuid));
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Response createCourse(Course course) {
        courseRepository.save(course);
        return Response.ok(course).status(201).contentLocation(URI.create("http://localhost:8080/api/course/" + course.getUuid())).build();
    }

    public Response removeCourse(UUID courseUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new IllegalStateException("course does not exist"));
        courseRepository.delete(course);
        return Response.ok(course).status(204).build();
    }

    public Response updateCourse(UUID courseUuid, Course course) {
        Course optionalCourse = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new IllegalStateException("course does not exist"));
        optionalCourse.setName(course.getName());
        optionalCourse.setcampus(course.getcampus());
        courseRepository.save(optionalCourse);
        return Response.ok(course).status(204).location(URI.create("http://localhost:8083/api/course/" + course.getUuid())).build();
    }

    public Response postStudentCourse(UUID courseUuid, UUID studentUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new IllegalStateException("course does not exist"));
        Student student = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new IllegalStateException("student does not exist"));
        if (course.getStudents().stream().anyMatch(student1 -> student1.getUuid().equals(student.getUuid()))) {
            return Response.noContent().status(406).build();
        } else {
            student.getCourses().add(course);
            this.studentRepository.save(student);
            return Response.ok(course).status(201).build();
        }
    }

    public Response deleteStudentCourse(UUID courseUuid, UUID studentUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new IllegalStateException("course does not exist"));
        Student student = studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new IllegalStateException("student does not exist"));
        if (course.getStudents().stream().anyMatch(student1 -> student1.getUuid().equals(student.getUuid()))) {
            student.getCourses().removeIf(course1 -> course1.getUuid().equals(course.getUuid()));
            this.studentRepository.save(student);
            return Response.status(204).build();
        }
        return Response.noContent().status(406).build();
    }

    public Response getStudentsCourse(UUID courseUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new IllegalStateException("course does not exist"));
        return Response.ok(course.getStudents()).status(200).build();
    }


    public Response getCourseSearchQueryParam(String university, String campus, String name) {
        if (!(university == null) && (campus == null) && (name == null)) {
            Optional<Collection<Course>> courses = courseRepository.findByUniversityContaining(university);
            return Response.ok(courses.get()).status(200).build();
        } else if (!(campus == null) && (university == null) && (name == null)) {
            Optional<Collection<Course>> courses = courseRepository.findByCampusContaining(campus);
            return Response.ok(courses.get()).status(200).build();
        } else if (!(name == null) && (university == null) && (campus == null)) {
            Optional<Collection<Course>> courses = courseRepository.findByNameContaining(name);
            return Response.ok(courses.get()).status(200).build();
        } else if (!(university == null) && !(campus == null) && !(name == null)) {
            return Response.ok().status(204).location(URI.create("https://www.w3schools.com/java/java_conditions.asp")).build();
        } else if (!(university == null) && !(campus == null) && (name == null)) {
            Optional<Collection<Course>> courses = courseRepository.findByUniversityContainingAndCampusContaining(university, campus);
            return Response.ok(courses.get()).status(200).build();
        } else if (!(university == null) && !(name == null) && (campus == null)) {
            Optional<Collection<Course>> courses = courseRepository.findByUniversityContainingAndNameContaining(university, name);
            return Response.ok(courses.get()).status(200).build();
        } else if (!(name == null) && !(campus == null) && (university == null)) {
            Optional<Collection<Course>> courses = courseRepository.findByNameContainingAndCampusContaining(name, campus);
            return Response.ok(courses.get()).status(200).build();
        }
        return Response.noContent().build();
    }
}
