package com.example.demo.teacher;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
