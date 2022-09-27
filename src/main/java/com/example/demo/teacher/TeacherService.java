package com.example.demo.teacher;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class TeacherService {
    
    private final TeacherRepository teacherRepository;
}
