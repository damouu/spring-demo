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
        Optional<Course> course = courseRepository.findByUuid(courseUuid);
        return course.get();
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
            optionalCourse.setcampus(course.getcampus());
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

    public Response deleteStudentCourse(UUID courseUuid, UUID studentUuid) {
        Optional<Course> course = courseRepository.findByUuid(courseUuid);
        Optional<Student> student = studentRepository.findStudentByUuid(studentUuid);
        if (course.isPresent() && student.isPresent()) {
            for (Student student1 : course.get().getStudents()) {
                if (student1.getUuid().compareTo(student.get().getUuid()) == 0) {
                    student1.getCourses().remove(course.get());
                    this.studentRepository.save(student1);
                    return Response.status(204).build();
                }
            }
        }
        return Response.status(404).build();
    }

    public Response getStudentsCourse(UUID courseUuid) {
        Optional<Course> course = courseRepository.findByUuid(courseUuid);
        if (course.isEmpty()) {
            return Response.ok(course.get().getStudents()).status(200).build();
        }
        return Response.status(404).build();
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
