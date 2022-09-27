package com.example.demo.teacher;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@Validated
@RestController
@RequestMapping("api/teacher")
public class TeacherController {
    
}
