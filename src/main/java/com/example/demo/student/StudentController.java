package com.example.demo.student;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@Data
@Validated
@RestController
@RequestMapping("api/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Student> allStudents(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page, @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        return studentService.allStudents(page, size);
    }

    @GetMapping(path = "/{studentUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudent(@PathVariable("studentUuid") UUID studentUuid) {
        return studentService.getStudent(studentUuid);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postStudent(@Valid @RequestBody Student student) {
        return studentService.postStudent(student);
    }

    @DeleteMapping(path = "/{studentUuid}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentUuid") UUID studentUuid) {
        return studentService.deleteStudent(studentUuid);
    }

    @PutMapping(path = "/{studentUuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Student updateStudent(@PathVariable("studentUuid") UUID studentUuid, @RequestBody Student student) {
        return studentService.updateStudent(studentUuid, student);
    }
}
