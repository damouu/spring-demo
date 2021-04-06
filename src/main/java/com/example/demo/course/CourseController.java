package com.example.demo.course;

import com.example.demo.student_id_card.StudentIdCard;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Validated
@RestController
@RequestMapping("api/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getCourses(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page, @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        return courseService.getCourses(page, size);
    }

    @GetMapping(path = "/{courseUuid}")
    public ResponseEntity<Course> getCourse(@PathVariable("courseUuid") UUID courseUuid) {
        return courseService.getCourse(courseUuid);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> postCourse(@Valid @RequestBody Course course) {
        return courseService.postCourse(course);
    }

    @DeleteMapping(path = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCourse(@PathVariable("courseUuid") UUID courseUuid) {
        return courseService.deleteCourse(courseUuid);
    }

    @PutMapping(path = "/{courseUuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCourse(@PathVariable("courseUuid") UUID courseUuid, @Valid @RequestBody Course course) {
        return courseService.updateCourse(courseUuid, course);
    }

    @PostMapping(path = "/{courseUuid}/student/{studentUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postStudentCourse(@PathVariable("courseUuid") UUID courseUuid, @PathVariable("studentUuid") UUID studentUuid) {
        return courseService.postStudentCourse(courseUuid, studentUuid);
    }

    @DeleteMapping(path = "/{courseUuid}/student/{studentUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteStudentCourse(@PathVariable("courseUuid") UUID courseUuid, @PathVariable("studentUuid") UUID studentUuid) {
        return courseService.deleteStudentCourse(courseUuid, studentUuid);
    }

    @GetMapping(path = "/{courseUuid}/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<StudentIdCard>> getStudentsCourse(@PathVariable("courseUuid") UUID courseUuid) {
        return courseService.getStudentsCourse(courseUuid);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCourseSearchQueryParam(@RequestParam(name = "university", required = false, defaultValue = "") String university, @RequestParam(name = "campus", required = false, defaultValue = "") String campus, @RequestParam(name = "name", required = false, defaultValue = "") String name) {
        return courseService.getCourseSearchQueryParam(university, campus, name);
    }
}
