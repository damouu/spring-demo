package com.example.demo.course;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    private StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public Course getCourse(UUID courseUuid) {
        Optional<Course> course = courseRepository.findByUuid(courseUuid);
        return course.orElseThrow();
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Response createCourse(Course course) {
        courseRepository.save(course);
        return Response.ok(course).status(201).contentLocation(URI.create("http://localhost:8080/api/course/" + course.getUuid())).build();
    }

    public Response removeCourse(UUID courseUuid) {
        if (courseRepository.findByUuid(courseUuid).isPresent()) {
            Course course = courseRepository.findByUuid(courseUuid).get();
            courseRepository.delete(course);
            return Response.ok(course).status(204).build();
        }
        return Response.noContent().status(404).build();
    }

    public Response updateCourse(UUID courseUuid, Course course) {
        if (courseRepository.findByUuid(courseUuid).isPresent()) {
            Course optionalCourse = courseRepository.findByUuid(courseUuid).get();
            optionalCourse.setName(course.getName());
            optionalCourse.setDepartment(course.getDepartment());
            courseRepository.save(optionalCourse);
            return Response.ok(course).status(204).location(URI.create("http://localhost:8080/api/course/" + course.getUuid())).build();
        }
        return Response.noContent().status(404).build();
    }

    public Response postStudentCourse(UUID courseUuid, UUID studentUuid) {
        Optional<Course> course = courseRepository.findByUuid(courseUuid);
        Optional<Student> student = studentRepository.findStudentByUuid(studentUuid);
        if (course.isPresent() && student.isPresent()) {
            if (!course.get().getStudents().contains(student.get())) {
                student.get().getCourses().add(course.get());
                this.studentRepository.save(student.get());
                return Response.ok(course.get()).status(201).build();
            }
        }
        return Response.noContent().build();
    }
}
