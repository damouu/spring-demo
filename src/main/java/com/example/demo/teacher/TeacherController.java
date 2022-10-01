package com.example.demo.teacher;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Data
@Validated
@RestController
@RequestMapping("api/teacher")
public class TeacherController {

    // first, we do need the TeacherService bean, to be able to forward to the good method.
    private final TeacherService teacherService;

    @GetMapping(path = "/{teacherUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Teacher getTeacherUuid(@PathVariable("teacherUuid") UUID teacherUuid) {
        return teacherService.getTeacherUuid(teacherUuid);
    }

    @GetMapping(path = "/email/{teacherEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Teacher> getTeacherEmail(@PathVariable("teacherEmail") String teacherEmail) {
        return teacherService.getTeacherEmail(teacherEmail);
    }

    @DeleteMapping(path = "/{teacherUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteTeacher(@PathVariable("teacherUuid") UUID uuid) {
        return teacherService.deleteTeacher(uuid);
    }

}
