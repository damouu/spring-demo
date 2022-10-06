package com.example.demo.course;

import com.example.demo.student_id_card.StudentIdCard;
import com.example.demo.student_id_card.StudentIdCardRepository;
import com.example.demo.teacher.Teacher;
import com.example.demo.teacher.TeacherRepository;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final TeacherRepository teacherRepository;

    private final StudentIdCardRepository studentIdCardRepository;

    public ResponseEntity<Course> getCourse(UUID courseUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new EntityNotFoundException("invalid course"));
        return ResponseEntity.ok(course);
    }

    public ResponseEntity<List<Course>> getCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> courses = courseRepository.findAll(pageable);
        return ResponseEntity.ok(courses.toList());
    }

    public ResponseEntity<Course> postCourse(Course course) {
        course.setUuid(UUID.randomUUID());
        courseRepository.save(course);
        return ResponseEntity.status(201).location(URI.create("http://localhost:8083/api/course/" + course.getUuid())).body(course);
    }

    public ResponseEntity<String> deleteCourse(UUID courseUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course does not exist"));
        courseRepository.delete(course);
        return ResponseEntity.status(204).body("course successfully deleted");
    }

    public ResponseEntity updateCourse(UUID courseUuid, Course course) {
        Course optionalCourse = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course does not exist"));
        optionalCourse.setName(course.getName());
        optionalCourse.setCampus(course.getCampus());
        optionalCourse.setUniversity(course.getUniversity());
        courseRepository.save(optionalCourse);
        return ResponseEntity.status(204).location(URI.create("http://localhost:8083/api/course/" + optionalCourse.getUuid())).build();
    }

    public ResponseEntity<String> postStudentCourse(UUID courseUuid, UUID studentCardUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course does not exist"));
        StudentIdCard studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentCardUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student card does not exist"));
        if (course.getStudentIdCards().stream().anyMatch(studentIdCard1 -> studentIdCard1.getUuid().equals(studentIdCard.getUuid()))) {
            return ResponseEntity.noContent().build();
        } else {
            studentIdCard.getCourses().add(course);
            course.getStudentIdCards().add(studentIdCard);
            this.studentIdCardRepository.save(studentIdCard);
            this.courseRepository.save(course);
            return ResponseEntity.status(201).body("student card" + " " + studentIdCard.getUuid() + " " + "added to the course" + " " + course.getUuid());
        }
    }

    public ResponseEntity deleteStudentCourse(UUID courseUuid, UUID studentCardUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course does not exist"));
        StudentIdCard studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentCardUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student card does not exist"));
        if (course.getStudentIdCards().stream().anyMatch(studentIdCard1 -> studentIdCard1.getUuid().equals(studentIdCard.getUuid()))) {
            studentIdCard.getCourses().removeIf(course1 -> course1.getUuid().equals(course.getUuid()));
            this.studentIdCardRepository.save(studentIdCard);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(204).build();
    }

    public ResponseEntity<Collection<StudentIdCard>> getStudentsCourse(UUID courseUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course does not exist"));
        return ResponseEntity.ok(course.getStudentIdCards());
    }

    public ResponseEntity<String> postTeacherCourse(UUID courseUuid, UUID teachersUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error Course UUID"));
        Teacher teacher = teacherRepository.findTeacherByUuid(teachersUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error Teacher UUID"));
        if (course.getTeacher() == null) {
            course.setTeacher(teacher);
            this.courseRepository.save(course);
            return ResponseEntity.created(URI.create("http://localhost:8083/api/course/" + course.getUuid() + "/teacher/")).contentType(MediaType.APPLICATION_JSON).body("teacher" + " " + teacher.getUuid() + " " + "is now in charge of the course" + " " + course.getUuid());
        } else {
            return ResponseEntity.badRequest().body("This course is already assigned to a teacher");
        }
    }

    public ResponseEntity<String> deleteTeacherCourse(UUID courseUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error Course UUID"));
        if (course.getTeacher() != null) {
            course.setTeacher(null);
            this.courseRepository.save(course);
            return ResponseEntity.status(204).body("teacher successfully removed from his assigned course :" + " " + course.getUuid());
        } else {
            return ResponseEntity.badRequest().body("This course does not have any teacher assigned to");
        }
    }

    public ResponseEntity<String> getTeacherCourse(UUID courseUuid) {
        Course course = courseRepository.findByUuid(courseUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error Course UUID"));
        if (course.getTeacher() != null) {
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("course :" + " " + course.getName() + " " + " teacher:" + " " + course.getTeacher().getName());
        } else {
            return ResponseEntity.badRequest().body("this course does not have any teacher assigned to it");
        }
    }

    public ResponseEntity<?> getCourseSearchQueryParam(String university, String campus, String name) {
//        if (!(university == null) && (campus == null) && (name == null)) {
//            Optional<Collection<Course>> courses = courseRepository.findByUniversityContaining(university);
//            return ResponseEntity.ok(courses.get()).status(200).build();
//        } else if (!(campus == null) && (university == null) && (name == null)) {
//            Optional<Collection<Course>> courses = courseRepository.findByCampusContaining(campus);
//            return ResponseEntity.ok(courses.get()).status(200).build();
//        } else if (!(name == null) && (university == null) && (campus == null)) {
//            Optional<Collection<Course>> courses = courseRepository.findByNameContaining(name);
//            return ResponseEntity.ok(courses.get()).status(200).build();
//        } else if (!(university == null) && !(campus == null) && !(name == null)) {
//            return ResponseEntity.ok(courses)
//        } else if (!(university == null) && !(campus == null) && (name == null)) {
//            Optional<Collection<Course>> courses = courseRepository.findByUniversityContainingAndCampusContaining(university, campus);
//            return ResponseEntity.ok(courses.get()).status(200).build();
//        } else if (!(university == null) && !(name == null) && (campus == null)) {
//            Optional<Collection<Course>> courses = courseRepository.findByUniversityContainingAndNameContaining(university, name);
//            return ResponseEntity.status(200).body(courses.get());
//        } else if (!(name == null) && !(campus == null) && (university == null)) {
//            Optional<Collection<Course>> courses = courseRepository.findByNameContainingAndCampusContaining(name, campus);
//            return ResponseEntity.status(200).body((courses.get()));
//        }
        return ResponseEntity.notFound().build();
    }
}
